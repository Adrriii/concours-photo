package services;

import dao.LabelDao;
import model.Label;

import javax.inject.Inject;
import java.util.List;

public class LabelService {
    @Inject
    LabelDao labelDao;

    public Label addOne(Label label) throws Exception {
        return labelDao.insert(label);
    }

    public List<Label> getAll() throws Exception {
        return labelDao.getAll();
    }

    public void deleteOne(Label label) throws Exception {
        labelDao.delete(label);
    }
}
