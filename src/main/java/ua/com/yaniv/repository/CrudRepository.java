package ua.com.yaniv.repository;

import ua.com.yaniv.model.Product;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E extends Product> {
    void save(E product);

    void saveAll(List<E> products);

    boolean update(E product);

    boolean delete(String id);

    List<E> getAll();

    Optional<E> findById(String id);
}
