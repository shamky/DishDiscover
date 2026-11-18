package com.amogh.dishdiscover.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class DBConnection {

  private static final Dotenv dotenv = Dotenv.configure()
          .directory(".")
          .ignoreIfMissing()
          .load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver class not found", e);
        }

        if (URL == null || USER == null || PASSWORD == null) {
            throw new SQLException("Database configuration missing. Check your .env file.");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}