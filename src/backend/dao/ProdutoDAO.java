package backend.dao;

import backend.models.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private final Connection conexao;

    public ProdutoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void adicionarProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (nome, preco, categoria, disponibilidade, mercado_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNome());
            stmt.setFloat(2, produto.getPreco());
            stmt.setString(3, produto.getCategoria());
            stmt.setBoolean(4, produto.isDisponibilidade());
            stmt.setInt(5, produto.getMercadoId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        produto.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public void atualizarProduto(int id, String nome, float preco, String categoria, boolean disponibilidade, int mercadoId) throws SQLException {
        String sql = "UPDATE produto SET nome = ?, preco = ?, categoria = ?, disponibilidade = ?, mercado_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setFloat(2, preco);
            stmt.setString(3, categoria);
            stmt.setBoolean(4, disponibilidade);
            stmt.setInt(5, mercadoId);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        }
    }

    public List<Produto> listarProdutosPorMercado(int mercadoId) throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE mercado_id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, mercadoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    produtos.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getFloat("preco"),
                        rs.getString("categoria"),
                        rs.getBoolean("disponibilidade"),
                        rs.getInt("mercado_id")
                    ));
                }
            }
        }
        return produtos;
    }

    public List<Produto> listarProdutosPorCategoria(int mercadoId, String categoria) throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE mercado_id = ? AND categoria = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, mercadoId);
            stmt.setString(2, categoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    produtos.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getFloat("preco"),
                        rs.getString("categoria"),
                        rs.getBoolean("disponibilidade"),
                        rs.getInt("mercado_id")
                    ));
                }
            }
        }
        return produtos;
    }

    public void removerProduto(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Nenhum produto encontrado com o ID: " + id);
            }
        }
    }
}
