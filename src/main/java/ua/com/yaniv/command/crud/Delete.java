package ua.com.yaniv.command.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.command.Command;
import ua.com.yaniv.model.Product;
import ua.com.yaniv.service.CrudService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Delete<E extends Product> implements Command {
    private final CrudService<E> crudService;

    private static final Logger logger = LoggerFactory.getLogger(Delete.class);
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public Delete(CrudService<E> crudService) {
        this.crudService = crudService;
    }

    @Override
    public void execute() {
        try {
            String deleteId;
            do {
                System.out.println("Write id to delete: ");
                deleteId = READER.readLine();
            } while (crudService.findById(deleteId).isEmpty());

            crudService.delete(deleteId);
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
    }
}
