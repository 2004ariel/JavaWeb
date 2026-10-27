package com.example.demo.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// lê "Authorization: Bearer <token>" e, se o token for válido, coloca o usuário no SecurityContext
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            try {
                DecodedJWT jwt = jwtService.parse(header.substring(7));
                var authority = new SimpleGrantedAuthority("ROLE_" + jwt.getClaim("role").asString());
                var auth = new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, List.of(authority));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JWTVerificationException e) {
                // assinatura errada, expirado ou malformado: segue sem autenticação e o EntryPoint responde 401
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }
}
