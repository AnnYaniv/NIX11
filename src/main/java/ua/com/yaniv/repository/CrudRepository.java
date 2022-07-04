package ua.com.yaniv.repository;

import ua.com.yaniv.model.Phone;

import java.util.List;
import java.util.Optional;

public interface CrudRepository {
    void save(Phone phone);

    void saveAll(List<Phone> phones);

    boolean update(Phone phone);

    boolean delete(String id);

    List<Phone> getAll();

    Optional<Phone> findById(String id);
}
