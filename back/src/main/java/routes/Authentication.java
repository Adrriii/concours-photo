package routes;

import model.UserAuth;
import services.AuthenticationService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@PermitAll
public class Authentication {
    @Inject AuthenticationService authenticationService;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(
            @Context HttpServletRequest req,
            UserAuth user
    ) {
        try {
            return authenticationService.loginUser(user.username, user.passwordHash).map(
                    userLog -> {
                        req.getSession(true).setAttribute("user", userLog);
                        return Response.ok().build();
                    }
            ).orElse(Response.status(400).entity("User or password incorrect.").build());

        } catch (Exception e) {
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage().toString()).build();
        }
    }

    @GET
    @RolesAllowed("user")
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
            UserAuth user
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
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
