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

public class ProductBean implements Dao<Product> {
	private final EntityManager entityManager;
	
	public ProductBean(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<Product> get(long id) {
		return Optional.ofNullable(entityManager.find(Product.class, id));
	}
	
	@Override
	public List<Product> getAll() {
		Query query = entityManager.createQuery("SELECT e FROM products e");
		return query.getResultList();
	}
	
	@Override
	public void save(Product product) {
		executeInsideTransaction(entityManager -> entityManager.persist(product));
	}
	
	@Override
	public void update(Product product, String[] params) {
		product.setTitle(Objects.requireNonNull(params[0], "Titulo nao pode ser nulo"));
		product.setDescription(Objects.requireNonNull(params[1], "Descricao nao pode ser nulo"));
		product.setImageUrl(Objects.requireNonNull(params[2], "Url da imagem nao pode ser nulo"));
		executeInsideTransaction(entityManager -> entityManager.merge(product));
	}
	
	@Override
	public void delete(Product product) {
		executeInsideTransaction(entityManager -> entityManager.remove(product));
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
	public void update(Cart cart, List<Product> products, long userId, double totalPrice) {
		// TODO Auto-generated method stub
		
	}
}
