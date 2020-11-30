import dao.UserDao;
import dao.sql.SqlUserDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SqlUserDao.class).to(UserDao.class);
    }
}