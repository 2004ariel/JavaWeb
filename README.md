# Java Web — Aulas 01 a 08

| Pasta | Aulas | Conteúdo |
|---|---|---|
| raiz (este README) | 01–04 | Servlet/JSP + JDBC, Tomcat 11, banco `cantina` |
| [`aula05-08-spring-imoveis/`](aula05-08-spring-imoveis/) | 05–08 | Spring Boot 4.1.1 + Spring Data JPA, API REST de imóveis (`/bairros`, `/tiposimoveis`, `/imoveis`), banco `restaurante` — ver o [README próprio](aula05-08-spring-imoveis/README.md) |

## Aulas 01 a 04 (raiz)

Projeto Maven `war` (Servlet 6.1 / Tomcat 11) da disciplina de Java Web, reaproveitando o código real do `ProjetoI` (aulas 1 e 2) e implementando as atividades das aulas 03 e 04.

## O que tem implementado

**Aula 03** — setup Maven + Tomcat, servlet + JSP e listagem de produtos:
- `index.jsp` → formulário que envia `nome` para o servlet.
- `SaudacaoServlet` (`/saudacao`) → recebe a mensagem e encaminha para `saudacao.jsp` com a saudação.
- `ProdutoServlet` (`/produtos`, `doGet`) → usa `ProdutoDAO.listarProdutos()` e encaminha para `produtos.jsp`, que lista a tabela.

**Aula 04** — reorganização em pacotes, autenticação e CRUD completo na web:
- Pacotes `model` / `dao` / `controller` / `filter` / `util` (antes eram `principal` / `servlets`).
- Cadastro (`/registro`), login e logout (`/login`, `/logout`) via `UsuarioController` e `AutenticacaoController`, com senha criptografada (bcrypt, `SenhaUtil`).
- `AuthFilter` protege `/admin/*` — só entra quem está logado.
- `ProdutoServlet` (`doPost`) agora expõe inserir/editar/excluir (já existiam no `ProdutoDAO` desde a aula 2) direto pelos formulários de `produtos.jsp`, restrito a quem está logado. A listagem continua pública.

## Pré-requisitos

- JDK (confirme com `java -version`; ajuste `maven.compiler.release` no `pom.xml` se necessário)
- Maven
- Tomcat 11 (Servlet API 6.1)
- MySQL rodando em `localhost:3306`, banco `cantina`

## Banco de dados

Rodar o script `sql/create_database.sql` no MySQL (cria o banco `cantina`, as tabelas `produto` e `usuarios`, e insere 3 produtos de exemplo — rodar de novo duplica esses 3 registros, então numa segunda vez rode só os `CREATE TABLE` se precisar).

Credenciais usadas em `Conexao.java`: usuário `root`, senha vazia (`""`).

## Build e execução

```bash
mvn clean package
```

Isso gera `target/aula03.war`. Copie o `.war` para a pasta `webapps/` do Tomcat (ou use a extensão *Community Server Connector* / *Tomcat for Java* do VS Code) e inicie o servidor:

```bash
# dentro de apache-tomcat-x/bin
./startup.sh      # ou startup.bat no Windows
```

Acesse: `http://localhost:8080/aula03/`

## Dependências no pom.xml

- `mysql-connector-j` — driver JDBC para conectar no MySQL.
- `jakarta.servlet-api` — API de servlets do Tomcat 11 (`provided`: já vem com o servidor).
- JSTL (`jakarta.servlet.jsp.jstl`) — tags `<c:forEach>`/`<c:if>` usadas nas JSPs.
- `jbcrypt` — usado por `SenhaUtil` para criptografar e verificar senha (aula 04).

Se o build der erro de versão de Java, ajuste `maven.compiler.release` no `pom.xml` para a versão mostrada por `java -version`.
