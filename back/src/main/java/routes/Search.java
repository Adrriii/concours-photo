package routes;

import services.*;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("search/")
@PermitAll
public class Search {
    @Inject public SearchService searchService;
    @Inject public AuthenticationService authenticationService;

    @Context private ResourceContext resourceContext;

    @GET
    @Path("{item}/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("item") String item, @PathParam("term") String term) {
        List results;

        switch(item) {
            case "user":
                results = searchService.searchUser(term);
                break;
            case "comment":
                results = searchService.searchComment(term);
                break;
            case "post":
                results = searchService.searchPost(term);
                break;
            default:
                return Response.status(400).entity("Bad item parameter (user,comment,post)").build();
        }
        return Response.ok(results).build();
    }
}