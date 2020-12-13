package services;

import java.io.InputStream;

public interface AbstractImageService {
    String postImage(InputStream imageData) throws Exception;
    String postImage(String imageUrl) throws Exception;
}
