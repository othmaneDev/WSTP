package stage.wstp.model.daos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;

@Stateless
public class WSTagAssociationDAO {

	@PersistenceContext(unitName="WSTP_PU")
	EntityManager em;
	
	public void add(WSTagAssociation wsta){
		em.persist(wsta);
	}
	
	public void update(WSTagAssociation wsta){
		em.merge(wsta);
	}
	/**
	 * 
	 * @param webservice
	 * @return tous les webservices qui partagent au moins un tag avec le web service passé en paramètre
	 */
	public ArrayList<WebService> findWebServiceWithCommunTag(WebService webservice){
		
		ArrayList<WebService> result = new ArrayList<WebService>();
		
		for(WSTagAssociation wstag : webservice.getWstagAssociations()){
			
			Query q = em.createQuery("SELECT DISTINCT ws FROM WSTagAssociation wstag join wstag.tag tag join wstag.webService ws where :Idtag = tag.idTag");
			q.setParameter("Idtag",wstag.getTag().getIdTag());
			Collection<WebService> webserv = (Collection<WebService>) q.getResultList();
			
			for(WebService ws : webserv){
				if(!result.contains(ws)){
					result.add(ws);
				}
			}
		}
		
		
		return result;
	}
	
	public WSTagAssociation findWSTagAssociation(int wsId, int tagId){
		Query q = em.createQuery("SELECT a FROM WSTagAssociation a WHERE a.tag.idTag=:tagId AND a.webService.idWebService=:wsId");
		q.setParameter("wsId",wsId);
		q.setParameter("tagId",tagId);
		
		try{
			WSTagAssociation wsta = (WSTagAssociation) q.getSingleResult();
			return wsta;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<WSTagAssociation> findAll(){
		Query q = em.createQuery("SELECT wsta FROM WSTagAssociation wsta");
		
		try{
			List<WSTagAssociation> resultList =  (List<WSTagAssociation>) q.getResultList();
			return resultList;
		}
		catch(NoResultException e){
			return null;
		}
	}
}
