# Aulas 05–11 — Spring Boot: Imóveis

Spring Boot 4.1.1, Java 17, Spring Data JPA e MySQL (banco `restaurante`, usuário `root` sem senha — ver `src/main/resources/application.properties`).

**Rodar:** com o MySQL ativo (o `dump.sql` recria as tabelas com dados de teste), execute `mvn spring-boot:run` nesta pasta. A API sobe em `http://localhost:8080`.

**Endpoints (CRUD):** `/bairros` (+ `/bairros/bairros-page`), `/tiposimoveis` (+ `/tiposimoveis/tipos-page`), `/imoveis`, `/users`. Também: `POST /fotos-imoveis/upload` (aula 09), `/email` e `POST /imoveis/sabermais/{id}` (aula 10, credenciais do Mailgun em `mail.properties`, fora do git) e `/public/register` + `/public/login` (aula 11).

**Atenção:** `GET /bairros` (e `GET /imoveis/{id}`) gera uma referência cíclica **de propósito** (Bairro → imóveis → Bairro …); o JSON sai truncado. Explicação e correções possíveis em [`docs/referencia-ciclica.md`](docs/referencia-ciclica.md).

## Autenticação JWT (aula 11)

| Quem | Rotas |
|---|---|
| Público (sem token) | `/public/register`, `/public/login`, `GET` em `/imoveis`, `/bairros`, `/tiposimoveis`, `POST /imoveis/sabermais/{id}` |
| Qualquer autenticado | `PATCH /imoveis/{id}/status` |
| Só ADMIN | `POST`/`PUT`/`DELETE` em `/imoveis`, `/bairros`, `/tiposimoveis`; tudo em `/users`, `/fotos-imoveis`, `/email` |

- **401** = sem token, token inválido ou expirado. **403** = token válido, mas a role não tem permissão (ex.: USER fazendo `POST /imoveis`).
- Token HS256, claims `sub` (email), `role` e `exp` (1h). Senhas com bcrypt.
- A chave vem de `JWT_SECRET` (mínimo 32 caracteres); sem ela usa o default de dev do `application.properties`. Em produção: `JWT_SECRET=... mvn spring-boot:run`.
- Rode o `migration_jwt.sql` uma vez (adiciona `USER` ao ENUM `tipo`). Usuários antigos com senha em texto puro não logam; recrie via `/public/register`.
- Respostas teóricas em [`RESPOSTAS.md`](RESPOSTAS.md).

## Como testar

Fluxo: **login → token → header `Authorization: Bearer <token>`**.

```bash
curl -X POST localhost:8080/public/register -H 'Content-Type: application/json' \
     -d '{"nome":"Admin","email":"admin@t.com","password":"1234","role":"ADMIN"}'
TOKEN=$(curl -s -X POST localhost:8080/public/login -H 'Content-Type: application/json' \
     -d '{"email":"admin@t.com","password":"1234"}' | jq -r .token)
curl localhost:8080/users -H "Authorization: Bearer $TOKEN"
```

- **`./testar-jwt.sh`** (precisa de `curl` e `jq`, com a app no ar): cria `admin@t.com`/`user@t.com` (senha `1234`) e imprime OK/FALHOU para cada caso de 401/403/200. Cada execução cria um imóvel de teste.
- **`jwt.insomnia.json`**: no Insomnia, *Import → From File*. Rode o login e cole o token na variável de ambiente `token`.
