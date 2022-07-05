package ua.com.yaniv.model;

import lombok.Getter;
import lombok.Setter;
import ua.com.yaniv.model.enums.CommunicationStandard;
import ua.com.yaniv.model.enums.Manufacturer;

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
}
