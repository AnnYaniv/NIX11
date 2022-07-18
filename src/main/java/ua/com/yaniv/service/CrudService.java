package ua.com.yaniv.service;

import ua.com.yaniv.model.Product;

import java.util.List;
import java.util.Optional;

public interface CrudService <E extends Product>{
    boolean save(E product);

    boolean saveAll(List<E> products);

    boolean update(String id, E product);

    boolean delete(String id);

    List<E> getAll();

    Optional<E> findById(String id);
}
