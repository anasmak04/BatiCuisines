package main.java.repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudInterface<T> {
    T save(T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    T update(T entity);

    boolean delete(Long id);

}
