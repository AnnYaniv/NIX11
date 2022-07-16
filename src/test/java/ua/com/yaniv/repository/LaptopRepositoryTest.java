package ua.com.yaniv.repository;

import org.junit.jupiter.api.*;
import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class LaptopRepositoryTest {
    LaptopRepository target;
    Laptop laptop1, laptop2, laptopNull;

    @BeforeEach
    void setUp() {
        target = LaptopRepository.getInstance();
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
        laptopNull = null;
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

    /**
     * Save in {@link LaptopRepository#save(Laptop)}  a not-null reference with optionals.orElseThrow
     *
     * @result laptop save
     */
    @Test
    void saveNotNull() {
        target.save(laptop1);
        final List<Laptop> laptops = target.getAll();
        assertEquals(1, laptops.size());
        assertEquals(laptops.get(0).getId(), laptop1.getId());
    }

    /**
     * Save in {@link LaptopRepository#save(Laptop)} a null reference with optionals.orElseThrow
     *
     * @throws IllegalArgumentException
     * @result laptop don`t save and throw exception
     */
    @Test
    void saveNull() {
        assertThrows(IllegalArgumentException.class, () -> target.save(laptopNull));
        final List<Laptop> actualResult = target.getAll();
        assertEquals(0, actualResult.size());
    }

    @Test
    void deleteExistingLaptop() {
        target.save(laptop1);
        boolean actual = target.delete(laptop1.getId());
        assertTrue(actual);
    }

    @Test
    void deleteNotExistingLaptop() {
        target.save(laptop1);
        boolean actual = target.delete("its_number_nothing_more");
        assertFalse(actual);
    }

    @Test
    void updateExistingLaptop() {
        target.save(laptop1);
        Laptop updLaptop = new Laptop(
                "upd",
                13,
                13000,
                "updated-model",
                Manufacturer.APPLE,
                DriveType.SDD,
                OS.MAC_OS
        );
        updLaptop.setId(laptop1.getId());
        boolean actual = target.update(updLaptop);
        Laptop actualLaptop = target.findById(updLaptop.getId()).get();
        assertTrue(actual);
        assertEquals(updLaptop, actualLaptop);
    }

    @Test
    void updateNotExistingLaptop() {
        target.save(laptop1);
        Laptop updLaptop = new Laptop(
                "upd",
                13,
                13000,
                "updated-model",
                Manufacturer.APPLE,
                DriveType.SDD,
                OS.MAC_OS
        );
        updLaptop.setId("its_number_nothing_more");
        boolean actual = target.update(updLaptop);
        assertFalse(actual);
    }

    @Test
    void findExistingLaptop() {
        target.save(laptop1);
        Optional<Laptop> result = target.findById(laptop1.getId());
        assertEquals(laptop1, result.get());
    }

    @Test
    void findNotExistingLaptop() {
        target.save(laptop1);
        Optional<Laptop> result = target.findById("its_number_nothing_more");
        assertTrue(result.isEmpty());
    }

    @Test
    void saveAllWithDublicate() {
        List<Laptop> laptops = new ArrayList<>();
        laptops.add(laptop1);
        laptops.add(laptop1);
        laptops.add(laptop2);
        boolean actual = target.saveAll(laptops);
        assertEquals(2, target.getAll().size());
        assertFalse(actual);
    }

    @Test
    void saveAllWithNull() {
        List<Laptop> laptops = new ArrayList<>();
        laptops.add(laptop1);
        laptops.add(laptopNull);
        laptops.add(laptop2);
        boolean actual = target.saveAll(laptops);
        assertEquals(2, target.getAll().size());
        assertFalse(actual);
    }

    @Test
    void saveAllWithCorrectLaptops() {
        List<Laptop> laptops = new ArrayList<>();
        laptops.add(laptop1);
        laptops.add(laptop2);
        boolean actual = target.saveAll(laptops);
        assertEquals(2, target.getAll().size());
        assertTrue(actual);
    }
}