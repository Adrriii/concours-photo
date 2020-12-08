package routes;

import model.SimpleUser;
import services.AuthenticationService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class Authentication {
    @Inject AuthenticationService authenticationService;

    @POST
    @Path("login")
    @Consumes("application/json")
    public Response login(
            @Context HttpServletRequest req,
            @FormParam("username") String username,
            @FormParam("passwordHash") String passwordHash
    ) {
        try {
            return authenticationService.loginUser(username, passwordHash).map(
                    user -> {
                        req.getSession(true).setAttribute("user", user);
                        return Response.ok().build();
                    }
            ).orElse(Response.status(Response.Status.NOT_FOUND).entity("User or password incorrect.").build());

        } catch (Exception e) {
            e.printStackTrace();
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage().toString()).build();
        }
    }

    @GET
    @Path("logout")
    public Response logout(@Context HttpServletRequest req) {
        req.getSession(true).setAttribute("user", null);

        return Response.ok().build();
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(
            @Context HttpServletRequest req,
            SimpleUser user
    ) {
        System.out.println("Receive register request, user: " + user.username + ", password: " + user.passwordHash);
        try {
            return authenticationService.registerUser(user.username, user.passwordHash).map(
                    userRegister -> {
                        req.getSession(true).setAttribute("user", userRegister);
                        return Response.ok().build();
                    }
            ).orElse(Response.status(400).entity("This username is already taken !").build());
        } catch (Exception e) {
            e.printStackTrace();
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
