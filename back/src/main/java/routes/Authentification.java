package routes;

import dao.UserDao;
import jdk.internal.net.http.Response;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.inject.Inject;

class Authentification {
    @Inject UserDao userDao;

    @POST
    @Path("login")
    @Consumes("application/json")
    public Response login(
        @Context HttpServletRequest req,
        @PathParam("username") String username,
        @PathParam("passwordHash") String passwordHash
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
        @PathParam("user") User user
    ) {
        try {
            userDao.getByUsername(user.username);

            return Response.status(400).entity("This username is already taken !");
        } catch (Exception e) {
            userDao.insert(user);
            
            HttpSession session = req.getSession(true);
            session.setAttribute("user", currentUser);
    
            return Response.status(Response.Status.OK).build();
        }
    }
}