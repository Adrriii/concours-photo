package services.implementation.imgur;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

public class HttpImgur {
    static String BASE_URL = "https://api.imgur.com/3/";
    static String CLIENT_ID = "35720fa74170088";
    static ObjectMapper mapper = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
    );

    static HttpRequestBase prepare(HttpRequestBase request) {
        request.addHeader("Authorization", "Client-ID "+CLIENT_ID);

        return request;
    }

    static HttpEntityEnclosingRequestBase prepareEntity(HttpEntityEnclosingRequestBase request, List<NameValuePair> params) throws IOException {
        prepare(request);

        request.setEntity(new UrlEncodedFormEntity(params));

        return request;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
    
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    static JSONObject requestJSON(HttpRequestBase request) throws IOException {
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        HttpEntity entity = response.getEntity();
        InputStream instream = entity.getContent();
        String result = convertStreamToString(instream);
        instream.close();

        JSONObject responseJSON = new JSONObject(result);

        Boolean success = responseJSON.getBoolean("success");
        int status = responseJSON.getInt("status");

        JSONObject returned = null;
        try {
            returned = responseJSON.getJSONObject("data");
        } catch(Exception ignore) {
            returned = new JSONObject();
            returned.append("response", responseJSON.get("data"));
        }

        return returned;
    }

    static JSONObject post(String endpoint, List<NameValuePair> params) throws IOException {
        HttpPost request = new HttpPost(BASE_URL + endpoint);
        return requestJSON(prepareEntity(request, params));
    }

    static void delete(String endpoint) throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + endpoint);
        requestJSON(prepare(request));
    }
}
