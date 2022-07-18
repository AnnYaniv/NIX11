package ua.com.yaniv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.repository.SmartWatchRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SmartWatchService implements CrudService<SmartWatch>{
    private static final Random RANDOM = new Random();
    private static final Logger logger = LoggerFactory.getLogger(SmartWatchService.class);
    private final SmartWatchRepository repository = SmartWatchRepository.getInstance();

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
        repository.saveAll(smartWatches);
    }

    @Override
    public boolean save(SmartWatch smartWatch) {
        try {
            repository.save(smartWatch);
            return true;
        } catch (IllegalArgumentException ex) {
            logger.warn("Could not save null");
            return false;
        }
    }

    @Override
    public boolean saveAll(List<SmartWatch> products) {
        return repository.saveAll(products);
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public List<SmartWatch> getAll() {
        return repository.getAll();
    }

    @Override
    public Optional<SmartWatch> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public boolean update(String id, SmartWatch smartWatch) {
        smartWatch.setId(id);
        return repository.update(smartWatch);
    }

    public void printAll() {
        for (SmartWatch smartWatch : repository.getAll()) {
            System.out.println(smartWatch);
        }
    }

    public static Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
