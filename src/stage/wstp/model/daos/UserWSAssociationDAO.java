package stage.wstp.model.daos;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import stage.wstp.model.entities.UserWSAssociation;
import stage.wstp.model.entities.WSTagAssociation;

@Stateless
public class UserWSAssociationDAO {
	
	@PersistenceContext(unitName="WSTP_PU")
	EntityManager em;
	
	public void add(UserWSAssociation userws){
		em.persist(userws);
	}
	
	public void update(UserWSAssociation userws){
		em.merge(userws);
	}
	
	public UserWSAssociation findUserWSAssociation(int wsId, int userId){
		Query q = em.createQuery("SELECT a FROM UserWSAssociation a WHERE a.user.idUser=:userId AND a.webService.idWebService=:wsId");
		q.setParameter("wsId",wsId);
		q.setParameter("userId",userId);
		
		try{
			UserWSAssociation userws = (UserWSAssociation) q.getSingleResult();
			return userws;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<UserWSAssociation> findAll(){
		Query q = em.createQuery("SELECT userws FROM UserWSAssociation userws");
		
		try{
			List<UserWSAssociation> resultList =  (List<UserWSAssociation>) q.getResultList();
			return resultList;
		}
		catch(NoResultException e){
			return null;
		}
	}

}
