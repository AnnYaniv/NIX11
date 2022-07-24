package ua.com.yaniv.command.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.command.Command;
import ua.com.yaniv.model.Product;
import ua.com.yaniv.model.ProductFactory;
import ua.com.yaniv.model.enums.ProductType;
import ua.com.yaniv.service.CrudService;

//add product
public class Create<E extends Product> implements Command {
    private final ProductType product;
    private final CrudService<E> crudService;
    private static final Logger logger = LoggerFactory.getLogger(Create.class);

    public Create(CrudService<E> crudService, ProductType product) {
        this.crudService = crudService;
        this.product = product;
    }

    @Override
    public void execute() {
        try {
            logger.info("Executing create {}", product);
            crudService.save((E) ProductFactory.createProduct(product));
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
    }


}
