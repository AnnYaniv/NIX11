package ua.com.yaniv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;
import ua.com.yaniv.repository.LaptopRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LaptopService {
    private static final Random RANDOM = new Random();
    private static final Logger logger = LoggerFactory.getLogger(LaptopService.class);
    private static final LaptopRepository REPOSITORY = LaptopRepository.getInstance();

    public void createAndSaveLaptops(int count) {
        List<Laptop> laptops = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            laptops.add(new Laptop(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer(),
                    getRandomDriveType(),
                    getRandomOS()
            ));
        }
        for (Laptop item : laptops) {
            save(item);
        }
    }

    public boolean save(Laptop laptop) {
        try {
            REPOSITORY.save(laptop);
            return true;
        } catch (IllegalArgumentException ex) {
            logger.warn("Could not save null");
            return false;
        }
    }

    public void saveAll(List<Laptop> products) {
        REPOSITORY.saveAll(products);
    }

    public boolean delete(String id) {
        return REPOSITORY.delete(id);
    }

    public Optional<Laptop> findById(String id) {
        return REPOSITORY.findById(id);
    }

    public boolean update(String id, Laptop laptop) {
        laptop.setId(id);
        return REPOSITORY.update(laptop);
    }

    public void printAll() {
        for (Laptop laptop : REPOSITORY.getAll()) {
            System.out.println(laptop);
        }
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private DriveType getRandomDriveType() {
        final DriveType[] values = DriveType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private OS getRandomOS() {
        final OS[] values = OS.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
