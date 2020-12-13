package services.implementation.imgur;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpImgur {
    static String BASE_URL = "https://api.imgur.com/3/";
    static String CLIENT_ID = "35720fa74170088";
    static ObjectMapper mapper = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
    );

    static HttpEntityEnclosingRequestBase prepare(HttpEntityEnclosingRequestBase request, String imageUrl) throws IOException {

        request.addHeader("Authorization", "Client-ID "+CLIENT_ID);

        List<NameValuePair> params = Arrays.asList(new BasicNameValuePair("image", imageUrl));
        request.setEntity(new UrlEncodedFormEntity(params));
        System.out.println("Request is -> " + request.toString());

        return request;
    }

    static HttpResponse makePost(String endpoint, String imageUrl) throws IOException {
        HttpPost request = new HttpPost(BASE_URL + endpoint);
        return HttpClientBuilder.create().build().execute(prepare(request, imageUrl));
    }
}
