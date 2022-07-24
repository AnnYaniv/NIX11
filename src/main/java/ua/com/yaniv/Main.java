package ua.com.yaniv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.command.CommandsUtils;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        startCommands();
    }

    public static void startCommands() {
        try {
            CommandsUtils.start();
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
    }
}
