package backend.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/buscai";
    private static final String USUARIO = "root"; 
    private static final String SENHA = "@Henri0202";

    public static Connection getConexao() throws SQLException {
        try {
            // O driver é carregado automaticamente, portanto, pode ser removido
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static void fecharConexao(Connection conexao) {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
