package routes;

import model.Comment;
import model.Post;
import model.User;
import org.glassfish.jersey.media.multipart.FormDataParam;
import services.AuthenticationService;
import services.PostService;
import services.ReactionService;

import java.io.InputStream;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("posts/")
public class Posts {
    @Inject PostService postService;
    @Inject ReactionService reactionService;
    @Inject AuthenticationService authenticationService;

    @Context private ResourceContext resourceContext;


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPost(
            @FormDataParam("file") InputStream fileInputStream
    ) {
        System.out.println("Add post -> receive " + fileInputStream);
        /*try {
            return postService.addOne(post)
                    .map(newPost -> Response.ok(newPost).build())
                    .orElse(Response.status(400).entity("Bad post format").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }*/

        return Response.status(200).entity(null).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") int id) {
        return postService.getById(id)
                .map(post -> Response.ok(post).build())
                .orElse(Response.status(400).entity("Post not found").build());
    }

    @PUT
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
    @Path("{id}/react")
    public Response addReactToPost(@Context HttpServletRequest req, @PathParam("id") int id, String reaction) {
        return changeReactToPost(req, id, reaction);
    }

    @DELETE
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

    @GET
    @Path("{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllComments(@PathParam("id") int id) {
        return resourceContext.getResource(Comments.class).getAllCommentsForPost(id);
    }

    @POST
    @Path("{id}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(@PathParam("id") int id, Comment comment) {
        return resourceContext.getResource(Comments.class).replyToPost(id, comment);
    }

    @PUT
    @Path("{id}/comments/{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifyComment(@PathParam("id") int id, Comment comment) {
        return resourceContext.getResource(Comments.class).modify(id, comment);
    }

    @DELETE
    @Path("{id}/comments/{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifyComment(@PathParam("id") int id) {
        return resourceContext.getResource(Comments.class).delete(id);
    }
}