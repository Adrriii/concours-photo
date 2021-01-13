package routes;

import model.Comment;
import model.Post;
import model.User;
import services.AuthenticationService;
import services.UserService;
import services.ReactionService;

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

@Path("user/")
@PermitAll
public class Users {
    @Inject UserService userService;
    @Inject AuthenticationService authenticationService;

    @Context private ResourceContext resourceContext;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        return userService.getById(id)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(400).entity("User not found").build());
    }
}