package routes;

import model.Theme;
import services.AuthenticationService;
import services.ThemeService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("themes")
@PermitAll
public class Themes {
    @Inject AuthenticationService authenticationService;
    @Inject ThemeService themeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllThemes() {
        try {
            return Response.ok(themeService.getAll()).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTheme(@Context HttpServletRequest req, Theme theme) {
        return authenticationService.getCurrentUser(req).map(currentUser ->
            themeService.proposeTheme(theme, currentUser)
                .map(newTheme -> Response.ok(newTheme).build())
                .orElse(Response.status(400).entity("Bad theme format or theme already exists").build())
        ).orElse(Response.status(400).entity("User not logged in!").build());
    }

    @GET
    @Path("current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrent() {
        try {
            return themeService.getCurrent()
                    .map(current -> Response.ok(current).build())
                    .orElse(Response.status(400).entity("No current theme").build());
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("proposals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProposals() {
        try {
            return Response.ok(themeService.getProposals()).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @GET
    @RolesAllowed("user")
    @Path("proposals/vote")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserVote(@Context HttpServletRequest req) {
        return authenticationService.getCurrentUser(req)
                .map(currentUser -> {
                    try {
                        return themeService.getCurrentUserVote(currentUser)
                                .map(theme -> Response.ok(theme).build())
                                .orElse(Response.status(400).entity("There is no vote for current user").build());
                    } catch (Exception e) {
                        return Response.status(500).entity(e.getMessage()).build();
                    }
                }).orElse(Response.status(400).entity("User not logged in!").build());
    }

    @POST
    @RolesAllowed("user")
    @Path("proposals/vote/{proposalId}")
    public Response setUserProposal(@Context HttpServletRequest req, @PathParam("proposalId") int proposalId) {
        return authenticationService.getCurrentUser(req).map(user -> {
            try {
                themeService.setUserVote(user, proposalId);
            } catch (Exception e) {
                return Response.status(500).entity(e.getMessage()).build();
            }

            return Response.ok().build();
        }).orElse(Response.status(400).entity("Bad proposal id or user not logged in.").build());
    }

    @DELETE
    @RolesAllowed("user")
    @Path("proposals/vote/{proposalId}")
    public Response removeUserProposal(@Context HttpServletRequest req) {
        return authenticationService.getCurrentUser(req).map(user -> {
            try {
                themeService.deleteUserVote(user);
            } catch (Exception e) {
                return Response.status(500).entity(e.getMessage()).build();
            }

            return Response.ok().build();
        }).orElse(Response.status(400).entity("User not logged in!").build());
    }
}
