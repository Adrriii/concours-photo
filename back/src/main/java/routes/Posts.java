package routes;

import dao.PostDao;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("posts/")
public class Posts {
    @Inject PostDao postDao;
    
    @POST
    public Response getAll() {
        return Response.ok().build();
    }
}