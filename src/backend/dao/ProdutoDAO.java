package backend.dao;

import backend.models.Produto;
import backend.models.Categoria;
import backend.models.Mercado;
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

    // Método para salvar o produto
    public void salvar(Produto produto) throws SQLException {
        Connection conn = conexao.conectar();
        String sql = "INSERT INTO produto (nome, preco, disponivel, categoria, mercado_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isDisponivel());
            stmt.setString(4, produto.getCategoria().name()); // Categoria como String

            // Verifica se o Mercado é nulo e ajusta a query
            if (produto.getMercado() != null) {
                stmt.setInt(5, produto.getMercado().getId());  // Mercado associado ao produto
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);  // Caso não haja mercado associado
            }

            stmt.executeUpdate();
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método para buscar todos os produtos
    public List<Produto> buscarTodos() throws SQLException {
        Connection conn = conexao.conectar();
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.preco, p.disponivel, p.categoria, p.mercado_id, m.nome AS mercado_nome "
                + "FROM produto p LEFT JOIN mercado m ON p.mercado_id = m.id";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Ajuste na construção do Mercado: Adicionando um valor padrão para localizacao
                Mercado mercado = new Mercado(
                        rs.getInt("mercado_id"),
                        rs.getString("mercado_nome"),
                        rs.getString("mercado_nome") != null ? rs.getString("mercado_nome") : "Localizacao desconhecida"
                );  // Ajuste para permitir valores padrão de localização

                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getBoolean("disponivel"),
                        Categoria.valueOf(rs.getString("categoria")),
                        mercado // Associando produto ao mercado
                );
                produtos.add(produto);
            }
        } finally {
            conexao.desconectar(conn);
        }
        return produtos;
    }

    // Método para atualizar um produto
    public void atualizar(Produto produto) throws SQLException {
        Connection conn = conexao.conectar();
        String sql = "UPDATE produto SET nome = ?, preco = ?, disponivel = ?, categoria = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isDisponivel());
            stmt.setString(4, produto.getCategoria().name());
            stmt.setInt(5, produto.getId());
            stmt.executeUpdate();
        } finally {
            conexao.desconectar(conn);
        }
    }

// Método para deletar um produto
    public void deletar(int produtoId) throws SQLException {
        Connection conn = conexao.conectar();
        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            stmt.executeUpdate();
        } finally {
            conexao.desconectar(conn);
        }
    }

}
