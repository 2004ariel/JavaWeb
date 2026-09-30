package com.example.demo.dtos;

import com.example.demo.models.TipoUsuario;
import com.example.demo.models.UserModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private TipoUsuario tipo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public UserDTO() {
    }

    public UserDTO(UserModel userModel) {
        this.id = userModel.getId();
        this.nome = userModel.getNome();
        this.email = userModel.getEmail();
        this.tipo = userModel.getTipo();
        // senha propositalmente não copiada do model
    }
}
