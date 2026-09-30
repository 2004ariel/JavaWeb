# Aulas 05–08 — Spring Boot: Imóveis

Spring Boot 4.1.1, Java 17, Spring Data JPA e MySQL (banco `restaurante`, usuário `root` sem senha — ver `src/main/resources/application.properties`).

**Rodar:** com o MySQL ativo (o `dump.sql` recria as tabelas com dados de teste), execute `mvn spring-boot:run` nesta pasta. A API sobe em `http://localhost:8080`.

**Endpoints (CRUD):** `/bairros` (+ `/bairros/bairros-page`), `/tiposimoveis` (+ `/tiposimoveis/tipos-page`), `/imoveis`.

**Atenção:** `GET /bairros` (e `GET /imoveis/{id}`) gera uma referência cíclica **de propósito** (Bairro → imóveis → Bairro …); o JSON sai truncado. Explicação e correções possíveis em [`docs/referencia-ciclica.md`](docs/referencia-ciclica.md).
