import dao.PostDao;
import dao.SettingDao;
import dao.UserDao;
import dao.UserSettingDao;
import dao.sql.SqlPostDao;
import dao.sql.SqlSettingDao;
import dao.sql.SqlUserDao;
import dao.sql.SqlUserSettingDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import services.AuthenticationService;
import services.PostService;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SqlUserDao.class).to(UserDao.class);
        bind(SqlPostDao.class).to(PostDao.class);
        bind(SqlUserSettingDao.class).to(UserSettingDao.class);
        bind(SqlSettingDao.class).to(SettingDao.class);

        bindAsContract(AuthenticationService.class);
        bindAsContract(PostService.class);
    }
}