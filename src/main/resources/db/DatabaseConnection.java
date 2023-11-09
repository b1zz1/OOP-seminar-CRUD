package main.resources.db;

import java.sql.*;
import java.util.Properties;
import java.nio.file.*;
import java.io.BufferedReader;
import java.io.IOException;

public class DatabaseConnection {
    private static Connection conn = null;

    public static Connection getConnection() {
        Properties props = loadProperties();
        String url = props.getProperty("dburl");

        try (Connection conn = DriverManager.getConnection(url, props)) {
            return conn;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        Path propertiesFilePath = Paths.get("database.properties");

        try (BufferedReader reader = Files.newBufferedReader(propertiesFilePath)) {
            Properties props = new Properties();
            props.load(reader);
            return props;
        } catch (IOException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
