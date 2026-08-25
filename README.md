# Aula 03 — Java Web (parte 1)

Projeto Maven `war` (Servlet 6.1 / Tomcat 11) implementando a atividade da aula 03, reaproveitando o código real do `ProjetoI` (aulas 1 e 2, feito no Mac):
- `index.jsp` → formulário que envia `nome` para o servlet.
- `SaudacaoServlet` (`/saudacao`) → recebe a mensagem e encaminha para `saudacao.jsp` com a saudação.
- `ProdutoServlet` (`/produtos`) → usa `ProdutoDAO.listarProdutos()` (mesmo DAO do `ProjetoI`, com `editar`/`excluir`/`inserirProduto` já prontos) e encaminha para `produtos.jsp`, que lista a tabela.
- Classes `Conexao`, `produto`, `ProdutoAlimenticio`, `ProdutoEletrico` e `ProdutoDAO` são cópias fiéis do `ProjetoI` (mesmos nomes, incluindo `produto` minúsculo e campos `public` em `Conexao`).

## Pré-requisitos

- JDK (mesma versão que você usa no `ProjetoI` — confirme com `java -version`; ajuste `maven.compiler.release` no `pom.xml` se necessário)
- Maven
- Tomcat 11 (Servlet API 6.1)
- MySQL rodando em `localhost:3306`, banco `cantina`

## Banco de dados

Rodar o script `sql/create_database.sql` no MySQL (cria o banco `cantina`, a tabela `produto` e insere 3 registros de exemplo — schema inferido do DAO, já que não achei script de criação no `ProjetoI` original).

Credenciais usadas em `Conexao.java`: usuário `root`, senha vazia (`""`) — mesmas do `ProjetoI`.

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

## Sobre as versões no pom.xml

- `mysql-connector-j:26.7.0` — mantive esse número porque você já tem `lib/mysql-connector-j-26.7.0.jar` de fato no `ProjetoI`, confirmando que essa versão existe. Numa resposta anterior eu tinha assumido (errado) que era um erro de digitação, baseado no meu conhecimento com corte em maio/2025.
- `maven.compiler.release=21` — não tenho como confirmar aqui se você tem JDK 26 instalado no Mac (o material da aula cita release 26). Deixei 21 como valor testado neste sandbox; ajuste para o que `java -version` mostrar na sua máquina.
- JSTL (`jakarta.servlet.jsp.jstl`) — adicionei essa dependência, necessária para as tags `<c:forEach>`/`<c:if>` do `produtos.jsp`, que não estava no `pom.xml` original da aula.

## O que já está pronto (herdado do ProjetoI)

Os 3 exercícios da aula 2 já estão implementados no `ProdutoDAO`: `editar`, `excluir`, `inserirProduto` (via `Scanner`, usado no `Main.java` standalone). O servlet web só expõe a listagem (`/produtos`), que é o que a atividade da aula 03 pede — se quiser, dá pra expor editar/excluir também via formulário web depois.
