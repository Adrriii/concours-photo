package services;

import dao.ThemeDao;
import model.Theme;
import model.User;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class ThemeService {
    @Inject ThemeDao themeDao;
    @Inject AuthenticationService authenticationService;

    public List<Theme> getAll() throws Exception {
        return themeDao.getAll();
    }

    public Optional<Theme> getById(int id) {
        try {
            return Optional.of(themeDao.getById(id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Theme> addOne(Theme theme) {
        try {
            return Optional.of(themeDao.insert(theme));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Theme> getCurrent() throws Exception {
        return themeDao.getCurrent();
    }

    public List<Theme> getProposals() throws Exception {
        return themeDao.getProposals();
    }

    public Optional<Theme> getCurrentUserVote(User user) throws Exception {
        return themeDao.getCurrentTheme(user);
    }

    public void setUserVote(User user, Integer themeId) throws Exception {
        themeDao.setUserVote(user, themeId);
    }

    public void deleteUserVote(User user) throws Exception {
        setUserVote(user, null);
    }
}