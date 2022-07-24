package ua.com.yaniv.model;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;
import ua.com.yaniv.model.enums.*;
import ua.com.yaniv.service.LaptopService;
import ua.com.yaniv.service.PhoneService;
import ua.com.yaniv.service.SmartWatchService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class ProductFactory {
    private static final Random RANDOM = new Random();
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private ProductFactory() {
    }

    public static Product createRandomProduct(ProductType type) {
        if (type.equals(ProductType.LAPTOP))
            return new Laptop(
                    "Laptop-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-l" + RANDOM.nextInt(1000),
                    LaptopService.getRandomManufacturer(),
                    LaptopService.getRandomDriveType(),
                    LaptopService.getRandomOS()
            );
        if (type.equals(ProductType.PHONE))
            return new Phone(
                    "Phone-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-p" + RANDOM.nextInt(1000),
                    PhoneService.getRandomManufacturer(),
                    RANDOM.nextInt(5),
                    PhoneService.getRandomCommunicationStandart()
            );
        if (type.equals(ProductType.SMART_WATCH))
            return new SmartWatch(
                    "SmartWatch-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-sw" + RANDOM.nextInt(1000),
                    SmartWatchService.getRandomManufacturer(),
                    RANDOM.nextInt(22)
            );
        return null;
    }

    public static Product createProduct(ProductType type) throws IOException {
        String temp;

        String title = getString("Title: ");
        String model = getString("Model: ");

        int count = getNumber("Count: ");
        double price = getDouble("Price: ");

        do {
            System.out.printf("Manufacturer (%s): ", getManufacturers());
            temp = READER.readLine();
        } while (!EnumUtils.isValidEnum(Manufacturer.class, temp));
        Manufacturer manufacturer = Manufacturer.valueOf(temp);

        if (type.equals(ProductType.LAPTOP)) {
            do {
                System.out.printf("DriveType (%s): ", getDriveType());
                temp = READER.readLine();
            } while (!EnumUtils.isValidEnum(DriveType.class, temp));
            DriveType driveType = DriveType.valueOf(temp);

            do {
                System.out.printf("OS (%s):", getOS());
                temp = READER.readLine();
            } while (!EnumUtils.isValidEnum(OS.class, temp));

            OS os = OS.valueOf(temp);

            return new Laptop(title, count, price, model, manufacturer, driveType, os);
        }
        if (type.equals(ProductType.PHONE)) {
            int numbersOfMainCameras = getNumber("Number of main camera: ");

            do {
                System.out.printf("Communication Standard (%s):", getCommunicationStandard());
                temp = READER.readLine();
            } while (!EnumUtils.isValidEnum(CommunicationStandard.class, temp));
            CommunicationStandard communicationStandard = CommunicationStandard.valueOf(temp);

            return new Phone(title, count, price, model, manufacturer, numbersOfMainCameras, communicationStandard);
        }
        if (type.equals(ProductType.SMART_WATCH)) {
            int daysWithoutCharge = getNumber("Days without charge: ");

            return new SmartWatch(title, count, price, model, manufacturer, daysWithoutCharge);
        }

        return null;
    }

    private static int getNumber(String message) throws IOException {
        String temp;
        do {
            System.out.print(message);
            temp = READER.readLine();
        } while (!NumberUtils.isParsable(temp));

        return Integer.parseInt(temp);
    }

    private static double getDouble(String message) throws IOException {
        String temp;
        do {
            System.out.print(message);
            temp = READER.readLine();
        } while (!NumberUtils.isParsable(temp));

        return Double.parseDouble(temp);
    }

    private static String getString(String message) throws IOException {
        System.out.print(message);
        return READER.readLine();
    }

    private static String getManufacturers() {
        StringBuilder sb = new StringBuilder();
        Manufacturer[] values = Manufacturer.values();
        for (Manufacturer manufacturer : values) {
            sb.append(manufacturer);
            sb.append(" ");
        }
        return sb.toString();
    }

    private static String getOS() {
        StringBuilder sb = new StringBuilder();
        OS[] values = OS.values();
        for (OS value : values) {
            sb.append(value);
            sb.append(" ");
        }
        return sb.toString();
    }

    private static String getCommunicationStandard() {
        StringBuilder sb = new StringBuilder();
        CommunicationStandard[] values = CommunicationStandard.values();
        for (CommunicationStandard value : values) {
            sb.append(value);
            sb.append(" ");
        }
        return sb.toString();
    }

    private static String getDriveType() {
        StringBuilder sb = new StringBuilder();
        DriveType[] values = DriveType.values();
        for (DriveType value : values) {
            sb.append(value);
            sb.append(" ");
        }
        return sb.toString();
    }
}
