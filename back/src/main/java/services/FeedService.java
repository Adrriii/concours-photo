package services;

import java.util.*;

import javax.inject.Inject;

import dao.*;
import model.*;

public class FeedService {
    @Inject public PostDao postDao;
    @Inject public ThemeDao themeDao;
    @Inject public LabelDao labelDao;
    @Inject public ReactionDao reactionDao;

    public List<Post> feedSearchForUser(User user, String sort, String direction, Integer themeId, String labels, Integer page, Integer nbPosts) throws Exception {
        List<Post> result = new ArrayList<>();

        for (Post post : feedSearch(sort, direction, themeId, labels, page, nbPosts)) {
            result.add(reactionDao.get(user.id, post.id).map(
                    reaction -> new Post(
                            post.title,
                            post.date,
                            reaction.reaction.name(),
                            post.reactions,
                            post.reactionsUser,
                            post.author,
                            post.label,
                            post.theme,
                            post.photo,
                            post.photoDelete,
                            post.score,
                            post.nbVote,
                            post.nbComment,
                            post.id
                    )
            ).orElse(post));
        }

        return result;
    }

    public List<Post> feedSearch(String sort, String direction, Integer themeId, String labels, Integer page, Integer nbPosts) throws Exception {
        Theme theme = getArgTheme(themeId);
        sort = getArgSort(sort);
        direction = getArgDirection(direction);
        Set<Label> labelSet = getArgLabels(labels);

        int offset = (page-1)*nbPosts;

        return postDao.getFeedSearch(sort, direction, theme, labelSet, offset, nbPosts);
    }

    private Theme getArgTheme(Integer themeId) throws Exception {
        Theme theme = null;
        if(themeId != null) {
            Optional<Theme> testTheme = themeDao.getById(themeId);
            if(testTheme.isPresent()) {
                theme = testTheme.get();
            }
        }
        if(theme == null) {
            theme = themeDao.getCurrent().orElseThrow(
                () -> new Exception("No current theme")
            );
        }

        return theme;
    }

    private String getArgSort(String sort) {
        switch(sort) {
            case "score":
            case "date":
            case "nbComment":
            case "nbVotes":
                break;
            default:
                sort = null;
        }
        if(sort == null) {
            sort = "score";
        }
        return sort;
    }

    private String getArgDirection(String direction) {
        switch(direction) {
            case "DESC":
            case "ASC":
                break;
            default:
                direction = null;
        }
        if(direction == null) {
            direction = "DESC";
        }

        return direction;
    }

    private Set<Label> getArgLabels(String labels) {
        String[] l = labels.split(",");

        Set<Label> labelList = new HashSet<>();
        for(String label : l) {
            try {
            labelList.add(
                    labelDao.get(new Label(label)).orElseThrow(Exception::new)
            );
            } catch(Exception e) {
                System.out.println("Ignoring invalid label "+label);
            }
        }
        return labelList;
    }
}
