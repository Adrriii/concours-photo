package services.implementation.imgur;

import java.util.Arrays;
import java.util.List;

import services.AbstractImageService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import model.Image;

public class ImgurImageService implements AbstractImageService {

    @Override
    public Image postImage(String image) throws Exception {
        List<NameValuePair> params = Arrays.asList(new BasicNameValuePair("image", image));
        
        JSONObject response = HttpImgur.post("image", params);

        return new Image(response.getString("link"), response.getString("deletehash"));
    }

    @Override
    public void deleteImage(String image) throws Exception {
        HttpImgur.delete("image/"+image);
    }
    
}
