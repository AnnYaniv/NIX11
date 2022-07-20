package ua.com.yaniv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.yaniv.model.Product;
import ua.com.yaniv.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
public abstract class CrudService <E extends Product>{

    protected CrudRepository<E> repository;
    private static final Logger logger = LoggerFactory.getLogger(CrudService.class);

    public boolean save(E product){
        try{
            repository.save(product);
            return true;
        }
        catch (Exception e){
            logger.error("Error saving product {}", product);
            return false;
        }
    }

    public boolean saveAll(List<E> products){
        return repository.saveAll(products);
    }

    public boolean update(String id, E product){
        product.setId(id);
        return repository.update(product);
    }

    public boolean delete(String id){
        return repository.delete(id);
    }

    public List<E> getAll(){
        return repository.getAll();
    }

    public Optional<E> findById(String id){
        return repository.findById(id);
    }
}
