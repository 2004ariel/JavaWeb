# Respostas – Autenticação e Autorização com JWT

## a) Autenticação vs. autorização

- **Autenticação**: provar **quem você é**. Resulta em 401 (Unauthorized) quando falha.
  Exemplo: `POST /public/login` com `{"email":"user@jwt.com","password":"user123"}`. Se a senha bate com o hash bcrypt, o servidor devolve um token. Com senha errada a resposta é **401**.
- **Autorização**: decidir **o que você pode fazer**, já sabendo quem você é. Resulta em 403 (Forbidden) quando falha.
  Exemplo: o USER está autenticado (token válido), mas `POST /imoveis` exige role ADMIN, então a resposta é **403**. Já o `PATCH /imoveis/{id}/status` aceita qualquer autenticado e devolve **204**.

## b) Stateful vs. stateless

- **Stateful**: o servidor **guarda** o estado do login. Exemplo: a aula 04 (servlets) põe o `UsuarioModel` na `HttpSession`, e o navegador manda o cookie `JSESSIONID`. Se o servidor reiniciar ou houver várias instâncias, a sessão se perde ou precisa ser compartilhada.
- **Stateless**: o servidor **não guarda nada** entre requisições. Cada uma traz tudo o que é preciso para se identificar. Exemplo: este projeto usa `SessionCreationPolicy.STATELESS`, e toda requisição protegida manda `Authorization: Bearer <jwt>`. O `JwtFilter` valida a assinatura e lê `sub` e `role` do próprio token, sem consultar sessão. Contrapartida: o token não pode ser "revogado" antes do `exp`, a menos que se crie uma blacklist.

## c) Semântica e sintaxe do JWT

**Sintaxe**: são três partes em **Base64URL** separadas por ponto: `header.payload.signature`.
Base64URL é o Base64 com `+`→`-`, `/`→`_` e sem o `=` final, para poder trafegar em URL e header.

```
eyJhbGciOiJIUzI1NiJ9                                   ← header
.eyJzdWIiOiJ1c2VyQGp3dC5jb20iLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc5MzE0MDEwMiwiZXhwIjoxNzkzMTQzNzAyfQ  ← payload
.r8N1N3v6-24QG2zHzdHgzbTRmNeHNpFUXW...                 ← signature
```

Decodificando:

| Parte | Conteúdo |
|---|---|
| header | `{"alg":"HS256"}`: algoritmo da assinatura |
| payload | `{"sub":"user@jwt.com","role":"USER","iat":1793140102,"exp":1793143702}` |
| signature | `HMAC-SHA256(base64url(header) + "." + base64url(payload), secret)` |

**Semântica (claims)**: são as afirmações sobre o usuário que o payload carrega.
- Registradas (RFC 7519): `sub` (sujeito, aqui o email), `iat` (emitido em), `exp` (expira em, aqui 1h depois).
- Privadas: `role` (ADMIN/USER), usada pelo Spring Security em `hasRole("ADMIN")`.

**Atenção**: o payload é só **codificado**, não criptografado. Qualquer um lê com `base64 -d`, então nunca coloque a senha nele. O que impede a fraude é a **assinatura**: no teste, trocar `"role":"USER"` por `"role":"ADMIN"` no payload e manter a assinatura antiga faz o servidor responder **401**, porque o HMAC deixa de bater.
