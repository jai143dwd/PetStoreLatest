package com.mindtickle.api.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.logging.Logger;

public class GetClient {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final Logger LOGGER = Logger.getLogger(GetClient.class.getName());

    public static String getUser(String username) {
        return executeGetRequest("/user/" + username);
    }

    public static String getPetsByStatus(String status) {
        return executeGetRequest("/pet/findByStatus?status=" + status);
    }

    private static String executeGetRequest(String endpoint) {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(BASE_URL + endpoint);

        HttpResponse response;
        String result = null;
        try {
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                LOGGER.info("GET Endpoint: " + endpoint);
                LOGGER.info("Response: " + result);
            }
        } catch (IOException e) {
            LOGGER.severe("Error executing GET request: " + e.getMessage());
        }

        return result;
    }
}




