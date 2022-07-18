package ua.com.yaniv.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.Manufacturer;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {

    Container<SmartWatch> target;
    SmartWatch smartWatch1, smartWatch2, smartWatch3;
    String id1, id2, id3;

    @BeforeEach
    void setUp() {
        smartWatch1 = new SmartWatch("Xiaomi MiBand", 500, 800, "MiBand6 NFC", Manufacturer.XIAOMI, 14);
        smartWatch2 = new SmartWatch("Apple Watch", 500, 2200, "IWatch 2", Manufacturer.APPLE, 14);
        smartWatch3 = new SmartWatch("Tester", 20, 99.9, "tester", Manufacturer.HUAWEI, 1);

        id1 = smartWatch1.getId();
        id2 = smartWatch2.getId();
        id3 = smartWatch3.getId();

        target = new Container<>(asList(smartWatch1, smartWatch2));
    }

    @Test
    void saveNotNull() {
        boolean actual = target.save(smartWatch3);
        int expectedSize = 3;
        SmartWatch actualSaved = target.findById(id3).get();
        assertEquals(expectedSize, target.getAll().size());
        assertEquals(smartWatch3, actualSaved);
        assertTrue(actual);
    }

    @Test
    void saveNull() {
        int expectedSize = target.getAll().size();
        assertThrows(IllegalArgumentException.class, () -> target.save(null));
        assertEquals(expectedSize, target.getAll().size());
    }

    @Test
    void saveAlreadyExists() {
        int expectedSize = target.getAll().size();
        boolean actual = target.save(smartWatch2);
        assertEquals(expectedSize, target.getAll().size());
        assertFalse(actual);
    }

    @Test
    void deleteExisting() {
        boolean actual = target.delete(id1);
        assertEquals(1, target.getAll().size());
        assertTrue(actual);
    }

    @Test
    void deleteNotExisting() {
        boolean actual = target.delete(id3);
        assertEquals(2, target.getAll().size());
        assertFalse(actual);
    }

    @Test
    void setRandomDiscountProductExist() {
        double before = smartWatch1.getPrice();
        boolean actual = target.setRandomDiscount(id1);
        double actualSmartWatch = target.findById(id1).get().getPrice();
        assertTrue(actual);
        assertNotEquals(before, actualSmartWatch);
    }

    @Test
    void setRandomDiscountProductDontExist() {
        boolean actual = target.setRandomDiscount(id3);
        assertFalse(actual);
    }

    @Test
    void provideProductDouble() {
        double added = 400.6d;
        int expected = (int) (target.findById(id1).get().getCount() + added);
        boolean actual = target.provideProductsById(id1, added);

        assertEquals(expected, target.findById(id1).get().getCount());
        assertTrue(actual);
    }

    @Test
    void provideProductInt() {
        int added = 256;
        int expected = (target.findById(id1).get().getCount() + added);
        boolean actual = target.provideProductsById(id1, added);

        assertEquals(expected, target.findById(id1).get().getCount());
        assertTrue(actual);
    }

    @Test
    void provideProductDontExist() {
        int added = 256;
        boolean actual = target.provideProductsById(id3, added);
        assertFalse(actual);
    }
}