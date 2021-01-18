package services;

import model.Image;

public interface AbstractImageService {
    Image postImage(String image) throws Exception;
    void deleteImage(String image) throws Exception;
}
