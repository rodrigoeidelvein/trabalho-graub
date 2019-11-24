package br.com.ftec.ecommerce.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaEntityManagerFactory {

	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ecommerce");
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public void close(EntityManager em) {
		em.close();
	}
	
	
}
