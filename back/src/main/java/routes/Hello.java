package routes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import services.implementation.imgur.ImgurImageService;

@Path("helloworld/")
public class Hello {
    @GET
    public String helloWorld() throws Exception {
        new ImgurImageService().postImage("https://i.imgur.com/Xz0uxJ0.jpeg");
        return "Hello world !";
    }
}
