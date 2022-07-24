package ua.com.yaniv.command;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.model.enums.ProductType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandsUtils {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final List<Commands> COMMANDS = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(CommandsUtils.class);

    private CommandsUtils() {
    }

    private static void choseProduct() throws IOException {
        String temp;
        ProductType productType;

        COMMANDS.add(Commands.EXIT);
        do {
            System.out.printf("Choose product type (%s):", getProductType());
            temp = READER.readLine();
        } while (!EnumUtils.isValidEnum(ProductType.class, temp));

        productType = ProductType.valueOf(temp);

        if (productType.equals(ProductType.PHONE)) {
            COMMANDS.add(Commands.CREATE_PHONE);
            COMMANDS.add(Commands.CREATE_RANDOM_PHONES);
            COMMANDS.add(Commands.UPDATE_PHONE);
            COMMANDS.add(Commands.DELETE_PHONE);
            COMMANDS.add(Commands.PRINT_PHONES);
        }

        if (productType.equals(ProductType.LAPTOP)) {
            COMMANDS.add(Commands.CREATE_LAPTOP);
            COMMANDS.add(Commands.CREATE_RANDOM_LAPTOPS);
            COMMANDS.add(Commands.UPDATE_LAPTOP);
            COMMANDS.add(Commands.DELETE_LAPTOP);
            COMMANDS.add(Commands.PRINT_LAPTOPS);
        }

        if (productType.equals(ProductType.SMART_WATCH)) {
            COMMANDS.add(Commands.CREATE_SMART_WATCH);
            COMMANDS.add(Commands.CREATE_RANDOM_SMART_WATCHES);
            COMMANDS.add(Commands.UPDATE_SMART_WATCH);
            COMMANDS.add(Commands.DELETE_SMART_WATCH);
            COMMANDS.add(Commands.PRINT_SMART_WATCHES);
        }
    }

    public static void start() {
        try {
            choseProduct();
            String temp;
            int choise;
            do {
                do {
                    System.out.printf("Choose in range(0 - %d)%n", COMMANDS.size() - 1);
                    for (int i = 0; i < COMMANDS.size(); i++) {
                        System.out.printf("To action - %s, write - %d%n", COMMANDS.get(i).getName(), i);
                    }
                    System.out.print("Action: ");
                    temp = READER.readLine();
                } while (!NumberUtils.isParsable(temp));

                choise = Integer.parseInt(temp);

                if (choise == 0) break;
                COMMANDS.get(choise).getCommand().execute();
            }
            while (true);
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
    }

    private static String getProductType() {
        StringBuilder sb = new StringBuilder();
        ProductType[] values = ProductType.values();
        for (ProductType value : values) {
            sb.append(value);
            sb.append(" ");
        }
        return sb.toString();
    }

}
