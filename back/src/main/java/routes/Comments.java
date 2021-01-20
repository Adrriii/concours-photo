package routes;

import model.*;
import services.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import filters.JWTTokenNeeded;

@Path("comments/")
public class Comments {
    @Inject public CommentService commentService;
    @Inject public UserService userService;

    @GET
    @Path("{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCommentsForPost(@PathParam("postId") int postId) {
        try {
            return Response.ok(commentService.getAllForPost(postId)).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{parentId}/reply")
    @JWTTokenNeeded
    @Consumes(MediaType.APPLICATION_JSON)
    public Response replyToComment(@Context ContainerRequestContext ctx, @PathParam("parentId") int id, Comment comment) {
        try {
            return commentService.replyToComment(userService.getUserFromRequestContext(ctx).get(), id, comment)
                    .map(newComment -> Response.ok(newComment).build())
                    .orElse(Response.status(400).entity("Invalid parentId").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @JWTTokenNeeded
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modify(@Context ContainerRequestContext ctx, @PathParam("id") int id, Comment comment) {
        try {
            return commentService.updateComment(userService.getUserFromRequestContext(ctx).get(), comment)
                    .map(newComment -> Response.ok(newComment).build())
                    .orElse(Response.status(400).entity("Invalid parentId").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    @JWTTokenNeeded
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@Context ContainerRequestContext ctx, @PathParam("id") int id) {
        try {
            commentService.deleteComment(userService.getUserFromRequestContext(ctx).get(), commentService.getById(id).get());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    public Response replyToPost(ContainerRequestContext ctx, int postId, Comment comment) {
        try {
            return commentService.replyToPost(userService.getUserFromRequestContext(ctx).get(), postId, comment)
                    .map(newComment -> Response.ok(newComment).build())
                    .orElse(Response.status(400).entity("Invalid post id").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}