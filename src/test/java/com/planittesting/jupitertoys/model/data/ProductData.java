package com.planittesting.jupitertoys.model.data;

public record ProductData(
    int quantity,
    boolean visible,
    String image,
    String category,
    double price,
    String id,
    int stars,
    String title
){}