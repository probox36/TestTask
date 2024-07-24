package Task2.Backend.Controllers;

import Task2.Backend.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Runner {
    public static void main(String[] args) throws SQLException {

        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Users");
        while (result.next()) {
            int id = result.getInt(1);
            String un = result.getString(2);
            String ln = result.getString(3);
            String pn = result.getString(4);
            int gr = result.getInt(5);
            System.out.printf("%d %s %s %s %d", id, un, ln, pn, gr);
        }

    }
}
