package socketServidor.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import socketServidor.models.user;
import socketServidor.utils.PersistenceUnit;

public class userController {

	public static EntityManager createEM() {
		return PersistenceUnit.getEM();
	}

	public List<user> showAllUsers() throws Exception {
		List<user> result = new ArrayList<user>();
		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			TypedQuery<user> q = em.createNamedQuery("getAllUsers", user.class);
			result = q.getResultList();
			em.getTransaction().commit();

		} catch (Exception e) {
			throw new Exception("Hubo algún error", e);
		}

		return result;
	}
	
	
	public user showUser(int id) throws Exception {
		user result = null;
		
		EntityManager em = createEM();
		
		try  {
			em.getTransaction().begin();
			result = em.find(user.class, id);
			em.getTransaction().commit();
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception("Hubo algún error", e);
		}
		
		return result;
	}
	
	public void updateUser(User u) throws Exception {
		
	}
	
}
