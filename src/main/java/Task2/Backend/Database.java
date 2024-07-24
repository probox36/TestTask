package Task2.Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test_task_db", "root", "#Hoover_Dam"
            );
        }

        return connection;
    }

}
