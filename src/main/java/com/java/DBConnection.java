package com.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private String url = "jdbc:postgresql://localhost:5432/product_management_db";
    private String user = "product_manager_user";
    private String password = "123456";

    public Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

