package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectMSSQL {

    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=IUManagementSystem";
        String username = "sa";
        String password = "sa";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("MSSQL connected");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("cannot connect");
            e.printStackTrace();
        }
    }
}
