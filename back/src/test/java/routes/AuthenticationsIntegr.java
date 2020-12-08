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

class AuthenticationsIntegr {
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
        request.addHeader("Content-Type", "application/json;charset=UTF-8");
        request.setEntity(new StringEntity("{\"username\": \"robin1\",\"passwordHash\":\"123456NRSTabép\"}", "UTF-8"));

        CloseableHttpResponse response = HttpClients.createDefault().execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testRegisterUserThatAlreadyExist() throws IOException {
        HttpPost request = new HttpPost("http://localhost:9000/api/v1/register");
        request.addHeader("Content-Type", "application/json;charset=UTF-8");
        request.setEntity(new StringEntity("{\"username\": \"robin2\",\"passwordHash\":\"123456NRSTabép\"}", "UTF-8"));

        CloseableHttpResponse response = HttpClients.createDefault().execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());

        request = new HttpPost("http://localhost:9000/api/v1/register");
        request.addHeader("Content-Type", "application/json;charset=UTF-8");
        request.setEntity(new StringEntity("{\"username\": \"robin2\",\"passwordHash\":\"123456NRSTabép\"}", "UTF-8"));

        response = HttpClients.createDefault().execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testLogin() throws IOException {
        // Create user
        HttpPost request = new HttpPost("http://localhost:9000/api/v1/register");
        request.addHeader("Content-Type", "application/json;charset=UTF-8");
        request.setEntity(new StringEntity("{\"username\": \"robin4\",\"passwordHash\":\"123456NRSTabép\"}", "UTF-8"));

        CloseableHttpResponse response = HttpClients.createDefault().execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());

        // try login
        request = new HttpPost("http://localhost:9000/api/v1/login");
        request.addHeader("Content-Type", "application/json;charset=UTF-8");
        request.setEntity(new StringEntity("{\"username\": \"robin4\",\"passwordHash\":\"123456NRSTabép\"}", "UTF-8"));

        response = HttpClients.createDefault().execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());

        // try with bad password
        request = new HttpPost("http://localhost:9000/api/v1/login");
        request.addHeader("Content-Type", "application/json;charset=UTF-8");
        request.setEntity(new StringEntity("{\"username\": \"robin4\",\"passwordHash\":\"1234NRSTabép\"}", "UTF-8"));

        response = HttpClients.createDefault().execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());


        // try with bad username
        request = new HttpPost("http://localhost:9000/api/v1/login");
        request.addHeader("Content-Type", "application/json;charset=UTF-8");
        request.setEntity(new StringEntity("{\"username\": \"jenexistepas\",\"passwordHash\":\"123456NRSTabép\"}", "UTF-8"));

        response = HttpClients.createDefault().execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }
}