package services.implementation.imgur;

import java.io.InputStream;

import services.AbstractImageService;

public class ImgurImageService implements AbstractImageService {

    @Override
    public String postImage(InputStream imageData) throws Exception {

        System.out.println(HttpImgur.postItem("upload", imageData).toString());

        return "";
    }

    @Override
    public String postImage(String imageUrl) throws Exception {

        System.out.println(HttpImgur.postItem("upload", imageUrl).toString());

        return "";
    }
    
}
