package stage.wstp.model.daos;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import stage.wstp.model.entities.User;

@Stateless
public class UserDAO {

	@PersistenceContext(unitName="WSTP_PU")
	EntityManager em;
	
	public void add(User utilisateur){
		em.persist(utilisateur);
	}
	
	public User find(int id){
		return em.find(User.class, id);
	}
	
	public User find(String name){
		Query q = em.createQuery("SELECT t FROM User t WHERE t.name=:name");
		q.setParameter("name",name);
		try{
			User t = (User) q.getSingleResult();
			return t;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	//recherche ou création de l'utilisateur selon son existe
	public User findOrCreate(String name){
		User utilisateur = find(name);
		if(utilisateur == null){
			utilisateur = new User();
			utilisateur.setName(name);
			utilisateur.setLevel(-1);
			add(utilisateur);
		}
		return utilisateur;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAll(){
		Query q = em.createQuery("SELECT u FROM User u");
		
		try{
			List<User> resultList =  (List<User>) q.getResultList();
			return resultList;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	public void update(User utilisateur){
		em.merge(utilisateur);
	}
}