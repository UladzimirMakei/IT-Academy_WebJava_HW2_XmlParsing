package by.uladzimirmakei.bankxml.repository;

import java.util.List;

public interface Repository<T> {

    List<T> getAll();

    void add(T object);

    void update(T oldObject, T newObject);

}
