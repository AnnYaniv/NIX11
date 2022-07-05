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
        phones.add(product);
        logger.info("{} was saved", product);
    }

    @Override
    public void saveAll(List<Phone> products) {
        for (Phone phone : products) {
            save(phone);
        }
    }

    @Override
    public boolean update(Phone product) {
        final Optional<Phone> result = findById(product.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Phone originPhone = result.get();
        PhoneCopy.copy(product, originPhone);
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

    private static class PhoneCopy {
        private static void copy(final Phone from, final Phone to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
            to.setCommunicationStandard(from.getCommunicationStandard());
            to.setNumbersOfMainCameras(from.getNumbersOfMainCameras());
        }
    }
}
