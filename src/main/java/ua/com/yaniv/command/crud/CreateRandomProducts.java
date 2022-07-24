package ua.com.yaniv.command.crud;

import lombok.Getter;
import lombok.Setter;
import ua.com.yaniv.command.Command;
import ua.com.yaniv.model.Product;
import ua.com.yaniv.model.ProductFactory;
import ua.com.yaniv.model.enums.ProductType;
import ua.com.yaniv.service.CrudService;

@Getter
@Setter
public class CreateRandomProducts<E extends Product> implements Command {
    private final ProductType product;
    private final CrudService<E> crudService;
    private int count = 5;

    public CreateRandomProducts(CrudService<E> crudService, ProductType product) {
        this.crudService = crudService;
        this.product = product;
    }

    @Override
    public void execute() {
        for (int i = 0; i < count; i++) {
            crudService.save((E) ProductFactory.createRandomProduct(product));
        }
    }
}
