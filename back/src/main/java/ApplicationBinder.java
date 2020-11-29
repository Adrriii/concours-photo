import dao.sql.SqlUserDao;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SqlUserDao.class).to(UserDao.class);
    }
}