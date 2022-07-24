package ua.com.yaniv.command.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.command.Command;
import ua.com.yaniv.model.Product;
import ua.com.yaniv.model.ProductFactory;
import ua.com.yaniv.model.enums.ProductType;
import ua.com.yaniv.service.CrudService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Update<E extends Product> implements Command {
    private final ProductType product;
    private final CrudService<E> crudService;
    private static final Logger logger = LoggerFactory.getLogger(Update.class);
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public Update(CrudService<E> crudService, ProductType product) {
        this.crudService = crudService;
        this.product = product;
    }

    @Override
    public void execute() {
        if (crudService.getAll().isEmpty()) {
            System.out.println("Repository is empty");
            return;
        }
        try {
            String updateId;
            do {
                System.out.println("Write id to update: ");
                updateId = READER.readLine();
            } while (crudService.findById(updateId).isEmpty());

            crudService.update(updateId, (E) ProductFactory.createProduct(product));
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
    }
}
