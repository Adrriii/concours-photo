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
        
        if(image.contains("https://i.imgur.com/")) {
            return new Image(image, "");
        }
        
        JSONObject response = HttpImgur.post("image", params);
        
        try {
            return new Image(response.getString("link"), response.getString("deletehash"));
        } catch(Exception e) {
            System.out.println("Communication with imgur failed "+response.toString());

            if(image.contains("https://")) {
                return new Image(image, "");
            } else {
                throw new Exception("Imgur failed to parse image.");
            }
        }
    }

    @Override
    public void deleteImage(String image) throws Exception {
        HttpImgur.delete("image/"+image);
    }
    
}
