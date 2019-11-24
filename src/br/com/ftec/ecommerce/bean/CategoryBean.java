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
import br.com.ftec.ecommerce.model.Category;
import br.com.ftec.ecommerce.model.Product;

public class CategoryBean implements Dao<Category> {
	private final EntityManager entityManager;
	
	public CategoryBean(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<Category> get(long id) {
		return Optional.ofNullable(entityManager.find(Category.class, id));
	}
	
	@Override
	public List<Category> getAll() {
		Query query = entityManager.createQuery("SELECT e FROM category e");
		return query.getResultList();
	}
	
	@Override
	public void save(Category category) {
		executeInsideTransaction(entityManager -> entityManager.persist(category));
	}
	
	@Override
	public void update(Cart cart, List<Product> products, long userId, double totalPrice) {
		
	}
	
	@Override
	public void delete(Category category) {
		executeInsideTransaction(entityManager -> entityManager.remove(category));
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
	public void update(Category category, String[] params) {
		category.setName(Objects.requireNonNull(params[0], "Nome da categoria nao pode ser nulo"));
		executeInsideTransaction(entityManager -> entityManager.merge(category));
		
	}
}
