package routes;

import filters.JWTTokenNeeded;
import services.UserService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user/")
@PermitAll
public class Users {
    @Inject UserService userService;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        return userService.getById(id)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(400).entity("User not found").build());
    }

    @GET
    @Path("me")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response getMe(@Context ContainerRequestContext ctx) {
        return userService.getUserFromRequestContext(ctx).map(
                user -> Response.ok().entity(user).build()
        ).orElse(Response.status(500).entity("Internal error, can't find logged user").build());
    }
}