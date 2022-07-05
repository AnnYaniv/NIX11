package ua.com.yaniv.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.model.Laptop;

import java.util.*;

public class LaptopRepository implements CrudRepository<Laptop> {
    private static final Logger logger = LoggerFactory.getLogger(LaptopRepository.class);
    private final List<Laptop> LAPTOPS;
    private static LaptopRepository instance;

    private LaptopRepository() {
        LAPTOPS = new LinkedList<Laptop>();
    }

    public static LaptopRepository getInstance() {
        if (instance == null) instance = new LaptopRepository();
        return instance;
    }

    @Override
    public void save(Laptop product) {
        LAPTOPS.add(product);
        logger.info("{} was saved", product);
    }

    @Override
    public void saveAll(List<Laptop> products) {
        for (Laptop laptop : products) {
            save(laptop);
        }
    }

    @Override
    public boolean update(Laptop product) {
        final Optional<Laptop> result = findById(product.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Laptop originLaptop = result.get();

        originLaptop.setDriveType(product.getDriveType());
        originLaptop.setOs(product.getOs());
        originLaptop.setCount(product.getCount());
        originLaptop.setPrice(product.getPrice());
        originLaptop.setTitle(product.getTitle());

        return true;

    }

    @Override
    public boolean delete(String id) {
        final Iterator<Laptop> iterator = LAPTOPS.iterator();
        while (iterator.hasNext()) {
            final Laptop laptop = iterator.next();
            if (laptop.getId().equals(id)) {
                iterator.remove();
                logger.info("Product with id {}, was deleted", id);
                return true;
            }
        }
        logger.info("Product with id {}, wasn`t deleted", id);
        return false;
    }

    @Override
    public List<Laptop> getAll() {
        if (LAPTOPS.isEmpty()) {
            return Collections.emptyList();
        }
        return LAPTOPS;
    }

    @Override
    public Optional<Laptop> findById(String id) {
        Laptop result = null;
        for (Laptop laptop : LAPTOPS) {
            if (laptop.getId().equals(id)) {
                result = laptop;
            }
        }
        return Optional.ofNullable(result);
    }
}

