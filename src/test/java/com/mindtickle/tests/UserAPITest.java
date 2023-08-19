package com.mindtickle.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtickle.api.client.GetClient;
import com.mindtickle.api.client.PostClient;
import com.mindtickle.api.client.PutClient;
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
import java.util.List;
import java.util.logging.Logger;

public class UserAPITest {
        private static final Logger LOGGER = Logger.getLogger(UserAPITest.class.getName());
        private static final String USER_JSON_PATH = "src/test/resources/users.json";
        private static final String MULTIUSER_JSON_PATH = "src/test/resources/multi_users.json";

        private static final String UPDATE_USER_JSON_PATH = "src/test/resources/user_for_put.json";

        private final PostClient postClient = new PostClient();

        @Test(priority = 0 , description = "This test creates single user from file ")
        public void createSingleUserWithOverrides() throws Exception {
                PostClient postClient = new PostClient();

                ObjectMapper mapper = new ObjectMapper();
                User[] users = mapper.readValue(new File(USER_JSON_PATH), User[].class);

                // Example: overriding details for the first user in the array
                users[0].setUsername("NewUser");
                users[0].setFirstName("NewName");

                HttpResponse response = postClient.postRequest("/user/createWithArray", users);
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        @Test(priority = 1 , description = "This test creates multiple user from file ")
        public void createMultiUsersFromJsonFile() throws Exception {
                PostClient postClient = new PostClient();

                ObjectMapper mapper = new ObjectMapper();
                User[] users = mapper.readValue(new File(MULTIUSER_JSON_PATH), User[].class);

                HttpResponse response = postClient.postRequest("/user/createWithArray", users);
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        }


        //Update Tests

        @Test(priority = 2 , description = "This test updates a users which was created at step0")
        public void testUpdateUser() throws IOException {
                PutClient putClient = new PutClient();

                User updatedUser = new User();
                updatedUser.setId(101);
                updatedUser.setFirstName("Mack");
                updatedUser.setUsername("FromPUT");

                HttpResponse response = putClient.updateUser("FromPUT", updatedUser);

                int statusCode = response.getStatusLine().getStatusCode();
                Assert.assertEquals(statusCode, 200, "Expected status code to be 200, but got: " + statusCode);
        }

        @Test(priority = 3 , description = "This test updates a users using JSOMN file which was created at step1")
        public void testUpdateUserFromFile() throws IOException {
                PutClient putClient = new PutClient();
                ObjectMapper objectMapper = new ObjectMapper();

                // 1. Deserialize the JSON from the file to User object
                User user = objectMapper.readValue(new File(UPDATE_USER_JSON_PATH), User.class);

                // 2. Update the fields in the User object
                user.setFirstName("UpdatedFromFile");
                user.setUsername("FromPUTFile");

                // 3. Update the user
                HttpResponse response = putClient.updateUser("FromPUTFile", user);

                int statusCode = response.getStatusLine().getStatusCode();
                Assert.assertEquals(statusCode, 200, "Expected status code to be 200, but got: " + statusCode);
        }

        @Test(priority = 4 , description = "This test gives the details of the user updated in previous step")
        public void testGetUserByUsername() throws JsonProcessingException {
                String username = "FromPUTFile";
                String response = GetClient.getUser(username);

                // Parse the response to a POJO class for more specific assertions
                User user = new ObjectMapper().readValue(response, User.class);

                Assert.assertNotNull(response, "Response is null");
                Assert.assertEquals(user.getUsername(), username, "Fetched username does not match the requested one");
                Assert.assertEquals(user.getFirstName(), "UpdatedFromFile", "Fetched firstname does not match the requested one");

        }




}

