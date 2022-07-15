package ua.com.yaniv.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ua.com.yaniv.model.Phone;
import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.repository.PhoneRepository;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class PhoneServiceTest {
    PhoneService target;
    Phone phone1, phone2;
    static PhoneRepository phoneRepository;

    @BeforeAll
    public static void setup() {
        phoneRepository = mock(PhoneRepository.class);
        setMock(phoneRepository);
    }

    @BeforeEach
    public void setUp() {
        reset(phoneRepository);
        target = new PhoneService();

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
        doNothing().when(phoneRepository).save(any());
        doThrow(new IllegalArgumentException("Cannot save a null phone")).when(phoneRepository).save(null);
        when(phoneRepository.saveAll(anyList())).thenCallRealMethod();
        when(phoneRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(phoneRepository.getAll()).thenReturn(Collections.emptyList());
        when(phoneRepository.update(any())).thenReturn(true);
        when(phoneRepository.delete(any())).thenReturn(true);
    }

    private static void setMock(PhoneRepository mock) {
        try {
            Field instance = PhoneRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void resetSingleton() {
        Field instance = null;
        try {
            instance = PhoneRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveNotNull() {
        String phoneId = phone1.getId();
        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);

        target.save(phone1);

        verify(phoneRepository).save(argument.capture());
        assertEquals(phoneId, argument.getValue().getId());
    }

    @Test
    void saveNull() {
        boolean actual = target.save(null);
        verify(phoneRepository).save(any());
        assertFalse(actual);
    }

    @Test
    void saveAll() {
        List<Phone> phoneList = asList(phone2, phone1);
        target.saveAll(phoneList);
        verify(phoneRepository).saveAll(anyList());
        verify(phoneRepository, times(phoneList.size())).findById(any());
        verify(phoneRepository, times(phoneList.size())).save(any());
    }

    @Test
    void deleteAnyPhone() {
        target.save(phone1);
        target.delete(phone1.getId());
        verify(phoneRepository).delete(any());
    }

    @Test
    void updateAnyPhone() {
        target.update("id", phone1);
        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);
        verify(phoneRepository).update(argument.capture());
        assertEquals("id", argument.getValue().getId());
    }

    @Test
    void findAnyPhone() {
        target.findById("id");
        verify(phoneRepository).findById("id");
    }

    @Test
    void createAndSavePhones() {
        int count = 6;
        target.createAndSavePhones(count);
        verify(phoneRepository).saveAll(any());
        verify(phoneRepository, times(count)).save(any());
    }
}