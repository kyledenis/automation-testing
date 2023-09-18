package com.planittesting.jupitertoys.model.data;

public record CartData(
    int quanitity,
    String productName,
    double price,
    double subTotal) {}
