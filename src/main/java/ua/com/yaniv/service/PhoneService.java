package ua.com.yaniv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.Phone;
import ua.com.yaniv.repository.PhoneRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PhoneService implements CrudService<Phone> {
    private static final Random RANDOM = new Random();
    private static final Logger logger = LoggerFactory.getLogger(PhoneService.class);
    private final PhoneRepository repository = PhoneRepository.getInstance();

    public void createAndSavePhones(int count) {
        List<Phone> phones = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            phones.add(new Phone(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer(),
                    RANDOM.nextInt(6),
                    getRandomCommunicationStandart()
            ));
        }
        repository.saveAll(phones);
    }

    public static Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public static CommunicationStandard getRandomCommunicationStandart() {
        final CommunicationStandard[] values = CommunicationStandard.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Phone phone : repository.getAll()) {
            System.out.println(phone);
        }
    }

    @Override
    public boolean save(Phone phone) {
        try {
            repository.save(phone);
            return true;
        } catch (IllegalArgumentException ex) {
            logger.warn("Could not save null");
            return false;
        }
    }

    @Override
    public boolean saveAll(List<Phone> products) {
        return repository.saveAll(products);
    }

    @Override
    public boolean update(String id, Phone phone) {
        phone.setId(id);
        return repository.update(phone);
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public List<Phone> getAll() {
        return repository.getAll();
    }

    @Override
    public Optional<Phone> findById(String id) {
        return repository.findById(id);
    }
}
