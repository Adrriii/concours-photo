package routes;

import dao.PostDao;
import model.Post;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("posts/")
public class Posts {
    //TODO Créer un service et l'utiliser à la place du dao
    @Inject PostDao postDao;
    
    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPost(Post post) {
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") int id) {
        return Response.ok().build();
    }


}