package com.planittesting.jupitertoys.tests.swaggerPetstoreAPITests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import javax.json.bind.JsonbBuilder;

import com.jayway.jsonpath.JsonPath;
import com.planittesting.jupitertoys.model.api.swaggerPetstore.PetAPI;
import com.planittesting.jupitertoys.model.api.swaggerPetstore.data.Pet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Execution(ExecutionMode.CONCURRENT)
@Tag("api")
class PetAPITests extends BaseAPITests {

    @ValueSource(strings = { 
      "{}", 
      """
        {
          "incorrect": "json object"
        }
      """})
    @ParameterizedTest(name="create pet API test expected error")
    void createPetTestError(String pet) throws IOException {
        var petAPI = new PetAPI(baseURL);
        petAPI.withHeaders(Map.of("x-api-token", "iamatoken")).createPet(pet);
        var response = JsonPath.parse(petAPI.getResponseBody());
        assertAll(
          ()->assertEquals(500, petAPI.getRawResponse().code()),
          ()->assertEquals(500, response.read("$.code", Integer.class)),
          ()->assertThat(response.read("$.message", String.class)).contains("There was an error processing your request")
        );
    }

    @ValueSource(strings = """
    {
      "id": 10,
      "name": "doggie",
      "category": {
        "id": 1,
        "name": "Dogs"
      },
      "photoUrls": ["string"],
      "tags":
        { "id": 0, "name": "string" }
      ,
      "status": "available"
    }
    """)
    @ParameterizedTest(name="create pet API test with missing json entries")
    void createPetTestMissingEntries(String pet) throws IOException {
        var petAPI = new PetAPI(baseURL);
        var responseBody = petAPI.withoutParams().post(pet);
        var responseJsonPath = JsonPath.parse(responseBody);

        assertAll(
          ()->assertEquals(400, petAPI.getRawResponse().code()),
          ()->assertEquals(400, responseJsonPath.read("$.code", Integer.class)),
          ()->assertThat(responseJsonPath.read("$.message", String.class)).isEqualTo("Input error: unable to convert input to io.swagger.petstore.model.Pet"));
    }

    @ValueSource(strings = """
    {
      "id": 10,
      "name": "doggie",
      "category": {
        "id": 1,
        "name": "Dogs"
      },
      "photoUrls": ["string"],
      "tags":[
        { "id": 0, "name": "string" }
      ],
      "status": "available"
    }
    """)
    @ParameterizedTest(name="create pet API test")
    void createPetTest(String pet) throws IOException {
        var petAPI = new PetAPI(baseURL);
        var actualPet = petAPI.createPet(pet);
        var expectedPet = JsonbBuilder.create().fromJson(pet, Pet.class);
        assertEquals(200, petAPI.getRawResponse().code());
        assertEquals(expectedPet, actualPet);
    }
}
