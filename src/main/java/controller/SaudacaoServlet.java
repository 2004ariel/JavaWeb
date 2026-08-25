package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Recebe a mensagem enviada pelo formulário em index.jsp,
 * monta a saudação e encaminha (forward) para saudacao.jsp.
 */
@WebServlet("/saudacao")
public class SaudacaoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        if (nome == null || nome.isBlank()) {
            nome = "visitante";
        }

        String saudacao = "Olá, " + nome + "! Seja bem-vindo(a).";

        request.setAttribute("saudacao", saudacao);
        request.getRequestDispatcher("/saudacao.jsp").forward(request, response);
    }
}
