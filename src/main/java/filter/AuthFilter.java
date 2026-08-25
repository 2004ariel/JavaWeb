package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro de autenticação (aula 04).
 *
 * Toda requisição para /admin/* passa aqui ANTES de chegar na página.
 * Se não tiver usuário na sessão, manda para a tela de login.
 *
 * Obs.: os imports são jakarta.servlet.* (Tomcat 11), e não javax.servlet.*
 * como no material da aula - com javax não compila neste projeto.
 */
@WebFilter("/admin/*")
public class AuthFilter extends HttpFilter implements Filter {

    public AuthFilter() {
        super();
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        boolean estaLogado = (session != null && session.getAttribute("usuario") != null);

        if (estaLogado) {
            // segue o caminho normal: chega na página pedida
            chain.doFilter(request, response);
        } else {
            // não deixa passar: manda para o login e encerra a requisição aqui
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}
