package dao.sql;

import java.sql.*;
import java.util.List;

public class SqlDatabase {
    private static final String DB_HOST = "db";
    private static final String DB_USERNAME = "photodb";
    private static final String DB_PASSWORD = "photodb";
    private static final String DB_NAME = "photodb";
    private static final int DB_PORT = 3306;

    private static final String BACKUP_HOST = "localhost";
    private static final int BACKUP_PORT = 3307;

    private static Connection connection;

    private static void openConnection() {
        try {
            Class.forName ("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?user="
                    + DB_USERNAME + "&password=" + DB_PASSWORD
            );
        } catch (Exception ignored) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mariadb://" + BACKUP_HOST + ":" + BACKUP_PORT + "/" + DB_NAME + "?user="
                        + DB_USERNAME + "&password=" + DB_PASSWORD
                );
            } catch (SQLException exception) {
                throw new Error(exception);
            }
        }
    }

    public static Connection getConnection() {
        if (connection == null)
            openConnection();

        return connection;
    }

    public static PreparedStatement prepare(String statement, List<Object> items) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

        for (int i = 1; items != null && i <= items.size(); i++) {
            preparedStatement.setObject(i, items.get(i - 1));
        }

        return preparedStatement;
    }

    public static PreparedStatement prepare(String statement) throws SQLException {
        return prepare(statement, null);
    }

    public static void exec(String statement, List<Object> items) throws SQLException {
        prepare(statement, items).execute();
    }

    public static void exec(String statement) throws SQLException {
        exec(statement, null);
    }
}
