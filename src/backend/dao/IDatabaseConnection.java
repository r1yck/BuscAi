package backend.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseConnection {
    Connection conectar() throws SQLException;
    void desconectar(Connection conexao) throws SQLException;
}
