package com.example.demo.dtos;

import com.example.demo.models.TipoUsuario;

public record RegisterDTO(String nome, String email, String password, TipoUsuario role) {
}
