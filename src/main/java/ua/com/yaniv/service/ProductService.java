package ua.com.yaniv.service;

import ua.com.yaniv.model.*;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.repository.CrudRepository;
import ua.com.yaniv.repository.LaptopRepository;
import ua.com.yaniv.repository.PhoneRepository;
import ua.com.yaniv.repository.SmartWatchRepository;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

//i`m sorry about this
public class ProductService<E extends Product> {
    CrudRepository<E> crudRepository;
    Class type;

    public ProductService(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
        if (crudRepository instanceof LaptopRepository) this.type = Laptop.class;
        else if (crudRepository instanceof PhoneRepository) this.type = Phone.class;
        else if (crudRepository instanceof SmartWatchRepository) this.type = SmartWatch.class;
        else throw new IllegalArgumentException("Unknown type of repository");
    }

    public String returnProductInfoWithManufacturer(String id, Manufacturer manufacturer) {
        return crudRepository.findById(id).filter(product -> product.getManufacturer().equals(manufacturer)).
                map(product -> product.toString()).orElse("Product with parameters not found");
    }

    public boolean buySingleProduct(String id) {
        AtomicBoolean result = new AtomicBoolean(false);
        crudRepository.findById(id)
                .filter(product -> product.getCount() > 0)
                .ifPresent((product) -> {
                    product.setCount(product.getCount() - 1);
                    result.set(true);
                });
        return result.get();
    }

    public E returnProductWithManufacturerOrSet(String id, Manufacturer manufacturer) {
        Optional<E> result = crudRepository.findById(id);
        return result.filter(product -> product.getManufacturer().equals(manufacturer)).
                orElseGet(() -> changeManufacturerOrCreate(id, manufacturer));
    }

    public E returnOrCreateWithId(String id) {
        return crudRepository.findById(id).or(() -> Optional.of(createAndAddWithId(id))).get();
    }

    private E createAndAddWithId(String id) {
        Product entity = ProductFactory.createProduct(type);
        entity.setId(id);
        crudRepository.save((E) entity);
        return (E) entity;
    }

    private E changeManufacturerOrCreate(String id, Manufacturer manufacturer) {
        crudRepository.findById(id).ifPresentOrElse(
                (product) -> product.setManufacturer(manufacturer),
                () -> {
                    Product entity = ProductFactory.createProduct(type);
                    entity.setId(id);
                    entity.setManufacturer(manufacturer);
                    crudRepository.save((E) entity);
                });
        Optional<E> result = crudRepository.findById(id);
        return result.get();
    }

    private class ProductFactory {
        private static final Random RANDOM = new Random();

        private ProductFactory() {
        }

        public static Product createProduct(Class type) {
            if (type.equals(Laptop.class))
                return new Laptop(
                        "Laptop-" + RANDOM.nextInt(1000),
                        RANDOM.nextInt(500),
                        RANDOM.nextDouble(1000.0),
                        "Model-l" + RANDOM.nextInt(1000),
                        LaptopService.getRandomManufacturer(),
                        LaptopService.getRandomDriveType(),
                        LaptopService.getRandomOS()
                );
            if (type.equals(Phone.class))
                return new Phone(
                        "Phone-" + RANDOM.nextInt(1000),
                        RANDOM.nextInt(500),
                        RANDOM.nextDouble(1000.0),
                        "Model-p" + RANDOM.nextInt(1000),
                        PhoneService.getRandomManufacturer(),
                        RANDOM.nextInt(5),
                        PhoneService.getRandomCommunicationStandart()
                );

            if (type.equals(SmartWatch.class))
                return new SmartWatch(
                        "SmartWatch-" + RANDOM.nextInt(1000),
                        RANDOM.nextInt(500),
                        RANDOM.nextDouble(1000.0),
                        "Model-sw" + RANDOM.nextInt(1000),
                        SmartWatchService.getRandomManufacturer(),
                        RANDOM.nextInt(22)
                );
            throw new ClassCastException("Invalid class type: " + type.getName());
        }
    }
}
