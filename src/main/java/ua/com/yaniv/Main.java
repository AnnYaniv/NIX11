package ua.com.yaniv;

import ua.com.yaniv.service.PhoneService;


public class Main {
    private static final PhoneService PHONE_SERVICE = new PhoneService();

    public static void main(String[] args) {
        PHONE_SERVICE.createAndSavePhones(10);
        PHONE_SERVICE.printAll();
    }
}
