package routes;

import model.Label;
import services.LabelService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("labels")
public class Labels {
    @Inject
    LabelService labelService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        try {
            return Response.ok().entity(labelService.getAll()).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Label label) {
        try {
            return Response.ok().entity(labelService.addOne(label)).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Label label) {
        try {
            labelService.deleteOne(label);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }
}
