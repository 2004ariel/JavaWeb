package com.example.demo.models;

// ADMIN e USER são as roles usadas no JWT; COMPRADOR/VENDEDOR ficam por compatibilidade
// com os registros antigos e são tratados como usuário comum.
public enum TipoUsuario {
    COMPRADOR,
    VENDEDOR,
    ADMIN,
    USER
}
