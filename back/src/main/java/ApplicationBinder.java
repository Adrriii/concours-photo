import dao.PostDao;
import dao.UserDao;
import dao.sql.SqlPostDao;
import dao.sql.SqlUserDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import services.AuthenticationService;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SqlUserDao.class).to(UserDao.class);
        bind(SqlPostDao.class).to(PostDao.class);

        bindAsContract(AuthenticationService.class);
    }
}