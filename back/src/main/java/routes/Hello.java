package routes;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import services.AbstractImageService;

@Path("helloworld/")
public class Hello {
    @Inject AbstractImageService imageService;

    @GET
    public String helloWorld() throws Exception {
        imageService.postImage("https://www.redwolf.in/image/catalog/stickers/bongo-cat-sticker.jpg");
        return "Hello world !";
    }
}
