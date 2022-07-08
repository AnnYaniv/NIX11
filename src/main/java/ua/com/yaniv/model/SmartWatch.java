package ua.com.yaniv.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SmartWatch that = (SmartWatch) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(daysWithoutCharge, that.daysWithoutCharge).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(daysWithoutCharge).toHashCode();
    }
}
