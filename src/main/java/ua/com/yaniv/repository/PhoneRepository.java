package ua.com.yaniv.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.model.Phone;

import java.util.*;

public class PhoneRepository implements CrudRepository<Phone> {
    private static final Logger logger = LoggerFactory.getLogger(PhoneRepository.class);
    private final List<Phone> phones;
    private static PhoneRepository instance;

    private PhoneRepository() {
        phones = new LinkedList<>();
    }

    public static PhoneRepository getInstance() {
        if (instance == null) instance = new PhoneRepository();
        return instance;
    }

    @Override
    public void save(Phone product) {
        if (product == null) {
            final IllegalArgumentException exception = new IllegalArgumentException("Cannot save a null phone");
            logger.error(exception.getMessage(), exception);
            throw exception;
        } else {
            phones.add(product);
            logger.info("{} was saved", product);
        }
    }

    @Override
    public boolean saveAll(List<Phone> products) {
        boolean result = true;

        for (Phone phone : products) {
            if ((phone == null) || !findById(phone.getId()).isEmpty()) result = false;
            else save(phone);
        }
        return result;
    }

    @Override
    public boolean update(Phone product) {
        final Optional<Phone> result = findById(product.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Phone originPhone = result.get();
        originPhone.setNumbersOfMainCameras(product.getNumbersOfMainCameras());

        originPhone.setCount(product.getCount());
        originPhone.setPrice(product.getPrice());
        originPhone.setTitle(product.getTitle());
        originPhone.setModel(product.getModel());
        originPhone.setManufacturer(product.getManufacturer());

        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Phone> iterator = phones.iterator();
        while (iterator.hasNext()) {
            final Phone phone = iterator.next();
            if (phone.getId().equals(id)) {
                iterator.remove();
                logger.info("Product with id {}, was deleted", id);
                return true;
            }
        }
        logger.info("Product with id {}, wasn`t deleted", id);
        return false;
    }

    @Override
    public List<Phone> getAll() {
        if (phones.isEmpty()) {
            return Collections.emptyList();
        }
        return phones;
    }

    @Override
    public Optional<Phone> findById(String id) {
        Phone result = null;
        for (Phone phone : phones) {
            if (phone.getId().equals(id)) {
                result = phone;
            }
        }
        return Optional.ofNullable(result);
    }
}
