package stage.wstp.model.daos;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import stage.wstp.model.entities.WebService;

@Stateless
public class WebServiceDAO {

	@PersistenceContext(unitName="WSTP_PU")
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<WebService> findAll(){
		Query q = em.createQuery("SELECT ws FROM WebService ws");
		
		try{
			List<WebService> resultList =  (List<WebService>) q.getResultList();
			return resultList;
		}
		catch(NoResultException e){
			return null;
		}
	}
	public List<WebService> findAllOrderedByDate(){
	     Query q = em.createQuery("SELECT ws FROM WebService ws order by ws.dateDernierTag DESC").setMaxResults(8);
			
			try{
				List<WebService> resultList =  (List<WebService>) q.getResultList();
				return resultList;
			}
			catch(NoResultException e){
				return null;
			}
	}
	
	//fonction qui vérifie qu'un web service avec le nom passé en paramètre n'existe pas déjà
	public boolean webServiceNameExist(String name){
		 Query q = em.createQuery("SELECT ws FROM WebService ws WHERE ws.name = :name");
		 q.setParameter("name", name);
		 
		 try{
			 WebService ws = (WebService) q.getSingleResult();
			 return true;
		 }catch(NoResultException e){
			 return false;
		 }
	}
	
	public WebService find(int id){
		return em.find(WebService.class, id);
	}
	
	public void add(WebService ws){
		em.persist(ws);
	}
	
	public void update(WebService ws){
		em.merge(ws);
	}
	
	
	
}