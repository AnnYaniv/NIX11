package ua.com.yaniv.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;
import ua.com.yaniv.repository.LaptopRepository;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

// TODO work it all work together
// it`s dont works when I start all tests
// but it works when I launch one by one

class LaptopServiceTest {
    LaptopService target;
    Laptop laptop;
    @Mock
    LaptopRepository laptopRepository;

    @BeforeEach
    public void setUp() {
        laptopRepository = mock(LaptopRepository.class);
        setMock(laptopRepository);

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
        doNothing().when(laptopRepository).save(any());
        doNothing().when(laptopRepository).saveAll(anyList());
        when(laptopRepository.delete(laptop.getId())).thenReturn(true);
        when(laptopRepository.delete("its_number_nothing_more")).thenReturn(false);

    }

    private void setMock(LaptopRepository mock) {
        //put mock in instance field
        try {
            Field instance = LaptopRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void resetSingleton() {
        //reset Instance Field with reflection
        Field instance = null;
        try {
            instance = LaptopRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null,null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //its static method to reset instance field
        //LaptopRepository.resetInstance();
    }

    @Test
    void saveNotNull() {
        target.save(laptop);
        ArgumentCaptor<Laptop> argument = ArgumentCaptor.forClass(Laptop.class);
        Mockito.verify(laptopRepository).save(argument.capture());
        Assertions.assertEquals("Laptop-Title", argument.getValue().getTitle());
    }

    @Test
    void saveNull() {
        doThrow(new IllegalArgumentException("Cannot save a null laptop")).when(laptopRepository).save(null);
        boolean actual = target.save(null);
        Mockito.verify(laptopRepository).save(any());
        Assertions.assertEquals(false, actual);
    }

    @Test
    void saveAll(){
        target.saveAll(anyList());
        Mockito.verify(laptopRepository).saveAll(anyList());
    }

    @Test
    void createAndSaveManyLaptops(){
        int counts_of_laptops = 3;
        target.createAndSaveLaptops(counts_of_laptops);
        Mockito.verify(laptopRepository,times(counts_of_laptops)).save(any());
    }

    @Test
    void deleteExistingLaptop() {
        target.save(laptop);
        boolean actual = target.delete(laptop.getId());
        Mockito.verify(laptopRepository).delete(any());
        Assertions.assertEquals(true, actual);
    }

    @Test
    void deleteNotExistingLaptop() {
        target.save(laptop);
        boolean actual = target.delete("its_number_nothing_more");
        Assertions.assertEquals(false, actual);
    }
}