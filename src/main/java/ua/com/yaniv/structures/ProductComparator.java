package ua.com.yaniv.structures;

import ua.com.yaniv.model.Product;

import java.util.Comparator;

public class ProductComparator<E extends Product> implements Comparator<E> {

    @Override
    public int compare(E o1, E o2) {
        int priceCompare = Double.compare(o1.getPrice(), o2.getPrice());
        if (priceCompare == 0) {
            int nameCompare = o1.getTitle().compareToIgnoreCase(o2.getTitle());
            if (nameCompare == 0) {
                return Integer.compare(o1.getCount(), o2.getCount());
            }
            return nameCompare;
        }
        return -priceCompare;
    }
}
