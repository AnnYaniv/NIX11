package ua.com.yaniv.model;

import lombok.Getter;
import lombok.Setter;
import ua.com.yaniv.model.enums.Manufacturer;

@Getter
@Setter
public class SmartWatch extends Product {
    private int daysWithoutCharge;

    public SmartWatch(String title, int count, double price, String model, Manufacturer manufacturer, int daysWithoutCharge) {
        super(title, count, price, model, manufacturer);
        this.daysWithoutCharge = daysWithoutCharge;
    }

    @Override
    public String toString() {
        return "SmartWatch{" +
                "id='" + id + '\'' +
                ", daysWithoutCharge=" + daysWithoutCharge +
                ", title='" + title + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer=" + manufacturer +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
