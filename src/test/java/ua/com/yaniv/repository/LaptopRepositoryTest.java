package ua.com.yaniv.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;

import java.util.List;

class LaptopRepositoryTest {
    LaptopRepository target;
    Laptop laptop;
    @BeforeEach
    void setUp()
    {
        target = LaptopRepository.getInstance();
        laptop = new Laptop(
                "Laptop-Title",
                13,
                22500.99,
                "Random-model",
                Manufacturer.XIAOMI,
                DriveType.SDD,
                OS.WINDOWS
        );
    }

    @Test
    void saveNotNullTest(){
        target.save(laptop);
        final List<Laptop> laptops = target.getAll();
        Assertions.assertEquals(1, laptops.size());
        Assertions.assertEquals(laptops.get(0).getId(), laptop.getId());
    }

    @Test
    void saveNullTest(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

}