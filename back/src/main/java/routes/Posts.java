package routes;

import filters.JWTTokenNeeded;
import model.Comment;
import model.Post;
import model.User;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import services.AuthenticationService;
import services.PostService;
import services.ReactionService;
import services.UserService;

import java.io.InputStream;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("posts/")
public class Posts {
    @Inject PostService postService;
    @Inject ReactionService reactionService;
    @Inject UserService userService;

    @Context private ResourceContext resourceContext;


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response addPost(
            @Context ContainerRequestContext ctx,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetails,
            @FormDataParam("post") Post post
    ) throws Exception {
        Optional<User> userOpt = userService.getUserFromRequestContext(ctx);
        User user = userOpt.orElseThrow(
                () -> new Exception("User logged in but can't find username in context")
        );

        System.out.println("[Posts - Route] -> Receive request from " + user.username);
        System.out.println("Filename is -> " + fileDetails.getFileName());

        try {
            return postService.addOne(post)
                    .map(newPost -> Response.ok(newPost).build())
                    .orElse(Response.status(400).entity("Bad post format").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }

/*
        String uploadedFileLocation = "/Users/temp/" + fileDetails.getFileName();

        // save it
        writeToFile(uploadedInputStream, uploadedFileLocation);

        String output = "File uploaded to : " + uploadedFileLocation;

        ResponseBean responseBean = new ResponseBean();

        responseBean.setCode(StatusConstants.SUCCESS_CODE);
        responseBean.setMessage(fileDetails.getFileName());
        responseBean.setResult(null);
        return responseBean;*/
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
    @JWTTokenNeeded
    public Response changeReactToPost(@Context ContainerRequestContext ctx, @PathParam("id") int id, String reaction) {

        Optional<User> userOp = userService.getUserFromRequestContext(ctx);

        if (userOp.isPresent()) {
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
    @JWTTokenNeeded
    public Response addReactToPost(@Context ContainerRequestContext ctx, @PathParam("id") int id, String reaction) {
        return changeReactToPost(ctx, id, reaction);
    }

    @DELETE
    @Path("{id}/react")
    @JWTTokenNeeded
    public Response cancelReactToPost(@Context ContainerRequestContext ctx, @PathParam("id") int id) {
        Optional<User> userOp = userService.getUserFromRequestContext(ctx);

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