package routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import filters.JWTTokenNeeded;
import model.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import services.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
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
    @Inject AbstractImageService imageService;
    @Inject ThemeService themeService;
    @Inject LabelService labelService;

    @Context private ResourceContext resourceContext;


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response addPost(
            @Context ContainerRequestContext ctx,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetails,
            @FormDataParam("post") String postJson
    ) {
        try {
            System.out.println("TTTTTTTTT Context object : "+ctx.getClass().toString());
            Optional<User> userOpt = userService.getUserFromRequestContext(ctx);
            User user = userOpt.orElseThrow(
                    () -> new Exception("User logged in but can't find username in context")
            );
            Theme theme = themeService.getCurrent().orElseThrow(
                () -> new Exception("No current theme")
            );
            ObjectMapper mapper = new ObjectMapper();
            Post post = mapper.readValue(postJson, Post.class);

            Label label = labelService.get(post.label).orElseThrow(
                () -> new Exception("Label not found : "+post.label)
            );
            String image64 = Base64.encodeBase64String(
                    IOUtils.toByteArray(uploadedInputStream)
            );

            Image image = imageService.postImage(image64);
            System.out.println("Image posted ! Link is : " + image.url);
            Post newPost = new Post(
                    post.title,
                    null,
                    null,
                    user,
                    label,
                    theme,
                    image.url,
                    image.delete_url
            );

            System.out.println("Create post : " + newPost);

            try {
                return postService.addOne(newPost)
                        .map(createdPost -> Response.ok(createdPost).build())
                        .orElse(Response.status(400).entity("Bad post format").build());
            } catch (SQLException e) {
                System.out.println("SQL Exception : " + e.getMessage());
                return Response.status(500).entity(e.getMessage()).build();
            } catch (Exception e) {
                System.out.println("Exception in add post : " + e.getMessage());
                return Response.status(500).entity(e.getMessage()).build();
            }
        } catch(Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") int id) {
        return postService.getById(id)
                .map(post -> Response.ok(post).build())
                .orElse(Response.status(400).entity("Post not found").build());
    }

    @GET
    @Path("theme/{themeId}")
    public Response getPostsByThemeId(@PathParam("themeId") int themeId) {
        try {
            List<Post> posts = postService.getPostsByThemeId(themeId);
            return Response.ok().entity(posts).build();
        } catch (Exception e) {
            return Response.status(500).entity("Internal error").build();
        }
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