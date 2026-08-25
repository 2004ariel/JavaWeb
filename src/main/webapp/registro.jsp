<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Criar conta</title>
</head>
<body>
    <h1>Criar conta</h1>

    <c:if test="${not empty erro}">
        <p style="color: red;">${erro}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/registro" method="post">
        <p>
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required>
        </p>
        <p>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </p>
        <p>
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha" required>
        </p>
        <button type="submit">Cadastrar</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/login">Já tenho conta</a></p>
</body>
</html>
