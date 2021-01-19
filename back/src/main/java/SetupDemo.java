import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.server.ContainerRequest;

import dao.sql.SqlDatabase;
import dao.sql.SqlLabelDao;
import dao.sql.SqlPostDao;
import dao.sql.SqlReactionDao;
import dao.sql.SqlThemeDao;
import dao.sql.SqlUserDao;
import model.*;
import routes.*;
import services.*;

import services.implementation.imgur.ImgurImageService;
import util.*;

public class SetupDemo {
    static boolean done = false;

    Themes themesRoute;
    Authentication authenticationRoute;
    Posts postsRoute;
    Users usersRoute;

    public void run() {
        if (done)
            return;

        try {
            manualBinding();
            waitForDB();

            createDemo();
            
            done = true;
        } catch (Exception e) {
            System.out.println("Plante");
            e.printStackTrace();
        }
    }

    public void createDemo() throws Exception {
        System.out.println("---- START DEMO INSERT ----");

        // Register users
        ContainerRequest adriCtx = createUser("Adri",
                "ab628dfe91bd9afd73bc53e5e105da9e3a97a04dd8dab1a3ddea1d921848d68e");
        ContainerRequest coucouCtx = createUser("coucou",
                "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest alexandreCtx = createUser("Alexandre",
                "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest JDCtx = createUser("JD",
                "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");

        ContainerRequest h1 = createUser("Hater1",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest h2 = createUser("Hater2",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest h3 = createUser("Hater3",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest h4 = createUser("Hater4",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest h5 = createUser("Hater5",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");

        // Set avatars
        changeAvatar(adriCtx, "https://a.ppy.sh/4579132?1594553960.png");

        // Create themes
        System.out.println("---- INSERT THEMES ----");
        themesRoute.themeService.themeDao.insert(new Theme("Architecture", "", "active", "2021-01-04"));
        themesRoute.addTheme(adriCtx, new Theme("Les Chiens", "", null, "2021-01-11"));
        themesRoute.addTheme(coucouCtx, new Theme("Les Chats", "", null, "2021-01-11"));
        themesRoute.addTheme(alexandreCtx, new Theme("Les Giraffes", "", null, "2021-01-11"));
        themesRoute.addTheme(JDCtx, new Theme("Les Oiseaux", "", null, "2021-01-11"));

        // Make users vote
        System.out.println("---- THEME VOTE ----");
        themesRoute.setUserProposal(adriCtx, 5);
        themesRoute.setUserProposal(coucouCtx, 5);
        themesRoute.setUserProposal(alexandreCtx, 4);
        themesRoute.setUserProposal(JDCtx, 3);

        // Creates posts
        System.out.println("---- INSERT POSTS ----");
        createPost(adriCtx, "https://whc.unesco.org/uploads/thumbs/site_0252_0008-1200-630-20151104113424.jpg", "Le Taj Mahal", "Building");
        createPost(adriCtx, "https://www.bautrip.com/images/what-to-visit/burj-khalifa.jpg", "Le Burj Khalifa", "Building");
        createPost(adriCtx, "https://cdn.paris.fr/paris/2020/05/12/huge-67a65318e89c13e2b63ddbe2bb89cc3c.jpg", "La Tour Eiffel", "Building");
        createPost(coucouCtx, "https://cdn.discordapp.com/attachments/765145671065665539/801146136839716884/archi.png", "De l'architecture", "Building");
        createPost(coucouCtx, "https://static.wikia.nocookie.net/lotr/images/e/e4/Minas_Tirith.jpg/revision/latest/scale-to-width-down/1000?cb=20141228214636", "Minas Tirith", "Building");

        // React to posts
        System.out.println("---- INSERT REACTIONS ----");
        postsRoute.addReactToPost(adriCtx, 1, "like");
        postsRoute.addReactToPost(adriCtx, 2, "like");
        postsRoute.addReactToPost(adriCtx, 3, "like");
        postsRoute.addReactToPost(adriCtx, 4, "like");
        postsRoute.addReactToPost(coucouCtx, 1, "dislike");
        postsRoute.addReactToPost(coucouCtx, 2, "dislike");
        postsRoute.addReactToPost(coucouCtx, 3, "like");
        postsRoute.addReactToPost(coucouCtx, 4, "like");
        postsRoute.addReactToPost(alexandreCtx, 1, "dislike");
        postsRoute.addReactToPost(alexandreCtx, 4, "like");
        postsRoute.addReactToPost(JDCtx, 1, "dislike");
        postsRoute.addReactToPost(JDCtx, 2, "dislike");
        postsRoute.addReactToPost(JDCtx, 3, "dislike");
        postsRoute.addReactToPost(JDCtx, 4, "like");

        postsRoute.addReactToPost(h1, 4, "like");
        postsRoute.addReactToPost(h2, 4, "like");
        postsRoute.addReactToPost(h3, 4, "dislike");
        postsRoute.addReactToPost(h4, 4, "like");
        postsRoute.addReactToPost(h5, 4, "dislike");
        postsRoute.addReactToPost(h1, 5, "dislike");
        postsRoute.addReactToPost(h2, 5, "dislike");
        postsRoute.addReactToPost(h3, 5, "dislike");
        postsRoute.addReactToPost(h4, 5, "like");
        postsRoute.addReactToPost(h5, 5, "dislike");

        System.out.println("---- DEMO INSERTED ----");
    }

    public ContainerRequest createUser(String username, String frontHash) {
        UserAuth userAuth = new UserAuth(username, frontHash);

        authenticationRoute.register(userAuth);

        ContainerRequest ctx;
        try {
            ctx = new ContainerRequest(new URI("localhost"), new URI(""), "POST", null, new MapPropertiesDelegate());
            ctx.setProperty("username", userAuth.username);
            System.out.println("Added a new user : "+username);
            return ctx;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createPost(ContainerRequest userCtx, String url, String title, String label) throws Exception {
        postsRoute.addPost(
            userCtx, 
            new ByteArrayInputStream(url.getBytes()), 
            FormDataContentDisposition.name("file").fileName("url").build(), 
            new ObjectMapper().writeValueAsString(new Post(title, null, null, null, new Label(label), null, null, null))
        );
        System.out.println("Posted : "+title);
    }

    public void changeAvatar(ContainerRequest userCtx, String url) throws Exception {
        usersRoute.changeAvatar(
            userCtx, 
            new ByteArrayInputStream(url.getBytes()), 
            FormDataContentDisposition.name("file").fileName("url").build()
        );

        System.out.println("Changed "+userCtx.getProperty("username")+"'s avatar");
    }

    public void manualBinding() {
        themesRoute = new Themes();
        authenticationRoute = new Authentication();
        postsRoute = new Posts();
        usersRoute = new Users();

        ThemeService themeService = new ThemeService();
        UserService userService = new UserService();
        AuthenticationService authenticationService = new AuthenticationService();
        LabelService labelService = new LabelService();
        PostService postService = new PostService();
        ReactionService reactionService = new ReactionService();

        AbstractImageService imageService = new ImgurImageService();

        themeService.themeDao = new SqlThemeDao();
        userService.userDao = new SqlUserDao();
        authenticationService.userDao = new SqlUserDao();
        labelService.labelDao = new SqlLabelDao();
        postService.postDao = new SqlPostDao();
        reactionService.postDao = new SqlPostDao();
        reactionService.reactionDao = new SqlReactionDao();
        reactionService.userDao = new SqlUserDao();

        themesRoute.themeService = themeService;
        themesRoute.userService = userService;

        authenticationRoute.authenticationService = authenticationService;
        authenticationRoute.keyGenerator = new SimpleKeyGenerator();

        postsRoute.imageService = imageService;
        postsRoute.labelService = labelService;
        postsRoute.postService = postService;
        postsRoute.reactionService = reactionService;
        postsRoute.themeService = themeService;
        postsRoute.userService = userService;

        usersRoute.imageService = imageService;
        usersRoute.postService = postService;
        usersRoute.userService = userService;
    }

    public void waitForDB() throws Exception {
        while(!SqlDatabase.isReady()) {
            ExecutorService exec = Executors.newFixedThreadPool(1);
            Future<Boolean> future = exec.submit(
                () -> {
                    try {
                        SqlDatabase.openConnection();
                    }
                    catch(Exception e) {
                        return false;
                    }
                    return true;
                }
            );
            try {
                if(future.get(1, TimeUnit.SECONDS)) break;
            } catch(Exception e) {
                Thread.sleep(1000);
            }
            System.out.println("DB not ready...");
        }

        System.out.println("---- DB Ready ----");
    }
}
