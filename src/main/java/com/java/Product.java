package com.java;

import java.time.Instant;

public class Product {
    private int id;
    private String name;
    private Instant creationDate;
    private String category;

    public Product(int id, String name, Instant creationDate, String category) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.category = category;
    }

    public String getCategoryName(){
        return name;
    }
}
