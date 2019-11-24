package br.com.ftec.ecommerce.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.ftec.ecommerce.model.User;
import br.com.ftec.ecommerce.util.JpaEntityManagerFactory;

public class UserDAO {

	public boolean exists(User usuario) {
		EntityManager em = new JpaEntityManagerFactory().getEntityManager();
		TypedQuery<User> query = em
				.createQuery(
						"select u from User u where u.email = :pEmail and u.password = :pSenha",
						User.class);

		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getPassword());

		try {
			User resultado = query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}

		em.close();
		
		return true;
	}

}
