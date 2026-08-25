<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Área restrita</title>
</head>
<body>
    <h1>Área restrita</h1>

    <p>Olá, ${sessionScope.usuario.nome}! Você está logado.</p>

    <p>Esta página só abre para quem passou pelo login (o AuthFilter cuida disso).</p>

    <ul>
        <li><a href="${pageContext.request.contextPath}/produtos">Gerenciar produtos</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Sair (logout)</a></li>
    </ul>
</body>
</html>
