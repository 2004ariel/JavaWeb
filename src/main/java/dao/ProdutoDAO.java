package dao;

import model.produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public Connection getConnection() {
        return new Conexao().conectar();
    }

    public List<produto> listarProdutos() {
        List<produto> produtos = new ArrayList<>();
        try (Connection conexao = getConnection()) {
            if (conexao == null) {
                throw new IllegalStateException("Não foi possível conectar ao banco de dados.");
            }
            String sql = "SELECT * FROM produto";
            try (PreparedStatement stmt = conexao.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    double preco = rs.getDouble("preco");
                    int quantidade = rs.getInt("quantidade");
                    produto p = new produto();
                    p.setNome(nome);
                    p.setPreco(preco);
                    p.setQuantidade(quantidade);
                    produtos.add(p);
                }
            }
        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public void salvar(produto p) {
        inserirProduto(p);
    }

    public void editar(produto p) {
        try (Connection conexao = new Conexao().conectar()) {
            if (conexao == null) {
                throw new IllegalStateException("Não foi possível conectar ao banco de dados.");
            }
            String sql = "UPDATE produto SET preco = ?, quantidade = ? WHERE nome = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setDouble(1, p.getPreco());
                stmt.setInt(2, p.getQuantidade());
                stmt.setString(3, p.getNome());
                stmt.executeUpdate();
            }
        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void excluir(String nome) {
        try (Connection conexao = new Conexao().conectar()) {
            if (conexao == null) {
                throw new IllegalStateException("Não foi possível conectar ao banco de dados.");
            }
            String sql = "DELETE FROM produto WHERE nome = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, nome);
                stmt.executeUpdate();
            }
        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void inserirProduto(produto p) {
        try (Connection conexao = new Conexao().conectar()) {
            if (conexao == null) {
                throw new IllegalStateException("Não foi possível conectar ao banco de dados.");
            }
            String sql = "INSERT INTO produto (nome, preco, quantidade) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, p.getNome());
                stmt.setDouble(2, p.getPreco());
                stmt.setInt(3, p.getQuantidade());
                stmt.executeUpdate();
            }
        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
