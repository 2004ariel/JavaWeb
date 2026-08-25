package model;

import java.io.Serializable;

/**
 * Usuário da aplicação (tabela usuarios).
 *
 * Implementa Serializable porque este objeto fica guardado na sessão HTTP.
 * O campo id é Integer (e não int) para poder ser null quando o usuário
 * ainda não foi salvo no banco - o AutenticacaoController usa isso para
 * saber se o login deu certo.
 */
public class UsuarioModel implements Serializable {

    private Integer id;
    private String nome;
    private String email;
    private String senha;

    public UsuarioModel() {
    }

    public UsuarioModel(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public UsuarioModel(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return this.senha; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public String toString() {
        return "UsuarioModel [id=" + id + ", nome=" + nome + ", email=" + email + "]";
    }
}
