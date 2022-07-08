package ua.com.yaniv.service;

import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.Phone;
import ua.com.yaniv.repository.PhoneRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PhoneService {
    private static final Random RANDOM = new Random();
    private static final PhoneRepository REPOSITORY = PhoneRepository.getInstance();

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
        REPOSITORY.saveAll(phones);
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private CommunicationStandard getRandomCommunicationStandart() {
        final CommunicationStandard[] values = CommunicationStandard.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Phone phone : REPOSITORY.getAll()) {
            System.out.println(phone);
        }
    }

    public void save(Phone phone) {
        REPOSITORY.save(phone);
    }
    public void saveAll(List<Phone> products) {
        REPOSITORY.saveAll(products);
    }
    public boolean update(String id, Phone phone) {
        phone.setId(id);
        return REPOSITORY.update(phone);
    }

    public boolean delete(String id) {
        return REPOSITORY.delete(id);
    }

    public Optional<Phone> findById(String id) {
        return REPOSITORY.findById(id);
    }
}
