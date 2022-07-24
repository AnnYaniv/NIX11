package ua.com.yaniv.command.crud;

import ua.com.yaniv.command.Command;
import ua.com.yaniv.model.Product;
import ua.com.yaniv.service.CrudService;

public class Print<E extends Product> implements Command {
    private final CrudService<E> crudService;

    public Print(CrudService<E> crudService) {
        this.crudService = crudService;
    }

    @Override
    public void execute() {
        for (E product : crudService.getAll()) {
            System.out.println(product.toString());
        }
    }
}
