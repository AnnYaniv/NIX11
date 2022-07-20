package ua.com.yaniv.service;

import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.repository.SmartWatchRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SmartWatchService extends CrudService<SmartWatch>{
    private static final Random RANDOM = new Random();

    public SmartWatchService(){
        repository = SmartWatchRepository.getInstance();
    }
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
