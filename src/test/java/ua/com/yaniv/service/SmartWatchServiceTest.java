package ua.com.yaniv.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.repository.SmartWatchRepository;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class SmartWatchServiceTest {
    SmartWatchService target;
    SmartWatch smartWatch1, smartWatch2;
    static SmartWatchRepository smartWatchRepository;

    @BeforeAll
    public static void setup() {
        smartWatchRepository = mock(SmartWatchRepository.class);
        setMock(smartWatchRepository);
    }

    @BeforeEach
    public void setUp() {
        reset(smartWatchRepository);
        target = new SmartWatchService();

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
        doNothing().when(smartWatchRepository).save(any());
        doThrow(new IllegalArgumentException("Cannot save a null smartWatch")).when(smartWatchRepository).save(null);
        when(smartWatchRepository.saveAll(anyList())).thenCallRealMethod();
        when(smartWatchRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(smartWatchRepository.getAll()).thenReturn(Collections.emptyList());
        when(smartWatchRepository.update(any())).thenReturn(true);
        when(smartWatchRepository.delete(any())).thenReturn(true);
    }

    private static void setMock(SmartWatchRepository mock) {
        try {
            Field instance = SmartWatchRepository.class.getDeclaredField("instance");
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
            instance = SmartWatchRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveNotNull() {
        String smartWatchId = smartWatch1.getId();
        ArgumentCaptor<SmartWatch> argument = ArgumentCaptor.forClass(SmartWatch.class);

        target.save(smartWatch1);

        verify(smartWatchRepository).save(argument.capture());
        assertEquals(smartWatchId, argument.getValue().getId());
    }

    @Test
    void saveNull() {
        boolean actual = target.save(null);
        verify(smartWatchRepository).save(any());
        assertFalse(actual);
    }

    @Test
    void saveAll() {
        List<SmartWatch> smartWatchList = asList(smartWatch2, smartWatch1);
        target.saveAll(smartWatchList);
        verify(smartWatchRepository).saveAll(anyList());
        verify(smartWatchRepository, times(smartWatchList.size())).findById(any());
        verify(smartWatchRepository, times(smartWatchList.size())).save(any());
    }

    @Test
    void deleteAnySmartWatch() {
        target.save(smartWatch1);
        target.delete(smartWatch1.getId());
        verify(smartWatchRepository).delete(any());
    }

    @Test
    void updateAnySmartWatch() {
        target.update("id", smartWatch1);
        ArgumentCaptor<SmartWatch> argument = ArgumentCaptor.forClass(SmartWatch.class);
        verify(smartWatchRepository).update(argument.capture());
        assertEquals("id", argument.getValue().getId());
    }

    @Test
    void findAnySmartWatch() {
        target.findById("id");
        verify(smartWatchRepository).findById("id");
    }

    @Test
    void createAndSaveSmartWatches() {
        int count = 6;
        target.createAndSaveSmartWatches(count);
        verify(smartWatchRepository).saveAll(any());
        verify(smartWatchRepository, times(count)).save(any());
    }
}