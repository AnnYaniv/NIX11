package ua.com.yaniv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.model.Product;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
public class Container<E extends Product> {
    private static final double MAX_DISCOUNT = 30;
    private static final double MIN_DISCOUNT = 10;
    private static final Logger logger = LoggerFactory.getLogger(Container.class);
    private static final Random RANDOM = new Random();
    private List<E> products;

    public Container() {
        products = new LinkedList<>();
    }

    public boolean save(E product) {
        if (product == null) throw new IllegalArgumentException("Cannot save a null product");
        if (findById(product.getId()).isEmpty()) {
            products.add(product);
            return true;
        } else return false;
    }

    public boolean saveAll(List<E> listProducts) {
        for(E product : listProducts){
            if(!save(product)) return false;
        }
        return true;
    }

    public boolean delete(String id) {
        final Iterator<E> iterator = products.iterator();
        while (iterator.hasNext()) {
            final E product = iterator.next();
            if (product.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public List<E> getAll() {
        return products;
    }

    public Optional<E> findById(String id) {
        E result = null;
        for (E product : products) {
            if (product.getId().equals(id)) {
                result = product;
            }
        }
        return Optional.ofNullable(result);
    }

    public boolean setRandomDiscount(String id) {
        Optional<E> optionalProduct = findById(id);
        AtomicBoolean result = new AtomicBoolean(false);
        optionalProduct.ifPresent(
                product -> {
                    double discount = MIN_DISCOUNT + (MAX_DISCOUNT - MIN_DISCOUNT) * RANDOM.nextDouble();
                    double priceBefore = product.getPrice();

                    product.setPrice(priceBefore - priceBefore * discount / 100);
                    logger.info("Random discount is - {}, actual price is - {}, price before discount was - {}", discount, product.getPrice(), priceBefore);
                    result.set(true);
                }
        );
        return result.get();
    }

    public <V extends Number> boolean provideProductsById(String id, V count) {
        Optional<E> optionalProduct = findById(id);
        AtomicBoolean result = new AtomicBoolean(false);
        optionalProduct.ifPresent(
                product -> {
                    double countBefore = product.getCount();
                    product.setCount(product.getCount() + count.intValue());
                    logger.info("Actual count is - {}, count before provide was - {}", product.getCount(), countBefore);
                    result.set(true);
                }
        );
        return result.get();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Product product : products) {
            result.append(product.toString());
            result.append("\n");
        }
        return result.toString();
    }
}
