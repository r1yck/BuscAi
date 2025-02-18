package backend.dao;

import backend.models.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public ProdutoDAO(Connection conexao) {
    }
    public void adicionarProduto(Produto produto) {
    String sql = "INSERT INTO produto (nome, preco, categoria, disponibilidade, mercado_id) VALUES (?, ?, ?, ?, ?)";
    try (Connection conexao = ConexaoMySQL.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {  
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
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void atualizarProduto(int id, String nome, float preco, String categoria, boolean disponibilidade, int mercadoId) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, categoria = ?, disponibilidade = ?, mercado_id = ? WHERE id = ?";
        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
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
        try (Connection conexao = ConexaoMySQL.getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getFloat("preco"),
                    rs.getString("categoria"),
                    rs.getBoolean("disponibilidade"),
                    rs.getInt("mercado_id")
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public void removerProduto(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
