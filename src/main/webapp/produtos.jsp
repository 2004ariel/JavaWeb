<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Listagem de Produtos</title>
</head>
<body>
    <h1>Produtos (banco: cantina)</h1>

    <c:set var="logado" value="${not empty sessionScope.usuario}" />

    <c:choose>
        <c:when test="${empty produtos}">
            <p>Nenhum produto encontrado.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="6" cellspacing="0">
                <tr>
                    <th>Nome</th>
                    <th>Preço</th>
                    <th>Quantidade</th>
                    <c:if test="${logado}"><th>Ações</th></c:if>
                </tr>
                <c:forEach var="p" items="${produtos}">
                    <tr>
                        <td>${p.nome}</td>
                        <td>R$ ${p.preco}</td>
                        <td>${p.quantidade}</td>
                        <c:if test="${logado}">
                            <td>
                                <%-- editar: manda nome (chave), preco e quantidade novos --%>
                                <form action="${pageContext.request.contextPath}/produtos" method="post"
                                      style="display: inline;">
                                    <input type="hidden" name="acao" value="editar">
                                    <input type="hidden" name="nome" value="${p.nome}">
                                    <input type="number" name="preco" value="${p.preco}" step="0.01" min="0" required>
                                    <input type="number" name="quantidade" value="${p.quantidade}" min="0" required>
                                    <button type="submit">Salvar</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/produtos" method="post"
                                      style="display: inline;">
                                    <input type="hidden" name="acao" value="excluir">
                                    <input type="hidden" name="nome" value="${p.nome}">
                                    <button type="submit">Excluir</button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${logado}">
            <h2>Novo produto</h2>
            <form action="${pageContext.request.contextPath}/produtos" method="post">
                <input type="hidden" name="acao" value="inserir">
                <input type="text" name="nome" placeholder="Nome" required>
                <input type="number" name="preco" placeholder="Preço" step="0.01" min="0" required>
                <input type="number" name="quantidade" placeholder="Qtd" min="0" required>
                <button type="submit">Inserir</button>
            </form>
        </c:when>
        <c:otherwise>
            <p><a href="${pageContext.request.contextPath}/login">Faça login</a> para inserir, editar ou excluir.</p>
        </c:otherwise>
    </c:choose>

    <p><a href="${pageContext.request.contextPath}/">Voltar</a></p>
</body>
</html>
