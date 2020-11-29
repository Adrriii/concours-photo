package routes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("helloworld/")
public class Hello {
    @GET
    public String helloWorld() {
        return "Hello world !";
    }
}
