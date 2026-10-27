#!/bin/bash
# Testa autenticação/autorização JWT. Requer a app rodando, curl e jq.
# Uso: ./testar-jwt.sh [url]   (padrão http://localhost:8080)

B=${1:-http://localhost:8080}
J='Content-Type: application/json'
FALHAS=0

# check "descrição" esperado recebido
check() {
    if [ "$2" = "$3" ]; then echo "OK      $1 (esperado $2, recebido $3)"
    else echo "FALHOU  $1 (esperado $2, recebido $3)"; FALHAS=$((FALHAS + 1)); fi
}

# status() faz a requisição e devolve só o código HTTP
status() { curl -s -o /dev/null -w '%{http_code}' "$@"; }

# 1. register (409 = já existe, tudo bem)
for u in "admin@t.com ADMIN" "user@t.com USER"; do
    set -- $u
    status -X POST $B/public/register -H "$J" \
        -d "{\"nome\":\"$2 teste\",\"email\":\"$1\",\"password\":\"1234\",\"role\":\"$2\"}" > /dev/null
done

# 2. login
login() { curl -s -X POST $B/public/login -H "$J" -d "{\"email\":\"$1\",\"password\":\"1234\"}" | jq -r '.token // empty'; }
ADMIN=$(login admin@t.com)
USER=$(login user@t.com)
[ -z "$ADMIN" ] || [ -z "$USER" ] && { echo "FALHOU  login (sem token) - a app está rodando?"; exit 1; }
echo "OK      login ADMIN e USER (tokens recebidos)"

# 3. casos
check "GET /users sem token"        401 $(status $B/users)
check "GET /users com ADMIN"        200 $(status $B/users -H "Authorization: Bearer $ADMIN")
check "GET /users com USER"         403 $(status $B/users -H "Authorization: Bearer $USER")
check "GET /imoveis sem token"      200 $(status $B/imoveis)

IMOVEL='{
  "titulo": "Casa de teste JWT",
  "descricao": "Criada pelo testar-jwt.sh",
  "precoVenda": 450000.00,
  "finalidade": "VENDA",
  "status": "DISPONIVEL",
  "dormitorios": 3,
  "banheiros": 2,
  "garagem": 1,
  "areaTotal": 300.50,
  "areaConstruida": 180.75,
  "endereco": "Rua das Flores",
  "numero": "100",
  "cep": "98280-000",
  "destaque": false,
  "tipoImovel": { "id": 1 },
  "bairro": { "id": 1 }
}'
check "POST /imoveis com USER"      403 $(status -X POST $B/imoveis -H "$J" -H "Authorization: Bearer $USER" -d "$IMOVEL")

# POST com ADMIN: pega status e id do header Location
RESP=$(curl -s -o /dev/null -D - -X POST $B/imoveis -H "$J" -H "Authorization: Bearer $ADMIN" -d "$IMOVEL")
check "POST /imoveis com ADMIN"     201 $(head -1 <<< "$RESP" | awk '{print $2}')
ID=$(grep -i '^location:' <<< "$RESP" | sed 's#.*/##' | tr -d '\r')

check "PATCH /imoveis/$ID/status com USER" 204 $(status -X PATCH $B/imoveis/$ID/status -H "$J" \
    -H "Authorization: Bearer $USER" -d '{"status":"VENDIDO"}')

# token adulterado: troca role USER -> ADMIN no payload e mantém a assinatura original
IFS=. read -r H P S <<< "$USER"
P=$(tr '_-' '/+' <<< "$P"); while [ $(( ${#P} % 4 )) -ne 0 ]; do P="$P="; done
FAKE=$(base64 -d <<< "$P" | jq -c '.role = "ADMIN"' | base64 | tr '+/' '-_' | tr -d '=\n')
check "GET /users com token adulterado" 401 $(status $B/users -H "Authorization: Bearer $H.$FAKE.$S")

echo
[ $FALHAS -eq 0 ] && echo "Todos os casos passaram." || echo "$FALHAS caso(s) falharam."
exit $FALHAS
