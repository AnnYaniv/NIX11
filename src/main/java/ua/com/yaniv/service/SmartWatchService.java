package ua.com.yaniv.service;

import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.repository.SmartWatchRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SmartWatchService {
    private static final Random RANDOM = new Random();
    private static final SmartWatchRepository REPOSITORY = SmartWatchRepository.getInstance();

    public void createAndSaveSmartWatches(int count) {
        List<SmartWatch> smartWatches = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            smartWatches.add(new SmartWatch(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer(),
                    RANDOM.nextInt(30)
            ));
        }
        REPOSITORY.saveAll(smartWatches);
    }

    public void save(SmartWatch smartWatch) {
        REPOSITORY.save(smartWatch);
    }

    public boolean delete(String id) {
        return REPOSITORY.delete(id);
    }

    public Optional<SmartWatch> findById(String id) {
        return REPOSITORY.findById(id);
    }

    public boolean update(String id, SmartWatch smartWatch) {
        smartWatch.setId(id);
        return REPOSITORY.update(smartWatch);
    }

    public void printAll() {
        for (SmartWatch smartWatch : REPOSITORY.getAll()) {
            System.out.println(smartWatch);
        }
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
