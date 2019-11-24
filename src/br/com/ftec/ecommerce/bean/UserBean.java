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
import br.com.ftec.ecommerce.model.User;

public class UserBean implements Dao<User> {
	private final EntityManager entityManager;
	
	public UserBean(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<User> get(long id) {
		return Optional.ofNullable(entityManager.find(User.class, id));
	}
	
	@Override
	public List<User> getAll() {
		Query query = entityManager.createQuery("SELECT e FROM users e");
		return query.getResultList();
	}
	
	@Override
	public void save(User user) {
		executeInsideTransaction(entityManager -> entityManager.persist(user));
	}
	
	@Override
	public void update(User user, String[] params) {
		user.setName(Objects.requireNonNull(params[0], "Nome nao pode ser nulo"));
		user.setEmail(Objects.requireNonNull(params[1], "E-mail nao pode ser nulo"));
		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}
	
	@Override
	public void delete(User user) {
		executeInsideTransaction(entityManager -> entityManager.remove(user));
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
