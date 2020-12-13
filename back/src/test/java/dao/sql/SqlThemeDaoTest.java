package dao.sql;

import model.Theme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqlThemeDaoTest {
    @Test
    public void addAndRemove() {
        Theme theme = new Theme(
                "Simple test",
                "http://imgur.com/nexistepas",
                "open",
                "2020/12/31",
                null
        );

        SqlThemeDao sqlThemeDao = new SqlThemeDao();

        Theme inserted = assertDoesNotThrow(() -> sqlThemeDao.insert(theme));
        assertEquals(
                new Theme(inserted.id, theme.title, theme.photo, theme.state, theme.date, theme.winner),
                inserted
        );
        
        assertDoesNotThrow(() -> sqlThemeDao.delete(inserted.id));
    }

    @Test
    public void getById() {
        Theme theme = new Theme(
                "Simple test",
                "http://imgur.com/nexistepas",
                "open",
                "2020/12/31",
                null
        );

        SqlThemeDao sqlThemeDao = new SqlThemeDao();

        Theme inserted = assertDoesNotThrow(() -> sqlThemeDao.insert(theme));
        Theme fromGet = assertDoesNotThrow(() -> sqlThemeDao.getById(inserted.id));

        assertEquals(inserted, fromGet);
    }
}