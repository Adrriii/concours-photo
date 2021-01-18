package routes;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import services.*;

@Path("feed")
public class Feed {
    @Inject FeedService feedService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response feed(
        @DefaultValue("DESC") @QueryParam("sort") String sort, 
        @DefaultValue("score") @QueryParam("direction") String direction, 
        @DefaultValue("") @QueryParam("labels") String labels,
        @DefaultValue("1") @QueryParam("page") Integer page,
        @DefaultValue("15") @QueryParam("nbPosts") Integer nbPosts,
        @QueryParam("theme") Integer themeId
    ) {
        try {
            return Response.ok().entity(feedService.feedSearch(sort, direction, themeId, labels, page, nbPosts)).build();
        } catch(Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
