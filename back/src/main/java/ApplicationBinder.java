import dao.PostDao;
import dao.SettingDao;
import dao.UserDao;
import dao.UserSettingDao;
import dao.sql.SqlPostDao;
import dao.sql.SqlSettingDao;
import dao.sql.SqlUserDao;
import dao.sql.SqlUserSettingDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import services.AbstractImageService;
import services.AuthenticationService;
import services.PostService;
import services.implementation.imgur.ImgurImageService;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SqlUserDao.class).to(UserDao.class);
        bind(SqlPostDao.class).to(PostDao.class);
        bind(SqlUserSettingDao.class).to(UserSettingDao.class);
        bind(SqlSettingDao.class).to(SettingDao.class);
        bind(ImgurImageService.class).to(AbstractImageService.class);

        bindAsContract(AuthenticationService.class);
        bindAsContract(PostService.class);
    }
}