package com.java;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        final Instant DATE_2024_02_01 = Instant.parse("2024-02-01T00:00:00Z");
        final Instant DATE_2024_03_01 = Instant.parse("2024-03-01T00:00:00Z");
        final Instant DATE_2024_01_01 = Instant.parse("2024-01-01T00:00:00Z");
        final Instant DATE_2024_12_01 = Instant.parse("2024-12-01T00:00:00Z");

        try {

            DBConnection dbConnection = new DBConnection();
            DataRetriever dataRetriever = new DataRetriever(dbConnection);

            Runnable printProductHeader = () -> System.out.println("  ID | Name | Creation Date | Category");

            System.out.println("==================================================");
            System.out.println("1. Test : getAllCategories() - All Categories");
            System.out.println("==================================================");
            List<Category> categories = dataRetriever.getAllCategories();
            System.out.println("Number of categories: " + categories.size());
            for (Category c : categories) {
                System.out.println("  " + c.getId() + " - " + c.getName());
            }

            System.out.println("\n==================================================");
            System.out.println("2. Tests : getProductList(page, size)");
            System.out.println("==================================================");


            System.out.println("\n--- 2.A : page=1, size=10 ---");
            printProductHeader.run();
            List<Product> productsB1 = dataRetriever.getProductList(1, 10);
            productsB1.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));

            System.out.println("\n--- 2.B : page=1, size=5 ---");
            printProductHeader.run();
            List<Product> productsB2 = dataRetriever.getProductList(1, 5);
            productsB2.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));

            System.out.println("\n--- 2.C : page=2, size=2 ---");
            printProductHeader.run();
            List<Product> productsB4 = dataRetriever.getProductList(2, 2);
            productsB4.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));

            System.out.println("\n==================================================");
            System.out.println("3. Tests : getProductsByCriteria (WITHOUT pagination)");
            System.out.println("==================================================");

            System.out.println("\n--- 3.A : productName=\"Dell\" ---");
            printProductHeader.run();
            List<Product> productsC1 = dataRetriever.getProductsByCriteria("Dell", null, null, null);
            productsC1.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));

            System.out.println("\n--- 3.B : categoryName=\"info\" ---");
            printProductHeader.run();
            List<Product> productsC2 = dataRetriever.getProductsByCriteria(null, "informatique", null, null);
            productsC2.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));

            System.out.println("\n--- 3.C : creationMin/Max (2024-02-01 / 2024-03-01) ---");
            printProductHeader.run();
            List<Product> productsC4 = dataRetriever.getProductsByCriteria(null, null, DATE_2024_02_01, DATE_2024_03_01);
            productsC4.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));

            System.out.println("\n--- 3.D : audio + Date (2024-01-01 / 2024-12-01) ---");
            printProductHeader.run();
            List<Product> productsC7 = dataRetriever.getProductsByCriteria(null, "audio", DATE_2024_01_01, DATE_2024_12_01);
            productsC7.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));

            System.out.println("\n==================================================");
            System.out.println("4. Tests : getProductsByCriteria (WITH pagination)");
            System.out.println("==================================================");

            System.out.println("\n--- 4.A : All, page=1, size=10 ---");
            printProductHeader.run();
            List<Product> productsD1 = dataRetriever.getProductsByCriteria(null, null, null, null, 1, 10);
            productsD1.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));

            System.out.println("\n--- 4.B : productName=\"Dell\", page=1, size=5 ---");
            printProductHeader.run();
            List<Product> productsD2 = dataRetriever.getProductsByCriteria("Dell", null, null, null, 1, 5);
            productsD2.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));

            System.out.println("\n--- 4.C : categoryName=\"informatique\", page=1, size=10 ---");
            printProductHeader.run();
            List<Product> productsD3 = dataRetriever.getProductsByCriteria(null, "informatique", null, null, 1, 10);
            productsD3.forEach(p -> System.out.println(p.getId() + " | " + p.getName()  + " | " + p.getCreationDate() + " | " + p.getCategoryName()));


        } catch (SQLException e) {
            System.err.println("\n!!! SQL Error: Check your database connection and SQL queries.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\n!!! Unexpected Error: Check the initialization of your objects (DBConnection, DataRetriever, etc.).");
            e.printStackTrace();
        }
    }
}