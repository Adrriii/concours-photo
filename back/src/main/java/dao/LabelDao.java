package dao;

import model.Label;

public interface LabelDao {
    List<Label> getAll() throws Exception;
    Label insert(Label label) throws Exception;
    Label update(Label label) throws Exception;
    void delete(Label label) throws Exception;
}