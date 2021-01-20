package routes;

import filters.JWTTokenNeeded;
import model.*;
import services.*;

import java.io.InputStream;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("user/")
@PermitAll
public class Users {
    @Inject public UserService userService;
    @Inject public PostService postService;
    @Inject public AbstractImageService imageService;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        return userService.getById(id)
                .map(user -> Response.ok(user.getPublicProfile()).build())
                .orElse(Response.status(400).entity("User not found").build());
    }

    @GET
    @Path("{id}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(@PathParam("id") int id) {
        try {
            return Response.ok(postService.getPostsByAuthorId(id)).build();
        } catch(Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("me")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response getMe(@Context ContainerRequestContext ctx) {
        return userService.getUserFromRequestContext(ctx).map(
                userContext -> userService.getById(userContext.id).map(
                    user -> {
                        return Response.status(200).entity(user).build();
                    }
            ).orElse(Response.status(500).entity("Internal error, user doesn't exist").build())               
        ).orElse(Response.status(500).entity("Internal error, can't find logged user").build());
    }

    @PUT
    @Path("me")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifyMe(User user) {
        try {
            return userService.update(user)
                    .map(userUpdated -> Response.ok(userUpdated).build())
                    .orElse(Response.status(400).entity("Internal error, cannot update logged user").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }


    @POST
    @Path("me/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response changeAvatar(
            @Context ContainerRequestContext ctx,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetails
    ) {
        try {
            Optional<User> userOpt = userService.getUserFromRequestContext(ctx);
            User userContext = userOpt.orElseThrow(
                    () -> new Exception("User logged in but can't find him in the context")
            );

            byte[] bytes = IOUtils.toByteArray(uploadedInputStream);
            String imageString = new String(bytes);
            if(!fileDetails.getFileName().equals("url")) {
                imageString = Base64.encodeBase64String(bytes);
            }

            Image image = imageService.postImage(imageString);

            return userService.getById(userContext.id).map(
                user -> {
                    user = new User(
                        user.username, 
                        user.settings, 
                        user.victories, 
                        user.score, 
                        user.theme_score, 
                        user.userlevel, 
                        user.participations, 
                        user.theme_participations, 
                        image.url, 
                        image.delete_url, 
                        user.theme, 
                        user.rank, 
                        user.id
                    );
                    
                    try {
                        return userService.update(user).map(
                            updatedUser -> {
                                return Response.ok(updatedUser).build();
                            }
                        ).orElse(Response.status(500).entity("Could not update the user").build());
                    } catch(Exception e) {
                        return Response.status(500).entity(e.getMessage()).build();
                    }
                }
            ).orElse(Response.status(500).entity("Could not find the logged in user").build());
        } catch(Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("leaderboard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeaderboard() {
        try {
            return Response.ok(userService.getLeaderboard()).build();
        } catch(Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("leaderboard/current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentLeaderboard() {
        try {
            return Response.ok(userService.getCurrentLeaderboard()).build();
        } catch(Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}


