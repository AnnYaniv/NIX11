package ua.com.yaniv;

import ua.com.yaniv.model.Laptop;
import ua.com.yaniv.model.Phone;
import ua.com.yaniv.model.SmartWatch;
import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;
import ua.com.yaniv.service.LaptopService;
import ua.com.yaniv.service.PhoneService;
import ua.com.yaniv.service.SmartWatchService;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final PhoneService PHONE_SERVICE = new PhoneService();
    private static final LaptopService LAPTOP_SERVICE = new LaptopService();
    private static final SmartWatchService SMART_WATCH_SERVICE = new SmartWatchService();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Create repositories with random products:");
        PHONE_SERVICE.createAndSavePhones(5);
        LAPTOP_SERVICE.createAndSaveLaptops(5);
        SMART_WATCH_SERVICE.createAndSaveSmartWatches(5);
        while (true) {
            printAll();
            addNewProducts(in);
            updateExistingProduct(in);
            deleteProduct(in);
            findById(in);
            System.out.println("------------");
        }
    }

    public static void findById(Scanner in){
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
        }
    }

    public static void printAll(){
        PHONE_SERVICE.printAll();
        System.out.println("------------");
        LAPTOP_SERVICE.printAll();
        System.out.println("------------");
        SMART_WATCH_SERVICE.printAll();
    }

    public static void addNewProducts(Scanner in){
        String choice;
        System.out.print("Print l - for add new laptop, p - for add new phone, s - for add new smart watch, another letters to skip: ");
        choice = in.nextLine();
        switch (choice) {
            case "l" -> LAPTOP_SERVICE.save(writeLaptop(in, true));
            case "p" -> PHONE_SERVICE.save(writePhone(in, true));
            case "s" -> SMART_WATCH_SERVICE.save(writeSmartWatch(in, true));
        }
    }

    public static void updateExistingProduct(Scanner in){
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
        }
    }

    public static void deleteProduct(Scanner in){
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
        }
    }

    public static Laptop writeLaptop(Scanner in, boolean isCreate){
        String title,model = "",temp;
        int count;
        double price;
        Manufacturer manufacturer = Manufacturer.APPLE;
        DriveType dt;
        OS os;

        System.out.print("Title: ");
        title = in.next();
        System.out.print("Count: ");
        count = in.nextInt();
        System.out.print("Price: ");
        price = in.nextDouble();
        in.nextLine();
        if(isCreate){
            System.out.print("Model: ");
            model = in.nextLine();

            System.out.print("Manufacturer (SAMSUNG, APPLE, XIAOMI, HUAWEI, ASUS): ");
            temp = in.nextLine();
            manufacturer = Manufacturer.valueOf(temp);
        }
        System.out.print("DriveType (HDD, SDD, HDD_AND_SDD): ");
        temp = in.nextLine();
        dt = DriveType.valueOf(temp);

        System.out.print("OS (Windows, Linux, MacOS, ChromeOS):");
        temp = in.nextLine();
        os = OS.valueOf(temp);

        return new Laptop(title,count,price,model,manufacturer,dt,os);
    }

    public static Phone writePhone(Scanner in, boolean isCreate){
        String title,model = " ",temp;
        int count;
        double price;
        Manufacturer manufacturer = Manufacturer.APPLE;
        int numbersOfMainCameras;
        CommunicationStandard communicationStandard;

        System.out.print("Title: ");
        title = in.next();
        System.out.print("Count: ");
        count = in.nextInt();
        System.out.print("Price: ");
        price = in.nextDouble();
        in.nextLine();
        if(isCreate){
            System.out.print("Model: ");
            model = in.nextLine();

            System.out.print("Manufacturer (SAMSUNG, APPLE, XIAOMI, HUAWEI, ASUS): ");
            temp = in.nextLine();
            manufacturer = Manufacturer.valueOf(temp);
        }
        System.out.print("Number of main camera: ");
        numbersOfMainCameras = in.nextInt();
        in.nextLine();
        System.out.print("Communication Standart (_2G, _3G, _4G, _5G):");
        temp = in.nextLine();
        communicationStandard = CommunicationStandard.valueOf(temp);

        return new Phone(title, count, price, model, manufacturer, numbersOfMainCameras,communicationStandard);
    }

    public static SmartWatch writeSmartWatch (Scanner in, boolean isCreate){
        String title,model = " ",temp;
        int count;
        double price;
        Manufacturer manufacturer = Manufacturer.APPLE;
        int daysWithoutCharge;

        System.out.print("Title: ");
        title = in.next();
        System.out.print("Count: ");
        count = in.nextInt();
        System.out.print("Price: ");
        price = in.nextDouble();
        in.nextLine();
        if(isCreate){
            System.out.print("Model: ");
            model = in.nextLine();

            System.out.print("Manufacturer (SAMSUNG, APPLE, XIAOMI, HUAWEI, ASUS): ");
            temp = in.nextLine();
            manufacturer = Manufacturer.valueOf(temp);
        }
        System.out.print("Days without charge: ");
        daysWithoutCharge = in.nextInt();
        in.nextLine();
        return new SmartWatch(title, count, price, model, manufacturer, daysWithoutCharge);
    }
}
