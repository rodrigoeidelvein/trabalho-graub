package br.com.ftec.ecommerce.bean;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.ftec.ecommerce.dao.Dao;
import br.com.ftec.ecommerce.model.Cart;
import br.com.ftec.ecommerce.model.Product;

public class CartBean implements Dao<Cart> {
	private final EntityManager entityManager;
	
	public CartBean(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<Cart> get(long id) {
		return Optional.ofNullable(entityManager.find(Cart.class, id));
	}
	
	@Override
	public List<Cart> getAll() {
		Query query = entityManager.createQuery("SELECT e FROM cart e");
		return query.getResultList();
	}
	
	@Override
	public void save(Cart cart) {
		executeInsideTransaction(entityManager -> entityManager.persist(product));
	}
	
	@Override
	public void update(Cart cart, List<Product> products, long userId, double totalPrice) {
		cart.setUserId(Objects.requireNonNull(userId, "ID do usuario nao pode ser nulo"));
		cart.setProducts(products);
		cart.setTotalPrice(Objects.requireNonNull(totalPrice, "Valo total nao pode ser nulo"));
		executeInsideTransaction(entityManager -> entityManager.merge(cart));
	}
	
	@Override
	public void delete(Cart cart) {
		executeInsideTransaction(entityManager -> entityManager.remove(cart));
	}
	
	private void executeInsideTransaction(Consumer<EntityManager> action) {
		final EntityTransaction tx = entityManager.getTransaction();
		
		try {
			tx.begin();
			action.accept(entityManager);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		}
	}

	@Override
	public void update(Cart t, String[] params) {
		// TODO Auto-generated method stub
		
	}
}
