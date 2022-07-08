package ua.com.yaniv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.Phone;
import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;
import ua.com.yaniv.repository.LaptopRepository;

import java.util.Scanner;

class MainTest {
    Main target;
    @Mock
    Main main;
    LaptopRepository laptopRepository;
    @BeforeEach
    void setUp() {
        target = new Main();
        main = target;
        laptopRepository = LaptopRepository.getInstance();
    }

    @Test
    void startExitTest() {

        Scanner in = new Scanner("skip_addNewProducts\n" +
                "skip_updateExistingProduct\n" +
                "skip_deleteProduct\n" +
                "skip_findById\n"+
                "exit");

        int i = target.start(in);

        Assertions.assertEquals(0, i);
    }


    @Test
    void writeCorrectLaptop() {
        Scanner in = new Scanner(
                "It`s a Laptop\n"+
                        "1\n"+
                        "22200.0\n"+
                        "HUAWEI-123\n"+
                        "HUAWEI\n"+
                        "HDD_AND_SDD\n"+
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
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void writeIncorrectLaptop() {
        Scanner in = new Scanner(
                "It`s a Laptop\n"+
                        "1\n"+
                        "22200.0\n"+
                        "HUAWEI-INCORECT\n"+
                        "INCORRECT_MANUFACTER\n"+
                        "HUAWEI\n"+
                        "INCORRECT_DRIVETYPE\n"+
                        "HDD_AND_SDD\n"+
                        "INCORRECT_OPERATION_SYSTEM\n"+
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
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void writeCorrectPhone() {
        Scanner in = new Scanner(
                "It`s a IPhone\n"+
                        "1\n"+
                        "5000.0\n"+
                        "APPLE\n"+
                        "APPLE\n"+
                        "7\n"+
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
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void writeIncorrectPhone() {
        Scanner in = new Scanner(
                "It`s a IPhone\n"+
                        "1\n"+
                        "5000.0\n"+
                        "APPLE-INCORECT\n"+
                        "INCORECT-MANUFACTURER\n"+
                        "APPLE\n"+
                        "7\n"+
                        "INCORECT-NETWORK\n"+
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
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void writeCorrectSmartWatch() {
        Scanner in = new Scanner(
                "It`s a smart watches\n"+
                        "1\n"+
                        "850.0\n"+
                        "Mi-Band-10000\n"+
                        "XIAOMI\n"+
                        "20\n"
        );
        SmartWatch actual = target.writeSmartWatch(in, true);

        SmartWatch  expected = new SmartWatch(
                "It`s a smart watches",
                1,
                850.0,
                "Mi-Band-10000",
                Manufacturer.XIAOMI,
                20
        );
        actual.setId("tester");
        expected.setId("tester");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void writeIncorrectSmartWatch() {
        Scanner in = new Scanner(
                "It`s a smart watches\n"+
                        "1\n"+
                        "850.0\n"+
                        "Mi-Band-10000\n"+
                        "INCORECT-MANUFACTURER\n"+
                        "XIAOMI\n"+
                        "20\n"
        );
        SmartWatch actual = target.writeSmartWatch(in, true);

        SmartWatch  expected = new SmartWatch(
                "It`s a smart watches",
                1,
                850.0,
                "Mi-Band-10000",
                Manufacturer.XIAOMI,
                20
        );
        actual.setId("tester");
        expected.setId("tester");
        Assertions.assertEquals(expected, actual);
    }
}