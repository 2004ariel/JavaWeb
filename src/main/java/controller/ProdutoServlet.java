package controller;

import java.io.IOException;
import java.util.List;

import dao.ProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.produto;

/**
 * Lista os produtos (aula 03) e agora também expõe inserir/editar/excluir
 * do ProdutoDAO na web (aula 04).
 *
 * A listagem (doGet) é pública. As ações que mudam o banco (doPost) só
 * valem para quem está logado.
 */
@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdutoDAO dao = new ProdutoDAO();
        List<produto> produtos = dao.listarProdutos();

        request.setAttribute("produtos", produtos);
        request.getRequestDispatcher("/produtos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        boolean estaLogado = (session != null && session.getAttribute("usuario") != null);

        if (!estaLogado) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String acao = request.getParameter("acao");
        ProdutoDAO dao = new ProdutoDAO();

        if ("excluir".equals(acao)) {
            dao.excluir(request.getParameter("nome"));

        } else if ("editar".equals(acao) || "inserir".equals(acao)) {
            produto p = new produto();
            p.setNome(request.getParameter("nome"));
            p.setPreco(paraDouble(request.getParameter("preco")));
            p.setQuantidade(paraInt(request.getParameter("quantidade")));

            if ("editar".equals(acao)) {
                dao.editar(p);
            } else {
                dao.salvar(p);
            }
        }

        // volta para a listagem; o redirect evita reenviar o formulário no F5
        response.sendRedirect(request.getContextPath() + "/produtos");
    }

    private double paraDouble(String valor) {
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    private int paraInt(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }
}
