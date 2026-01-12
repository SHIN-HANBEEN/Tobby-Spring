package Chapter_01.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/DAO", "root", "skaehddn12!@");
    }
}
