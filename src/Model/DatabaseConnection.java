package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection connection;

    public Connection getConnection() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=IUManagementSystem";
        String username = "sa";
        String password = "sa";
        try {
            connection = DriverManager.getConnection(url, username, password);
            // System.out.println("MSSQL connected");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.getCause();
        }
        return connection;

    }

}
