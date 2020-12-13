package dao;

import model.Theme;

public interface ThemeDao {
    Theme getById(int id) throws Exception;
    Theme insert(Theme theme) throws Exception;
    void delete(int id) throws Exception;
}
