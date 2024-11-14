package backend.dao;

import backend.models.Mercado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MercadoDAO {
    private final IDatabaseConnection conexao;

    public MercadoDAO(IDatabaseConnection conexao) {
        this.conexao = conexao;
    }

    // Método para salvar um novo mercado no banco de dados
    public void salvar(Mercado mercado) throws SQLException {
        Connection conn = conexao.conectar();
        String sql = "INSERT INTO mercado (nome, localizacao) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mercado.getNome());
            stmt.setString(2, mercado.getLocalizacao());
            stmt.executeUpdate();
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método para atualizar um mercado existente
    public void atualizar(Mercado mercado) throws SQLException {
        Connection conn = conexao.conectar();
        String sql = "UPDATE mercado SET nome = ?, localizacao = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mercado.getNome());
            stmt.setString(2, mercado.getLocalizacao());
            stmt.setInt(3, mercado.getId());
            stmt.executeUpdate();
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método para deletar um mercado pelo ID
    public void deletar(int id) throws SQLException {
        Connection conn = conexao.conectar();
        String sql = "DELETE FROM mercado WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método para buscar todos os mercados
    public List<Mercado> buscarTodos() throws SQLException {
        Connection conn = conexao.conectar();
        List<Mercado> mercados = new ArrayList<>();
        String sql = "SELECT * FROM mercado";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Mercado mercado = new Mercado(rs.getInt("id"), rs.getString("nome"), rs.getString("localizacao"));
                mercados.add(mercado);
            }
        } finally {
            conexao.desconectar(conn);
        }
        return mercados;
    }
}
