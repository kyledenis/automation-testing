package com.planittesting.jupitertoys.model.api.swaggerPetstore.data;

import java.util.List;

public record Pet(
    long id, 
    String name, 
    Category category,
    List<String> photoUrls,
    List<Tag> tags,
    String status){}
