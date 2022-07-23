package ua.com.yaniv;

import org.apache.commons.lang3.EnumUtils;
import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.Phone;
import ua.com.yaniv.model.Product;
import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;
import ua.com.yaniv.repository.LaptopRepository;
import ua.com.yaniv.repository.PhoneRepository;
import ua.com.yaniv.repository.SmartWatchRepository;
import ua.com.yaniv.service.LaptopService;
import ua.com.yaniv.service.PhoneService;
import ua.com.yaniv.service.ProductService;
import ua.com.yaniv.service.SmartWatchService;
import ua.com.yaniv.service.Container;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final PhoneService PHONE_SERVICE = new PhoneService();
    private static final LaptopService LAPTOP_SERVICE = new LaptopService();
    private static final SmartWatchService SMART_WATCH_SERVICE = new SmartWatchService();
    private static Container container;

    private static ProductService productService;
    private static String printVar = "a";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        startContainer(in);
        printVar = "a";
        start(in); // work with services
    }

    public static int startContainer(Scanner in) {
        String choice;
        System.out.println("Choose product for work with");
        System.out.print("Print l - for laptop, p - for phone, s - for smart watch, another letters to exit: ");
        choice = in.nextLine();
        switch (choice) {
            case "l" -> {
                container = new Container<Laptop>();
                printVar = "l";
            }
            case "p" -> {
                container = new Container<Phone>();
                printVar = "p";
            }
            case "s" -> {
                container = new Container<SmartWatch>();
                printVar = "s";
            }
            default -> {
                System.out.println("Skipped");
                return 1;
            }
        }

        while (true) {
            System.out.print("Print `y` - to add products, another letters to skip:");
            choice = in.nextLine();
            if (choice.equals("y")) {
                switch (printVar){
                    case "l" -> container.save(writeLaptop(in, true));
                    case "p" -> container.save(writePhone(in, true));
                    case "s" -> container.save(writeSmartWatch(in, true));
                }
            }
            else System.out.println("Skipped");
            System.out.print(container);
            System.out.print("Print `y` - to delete products, another letters to skip:");
            choice = in.nextLine();
            if (choice.equals("y")) {
                System.out.print("Print id: ");
                String id = in.nextLine();
                boolean res = container.delete(id);
                if (res) System.out.println("Product was deleted");
                else System.out.println("Product wasn`t deleted");
            }
            else System.out.println("Skipped");
            System.out.print("Print `y` - to add discount, another letters to skip:");
            choice = in.nextLine();
            if (choice.equals("y")) {
                System.out.print("Print id: ");
                String id = in.nextLine();
                boolean res = container.setRandomDiscount(id);
                if (res) System.out.println("Product discount was changed");
                else System.out.println("Product discount wasn`t changed");
            }
            System.out.print("Print `y` - to add product count by id, another letters to skip:");
            choice = in.nextLine();
            if (choice.equals("y")) {
                System.out.print("Print id: ");
                String id = in.nextLine();
                System.out.print("Print count to add: ");
                double count = in.nextDouble();
                in.nextLine();
                boolean res = container.provideProductsById(id, count);
                if (res) System.out.println("Product count was changed");
                else System.out.println("Product count wasn`t changed");
            }

            System.out.print("Print 'exit' - to exit, another letters to continue: ");
            choice = in.nextLine();
            if (choice.equals("exit")) {
                return 0;
            }
        }
    }

    public static int startOptionals(Scanner in) {
        String choice;
        System.out.println("Choose product for work with");
        System.out.print("Print l - for laptop, p - for phone, s - for smart watch, another letters to exit: ");
        choice = in.nextLine();
        switch (choice) {
            case "l" -> {
                productService = new ProductService(LaptopRepository.getInstance());
                printVar = "l";
            }
            case "p" -> {
                productService = new ProductService(PhoneRepository.getInstance());
                printVar = "p";
            }
            case "s" -> {
                productService = new ProductService(SmartWatchRepository.getInstance());
                printVar = "s";
            }
            default -> {
                System.out.println("Skipped");
                return 1;
            }
        }
        addAnyRandomProducts(in);
        while (true) {
            printAll();
            System.out.print("Print 'y' if you want check manufacturer for product with specified id: ");
            choice = in.nextLine();
            if (choice.equals("y")) checkManufacturer(in);
            else System.out.println("Skipped");

            System.out.print("Print 'y' if you want buy product with specified id: ");
            choice = in.nextLine();
            if (choice.equals("y")) buyProduct(in);
            else System.out.println("Skipped");

            System.out.print("Print 'y' if you want to find product with specified id and manufacturer\n" +
                    "(NOTE!) If product dont exist, it was created\n" +
                    "If product exist with another manufacturer, it will be changed: ");
            choice = in.nextLine();
            if (choice.equals("y")) System.out.println(findOrCreateWithManufacter(in));
            else System.out.println("Skipped");

            System.out.print("Print 'y' if you want to find product with specified id\n" +
                    "(NOTE!) If product dont exist, it was created: ");
            choice = in.nextLine();
            if (choice.equals("y")) System.out.println(findOrCreateWithId(in));
            else System.out.println("Skipped");

            System.out.print("Print 'exit' - to exit, another letters to continue: ");
            choice = in.nextLine();
            if (choice.equals("exit")) {
                return 0;
            }
        }
    }

    public static Product findOrCreateWithId(Scanner in) {
        String id = getId(in);
        return productService.returnOrCreateWithId(id);
    }

    public static Product findOrCreateWithManufacter(Scanner in) {
        String id = getId(in);
        Manufacturer manufacturer = getManufacturer(in);
        return productService.returnProductWithManufacturerOrSet(id, manufacturer);
    }

    public static void buyProduct(Scanner in) {
        String id = getId(in);
        if (productService.buySingleProduct(id)) {
            System.out.println("Bought product with id '" + id + "'");
        } else {
            System.out.println("Failed to buy product with id '" + id + "'");
        }
    }

    ;

    public static void checkManufacturer(Scanner in) {
        String id = getId(in);
        Manufacturer manufacturer = getManufacturer(in);
        System.out.println(productService.returnProductInfoWithManufacturer(id, manufacturer));
    }

    public static String getId(Scanner in) {
        System.out.print("Enter the ID of the product:");
        return in.nextLine();
    }

    public static Manufacturer getManufacturer(Scanner in) {
        String temp;
        System.out.printf("Enter the manufacturer(%s): ", getManufacturers());
        do {
            temp = in.nextLine();
            if (EnumUtils.isValidEnum(Manufacturer.class, temp))
                break;
            else System.out.println("Wrong manufacturer, try again:");
        } while (true);
        return Manufacturer.valueOf(temp);
    }

    public static int start(Scanner in) {
        String choice;

        while (true) {
            addAnyRandomProducts(in);
            printAll();
            addNewProducts(in);
            updateExistingProduct(in);
            deleteProduct(in);
            findById(in);
            System.out.print("Print 'exit' - to exit, another letters to continue");
            choice = in.nextLine();
            if (choice.equals("exit")) {
                return 0;
            }
        }
    }


    public static void addAnyRandomProducts(Scanner in) {
        String choice;
        System.out.print("Print y - to add any random products, another letters to skip: ");
        choice = in.nextLine();
        switch (choice) {
            case "y" -> {
                PHONE_SERVICE.createAndSavePhones(5);
                LAPTOP_SERVICE.createAndSaveLaptops(5);
                SMART_WATCH_SERVICE.createAndSaveSmartWatches(5);
            }
            default -> System.out.println("Skipped");
        }

    }

    public static void findById(Scanner in) {
        String choice, id;
        System.out.print("Print l - for find laptop, p - for find phone, s - for find smart watch, another letters to skip: ");
        choice = in.nextLine();
        switch (choice) {
            case "l" -> {
                System.out.print("Print id: ");
                id = in.nextLine();
                Optional<Laptop> result = LAPTOP_SERVICE.findById(id);
                if (result.isEmpty()) {
                    System.out.println("Laptop not found");
                } else {
                    System.out.println(result.get());
                }
            }
            case "p" -> {
                System.out.print("Print id: ");
                id = in.nextLine();
                Optional<Phone> result1 = PHONE_SERVICE.findById(id);
                if (result1.isEmpty()) {
                    System.out.println("Phone not found");
                } else {
                    System.out.println(result1.get());
                }
            }
            case "s" -> {
                System.out.print("Print id: ");
                id = in.nextLine();
                Optional<SmartWatch> result2 = SMART_WATCH_SERVICE.findById(id);
                if (result2.isEmpty()) {
                    System.out.println("Smart watch not found");
                } else {
                    System.out.println(result2.get());
                }
            }
            default -> System.out.println("Skipped");
        }
    }

    public static void printAll() {

        switch (printVar) {
            case "p" -> {
                PHONE_SERVICE.printAll();
            }
            case "l" -> {
                LAPTOP_SERVICE.printAll();
            }
            case "s" -> {
                SMART_WATCH_SERVICE.printAll();
            }
            default -> {
                PHONE_SERVICE.printAll();
                LAPTOP_SERVICE.printAll();
                SMART_WATCH_SERVICE.printAll();
            }
        }
    }

    public static void addNewProducts(Scanner in) {
        String choice;
        System.out.print("Print l - for add new laptop, p - for add new phone, s - for add new smart watch, another letters to skip: ");
        choice = in.nextLine();
        switch (choice) {
            case "l" -> LAPTOP_SERVICE.save(writeLaptop(in, true));
            case "p" -> PHONE_SERVICE.save(writePhone(in, true));
            case "s" -> SMART_WATCH_SERVICE.save(writeSmartWatch(in, true));
            default -> System.out.println("Skipped");
        }
    }

    public static void updateExistingProduct(Scanner in) {
        String choice, id;
        System.out.print("Print l - for update laptop, p - for update phone, s - for update smart watch, another letters to skip: ");
        choice = in.nextLine();
        switch (choice) {
            case "l" -> {
                System.out.print("Print id: ");
                id = in.nextLine();
                if (LAPTOP_SERVICE.findById(id).isEmpty()) System.out.println("Product not found");
                else LAPTOP_SERVICE.update(id, writeLaptop(in, false));
            }
            case "p" -> {
                System.out.print("Print id: ");
                id = in.nextLine();
                if (PHONE_SERVICE.findById(id).isEmpty()) System.out.println("Product not found");
                else PHONE_SERVICE.update(id, writePhone(in, false));
            }
            case "s" -> {
                System.out.print("Print id: ");
                id = in.nextLine();
                if (SMART_WATCH_SERVICE.findById(id).isEmpty()) System.out.println("Product not found");
                else SMART_WATCH_SERVICE.update(id, writeSmartWatch(in, false));
            }
            default -> System.out.println("Skipped");
        }
    }

    public static void deleteProduct(Scanner in) {
        String choice, id;
        boolean res;
        System.out.print("Print l - for delete laptop, p - for delete phone, s - for delete smart watch, another letters to skip: ");
        choice = in.nextLine();
        switch (choice) {
            case "l" -> {
                System.out.print("Print id: ");
                id = in.nextLine();
                res = LAPTOP_SERVICE.delete(id);
                if (res) System.out.println("Product was deleted");
                else System.out.println("Product wasn`t deleted");
            }
            case "p" -> {
                System.out.print("Print id: ");
                id = in.nextLine();
                res = PHONE_SERVICE.delete(id);
                if (res) System.out.println("Product was deleted");
                else System.out.println("Product wasn`t deleted");
            }
            case "s" -> {
                System.out.print("Print id: ");
                id = in.nextLine();
                res = SMART_WATCH_SERVICE.delete(id);
                if (res) System.out.println("Product was deleted");
                else System.out.println("Product wasn`t deleted");
            }
            default -> System.out.println("Skipped");
        }
    }

    public static Laptop writeLaptop(Scanner in, boolean isCreate) {
        String title, model = "", temp;
        int count;
        double price;
        Manufacturer manufacturer = Manufacturer.APPLE;
        DriveType dt;
        OS os;

        System.out.print("Title: ");
        title = in.nextLine();
        System.out.print("Count: ");
        count = in.nextInt();
        System.out.print("Price: ");
        price = in.nextDouble();
        in.nextLine();
        if (isCreate) {
            System.out.print("Model: ");
            model = in.nextLine();

            System.out.printf("Manufacturer (%s): ", getManufacturers());
            do {
                temp = in.nextLine();
                if (EnumUtils.isValidEnum(Manufacturer.class, temp))
                    break;
                else System.out.println("Wrong manufacturer, try again:");
            } while (true);
            manufacturer = Manufacturer.valueOf(temp);
        }
        System.out.printf("DriveType (%s): ", getDriveType());
        do {
            temp = in.nextLine();
            if (EnumUtils.isValidEnum(DriveType.class, temp))
                break;
            else System.out.println("Wrong drive type, try again:");
        } while (true);
        dt = DriveType.valueOf(temp);

        System.out.printf("OS (%s):", getOS());
        do {
            temp = in.nextLine();
            if (EnumUtils.isValidEnum(OS.class, temp))
                break;
            else System.out.println("Wrong operating system, try again:");
        } while (true);
        os = OS.valueOf(temp);

        return new Laptop(title, count, price, model, manufacturer, dt, os);
    }

    public static Phone writePhone(Scanner in, boolean isCreate) {
        String title, model = " ", temp;
        int count;
        double price;
        Manufacturer manufacturer = Manufacturer.APPLE;
        int numbersOfMainCameras;
        CommunicationStandard communicationStandard;

        System.out.print("Title: ");
        title = in.nextLine();
        System.out.print("Count: ");
        count = in.nextInt();
        System.out.print("Price: ");
        price = in.nextDouble();
        in.nextLine();
        if (isCreate) {
            System.out.print("Model: ");
            model = in.nextLine();

            System.out.printf("Manufacturer (%s): ", getManufacturers());
            do {
                temp = in.nextLine();
                if (EnumUtils.isValidEnum(Manufacturer.class, temp))
                    break;
                else System.out.println("Wrong manufacturer, try again:");
            } while (true);
            manufacturer = Manufacturer.valueOf(temp);
        }
        System.out.print("Number of main camera: ");
        numbersOfMainCameras = in.nextInt();
        in.nextLine();
        System.out.printf("Communication Standart (%s):", getCommunicationStandard());

        do {
            temp = in.nextLine();
            if (EnumUtils.isValidEnum(CommunicationStandard.class, temp))
                break;
            else System.out.println("Wrong communication standard, try again:");
        } while (true);
        communicationStandard = CommunicationStandard.valueOf(temp);

        return new Phone(title, count, price, model, manufacturer, numbersOfMainCameras, communicationStandard);
    }

    public static SmartWatch writeSmartWatch(Scanner in, boolean isCreate) {
        String title, model = " ", temp;
        int count;
        double price;
        Manufacturer manufacturer = Manufacturer.APPLE;
        int daysWithoutCharge;

        System.out.print("Title: ");
        title = in.nextLine();
        System.out.print("Count: ");
        count = in.nextInt();
        System.out.print("Price: ");
        price = in.nextDouble();
        in.nextLine();
        if (isCreate) {
            System.out.print("Model: ");
            model = in.nextLine();

            System.out.printf("Manufacturer (%s): ", getManufacturers());
            do {
                temp = in.nextLine();
                if (EnumUtils.isValidEnum(Manufacturer.class, temp))
                    break;
                else System.out.println("Wrong manufacturer, try again:");
            } while (true);
            manufacturer = Manufacturer.valueOf(temp);
        }
        System.out.print("Days without charge: ");
        daysWithoutCharge = in.nextInt();
        in.nextLine();
        return new SmartWatch(title, count, price, model, manufacturer, daysWithoutCharge);
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
