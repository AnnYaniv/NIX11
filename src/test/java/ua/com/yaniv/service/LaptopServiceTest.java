package ua.com.yaniv.service;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;
import ua.com.yaniv.repository.LaptopRepository;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class LaptopServiceTest {
    LaptopService target;
    Laptop laptop, laptop1, laptop2;
    static LaptopRepository laptopRepository;

    @BeforeAll
    public static void setup() {
        laptopRepository = mock(LaptopRepository.class);
        setMock(laptopRepository);
    }

    @BeforeEach
    public void setUp() {
        reset(laptopRepository);
        target = new LaptopService();
        laptop = new Laptop(
                "Laptop-Title",
                13,
                22500.99,
                "Random-model",
                Manufacturer.XIAOMI,
                DriveType.SDD,
                OS.WINDOWS
        );
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

        doNothing().when(laptopRepository).save(any());
        doThrow(new IllegalArgumentException("Cannot save a null laptop")).when(laptopRepository).save(null);
        when(laptopRepository.saveAll(anyList())).thenCallRealMethod();
        when(laptopRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(laptopRepository.getAll()).thenReturn(Collections.emptyList());
        when(laptopRepository.update(any())).thenReturn(true);
        when(laptopRepository.delete(any())).thenReturn(true);
    }

    private static void setMock(LaptopRepository mock) {
        try {
            Field instance = LaptopRepository.class.getDeclaredField("instance");
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
            instance = LaptopRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        reset(laptopRepository);
    }

    @Test
    void saveNotNull() {
        String laptopId = laptop.getId();
        ArgumentCaptor<Laptop> argument = ArgumentCaptor.forClass(Laptop.class);

        target.save(laptop);

        verify(laptopRepository).save(argument.capture());
        assertEquals(laptopId, argument.getValue().getId());
    }

    @Test
    void saveNull() {
        boolean actual = target.save(null);
        Mockito.verify(laptopRepository).save(any());
        assertFalse(actual);
    }

    @Test
    void saveAll() {
        List<Laptop> laptopList = asList(laptop, laptop1, laptop2);
        target.saveAll(laptopList);
        verify(laptopRepository).saveAll(anyList());
        verify(laptopRepository, times(laptopList.size())).findById(any());
        verify(laptopRepository, times(laptopList.size())).save(any());
    }

    @Test
    void deleteAnyLaptop() {
        target.save(laptop);
        target.delete(laptop.getId());
        verify(laptopRepository).delete(any());
    }

    @Test
    void updateAnyLaptop() {
        target.update("id", laptop);
        ArgumentCaptor<Laptop> argument = ArgumentCaptor.forClass(Laptop.class);
        verify(laptopRepository).update(argument.capture());
        assertEquals("id", argument.getValue().getId());
    }

    @Test
    void findAnyLaptop() {
        target.findById("id");
        verify(laptopRepository).findById("id");
    }

    @Test
    void createAndSaveLaptops() {
        int count = 6;
        target.createAndSaveLaptops(count);
        verify(laptopRepository).saveAll(any());
        verify(laptopRepository, times(count)).save(any());
    }
}