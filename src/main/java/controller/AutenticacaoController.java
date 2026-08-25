package controller;

import java.io.IOException;

import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UsuarioModel;

/**
 * Cuida do login e do logout (aula 04).
 *
 * Um mesmo servlet atende duas URLs; getServletPath() diz qual delas foi
 * chamada (é o "acao" do material da aula).
 */
@WebServlet({ "/login", "/logout" })
public class AutenticacaoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getServletPath();

        if (acao.equals("/logout")) {
            // getSession(false) = pega a sessão existente, sem criar uma nova
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // apaga o usuário guardado na sessão
            }
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getServletPath();

        if (acao.equals("/login")) {
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            UsuarioModel usuario = UsuarioDAO.login(email, senha);

            if (usuario != null && usuario.getId() != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                response.sendRedirect(request.getContextPath() + "/admin/");
            } else {
                response.sendRedirect(request.getContextPath() + "/login?erro=1");
            }
        } else {
            doGet(request, response);
        }
    }
}
