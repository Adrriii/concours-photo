package services.implementation.imgur;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import services.AbstractImageService;

public class ImgurImageService implements AbstractImageService {

    @Override
    public String postImage(InputStream imageData) throws Exception {

        //System.out.println(HttpImgur.postItem("upload", imageData).toString());

        return "";
    }

    @Override
    public String postImage(String imageUrl) throws Exception {
        System.out.println("Posting image with url " + imageUrl);
        HttpResponse response = HttpImgur.makePost("image", imageUrl);

        System.out.println("Status code is : " + response.getStatusLine().getStatusCode());

        return "";
    }
    
}
