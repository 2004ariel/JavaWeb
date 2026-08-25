<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Java Web - Aulas 01 a 04</title>
</head>
<body>
    <h1>Java Web - Aulas 01 a 04</h1>

    <h2>Enviar mensagem para o servlet</h2>
    <form action="saudacao" method="post">
        <label for="nome">Seu nome:</label>
        <input type="text" id="nome" name="nome" required>
        <button type="submit">Enviar</button>
    </form>

    <p><a href="produtos">Ver listagem de produtos</a></p>

    <h2>Área restrita (aula 04)</h2>
    <ul>
        <li><a href="login">Login</a></li>
        <li><a href="registro">Criar conta</a></li>
        <li><a href="admin/">Área restrita</a></li>
    </ul>
</body>
</html>
