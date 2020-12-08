package routes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtils {
    static String baseUrl = "http://localhost:9000/api/v1/";
    static ObjectMapper mapper = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
    );

    static <T> HttpResponse postItem(String endpoint, T item) throws IOException {
        HttpPost request = new HttpPost(baseUrl + endpoint);
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-type", "application/json");

        request.setEntity(new StringEntity(mapper.writeValueAsString(item)));

        return HttpClientBuilder.create().build().execute(request);
    }

    static <T> HttpResponse putItem(String endpoint, T item) throws IOException {
        HttpPut request = new HttpPut(baseUrl + endpoint);
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-type", "application/json");

        request.setEntity(new StringEntity(mapper.writeValueAsString(item)));

        return HttpClientBuilder.create().build().execute(request);
    }

    static <T> T getItemFromResponse(Class<T> type, HttpResponse response) throws IOException {
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        return mapper.readValue(jsonFromResponse, type);
    }

    static HttpResponse get(String endpoint) throws IOException {
        HttpUriRequest request = new HttpGet(baseUrl + endpoint);
        return HttpClientBuilder.create().build().execute(request);
    }

    static HttpResponse delete(String endoint) throws IOException {
        HttpDelete delete = new HttpDelete(baseUrl + endoint);
        return HttpClientBuilder.create().build().execute(delete);
    }
}
