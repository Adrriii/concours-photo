package services;

import java.io.InputStream;

import model.Image;

public interface AbstractImageService {
    Image postImage(InputStream imageData) throws Exception;
    Image postImage(String imageUrl) throws Exception;
}
