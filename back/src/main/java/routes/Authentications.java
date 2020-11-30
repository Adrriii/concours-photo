package routes;

import dao.UserDao;
import model.User;
import services.AuthenticationService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("")
class Authentications {
    @Inject UserDao userDao;

    @POST
    @Path("login")
    @Consumes("application/json")
    public Response login(
        @Context HttpServletRequest req,
        @FormParam("username") String username,
        @FormParam("passwordHash") String passwordHash
    ) {
        try {
            User currentUser = userDao.getByLogin(username, passwordHash);
    
            HttpSession session = req.getSession(true);
            session.setAttribute("user", currentUser);
    
            return Response.status(Response.Status.OK).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("logout")
    public Response logout(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        session.setAttribute("user", null);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("register")
    @Consumes("application/json")
    public Response register(
        @Context HttpServletRequest req,
        @FormParam("user") String username,
        @FormParam("passwordHash") String passwordHash
    ) {
        try {
            userDao.getByUsername(username);

            return Response.status(400).entity("This username is already taken !").build();
        } catch (Exception e) {
            User newUser = null;
            try {
                newUser = AuthenticationService.registerUser(username, passwordHash);
            } catch (Exception exception) {
                return Response.status(500).entity("Unknown error : " + exception.getMessage()).build();
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("user", newUser);
    
            return Response.status(Response.Status.OK).build();
        }
    }
}