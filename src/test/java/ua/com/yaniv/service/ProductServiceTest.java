package ua.com.yaniv.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;
import ua.com.yaniv.repository.LaptopRepository;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    ProductService<Laptop> target;
    LaptopRepository laptopRepository;
    Laptop laptop1, laptop2;

    @BeforeEach
    void setUp() {
        laptopRepository = LaptopRepository.getInstance();
        target = new ProductService<>(laptopRepository);
        laptop1 = new Laptop(
                "MACBOOK",
                13,
                22500.99,
                "MACBOOK-model",
                Manufacturer.APPLE,
                DriveType.SDD,
                OS.MAC_OS
        );
        laptop2 = new Laptop(
                "Laptop-HUAWEI",
                13,
                22500.99,
                "Inspiron",
                Manufacturer.HUAWEI,
                DriveType.SDD,
                OS.WINDOWS
        );
    }

    @AfterEach
    void tearDown() {
        Field instance = null;
        try {
            instance = LaptopRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void buySingleProductMoreThanZero() {
        int count = laptop1.getCount();
        String id = laptop1.getId();
        laptopRepository.save(laptop1);

        boolean actual = target.buySingleProduct(id);
        assertEquals(count - 1, laptopRepository.findById(id).get().getCount());
        assertTrue(actual);
    }

    @Test
    void buySingleProductThatZero() {
        laptop1.setCount(0);
        int count = laptop1.getCount();
        String id = laptop1.getId();

        laptopRepository.save(laptop1);

        boolean actual = target.buySingleProduct(id);
        assertEquals(count, laptopRepository.findById(id).get().getCount());
        assertFalse(actual);
    }

    @Test
    void buySingleProductThatDontExist() {
        String id = "random-id";
        laptopRepository.save(laptop1);

        boolean actual = target.buySingleProduct(id);
        assertFalse(actual);
    }

    @Test
    void returnProductWithManufacturerOrSetThatMatch() {
        String id = laptop1.getId();
        Manufacturer manufacturer = laptop1.getManufacturer();
        laptopRepository.save(laptop1);

        Laptop l = target.returnProductWithManufacturerOrSet(id, manufacturer);
        assertEquals(id, l.getId());
        assertTrue(laptopRepository.findById(id).isPresent());
    }

    @Test
    void returnProductWithManufacturerOrSetThatDontMatch() {
        String id = laptop1.getId();
        Manufacturer manufacturer = Manufacturer.SAMSUNG;
        laptopRepository.save(laptop1);

        Laptop l = target.returnProductWithManufacturerOrSet(id, manufacturer);
        assertEquals(id, l.getId());
        assertEquals(manufacturer, l.getManufacturer());
        assertTrue(laptopRepository.findById(id).isPresent());
    }

    @Test
    void returnProductWithManufacturerOrSetDontExist() {
        String id = "random_id";
        Manufacturer manufacturer = Manufacturer.SAMSUNG;
        laptopRepository.save(laptop1);

        Laptop l = target.returnProductWithManufacturerOrSet(id, manufacturer);
        assertEquals(id, l.getId());
        assertEquals(manufacturer, l.getManufacturer());
        assertTrue(laptopRepository.findById(id).isPresent());
    }

    @Test
    void checkInfoProductMatch() {
        String id = laptop1.getId();
        Manufacturer manufacturer = laptop1.getManufacturer();
        laptopRepository.save(laptop1);

        String expected = laptop1.toString();
        String actual = target.returnProductInfoWithManufacturer(id, manufacturer);
        assertEquals(actual, expected);
    }

    @Test
    void checkInfoProductDontMatch() {
        String id = laptop1.getId();
        Manufacturer manufacturer = Manufacturer.XIAOMI;
        laptopRepository.save(laptop1);

        String expected = laptop1.toString();
        String actual = target.returnProductInfoWithManufacturer(id, manufacturer);
        assertNotEquals(actual, expected);
    }

    @Test
    void checkInfoProductDontExist() {
        String id = "random_id";
        Manufacturer manufacturer = Manufacturer.XIAOMI;
        laptopRepository.save(laptop1);

        String expected = "Product with parameters not found";
        String actual = target.returnProductInfoWithManufacturer(id, manufacturer);
        assertEquals(actual, expected);
    }

    @Test
    void returnProductWithId() {
        String id = laptop1.getId();
        laptopRepository.save(laptop1);

        Laptop actual = target.returnOrCreateWithId(id);
        assertEquals(laptop1, actual);
    }

    @Test
    void returnProductWithIdThatDontExist() {
        String id = "random_id";
        laptopRepository.save(laptop1);

        Laptop actual = target.returnOrCreateWithId(id);
        assertEquals(actual, laptopRepository.findById(id).get());
    }
}