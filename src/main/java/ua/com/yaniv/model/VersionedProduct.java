package ua.com.yaniv.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class VersionedProduct<E extends Product> {
    private E product;
    private int version;
    private final Date date = new Date();

    public VersionedProduct(E product, int version){
        this.product = product;
        this.version = version;
    }

    public String getId(){
        return product.getId();
    }

    @Override
    public String toString() {
        return "VersionedProduct{" +
                "version=" + version +
                ", date=" + date +
                ", product=" + product +
                '}';
    }

}
