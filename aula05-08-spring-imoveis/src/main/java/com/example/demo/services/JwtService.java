package com.example.demo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.models.UserModel;

@Service
public class JwtService {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final long expirationMs;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration-ms}") long expirationMs) {
        // HS256: use uma chave de pelo menos 256 bits (32 caracteres)
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).build();
        this.expirationMs = expirationMs;
    }

    public String generateToken(UserModel user) {
        Date now = new Date();
        return JWT.create()
            .withSubject(user.getEmail())
            .withClaim("role", user.getTipo().name())
            .withIssuedAt(now)
            .withExpiresAt(new Date(now.getTime() + expirationMs))
            .sign(algorithm);
    }

    // valida assinatura e expiração; lança JWTVerificationException se o token for inválido
    public DecodedJWT parse(String token) {
        return verifier.verify(token);
    }
}
