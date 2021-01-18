package routes;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import filters.JWTTokenOptional;
import model.Post;
import model.User;
import services.*;

import java.util.List;
import java.util.Optional;

@Path("feed")
public class Feed {
    @Inject
    FeedService feedService;
    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenOptional
    public Response feed(
            @Context ContainerRequestContext ctx,
            @DefaultValue("DESC") @QueryParam("direction") String direction,
            @DefaultValue("score") @QueryParam("sort") String sort,
            @DefaultValue("") @QueryParam("labels") String labels,
            @DefaultValue("1") @QueryParam("page") Integer page,
            @DefaultValue("15") @QueryParam("nbPosts") Integer nbPosts,
            @QueryParam("theme") Integer themeId
    ) {
        try {
            return userService.getUserFromRequestContext(ctx)
                .map(user -> {
                    try {
                        return Response.ok().entity(feedService.feedSearchForUser(user, sort, direction, themeId, labels, page, nbPosts)).build();
                    } catch (Exception e) {
                        return Response.status(500).entity(e.getMessage()).build();
                    }
                })
                .orElseGet(() -> {
                    try {
                        return Response.ok().entity(feedService.feedSearch(sort, direction, themeId, labels, page, nbPosts)).build();
                    } catch (Exception e) {
                        return Response.status(500).entity(e.getMessage()).build();
                    }
                });
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
