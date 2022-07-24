package ua.com.yaniv.service;

import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;
import ua.com.yaniv.repository.LaptopRepository;

import java.util.*;

public class LaptopService extends CrudService<Laptop>{
    private static final Random RANDOM = new Random();

    public LaptopService(){
        repository = LaptopRepository.getInstance();
    }

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
        saveAll(laptops);
    }

    public void printAll() {
        for (Laptop laptop : repository.getAll()) {
            System.out.println(laptop);
        }
    }

    public static Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public static DriveType getRandomDriveType() {
        final DriveType[] values = DriveType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public static OS getRandomOS() {
        final OS[] values = OS.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
