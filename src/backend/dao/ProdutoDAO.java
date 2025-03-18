package backend.dao;

import backend.models.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private Connection conexao;

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
                        produto.setId(generatedKeys.getInt(1)); // Atualiza o ID gerado automaticamente
                    }
                }
            }
        }
    }

    public void atualizarProduto(int id, String nome, float preco, String categoria, boolean disponibilidade, int mercadoId) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, categoria = ?, disponibilidade = ?, mercado_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setFloat(2, preco);
            stmt.setString(3, categoria);
            stmt.setBoolean(4, disponibilidade);
            stmt.setInt(5, mercadoId);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto produto = new Produto(
                                        rs.getString("nome"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public void removerProduto(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
