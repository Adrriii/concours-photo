package dao;

import model.Label;

import java.util.List;
import java.util.Optional;

public interface LabelDao {
    List<Label> getAll() throws Exception;
    Optional<Label> get(Label label) throws Exception;
    Label insert(Label label) throws Exception;
    Label update(Label label) throws Exception;
    void delete(Label label) throws Exception;
}