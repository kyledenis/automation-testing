package com.planittesting.jupitertoys.model.data;

import java.util.List;

public record ShopData (List<ProductData> products, int cartCount){}
    