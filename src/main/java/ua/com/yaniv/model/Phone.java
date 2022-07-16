package ua.com.yaniv.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.Manufacturer;

import java.util.Objects;

@Getter
@Setter
public class Phone extends Product {

    private int numbersOfMainCameras;
    private CommunicationStandard communicationStandard;

    public Phone(String title, int count, double price, String model, Manufacturer manufacturer, int numbersOfMainCameras, CommunicationStandard communicationStandard) {
        super(title, count, price, model, manufacturer);
        this.numbersOfMainCameras = numbersOfMainCameras;
        this.communicationStandard = communicationStandard;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", numbersOfMainCameras=" + numbersOfMainCameras +
                ", communicationStandard=" + communicationStandard +
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

        Phone phone = (Phone) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(numbersOfMainCameras, phone.numbersOfMainCameras).append(communicationStandard, phone.communicationStandard).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(numbersOfMainCameras).append(communicationStandard).toHashCode();
    }
}
