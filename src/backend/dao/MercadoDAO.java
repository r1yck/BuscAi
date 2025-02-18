package backend.dao;

import backend.models.Mercado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MercadoDAO {

    public MercadoDAO(Connection conexao) {
    }

    public void adicionarMercado(Mercado mercado) {
        String sql = "INSERT INTO mercado (nome, localizacao) VALUES (?, ?)";
        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
            stmt.setString(1, mercado.getNome());
            stmt.setString(2, mercado.getLocalizacao());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    mercado.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarMercado(int id, String nome, String localizacao) {
        String sql = "UPDATE mercado SET nome = ?, localizacao = ? WHERE id = ?";
        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, localizacao);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Mercado> listarMercados() {
        List<Mercado> mercados = new ArrayList<>();
        String sql = "SELECT * FROM mercado";
        try (Connection conexao = ConexaoMySQL.getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Mercado mercado = new Mercado(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("localizacao")
                );
                mercados.add(mercado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mercados;
    }

    public void removerMercado(int id) {
        String sql = "DELETE FROM mercado WHERE id = ?";
        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
