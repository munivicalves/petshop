package com.petshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/petshop";
    private static final String USER = "root"; 
    private static final String PASSWORD = "12345678"; 

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
