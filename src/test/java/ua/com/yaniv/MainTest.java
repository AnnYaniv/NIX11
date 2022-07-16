package ua.com.yaniv;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatcher;
import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.Phone;
import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.*;
import ua.com.yaniv.repository.LaptopRepository;

import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MainTest {
    Main target;
    static LaptopRepository laptopRepository;

    @BeforeAll
    public static void setup() {
        laptopRepository = mock(LaptopRepository.class);
        setMock(laptopRepository);
    }

    @BeforeEach
    void setUp() {
        target = new Main();
        reset(laptopRepository);
    }


    private static void setMock(LaptopRepository mock) {
        Field instance = null;
        try {
            instance = LaptopRepository.class.getDeclaredField("instance");
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
    }

    @Test
    void writeLaptopFromStart() {
        doNothing().when(laptopRepository).save(any());
        Scanner in = new Scanner(
                "skip\n" +
                        "l\n" +
                        "It`s a Laptop\n" +
                        "1\n" +
                        "22200.0\n" +
                        "HUAWEI-INCORECT\n" +
                        "INCORRECT_MANUFACTER\n" +
                        "HUAWEI\n" +
                        "INCORRECT_DRIVETYPE\n" +
                        "HDD_AND_SDD\n" +
                        "INCORRECT_OPERATION_SYSTEM\n" +
                        "WINDOWS\n" +
                        "exit\n" +
                        "exit\n" +
                        "exit\n" +
                        "exit\n"
        );

        target.start(in);
        verify(laptopRepository).save(argThat(new ArgumentMatcher<Laptop>() {

            @Override
            public boolean matches(Laptop argument) {
                boolean result = (argument.getDriveType() == DriveType.HDD_AND_SDD) &&
                        (argument.getCount() == 1) &&
                        (argument.getPrice() == 22200.0) &&
                        (argument.getTitle().equals("It`s a Laptop")) &&
                        (argument.getOs() == OS.WINDOWS) &&
                        (argument.getManufacturer() == Manufacturer.HUAWEI) &&
                        (argument.getModel().equals("HUAWEI-INCORECT"));
                return result;
            }

        }));
    }

    @Test
    void writeCorrectLaptop() {
        Scanner in = new Scanner(
                "It`s a Laptop\n" +
                        "1\n" +
                        "22200.0\n" +
                        "HUAWEI-123\n" +
                        "HUAWEI\n" +
                        "HDD_AND_SDD\n" +
                        "WINDOWS\n"
        );
        Laptop actual = target.writeLaptop(in, true);

        Laptop expected = new Laptop(
                "It`s a Laptop",
                1,
                22200.0,
                "HUAWEI-123",
                Manufacturer.HUAWEI,
                DriveType.HDD_AND_SDD,
                OS.WINDOWS
        );
        actual.setId("tester");
        expected.setId("tester");
        assertEquals(expected, actual);
    }

    @Test
    void writeIncorrectLaptop() {
        Scanner in = new Scanner(
                "It`s a Laptop\n" +
                        "1\n" +
                        "22200.0\n" +
                        "HUAWEI-INCORECT\n" +
                        "INCORRECT_MANUFACTER\n" +
                        "HUAWEI\n" +
                        "INCORRECT_DRIVETYPE\n" +
                        "HDD_AND_SDD\n" +
                        "INCORRECT_OPERATION_SYSTEM\n" +
                        "WINDOWS\n"
        );
        Laptop actual = target.writeLaptop(in, true);

        Laptop expected = new Laptop(
                "It`s a Laptop",
                1,
                22200.0,
                "HUAWEI-INCORECT",
                Manufacturer.HUAWEI,
                DriveType.HDD_AND_SDD,
                OS.WINDOWS
        );
        actual.setId("tester");
        expected.setId("tester");
        assertEquals(expected, actual);
    }

    @Test
    void writeCorrectPhone() {
        Scanner in = new Scanner(
                "It`s a IPhone\n" +
                        "1\n" +
                        "5000.0\n" +
                        "APPLE\n" +
                        "APPLE\n" +
                        "7\n" +
                        "NETWORK_5G\n"
        );
        Phone actual = target.writePhone(in, true);

        Phone expected = new Phone(
                "It`s a IPhone",
                1,
                5000.0,
                "APPLE",
                Manufacturer.APPLE,
                7,
                CommunicationStandard.NETWORK_5G
        );
        actual.setId("tester");
        expected.setId("tester");
        assertEquals(expected, actual);
    }

    @Test
    void writeIncorrectPhone() {
        Scanner in = new Scanner(
                "It`s a IPhone\n" +
                        "1\n" +
                        "5000.0\n" +
                        "APPLE-INCORECT\n" +
                        "INCORECT-MANUFACTURER\n" +
                        "APPLE\n" +
                        "7\n" +
                        "INCORECT-NETWORK\n" +
                        "NETWORK_5G\n"
        );
        Phone actual = target.writePhone(in, true);

        Phone expected = new Phone(
                "It`s a IPhone",
                1,
                5000.0,
                "APPLE-INCORECT",
                Manufacturer.APPLE,
                7,
                CommunicationStandard.NETWORK_5G
        );
        actual.setId("tester");
        expected.setId("tester");
        assertEquals(expected, actual);
    }

    @Test
    void writeCorrectSmartWatch() {
        Scanner in = new Scanner(
                "It`s a smart watches\n" +
                        "1\n" +
                        "850.0\n" +
                        "Mi-Band-10000\n" +
                        "XIAOMI\n" +
                        "20\n"
        );
        SmartWatch actual = target.writeSmartWatch(in, true);

        SmartWatch expected = new SmartWatch(
                "It`s a smart watches",
                1,
                850.0,
                "Mi-Band-10000",
                Manufacturer.XIAOMI,
                20
        );
        actual.setId("tester");
        expected.setId("tester");
        assertEquals(expected, actual);
    }

    @Test
    void writeIncorrectSmartWatch() {
        Scanner in = new Scanner(
                "It`s a smart watches\n" +
                        "1\n" +
                        "850.0\n" +
                        "Mi-Band-10000\n" +
                        "INCORECT-MANUFACTURER\n" +
                        "XIAOMI\n" +
                        "20\n"
        );
        SmartWatch actual = target.writeSmartWatch(in, true);

        SmartWatch expected = new SmartWatch(
                "It`s a smart watches",
                1,
                850.0,
                "Mi-Band-10000",
                Manufacturer.XIAOMI,
                20
        );
        actual.setId("tester");
        expected.setId("tester");
        assertEquals(expected, actual);
    }
}