<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>

    <c:if test="${param.erro == '1'}">
        <p style="color: red;">Email ou senha inválidos.</p>
    </c:if>
    <c:if test="${param.cadastrado == '1'}">
        <p style="color: green;">Cadastro realizado! Faça o login.</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <p>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </p>
        <p>
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha" required>
        </p>
        <button type="submit">Entrar</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/registro">Criar uma conta</a></p>
    <p><a href="${pageContext.request.contextPath}/">Voltar</a></p>
</body>
</html>
