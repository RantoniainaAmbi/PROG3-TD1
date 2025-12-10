package com.java;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private DBConnection dbConnection;

    public DataRetriever(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT id, name FROM product_category";

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                categories.add(c);
            }
        }

        return categories;
    }

    public List<Product> getProductList(int page, int size) throws SQLException {
        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;

        String sql = """
            SELECT p.id, p.name, p.price, p.creation_datetime,
                   c.id AS category_id, c.name AS category_name
            FROM product p
            JOIN product_category c ON c.product_id = p.id
            LIMIT ? OFFSET ?
        """;

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                products.add(product);
            }
        }

        return products;
    }

    public List<Product> getProductsByCriteria(String productName,
                                               String categoryName,
                                               Instant creationMin,
                                               Instant creationMax) throws SQLException {

        List<Product> results = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT p.id, p.name, p.price, p.creation_datetime,
                   c.id AS category_id, c.name AS category_name
            FROM product p
            JOIN product_category c ON c.product_id = p.id
            WHERE 1=1
        """);

        if (productName != null) {
            sql.append(" AND p.name ILIKE ? ");
            params.add("%" + productName + "%");
        }

        if (categoryName != null) {
            sql.append(" AND c.name ILIKE ? ");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                results.add(product);
            }
        }

        return results;
    }

    public List<Product> getProductsByCriteria(String productName,
                                               String categoryName,
                                               Instant creationMin,
                                               Instant creationMax,
                                               int page,
                                               int size) throws SQLException {

        List<Product> filtered = getProductsByCriteria(
                productName,
                categoryName,
                creationMin,
                creationMax
        );

        int start = (page - 1) * size;
        int end = Math.min(start + size, filtered.size());

        if (start >= filtered.size()) {
            return new ArrayList<>();
        }

        return filtered.subList(start, end);
    }
}