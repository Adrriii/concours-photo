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

    int postLastId = 0;
    int themeLastId = 1;

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

        ContainerRequest adriCtx = createUser("Adri",
                "ab628dfe91bd9afd73bc53e5e105da9e3a97a04dd8dab1a3ddea1d921848d68e");
        ContainerRequest coucouCtx = createUser("coucou",
                "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest alexandreCtx = createUser("Alexandre",
                "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest JDCtx = createUser("JD",
                "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");

        ContainerRequest h1 = createUser("Photo Lover",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest h2 = createUser("jleauvo",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest h3 = createUser("Lampadaire974",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest h4 = createUser("4857485516",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");
        ContainerRequest h5 = createUser("__image__",
        "110812f67fa1e1f0117f6f3d70241c1a42a7b07711a93c2477cc516d9042f9db");

        // Set avatars
        changeAvatar(adriCtx, "https://a.ppy.sh/4579132?1594553960.png");

        System.out.println("---- INSERT PREVIOUS THEMES ----");
        themesRoute.themeService.themeDao.insert(new Theme("Les Oiseaux", "", "active", "2021-01-11"));
        createTheme(adriCtx, "Les Chiens", "", "2021-01-18");
        createTheme(coucouCtx, "Les Chats", "", "2021-01-18");
        createTheme(alexandreCtx, "Les Giraffes", "", "2021-01-18");
        createTheme(JDCtx, "Architecture", "", "2021-01-18");

        System.out.println("---- THEME VOTE ----");
        themesRoute.setUserProposal(adriCtx, 5);
        themesRoute.setUserProposal(coucouCtx, 5);
        themesRoute.setUserProposal(alexandreCtx, 4);
        themesRoute.setUserProposal(JDCtx, 3);

        System.out.println("---- INSERT PREVIOUS POSTS ----");
        createPost(adriCtx, "https://see.news/wp-content/uploads/2020/12/UK_wildbirds-01-robin.jpg", "Un super oiseau", "Nature");
        createPost(alexandreCtx, "https://nas-national-prod.s3.amazonaws.com/styles/hero_image/s3/web_groombaltimoreoriole-and-a-male-red-breasted-grosbeak.jpg?itok=mGKiNpgF", "Orange", "Nature");
        createPost(coucouCtx, "https://www.sciencemag.org/sites/default/files/styles/article_main_large/public/images/ss-bird_honeycreeper.jpg?itok=VlVY6gUB", "Bleu", "Nature");
        createPost(JDCtx, "https://i.guim.co.uk/img/media/43d137ea70cc05d51bccea2adbc35f3468148162/0_58_3500_2101/master/3500.jpg?width=1200&quality=85&auto=format&fit=max&s=f1781226612b87f4835e2a5cd1d85b1f", "Violet", "Nature");
        createPost(h1, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSj8P_Nqzn4XYT4RrjUjoSfSXyBZ-8zU3iZPg&usqp=CAU", "Chelou lui", "Nature");
        createPost(adriCtx, "https://store-images.s-microsoft.com/image/apps.25758.14302936925643487.4d207f31-f506-432a-b6bd-72a91caf91f8.4752c3f6-0871-4ac4-b6bf-8fb2e382c8ed?mode=scale&q=90&h=1080&w=1920", "Toutes les couleurs", "Nature");
        createPost(coucouCtx, "https://www.nationalgeographic.com/content/dam/animals/rights-exempt/best-of-animals-2018/photo-ark-parrots-endangered-bird-world-intelligence-3.adapt.1900.1.jpg", "Test nouvel appareil photo", "Nature");

        System.out.println("---- INSERT PREVIOUS REACTIONS ----");
        postsRoute.addReactToPost(adriCtx, 1, "like");
        postsRoute.addReactToPost(adriCtx, 2, "dislike");
        postsRoute.addReactToPost(adriCtx, 3, "like");
        postsRoute.addReactToPost(adriCtx, 4, "dislike");
        postsRoute.addReactToPost(adriCtx, 5, "dislike");
        postsRoute.addReactToPost(adriCtx, 6, "like");
        postsRoute.addReactToPost(adriCtx, 7, "like");
        postsRoute.addReactToPost(alexandreCtx, 1, "dislike");
        postsRoute.addReactToPost(alexandreCtx, 2, "like");
        postsRoute.addReactToPost(alexandreCtx, 3, "like");
        postsRoute.addReactToPost(alexandreCtx, 4, "like");
        postsRoute.addReactToPost(alexandreCtx, 5, "dislike");
        postsRoute.addReactToPost(alexandreCtx, 6, "like");
        postsRoute.addReactToPost(alexandreCtx, 7, "like");
        postsRoute.addReactToPost(coucouCtx, 1, "like");
        postsRoute.addReactToPost(coucouCtx, 2, "like");
        postsRoute.addReactToPost(coucouCtx, 3, "like");
        postsRoute.addReactToPost(coucouCtx, 4, "dislike");
        postsRoute.addReactToPost(coucouCtx, 5, "dislike");
        postsRoute.addReactToPost(coucouCtx, 6, "dislike");
        postsRoute.addReactToPost(coucouCtx, 7, "like");
        postsRoute.addReactToPost(JDCtx, 1, "like");
        postsRoute.addReactToPost(JDCtx, 2, "like");
        postsRoute.addReactToPost(JDCtx, 3, "like");
        postsRoute.addReactToPost(JDCtx, 4, "like");
        postsRoute.addReactToPost(JDCtx, 5, "like");
        postsRoute.addReactToPost(JDCtx, 6, "like");
        postsRoute.addReactToPost(JDCtx, 7, "like");
        postsRoute.addReactToPost(h1, 1, "dislike");
        postsRoute.addReactToPost(h1, 2, "dislike");
        postsRoute.addReactToPost(h1, 3, "dislike");
        postsRoute.addReactToPost(h1, 4, "dislike");
        postsRoute.addReactToPost(h1, 5, "like");
        postsRoute.addReactToPost(h1, 6, "dislike");
        postsRoute.addReactToPost(h1, 7, "dislike");
        postsRoute.addReactToPost(h2, 1, "like");
        postsRoute.addReactToPost(h2, 2, "like");
        postsRoute.addReactToPost(h2, 3, "dislike");
        postsRoute.addReactToPost(h2, 4, "dislike");
        postsRoute.addReactToPost(h2, 5, "like");
        postsRoute.addReactToPost(h2, 6, "like");
        postsRoute.addReactToPost(h2, 7, "like");

        System.out.println("---- NEXT THEME ----");
        themesRoute.nextTheme();

        int archiIdStart = postLastId;
        System.out.println("---- INSERT POSTS ----");
        createPost(adriCtx, "https://whc.unesco.org/uploads/thumbs/site_0252_0008-1200-630-20151104113424.jpg", "Le Taj Mahal", "Building");
        createPost(adriCtx, "https://www.bautrip.com/images/what-to-visit/burj-khalifa.jpg", "Le Burj Khalifa", "Building");
        createPost(adriCtx, "https://cdn.paris.fr/paris/2020/05/12/huge-67a65318e89c13e2b63ddbe2bb89cc3c.jpg", "La Tour Eiffel", "Building");
        createPost(coucouCtx, "https://cdn.discordapp.com/attachments/765145671065665539/801146136839716884/archi.png", "De l'architecture", "Building");
        createPost(coucouCtx, "https://static.wikia.nocookie.net/lotr/images/e/e4/Minas_Tirith.jpg/revision/latest/scale-to-width-down/1000?cb=20141228214636", "Minas Tirith", "Building");

        System.out.println("---- INSERT REACTIONS ----");
        postsRoute.addReactToPost(adriCtx, archiIdStart + 1, "like");
        postsRoute.addReactToPost(adriCtx, archiIdStart + 2, "like");
        postsRoute.addReactToPost(adriCtx, archiIdStart + 3, "like");
        postsRoute.addReactToPost(adriCtx, archiIdStart + 4, "like");
        postsRoute.addReactToPost(coucouCtx, archiIdStart + 1, "dislike");
        postsRoute.addReactToPost(coucouCtx, archiIdStart + 2, "dislike");
        postsRoute.addReactToPost(coucouCtx, archiIdStart + 3, "like");
        postsRoute.addReactToPost(coucouCtx, archiIdStart + 4, "like");
        postsRoute.addReactToPost(alexandreCtx, archiIdStart + 1, "dislike");
        postsRoute.addReactToPost(alexandreCtx, archiIdStart + 4, "like");
        postsRoute.addReactToPost(JDCtx, archiIdStart + 1, "dislike");
        postsRoute.addReactToPost(JDCtx, archiIdStart + 2, "dislike");
        postsRoute.addReactToPost(JDCtx, archiIdStart + 3, "dislike");
        postsRoute.addReactToPost(JDCtx, archiIdStart + 4, "like");
        postsRoute.addReactToPost(h1, archiIdStart + 4, "like");
        postsRoute.addReactToPost(h2, archiIdStart + 4, "like");
        postsRoute.addReactToPost(h3, archiIdStart + 4, "dislike");
        postsRoute.addReactToPost(h4, archiIdStart + 4, "like");
        postsRoute.addReactToPost(h5, archiIdStart + 4, "dislike");
        postsRoute.addReactToPost(h1, archiIdStart + 5, "dislike");
        postsRoute.addReactToPost(h2, archiIdStart + 5, "dislike");
        postsRoute.addReactToPost(h3, archiIdStart + 5, "dislike");
        postsRoute.addReactToPost(h4, archiIdStart + 5, "like");
        postsRoute.addReactToPost(h5, archiIdStart + 5, "dislike");

        int nextThemeIdStart = themeLastId;
        System.out.println("---- INSERT NEXT THEMES ----");
        createTheme(adriCtx, "Les Chevaux", "", "2021-01-25");
        createTheme(coucouCtx, "La vie à la campagne", "", "2021-01-25");
        createTheme(alexandreCtx, "La vie en ville", "", "2021-01-25");
        createTheme(JDCtx, "L'espace", "", "2021-01-25");

        System.out.println("---- THEME VOTE ----");
        themesRoute.setUserProposal(adriCtx, nextThemeIdStart + 3);
        themesRoute.setUserProposal(coucouCtx, nextThemeIdStart + 2);
        themesRoute.setUserProposal(alexandreCtx, nextThemeIdStart + 2);
        themesRoute.setUserProposal(JDCtx, nextThemeIdStart + 4);
        themesRoute.setUserProposal(h1, nextThemeIdStart + 4);
        themesRoute.setUserProposal(h2, nextThemeIdStart + 2);
        themesRoute.setUserProposal(h3, nextThemeIdStart + 2);
        themesRoute.setUserProposal(h4, nextThemeIdStart + 2);
        themesRoute.setUserProposal(h5, nextThemeIdStart + 4);


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
        postLastId++;
        System.out.println("Posted : "+title);
    }

    public void createTheme(ContainerRequest userCtx, String title, String photo, String date) {
        themesRoute.addTheme(userCtx, new Theme(title, photo, null, date));
        themeLastId++;
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
        themeService.userDao = new SqlUserDao();
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
