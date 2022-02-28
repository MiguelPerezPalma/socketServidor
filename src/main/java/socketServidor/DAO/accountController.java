package socketServidor.DAO;

import socketServidor.models.account;
import socketServidor.utils.PersistenceUnit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class accountController {
	public static EntityManager createEM() {
		return PersistenceUnit.getEM();
	}
	public List<account> getAllAccounts() throws Exception {
		List<account> result = new ArrayList<account>();
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			TypedQuery<account> q = em.createNamedQuery("getAllAccounts", account.class);
			result = q.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception("No se encontraron Cuentas", e);
		}
		return result;
	}
	
	public void saveAccount(account ac) throws Exception {
		EntityManager em = createEM();
		
		try {
			em.getTransaction().begin();
			em.persist(ac);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception("No se pudo guardar la nueva cuenta", e);
		}
	}
	public void deleteUser(account ac) throws Exception {
		EntityManager em = createEM();
		
		try {
			em.getTransaction().begin();
			em.remove(ac);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception("La cuenta no existe o No se pudo eliminar", e);
		}
	}
}
