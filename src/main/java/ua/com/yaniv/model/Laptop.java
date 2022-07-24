package ua.com.yaniv.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
                ", title='" + title + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", driveType=" + driveType +
                ", os=" + os +
                ", model='" + model + '\'' +
                ", manufacturer=" + manufacturer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Laptop laptop = (Laptop) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(driveType, laptop.driveType).append(os, laptop.os).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(driveType).append(os).toHashCode();
    }
}
