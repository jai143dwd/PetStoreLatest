package com.mindtickle.api.client;

import org.apache.http.util.EntityUtils;

import java.io.IOException;
import org.apache.http.HttpResponse;


public class BaseClient {
    protected static final String BASE_URL = "https://petstore.swagger.io/v2";

    protected void logEndpointAndResponse(String endpoint, HttpResponse response) throws IOException {
        System.out.println("Called endpoint: " + endpoint);
        System.out.println("Received response: " + EntityUtils.toString(response.getEntity()));
    }
}

