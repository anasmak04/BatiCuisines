package main.java.config;


import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");
    private static Connection connection = null;

    private DatabaseConnection() {
    }

    public static void migrateDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .locations("filesystem:src/db/migration")
                .load();
        flyway.migrate();
    }

    public static Connection getConnection() {

        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                migrateDatabase();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException("PostgreSQL JDBC Driver not found.", e);
            } catch (SQLException e) {
                throw new RuntimeException("Connection to PostgreSQL database failed.", e);
            }

        }


        return connection;
    }
}