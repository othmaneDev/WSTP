package stage.wstp.model.daos;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import stage.wstp.model.entities.Category;
@Stateless
public class CategoryDAO {

	@PersistenceContext(unitName="WSTP_PU")
	EntityManager em;
	
	public void add(Category cat){
		em.persist(cat);
	}
	
	public Category find(int id){
		return em.find(Category.class, id);
	}
	
	public Category find(String name){
		Query q = em.createQuery("select c from Category c where c.name=:name");
		q.setParameter("name",name);
		
		try{
		Category cat = (Category) q.getSingleResult();
			return cat;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	public List<Category> findAll(){
		Query q = em.createQuery("SELECT cat FROM Category cat");
		
		try{
			@SuppressWarnings("unchecked")
			List<Category> resultList =  (List<Category>) q.getResultList();
			return resultList;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	public Category findOrCreate(String name){
		Category cat = find(name);
		if(cat == null){
			cat = new Category();
			cat.setName(name);
			add(cat);
		}
		return cat;
	}
	
	public void update(Category cat){
		em.merge(cat);
	}
}
