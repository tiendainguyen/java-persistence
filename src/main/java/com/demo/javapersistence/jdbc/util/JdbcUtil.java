package com.demo.javapersistence.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/hibernate";
    private static final String USER = "root";
    private static final String PASSWORD = "daint123@";
    private static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection created successfully");
        } catch (Throwable ex) {
            System.err.println("Initial Connection creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Close jdbc connection failed." + e);
        }
    }

}
