package routes;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationsTest {
    static CloseableHttpClient clients;

    @BeforeAll
    static void beforeAll() {
        clients = HttpClients.createDefault();
    }

    @AfterAll
    static void afterAll() throws IOException {
        clients.close();
    }

    static CloseableHttpClient getClients() {
        return clients;
    }

    @Test
    public void testRegister() throws IOException {
        HttpPost request = new HttpPost("http://localhost:9000/api/v1/register");
        request.addHeader("content-type", "application/json");
        request.setEntity(new StringEntity("{username: \"robin\",passwordHash:\"123456NRSTabép\"}"));

        CloseableHttpResponse response = HttpClients.createDefault().execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }
}