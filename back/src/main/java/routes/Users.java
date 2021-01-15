package routes;

import filters.JWTTokenNeeded;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.User;
import services.UserService;


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
}
