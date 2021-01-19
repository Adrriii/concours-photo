package routes;

import filters.JWTTokenNeeded;
import filters.JWTTokenOptional;
import model.Theme;
import services.ThemeService;
import services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("themes")
@JWTTokenOptional
public class Themes {
    @Inject UserService userService;
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTheme(@Context ContainerRequestContext ctx, Theme theme) {
        return userService.getUserFromRequestContext(ctx).map(currentUser ->
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
    @Path("proposals/vote")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response getUserVote(@Context ContainerRequestContext ctx) {
        return userService.getUserFromRequestContext(ctx)
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
    @Path("proposals/vote/{proposalId}")
    public Response setUserProposal(@Context ContainerRequestContext ctx, @PathParam("proposalId") int proposalId) {
        return userService.getUserFromRequestContext(ctx).map(user -> {
            try {
                themeService.setUserVote(user, proposalId);
            } catch (Exception e) {
                return Response.status(500).entity(e.getMessage()).build();
            }

            return Response.ok().build();
        }).orElse(Response.status(400).entity("Bad proposal id or user not logged in.").build());
    }

    @DELETE
    @Path("proposals/vote/{proposalId}")
    public Response removeUserProposal(@Context ContainerRequestContext ctx) {
        return userService.getUserFromRequestContext(ctx).map(user -> {
            try {
                themeService.deleteUserVote(user);
            } catch (Exception e) {
                return Response.status(500).entity(e.getMessage()).build();
            }

            return Response.ok().build();
        }).orElse(Response.status(400).entity("User not logged in!").build());
    }
}




