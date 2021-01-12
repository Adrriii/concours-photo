package dao;

import model.Theme;
import model.User;

import java.util.List;
import java.util.Optional;

public interface ThemeDao {
    List<Theme> getAll() throws Exception;
    Theme getById(int id) throws Exception;
    Optional<Theme> getCurrent() throws Exception;
    
    List<Theme> getProposals() throws Exception;

    Optional<Theme> getUserProposal(User user) throws Exception;
    Optional<Theme> getUserThemeVote(User user) throws Exception;

    void setUserVote(User user, Integer themeId) throws Exception;

    Theme insert(Theme theme) throws Exception;
    void delete(int id) throws Exception;
}
