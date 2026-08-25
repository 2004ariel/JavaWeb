package dao;

import model.UsuarioModel;
import util.SenhaUtil;
import java.sql.*;

public class UsuarioDAO {

    /**
     * Cadastra um usuário novo. A senha já deve chegar aqui criptografada
     * (o controller chama SenhaUtil.hashSenha antes).
     * Retorna false se o email já existir (a coluna email é UNIQUE).
     */
    public static boolean inserir(UsuarioModel usuario) {
        try (Connection conexao = new Conexao().conectar()) {
            if (conexao == null) {
                throw new IllegalStateException("Não foi possível conectar ao banco de dados.");
            }
            String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getEmail());
                stmt.setString(3, usuario.getSenha());
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // email repetido
            return false;
        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Procura o usuário pelo email e confere a senha digitada contra o hash
     * salvo no banco. Retorna o usuário (sem a senha) se bater, ou null.
     */
    public static UsuarioModel login(String email, String senha) {
        try (Connection conexao = new Conexao().conectar()) {
            if (conexao == null) {
                throw new IllegalStateException("Não foi possível conectar ao banco de dados.");
            }
            String sql = "SELECT * FROM usuarios WHERE email = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && SenhaUtil.checarSenha(senha, rs.getString("senha"))) {
                        UsuarioModel usuario = new UsuarioModel();
                        usuario.setId(rs.getInt("id"));
                        usuario.setNome(rs.getString("nome"));
                        usuario.setEmail(rs.getString("email"));
                        return usuario;
                    }
                }
            }
        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
