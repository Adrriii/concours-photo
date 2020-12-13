package services.implementation.imgur;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import services.AbstractImageService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import model.Image;

public class ImgurImageService implements AbstractImageService {

    @Override
    public Image postImage(InputStream imageData) throws Exception {

        //System.out.println(HttpImgur.postItem("upload", imageData).toString());

        return new Image();
    }

    @Override
    public Image postImage(String imageUrl) throws Exception {
        List<NameValuePair> params = Arrays.asList(new BasicNameValuePair("image", imageUrl));
        
        JSONObject response = HttpImgur.post("image", params);

        return new Image(response.getString("link"), response.getString("deletehash"));
    }
    
}
