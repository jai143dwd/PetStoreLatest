package com.mindtickle.api.client;



import com.mindtickle.api.model.Pet;
import com.mindtickle.api.model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.logging.Logger;

public class PutClient {

    private final String BASE_URL = "https://petstore.swagger.io/v2";
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final Logger logger = Logger.getLogger(PutClient.class.getName());

    public HttpResponse putData(String endpoint, String jsonPayload) throws IOException {
        HttpPut putRequest = new HttpPut(BASE_URL + endpoint);
        putRequest.setEntity(new StringEntity(jsonPayload));
        putRequest.setHeader("Accept", "application/json");
        putRequest.setHeader("Content-type", "application/json");

        logger.info("Executing PUT request: " + BASE_URL + endpoint);
        logger.info("Payload: " + jsonPayload);

        HttpResponse response = httpClient.execute(putRequest);

        // Logging the response
        String responseBody = EntityUtils.toString(response.getEntity());
        logger.info("PUT Response: " + responseBody);

        return response;
    }

    public HttpResponse updateUser(String username, User user) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        return putData("/user/" + username, userJson);
    }

    public HttpResponse updatePet(Pet pet) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String petJson = objectMapper.writeValueAsString(pet);
        return putData("/pet", petJson);
    }
}



