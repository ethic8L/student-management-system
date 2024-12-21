package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// connection with DB
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5433/student_management";
    private static final String USER = "postgres";
    private static final String PASSWORD = "9999";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
