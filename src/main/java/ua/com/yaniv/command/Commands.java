package ua.com.yaniv.command;

import lombok.Getter;
import ua.com.yaniv.command.crud.*;
import ua.com.yaniv.model.enums.ProductType;
import ua.com.yaniv.service.LaptopService;
import ua.com.yaniv.service.PhoneService;
import ua.com.yaniv.service.SmartWatchService;

@Getter
public enum Commands {

    CREATE_PHONE("Create phone", new Create<>(new PhoneService(), ProductType.PHONE)),
    CREATE_LAPTOP("Create laptop", new Create<>(new LaptopService(), ProductType.LAPTOP)),
    CREATE_SMART_WATCH("Create smart watch", new Create<>(new SmartWatchService(), ProductType.SMART_WATCH)),

    CREATE_RANDOM_PHONES("Create random phones", new CreateRandomProducts<>(new PhoneService(), ProductType.PHONE)),
    CREATE_RANDOM_LAPTOPS("Create random laptops", new CreateRandomProducts<>(new LaptopService(), ProductType.LAPTOP)),
    CREATE_RANDOM_SMART_WATCHES("Create random smart watches", new CreateRandomProducts<>(new SmartWatchService(), ProductType.SMART_WATCH)),

    UPDATE_PHONE("Update phone", new Update<>(new PhoneService(), ProductType.PHONE)),
    UPDATE_LAPTOP("Update laptop", new Update<>(new LaptopService(), ProductType.LAPTOP)),
    UPDATE_SMART_WATCH("Update smart watch", new Update<>(new SmartWatchService(), ProductType.SMART_WATCH)),

    PRINT_PHONES("Print phone", new Print<>(new PhoneService())),
    PRINT_LAPTOPS("Print laptops", new Print<>(new LaptopService())),
    PRINT_SMART_WATCHES("Print smart watches", new Print<>(new SmartWatchService())),

    DELETE_PHONE("Delete phone", new Delete<>(new PhoneService())),
    DELETE_LAPTOP("Delete laptop", new Delete<>(new LaptopService())),
    DELETE_SMART_WATCH("Delete smart watches", new Delete<>(new SmartWatchService())),

    EXIT("Exit", null);

    private final String name;
    private final Command command;

    Commands(String name, Command command) {
        this.name = name;
        this.command = command;
    }
}

