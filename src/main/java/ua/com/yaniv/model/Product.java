package ua.com.yaniv.model;

import lombok.Getter;
import lombok.Setter;
import ua.com.yaniv.model.enums.Manufacturer;

import java.util.UUID;

@Getter
@Setter
public abstract class Product {
    protected String id;
    protected String title;
    protected final String model;
    protected final Manufacturer manufacturer;
    protected int count;
    protected double price;

    protected Product(String title, int count, double price, String model, Manufacturer manufacturer) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.count = count;
        this.price = price;
        this.model = model;
        this.manufacturer = manufacturer;
    }
}
