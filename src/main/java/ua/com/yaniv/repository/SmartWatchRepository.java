package ua.com.yaniv.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.model.SmartWatch;

import java.util.*;

public class SmartWatchRepository implements CrudRepository<SmartWatch> {
    private static final Logger logger = LoggerFactory.getLogger(SmartWatchRepository.class);
    private final List<SmartWatch> SMART_WATCHES;
    private static SmartWatchRepository instance;

    private SmartWatchRepository() {
        SMART_WATCHES = new LinkedList<SmartWatch>();
    }

    public static SmartWatchRepository getInstance() {
        if (instance == null) instance = new SmartWatchRepository();
        return instance;
    }

    @Override
    public void save(SmartWatch product) {
        if (product == null) {
            final IllegalArgumentException exception = new IllegalArgumentException("Cannot save a null phone");
            logger.error(exception.getMessage(), exception);
            throw exception;
        } else {
            SMART_WATCHES.add(product);
            logger.info("{} was saved", product);
        }
    }

    @Override
    public boolean saveAll(List<SmartWatch> products) {
        boolean result = true;
        for (SmartWatch smartWatch : products) {
            if ((smartWatch == null) || !findById(smartWatch.getId()).isEmpty()) result = false;
            else save(smartWatch);
        }
        return result;
    }

    @Override
    public boolean update(SmartWatch product) {
        final Optional<SmartWatch> result = findById(product.getId());
        if (result.isEmpty()) {
            return false;
        }
        final SmartWatch originSmartWatch = result.get();

        originSmartWatch.setDaysWithoutCharge(product.getDaysWithoutCharge());
        originSmartWatch.setCount(product.getCount());
        originSmartWatch.setPrice(product.getPrice());
        originSmartWatch.setTitle(product.getTitle());
        originSmartWatch.setModel(product.getModel());
        originSmartWatch.setManufacturer(product.getManufacturer());
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<SmartWatch> iterator = SMART_WATCHES.iterator();
        while (iterator.hasNext()) {
            final SmartWatch smartWatch = iterator.next();
            if (smartWatch.getId().equals(id)) {
                iterator.remove();
                logger.info("Product with id {}, was deleted", id);
                return true;
            }
        }
        logger.info("Product with id {}, wasn`t deleted", id);
        return false;
    }

    @Override
    public List<SmartWatch> getAll() {
        if (SMART_WATCHES.isEmpty()) {
            return Collections.emptyList();
        }
        return SMART_WATCHES;
    }

    @Override
    public Optional<SmartWatch> findById(String id) {
        SmartWatch result = null;
        for (SmartWatch smartWatch : SMART_WATCHES) {
            if (smartWatch.getId().equals(id)) {
                result = smartWatch;
            }
        }
        return Optional.ofNullable(result);
    }
}
