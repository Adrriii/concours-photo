package dao;

import java.util.List;

public interface Searchable<T> {
    List<T> search(String searchString) throws Exception;
}
