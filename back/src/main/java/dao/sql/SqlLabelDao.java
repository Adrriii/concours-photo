package dao.sql;

import dao.LabelDao;
import model.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Arrays;

public class SqlLabelDao extends SqlDao<Label> implements LabelDao {

    @Override
    protected Label createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Label(
            resultSet.getString("label")
        );
    }
    public List<Label> getAll() throws SQLException {
        String statement = "SELECT * FROM label";

        return queryAllObjects(statement);
    }

    public Label insert(Label label) throws SQLException {
        String statement = "INSERT INTO label (label) VALUES (?)";
        List<Object> opt = Arrays.asList(label.label);

        exec(statement, opt);
        return new Label(label.label);
    }
    
    public Label update(Label label) throws SQLException {
        String statement = "UPDATE label SET label = ? WHERE label = ?";
        List<Object> opt = Arrays.asList(label.label, label.label);

        exec(statement, opt);
        return new Label(label.label);
    }
    
    public void delete(Label label) throws SQLException {
        String statement = "DELETE FROM label WHERE label = ?";
        List<Object> opt = Arrays.asList(label.label);

        exec(statement, opt);
    }
    
}