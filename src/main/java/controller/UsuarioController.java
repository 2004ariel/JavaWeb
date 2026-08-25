package controller;

import java.io.IOException;

import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UsuarioModel;
import util.SenhaUtil;

/**
 * Cadastro de usuário (aula 04).
 */
@WebServlet("/registro")
public class UsuarioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/registro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha"); // senha que veio do formulário

        if (nome == null || nome.isBlank() || email == null || email.isBlank()
                || senha == null || senha.isBlank()) {
            request.setAttribute("erro", "Preencha todos os campos.");
            doGet(request, response);
            return;
        }

        String senhaCrip = SenhaUtil.hashSenha(senha); // senha criptografada

        UsuarioModel novoUsuario = new UsuarioModel();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senhaCrip);

        boolean resultado = UsuarioDAO.inserir(novoUsuario);

        if (resultado) {
            response.sendRedirect(request.getContextPath() + "/login?cadastrado=1");
        } else {
            request.setAttribute("erro", "Não foi possível cadastrar. Esse email já está em uso?");
            doGet(request, response);
        }
    }
}
