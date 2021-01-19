import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.glassfish.jersey.internal.MapPropertiesDelegate;
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

    public void createDemo() throws Exception {
        System.out.println("---- START DEMO INSERT ----");

        ContainerRequest adriCtx = createUser("Adri",
                "ab628dfe91bd9afd73bc53e5e105da9e3a97a04dd8dab1a3ddea1d921848d68e");
        ContainerRequest coucouCtx = createUser("coucou",
                "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");

        System.out.println("---- DEMO INSERTED ----");
    }

    public void manualBinding() {
        themesRoute = new Themes();
        authenticationRoute = new Authentication();
        postsRoute = new Posts();

        ThemeService themeService = new ThemeService();
        UserService userService = new UserService();
        AuthenticationService authenticationService = new AuthenticationService();
        LabelService labelService = new LabelService();
        PostService postService = new PostService();
        ReactionService reactionService = new ReactionService();

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

        postsRoute.imageService = new ImgurImageService();
        postsRoute.labelService = labelService;
        postsRoute.postService = postService;
        postsRoute.reactionService = reactionService;
        postsRoute.themeService = themeService;
        postsRoute.userService = userService;
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
