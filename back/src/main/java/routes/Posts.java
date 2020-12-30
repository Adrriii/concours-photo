package routes;

import model.Post;
import model.User;
import services.AuthenticationService;
import services.PostService;
import services.ReactionService;

import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("posts/")
@PermitAll
public class Posts {
    @Inject PostService postService;
    @Inject ReactionService reactionService;
    @Inject AuthenticationService authenticationService;
    
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

    @PUT
    @RolesAllowed("user")
    @Path("{id}/react")
    public Response changeReactToPost(@Context HttpServletRequest req, @PathParam("id") int id, String reaction) {
        Optional<User> userOp = authenticationService.getCurrentUser(req);
        if(userOp.isPresent()) {
            if(reactionService.changeReactToPost(id, userOp.get().id, reaction)) {
                return Response.ok().build();
            } else {
                return Response.status(400).entity("Could not react to this post").build();
            }
        } else {
            return Response.status(403).entity("Must be logged in to react to a post").build();
        }
    }

    @POST
    @RolesAllowed("user")
    @Path("{id}/react")
    public Response addReactToPost(@Context HttpServletRequest req, @PathParam("id") int id, String reaction) {
        return changeReactToPost(req, id, reaction);
    }

    @DELETE
    @RolesAllowed("user")
    @Path("{id}/react")
    public Response cancelReactToPost(@Context HttpServletRequest req, @PathParam("id") int id) {
        Optional<User> userOp = authenticationService.getCurrentUser(req);
        if(userOp.isPresent()) {
            if(reactionService.cancelReactToPost(id, userOp.get().id)) {
                return Response.ok().build();
            } else {
                return Response.status(400).entity("Could not cancel the reaction to this post").build();
            }
        } else {
            return Response.status(403).entity("Must be logged in to remove a reaction to a post").build();
        }
    }

    
}