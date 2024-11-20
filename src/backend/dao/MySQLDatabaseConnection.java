package backend.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnection implements IDatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/buscai_db";
    private static final String USER = "seu_usuario"; // Substituir pelo seu usuário do MySQL
    private static final String PASSWORD = "sua_senha"; // Substituir pela sua senha do MySQL

    @Override
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void desconectar(Connection conexao) throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }
}
