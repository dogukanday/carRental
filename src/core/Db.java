package core;

import java.sql.*;

public class Db {
    private static Db instance;
    private static Connection connection;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/rentacar";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "12345";

    private Db() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Connection getInstance()  {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Db();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return instance.getConnection();
    }
}