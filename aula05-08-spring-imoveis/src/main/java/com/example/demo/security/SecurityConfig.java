package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**", "/error").permitAll()

                // imóveis: leitura pública, status para qualquer logado, resto só ADMIN
                .requestMatchers(HttpMethod.GET, "/imoveis", "/imoveis/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/imoveis/sabermais/*").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/imoveis/*/status").authenticated()
                .requestMatchers("/imoveis/**").hasRole("ADMIN")

                // bairros e tipos: GET público (inclui paginação), escrita só ADMIN
                .requestMatchers(HttpMethod.GET, "/bairros/**", "/tiposimoveis/**").permitAll()
                .requestMatchers("/bairros/**", "/tiposimoveis/**").hasRole("ADMIN")

                .requestMatchers("/users/**", "/fotos-imoveis/**", "/email/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .exceptionHandling(ex -> ex
                // sem token, token inválido ou expirado
                .authenticationEntryPoint((req, res, e) ->
                    writeJson(res, HttpServletResponse.SC_UNAUTHORIZED, "Token ausente, inválido ou expirado"))
                // token válido, mas a role não tem permissão
                .accessDeniedHandler((req, res, e) ->
                    writeJson(res, HttpServletResponse.SC_FORBIDDEN, "Acesso negado")))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static void writeJson(HttpServletResponse res, int status, String message) throws java.io.IOException {
        res.setStatus(status);
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write("{\"status\":" + status + ",\"error\":\"" + message + "\"}");
    }
}
