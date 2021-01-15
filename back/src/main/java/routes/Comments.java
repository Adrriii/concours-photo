package routes;

import model.*;
import services.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("comments/")
public class Comments {
    @Inject CommentService commentService;

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response replyToComment(@PathParam("parentId") int id, Comment comment) {
        try {
            return commentService.replyToComment(id, comment)
                    .map(newComment -> Response.ok(newComment).build())
                    .orElse(Response.status(400).entity("Invalid parentId").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modify(@PathParam("id") int id, Comment comment) {
        try {
            return commentService.updateComment(comment)
                    .map(newComment -> Response.ok(newComment).build())
                    .orElse(Response.status(400).entity("Invalid parentId").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        try {
            commentService.deleteComment(id);
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    public Response replyToPost(int postId, Comment comment) {
        try {
            return commentService.replyToPost(postId, comment)
                    .map(newComment -> Response.ok(newComment).build())
                    .orElse(Response.status(400).entity("Invalid post id").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}