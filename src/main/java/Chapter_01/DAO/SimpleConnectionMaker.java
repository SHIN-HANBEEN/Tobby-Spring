package Chapter_01.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/DAO", "root", "skaehddn12!@");
    }
}
