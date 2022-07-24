package ua.com.yaniv.service;

import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.Phone;
import ua.com.yaniv.repository.PhoneRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PhoneService extends CrudService<Phone> {
    private static final Random RANDOM = new Random();

    public PhoneService(){
        repository = PhoneRepository.getInstance();
    }

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
}
