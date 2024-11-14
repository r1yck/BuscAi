package backend.dao;

import backend.models.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private final IDatabaseConnection conexao;

    public ProdutoDAO(IDatabaseConnection conexao) {
        this.conexao = conexao;
    }

    // Método para salvar um novo produto no banco de dados
    public void salvar(Produto produto) throws SQLException {
        Connection conn = conexao.conectar();
        String sql = "INSERT INTO produto (nome, preco, disponivel) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isDisponivel());
            stmt.executeUpdate();
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método para atualizar um produto existente
    public void atualizar(Produto produto) throws SQLException {
        Connection conn = conexao.conectar();
        String sql = "UPDATE produto SET nome = ?, preco = ?, disponivel = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isDisponivel());
            stmt.setInt(4, produto.getId());
            stmt.executeUpdate();
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método para deletar um produto pelo ID
    public void deletar(int id) throws SQLException {
        Connection conn = conexao.conectar();
        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método para buscar todos os produtos
    public List<Produto> buscarTodos() throws SQLException {
        Connection conn = conexao.conectar();
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"), rs.getBoolean("disponivel"));
                produtos.add(produto);
            }
        } finally {
            conexao.desconectar(conn);
        }
        return produtos;
    }
}