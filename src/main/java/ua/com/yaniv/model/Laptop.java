package ua.com.yaniv.model;

import lombok.Getter;
import lombok.Setter;
import ua.com.yaniv.model.enums.DriveType;
import ua.com.yaniv.model.enums.Manufacturer;
import ua.com.yaniv.model.enums.OS;

@Getter
@Setter
public class Laptop extends Product {
    private DriveType driveType;
    private OS os;

    public Laptop(String title, int count, double price, String model, Manufacturer manufacturer, DriveType driveType, OS os) {
        super(title, count, price, model, manufacturer);
        this.driveType = driveType;
        this.os = os;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id='" + id + '\'' +
                ", driveType=" + driveType +
                ", os=" + os +
                ", title='" + title + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer=" + manufacturer +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
