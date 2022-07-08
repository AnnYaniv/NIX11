package ua.com.yaniv.model;

import lombok.Getter;
import lombok.Setter;
import ua.com.yaniv.model.enums.Manufacturer;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return count == product.count && Double.compare(product.price, price) == 0 && Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(model, product.model) && manufacturer == product.manufacturer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, model, manufacturer, count, price);
    }
}
