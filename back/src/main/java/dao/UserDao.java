package dao;

import java.util.List;
import java.util.Optional;

import model.*;

public interface UserDao extends Searchable<User> {
    User getById(int id) throws Exception;
    User getByUsername(String username) throws Exception;

    User insert(User user, String hash, String email) throws Exception;
    User update(User user) throws Exception;
    void delete(User user) throws Exception;

    User getByLogin(String username, String hash) throws Exception;
    void updateHash(User user, String hash) throws Exception;
    void updateUserScore(int id) throws Exception;
    void updateUsersRanks() throws Exception;

    List<User> getLeaderboard() throws Exception;
    List<User> getCurrentLeaderboard() throws Exception;
    Optional<User> getCurrentThemeWinner() throws Exception;
    void resetThemeScore() throws Exception;
    void resetThemeVote() throws Exception;
    User addVictoryToUser(User user) throws Exception;
}