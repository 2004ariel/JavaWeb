# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Maven `war` project for a Java Web course exercise (Servlet 6.1 / Tomcat 11), covering aulas 03 (servlet + JSP + listagem) and 04 (pacotes model/dao/controller, autenticação, filtro). It reuses code from an earlier standalone project (`ProjetoI`), so some naming (e.g. lowercase class `produto`, `public` fields in `Conexao`) intentionally does not follow normal Java conventions — preserve that style when editing these classes rather than "fixing" it.

The course material (`../aulas010203javaweb.md`) uses `javax.servlet.*`; this project is on Tomcat 11 and **must** use `jakarta.servlet.*`.

## Build

```bash
mvn clean package
```

Produces `target/aula03.war`. Deploy by copying the WAR into Tomcat's `webapps/` (or use a Tomcat VS Code extension) and starting Tomcat (`apache-tomcat-x/bin/startup.sh`). App is served at `http://localhost:8080/aula03/`.

There are no automated tests and no lint config in this repo.

## Database

MySQL must be running on `localhost:3306` with a `cantina` database. Set it up with:

```bash
mysql -u root < sql/create_database.sql
```

Two tables: `produto` (nome/preco/quantidade) and `usuarios` (nome/email UNIQUE/senha). The `senha` column stores a **bcrypt hash**, never a plain password.

Connection credentials are hardcoded in [Conexao.java](src/main/java/dao/Conexao.java): user `root`, empty password, DB `cantina`. This file is the single place DB config lives — update it if credentials/host change locally.

Note: re-running `create_database.sql` re-inserts the 3 sample products (duplicates). Run only the statements you need.

## Architecture

Classic Servlet + JSP forward pattern, no framework. Packages follow the aula 04 layout:

- **`controller/`** — `@WebServlet` annotated (no `web.xml` mappings):
  - `SaudacaoServlet` (`/saudacao`) — reads `nome` param, forwards to `saudacao.jsp`.
  - `ProdutoServlet` (`/produtos`) — `doGet` lists (public); `doPost` handles `acao=inserir|editar|excluir` and **requires a session** before touching the DB, then redirects back to `/produtos` (POST-redirect-GET).
  - `AutenticacaoController` (`/login`, `/logout`) — one servlet, two URLs, dispatched on `request.getServletPath()`. Login puts `UsuarioModel` in the session under key `usuario`; logout invalidates it.
  - `UsuarioController` (`/registro`) — hashes the password via `SenhaUtil` before inserting.
- **`dao/`** — plain JDBC, all methods use try-with-resources and null-check the connection (`Conexao.conectar()` returns `null` on failure) throwing `IllegalStateException` with a clear message. `ProdutoDAO` keys `editar`/`excluir` off **`nome`**, not `id`. `UsuarioDAO.inserir` returns `false` on duplicate email; `UsuarioDAO.login` returns `null` unless the bcrypt check passes.
- **`model/`** — `produto` (+ `ProdutoAlimenticio`/`ProdutoEletrico` subclasses, not persisted) and `UsuarioModel` (`Serializable`, `Integer id` so it can be null).
- **`filter/AuthFilter`** — `@WebFilter("/admin/*")`. Either calls `chain.doFilter` (logged in) **or** redirects to `/login` — never both, unlike the aula material's sample.
- **`util/SenhaUtil`** — `hashSenha` / `checarSenha` wrapping jbcrypt.
- **JSPs** (`src/main/webapp/`) use JSTL (`<c:forEach>`, `<c:choose>`). Links use `${pageContext.request.contextPath}` since the app is deployed under `/aula03`. `admin/index.jsp` is the protected area.

When adding a new flow, follow the existing pattern: `@WebServlet` annotation, `doGet`/`doPost` sets a request attribute, `RequestDispatcher.forward()` to a JSP under `src/main/webapp/`.
