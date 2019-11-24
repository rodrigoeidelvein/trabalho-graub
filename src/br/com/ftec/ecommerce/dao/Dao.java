package br.com.ftec.ecommerce.dao;

import java.util.List;
import java.util.Optional;

import br.com.ftec.ecommerce.model.Cart;
import br.com.ftec.ecommerce.model.Product;

public interface Dao<T> {
	Optional<T> get(long id);
    
    List<T> getAll();
     
    void save(T t);
     
    void update(T t, String[] params);
     
    void delete(T t);

	void update(Cart cart, List<Product> products, long userId, double totalPrice);
}
