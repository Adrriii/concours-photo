package dao;

import model.Theme;
import model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ThemeDao {
    List<Theme> getAll() throws Exception;
    Optional<Theme> getCurrent() throws Exception;
    List<Theme> getProposals() throws Exception;
    Theme getById(int id) throws Exception;
    Optional<Theme> getCurrentTheme(User user) throws Exception;
    void setUserVote(User user, Integer themeId) throws Exception;

    Theme insert(Theme theme) throws Exception;
    void delete(int id) throws Exception;
}
