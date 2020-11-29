package routes;

@Path("posts/")
public class Posts {
    @Inject PostDao postDao;
    
    @POST
    public Response getAll() {

    }
}