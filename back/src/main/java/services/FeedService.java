package services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import dao.*;
import model.*;

public class FeedService {
    @Inject PostDao postDao;
    @Inject ThemeDao themeDao;
    @Inject LabelDao labelDao;

    public List<Post> feedSearch(String sort, String direction, Integer themeId, String labels) throws Exception {
        Theme theme = getArgTheme(themeId);
        sort = getArgSort(sort);
        direction = getArgDirection(direction);
        Set<Label> labelSet = getArgLabels(labels);

        return postDao.getFeedSearch(sort, direction, theme, labelSet);
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
