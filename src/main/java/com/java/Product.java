package com.java;

import java.time.Instant;

public class Product {
    private int id;
    private String name;
    private Instant creationDateTime;
    private String category;

    public Product(int id, String name, Instant creationDateTime, String category) {
        this.id = id;
        this.name = name;
        this.creationDateTime = creationDateTime;
        this.category = category;
    }

    public String getCategoryName(){
        return category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Instant getCreationDate() {
        return creationDateTime;
    }
}
