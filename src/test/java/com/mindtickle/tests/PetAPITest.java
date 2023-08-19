package com.mindtickle.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtickle.api.client.GetClient;
import com.mindtickle.api.client.PostClient;
import com.mindtickle.api.client.PutClient;
import com.mindtickle.api.model.Pet;
import com.mindtickle.api.model.PostResponse;
import com.mindtickle.api.model.User;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.http.HttpResponse;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class PetAPITest {
    private static final Logger LOGGER = Logger.getLogger(UserAPITest.class.getName());
    private static final String PET_JSON_PATH = "src/test/resources/pet.json";
    private static final String MULTI_PET_JSON_PATH = "src/test/resources/multi_pet.json";
    private static final String UPDATE_PET_JSON_PATH = "src/test/resources/update_pet.json";
    private final PostClient postClient = new PostClient();

    @Test(priority = 0 , description = "This test create a single pet object from file")
    public void createPetFromJsonFile() throws Exception {
        PostClient postClient = new PostClient();

        ObjectMapper mapper = new ObjectMapper();
        Pet pet = mapper.readValue(new File(PET_JSON_PATH), Pet.class);
        pet.setName("Bunny");
        HttpResponse response = postClient.postRequest("/pet", pet);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    @Test(priority = 1 , description = "This test creates multiple pet object from file ")
    // This end point is actually failing when we give arrary of pet objects
    public void createMultiPetFromJsonFile() throws Exception {
        PostClient postClient = new PostClient();

        ObjectMapper mapper = new ObjectMapper();
        Pet[] pet = mapper.readValue(new File(MULTI_PET_JSON_PATH), Pet[].class);
        pet[0].setName("Tiger");

        HttpResponse response = postClient.postRequest("/pet", pet);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 500);
    }

    @Test(priority = 2 , description = "This test updates a pet record which was previously created ")
    public void testUpdatePet() throws IOException {
        PutClient putClient = new PutClient();

        Pet updatedPet = new Pet();
        updatedPet.setId(100);
        updatedPet.setName("Jacky");
        updatedPet.setStatus("pending");
        updatedPet.setPhotoUrls(Arrays.asList("url1", "url2"));

        HttpResponse response = putClient.updatePet(updatedPet);

        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200, "Expected status code to be 200, but got: " + statusCode);
    }

    @Test(priority = 3 , description = "This test updates a pet record from file which was previously created ")
    public void testUpdatePetFromFile() throws IOException {
        PutClient putClient = new PutClient();
        ObjectMapper objectMapper = new ObjectMapper();

        // 1. Deserialize the JSON from the file to Pet object
        Pet pet = objectMapper.readValue(new File(UPDATE_PET_JSON_PATH), Pet.class);

        // 2. Update the fields in the Pet object
        pet.setName("UpdatedPetNameFromFile");

        // 3. Update the pet
        HttpResponse response = putClient.updatePet(pet);

        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200, "Expected status code to be 200, but got: " + statusCode);
    }

    @Test(priority = 4 , description = "This test updates a pet record from file which was previously created ")
    public void testGetPetsByStatus() throws JsonProcessingException {
        String status = "sold";
        String response = GetClient.getPetsByStatus(status);

        // Parsing the response to a POJO class (or a list of POJOs) allows for more specific assertions
        Pet[] pets = new ObjectMapper().readValue(response, Pet[].class);

        Assert.assertNotNull(response, "Response is null");
        for (Pet pet : pets) {
            if (pet.getId()==100){
                Assert.assertEquals(pet.getName(), "UpdatedPetNameFromFile", "Pet name does not match the requested status");
                Assert.assertEquals(pet.getStatus(), status, "Pet status does not match the requested status");

            }
            Assert.assertEquals(pet.getStatus(), status, "Pet status does not match the requested status");
        }
        // Add more assertions based on the expected Pet object fields and values
    }


}

