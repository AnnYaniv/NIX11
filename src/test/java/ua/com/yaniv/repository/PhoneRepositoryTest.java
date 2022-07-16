package ua.com.yaniv.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.yaniv.model.Phone;
import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.Manufacturer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PhoneRepositoryTest {
    PhoneRepository target;
    Phone phone1, phone2, phoneNull;

    @BeforeEach
    void setUp() {
        target = PhoneRepository.getInstance();
        phone1 = new Phone(
                "IPhone",
                12,
                9999.9,
                "IPhone 10",
                Manufacturer.APPLE,
                5,
                CommunicationStandard.NETWORK_5G
        );
        phone2 = new Phone(
                "XIAOMI RedMi",
                100,
                3000.9,
                "RedMi Note 7",
                Manufacturer.XIAOMI,
                2,
                CommunicationStandard.NETWORK_5G
        );
        phoneNull = null;
    }

    @AfterEach
    void tearDown() {
        Field instance = null;
        try {
            instance = PhoneRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save in {@link PhoneRepository#save(Phone)} a not-null reference with optionals.ifPresentOrElse
     *
     * @result phone save
     */
    @Test
    void saveNotNull() {
        target.save(phone1);
        final List<Phone> phones = target.getAll();
        assertEquals(1, phones.size());
        assertEquals(phones.get(0).getId(), phone1.getId());
    }

    /**
     * Save in {@link PhoneRepository#save(Phone)} a null reference with optionals.ifPresentOrElse
     *
     * @throws IllegalArgumentException
     * @result phone don`t save and throw exception
     */
    @Test
    void saveNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(phoneNull));
        final List<Phone> actualResult = target.getAll();
        assertEquals(0, actualResult.size());
    }

    @Test
    void saveAllWithDublicate() {
        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        phones.add(phone2);
        phones.add(phone2);
        boolean actual = target.saveAll(phones);
        assertEquals(2, target.getAll().size());
        assertFalse(actual);

    }

    @Test
    void saveAllWithNull() {
        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        phones.add(phone2);
        phones.add(phoneNull);
        boolean actual = target.saveAll(phones);
        assertEquals(2, target.getAll().size());
        assertFalse(actual);
    }

    @Test
    void saveAllWithCorrectPhones() {
        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        phones.add(phone2);
        boolean actual = target.saveAll(phones);
        assertEquals(2, target.getAll().size());
        assertTrue(actual);
    }

    @Test
    void updateExistingPhone() {
        target.save(phone1);
        Phone updPhone = new Phone(
                "XIAOMI RedMi",
                100,
                3000.9,
                "RedMi Note 7",
                Manufacturer.XIAOMI,
                2,
                CommunicationStandard.NETWORK_5G
        );
        updPhone.setId(phone1.getId());
        boolean actual = target.update(updPhone);
        Phone actualPhone = target.findById(updPhone.getId()).get();
        assertTrue(actual);
        assertEquals(updPhone, actualPhone);
    }

    @Test
    void updateNotExistingPhone() {
        target.save(phone1);
        Phone updPhone = new Phone(
                "XIAOMI RedMi",
                100,
                3000.9,
                "RedMi Note 7",
                Manufacturer.XIAOMI,
                2,
                CommunicationStandard.NETWORK_5G
        );
        updPhone.setId("its_number_nothing_more");
        boolean actual = target.update(updPhone);
        assertFalse(actual);
    }

    @Test
    void deleteExistingPhone() {
        target.save(phone1);
        boolean actual = target.delete(phone1.getId());
        assertTrue(actual);
    }

    @Test
    void deleteNotExistingPhone() {
        target.save(phone1);
        boolean actual = target.delete("its_number_nothing_more");
        assertFalse(actual);
    }

    @Test
    void findExistingPhone() {
        target.save(phone1);
        Optional<Phone> result = target.findById(phone1.getId());
        assertEquals(phone1, result.get());
    }

    @Test
    void findNotExistingPhone() {
        target.save(phone1);
        Optional<Phone> result = target.findById("its_number_nothing_more");
        assertTrue(result.isEmpty());
    }
}