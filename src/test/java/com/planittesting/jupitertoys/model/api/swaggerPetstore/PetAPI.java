package com.planittesting.jupitertoys.model.api.swaggerPetstore;

import java.io.IOException;

import javax.json.bind.JsonbBuilder;

import com.planittesting.jupitertoys.model.api.BaseAPI;
import com.planittesting.jupitertoys.model.api.swaggerPetstore.data.Pet;

import okhttp3.HttpUrl;

public class PetAPI extends BaseAPI<PetAPI> {

    public PetAPI(HttpUrl baseURL) {
        super(baseURL);
        setMediaType("application/json; charset=utf-8");
        setResourceURL("/pet");
    }

    public Pet createPet(String payload) throws IOException {
        post(payload);
        return JsonbBuilder.create().fromJson(getResponseBody(), Pet.class);
    }
}
