package backend.dao;

import backend.models.Mercado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MercadoDAO {
    private Connection conexao; // A conexão agora será armazenada como um atributo

    public MercadoDAO(Connection conexao) {
        this.conexao = conexao; // Recebe e armazena a conexão passada
    }

    public void adicionarMercado(Mercado mercado) {
        String sql = "INSERT INTO mercado (nome, localizacao) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
            stmt.setString(1, mercado.getNome());
            stmt.setString(2, mercado.getLocalizacao());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    mercado.setId(rs.getInt(1)); // Define o ID gerado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Alterando o método atualizarMercado para aceitar um objeto Mercado
    public void atualizarMercado(Mercado mercado) {
    String sql = "UPDATE mercado SET nome = ?, localizacao = ? WHERE id = ?";
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, mercado.getNome());
        stmt.setString(2, mercado.getLocalizacao());
        stmt.setInt(3, mercado.getId()); // Agora, o ID é acessado diretamente do objeto Mercado
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public List<Mercado> listarMercados() {
        List<Mercado> mercados = new ArrayList<>();
        String sql = "SELECT * FROM mercado";
        try (Statement stmt = conexao.createStatement();
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
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar o último ID inserido
    public int getUltimoMercadoId() {
        String sql = "SELECT MAX(id) AS ultimo_id FROM mercado";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("ultimo_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 caso não encontre nenhum ID
    }
}
