package com.mindtickle.api.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class PostClient {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final Logger LOGGER = Logger.getLogger(PostClient.class.getName());
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public HttpResponse postRequest(String endpoint, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload;

        if (endpoint.equals("/user/createWithArray")) {
            jsonPayload = object instanceof Object[] ? mapper.writeValueAsString(object) : mapper.writeValueAsString(new Object[]{object});
        } else {
            jsonPayload = mapper.writeValueAsString(object);
        }

        LOGGER.info("Making POST request to endpoint: " + BASE_URL + endpoint);
        LOGGER.info("Payload: " + jsonPayload);

        HttpPost postRequest = new HttpPost(BASE_URL + endpoint);
        StringEntity entity = new StringEntity(jsonPayload);
        postRequest.setEntity(entity);
        postRequest.setHeader("Content-Type", "application/json");

        CloseableHttpResponse response = httpClient.execute(postRequest);

        // Reading and  logging the response
        HttpEntity responseEntity = response.getEntity();
        String responseBody = EntityUtils.toString(responseEntity);
        LOGGER.info("Response: " + responseBody);

        EntityUtils.consume(responseEntity);

        response.close();

        return response;
    }
}









