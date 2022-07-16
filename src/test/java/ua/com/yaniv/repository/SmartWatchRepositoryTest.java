package ua.com.yaniv.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.Manufacturer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


class SmartWatchRepositoryTest {
    SmartWatchRepository target;
    SmartWatch smartWatch1, smartWatch2, smartWatchNull;

    @BeforeEach
    void setUp() {
        target = SmartWatchRepository.getInstance();
        smartWatch1 = new SmartWatch(
                "IWatch",
                100,
                1999.9,
                "APPLE Watch",
                Manufacturer.APPLE,
                5
        );
        smartWatch2 = new SmartWatch(
                "XIAOMI MiBand",
                100,
                800.0,
                "MiBand 4 NFC",
                Manufacturer.XIAOMI,
                14
        );
        smartWatchNull = null;
    }

    @AfterEach
    void tearDown() {//reset instance
        Field instance = null;
        try {
            instance = SmartWatchRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveNotNull() {
        target.save(smartWatch1);
        final List<SmartWatch> smartWatches = target.getAll();
        Assertions.assertEquals(1, smartWatches.size());
        Assertions.assertEquals(smartWatches.get(0).getId(), smartWatch1.getId());
    }

    @Test
    void saveNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(smartWatchNull));
        final List<SmartWatch> smartWatches = target.getAll();
        Assertions.assertEquals(0, smartWatches.size());
    }

    @Test
    void saveAllWithDublicate() {
        List<SmartWatch> smartWatches = new ArrayList<>();
        smartWatches.add(smartWatch1);
        smartWatches.add(smartWatch2);
        smartWatches.add(smartWatch2);
        boolean actual = target.saveAll(smartWatches);
        Assertions.assertEquals(2, target.getAll().size());
        Assertions.assertFalse(actual);

    }

    @Test
    void saveAllWithNull() {
        List<SmartWatch> smartWatches = new ArrayList<>();
        smartWatches.add(smartWatch1);
        smartWatches.add(smartWatch2);
        smartWatches.add(smartWatchNull);
        boolean actual = target.saveAll(smartWatches);
        Assertions.assertEquals(2, target.getAll().size());
        Assertions.assertFalse(actual);
    }

    @Test
    void saveAllWithCorrectSmartWatches() {
        List<SmartWatch> smartWatches = new ArrayList<>();
        smartWatches.add(smartWatch1);
        smartWatches.add(smartWatch2);
        boolean actual = target.saveAll(smartWatches);
        Assertions.assertEquals(2, target.getAll().size());
        Assertions.assertTrue(actual);
    }


    @Test
    void updateExistingSmartWatch() {
        target.save(smartWatch1);
        SmartWatch updSmartWatch = new SmartWatch(
                "XIAOMI MiBand",
                100,
                800.0,
                "MiBand 4 NFC",
                Manufacturer.XIAOMI,
                14
        );
        updSmartWatch.setId(smartWatch1.getId());
        boolean actual = target.update(updSmartWatch);
        SmartWatch actualSmartWatch = target.findById(updSmartWatch.getId()).get();
        Assertions.assertTrue(actual);
        Assertions.assertEquals(updSmartWatch, actualSmartWatch);
    }

    @Test
    void updateNotExistingSmartWatch() {
        target.save(smartWatch1);
        SmartWatch updSmartWatch = new SmartWatch(
                "XIAOMI MiBand",
                100,
                800.0,
                "MiBand 4 NFC",
                Manufacturer.XIAOMI,
                14
        );
        updSmartWatch.setId("its_number_nothing_more");
        boolean actual = target.update(updSmartWatch);
        Assertions.assertFalse(actual);
    }

    @Test
    void deleteExistingSmartWatch() {
        target.save(smartWatch1);
        boolean actual = target.delete(smartWatch1.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteNotExistingSmartWatch() {
        target.save(smartWatch1);
        boolean actual = target.delete("its_number_nothing_more");
        Assertions.assertFalse(actual);
    }

    @Test
    void findExistingSmartWatch() {
        target.save(smartWatch1);
        Optional<SmartWatch> result = target.findById(smartWatch1.getId());
        Assertions.assertEquals(smartWatch1, result.get());
    }

    @Test
    void findNotExistingSmartWatch() {
        target.save(smartWatch1);
        Optional<SmartWatch> result = target.findById("its_number_nothing_more");
        Assertions.assertTrue(result.isEmpty());
    }
}