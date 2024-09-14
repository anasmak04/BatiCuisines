package main.java.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to PostgreSQL database established.");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
        } catch (SQLException e) {
            System.out.println("Connection to PostgreSQL database failed.");
        }
        return connection;
    }
}
