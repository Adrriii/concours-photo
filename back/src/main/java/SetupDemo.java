import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.server.ContainerRequest;

import dao.sql.*;
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
    Comments commentsRoute;

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
        changeAvatar(coucouCtx, "https://avatars1.githubusercontent.com/u/51917122?s=460&u=d08b9b9b05bfd85143e2789d3e75d37910aafb2f&v=4");
        changeAvatar(alexandreCtx, "https://i.imgur.com/eWIkRqz.png");
        changeAvatar(JDCtx, "https://cdn.discordapp.com/avatars/310533826868674572/d9ef342d2a9ab22d634ab5de5f8a925f.png?size=128");
        changeAvatar(h1, "https://data.whicdn.com/images/322027365/original.jpg?t=1541703413");
        changeAvatar(h2, "https://images.unsplash.com/photo-1529665253569-6d01c0eaf7b6?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80");
        changeAvatar(h3, "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/e1773fc5-4926-4a6f-940c-25645a1b3320/dctlyf9-b37b4f5b-45f8-4690-a244-a9e09462917a.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvZTE3NzNmYzUtNDkyNi00YTZmLTk0MGMtMjU2NDVhMWIzMzIwXC9kY3RseWY5LWIzN2I0ZjViLTQ1ZjgtNDY5MC1hMjQ0LWE5ZTA5NDYyOTE3YS5wbmcifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.89AJtCEkFv-8Tqogf7vsFgxldxtEUSA7wlacyzDSm2I");

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
        createPost(adriCtx, "https://i.imgur.com/G4M0WuT.jpg", "Un super oiseau", "Nature");
        createPost(alexandreCtx, "https://i.imgur.com/VZ7bqit.jpg", "Orange", "Nature");
        createPost(coucouCtx, "https://i.imgur.com/6gt1Zpn.jpg", "Bleu", "Nature");
        createPost(JDCtx, "https://i.imgur.com/iE4McQ8.jpg", "Violet", "Nature");
        createPost(h1, "https://i.imgur.com/242E6T5.jpg", "Chelou lui", "Nature");
        createPost(adriCtx, "https://i.imgur.com/uirY6ai.jpg", "Toutes les couleurs", "Nature");
        createPost(coucouCtx, "https://i.imgur.com/Nim4H1G.jpg", "Test nouvel appareil photo", "Nature");

        System.out.println("---- INSERT COMMENTS ----");
        createComment(adriCtx, 2, "Pas terrible :/");
        createComment(adriCtx, 6, "Ah ouais trop beau :O");
        createComment(h1, 1, "mdr nul");
        createComment(h1, 2, "mdr nul");
        createComment(h1, 3, "mdr nul");
        createComment(h1, 5, "TROP BIEN!!!");
        createComment(h1, 6, "mdr nul");
        createComment(h1, 7, "mdr nul");

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
        createPost(adriCtx, "https://i.imgur.com/bsInceU.jpg", "Le Taj Mahal", "Building");
        createPost(adriCtx, "https://i.imgur.com/Zqyj0sE.jpg", "Le Burj Khalifa", "Building");
        createPost(adriCtx, "https://i.imgur.com/3fAyncI.jpg", "La Tour Eiffel", "Building");
        createPost(coucouCtx, "https://i.imgur.com/aQJievr.png", "De l'architecture", "Building");
        createPost(coucouCtx, "https://i.imgur.com/WANEyvy.jpg", "Minas Tirith", "Building");

        System.out.println("---- INSERT COMMENTS ----");
        createComment(adriCtx, archiIdStart + 1, "Photo que j'ai prise en vacances ...");
        createComment(adriCtx, archiIdStart + 2, "Petite photo que j'ai prise pendant mon voyage d'affaires ...");
        createComment(adriCtx, archiIdStart + 3, "De retour à la maison");
        createComment(coucouCtx, archiIdStart + 4, "Je l'ai faite moi même :)");
        createComment(adriCtx, archiIdStart + 4, "... j'imagine que c'est valide x)");

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

    public void createComment(ContainerRequest authorCtx, int postId, String content) throws Exception {
        commentsRoute.replyToPost(
            authorCtx, 
            postId, 
            new Comment(
                usersRoute.userService.getUserFromRequestContext(authorCtx).get(), 
                postsRoute.postService.getById(postId).get(), 
                null, 
                content
            )
        );
    }

    public void manualBinding() {
        themesRoute = new Themes();
        authenticationRoute = new Authentication();
        postsRoute = new Posts();
        usersRoute = new Users();
        commentsRoute = new Comments();

        ThemeService themeService = new ThemeService();
        CommentService commentService = new CommentService();
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
        commentService.commentDao = new SqlCommentDao();
        commentService.postDao = new SqlPostDao();

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

        commentsRoute.commentService = commentService;
        commentsRoute.userService = userService;
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
