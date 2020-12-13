package services.implementation.imgur;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpImgur {
    static String BASE_URL = "http://api.imgur.com/";
    static String CLIENT_ID = "35720fa74170088";
    static ObjectMapper mapper = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
    );

    static <T> HttpEntityEnclosingRequestBase prepare(HttpEntityEnclosingRequestBase request, T item) throws IOException {
        request.addHeader("Authorization", "Client-ID "+CLIENT_ID);

        request.setEntity(new StringEntity(item.toString()));

        System.out.println(request.toString());

        return request;
    }

    static <T> HttpResponse postItem(String endpoint, T item) throws IOException {
        HttpPost request = new HttpPost(BASE_URL + endpoint);

        return HttpClientBuilder.create().build().execute(prepare(request, item));
    }

    static <T> HttpResponse putItem(String endpoint, T item) throws IOException {
        HttpPut request = new HttpPut(BASE_URL + endpoint);

        return HttpClientBuilder.create().build().execute(prepare(request, item));
    }

    static <T> T getItemFromResponse(Class<T> type, HttpResponse response) throws IOException {
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        return mapper.readValue(jsonFromResponse, type);
    }

    static HttpResponse get(String endpoint) throws IOException {
        HttpUriRequest request = new HttpGet(BASE_URL + endpoint);
        return HttpClientBuilder.create().build().execute(request);
    }

    static HttpResponse delete(String endoint) throws IOException {
        HttpDelete delete = new HttpDelete(BASE_URL + endoint);
        return HttpClientBuilder.create().build().execute(delete);
    }
}
