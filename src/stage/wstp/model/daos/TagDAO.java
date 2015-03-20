package stage.wstp.model.daos;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import stage.wstp.model.entities.Tag;
import stage.wstp.others.PopularTag;

@Stateless
public class TagDAO {

	@PersistenceContext(unitName="WSTP_PU")
	EntityManager em;
	
	public void add(Tag tag){
		em.persist(tag);
	}
	
	public Tag find(int id){
		return em.find(Tag.class, id);
	}
	
	public Tag find(String name){
		Query q = em.createQuery("SELECT t FROM Tag t WHERE t.name=:name");
		q.setParameter("name",name);
		try{
			Tag t = (Tag) q.getSingleResult();
			return t;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	public Tag findOrCreate(String name){
		Tag tag = find(name);
		if(tag == null){
			tag = new Tag();
			tag.setName(name);
			add(tag);
		}
		return tag;
	}

	public List<PopularTag> findByCategory(int CategoryId){
		Query q = em.createNamedQuery("Tag.findByCategory");
		q.setParameter("idCategory", CategoryId);
		try{
			List<PopularTag> resultList =  new ArrayList<PopularTag>();
			for(Tag tag :(List<Tag>) q.getResultList()){
				resultList.add(new PopularTag(tag, 1L));
			}
			return resultList;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	/*
	 * fonction pour trouver les tags les plus populaires pour le nuage de tag 
	 */
	public List<PopularTag> findMostPopularTags(){
		TypedQuery<Object[]> q = em.createNamedQuery("Tag.findMostPopularTags",Object[].class);
		try{
			List<PopularTag> resultList =  new ArrayList<PopularTag>();
			List<Object[]> results = q.setMaxResults(10).getResultList();
			for (Object[] result : results) {
				resultList.add(new PopularTag((Tag)result[0], (Long)result[1]));
			}
			  
			return resultList;
		}
		catch(NoResultException e){
			return null;
		}
	}

	
	/* 
	 * fonction pour récupérer les données pour le graphe de statistique. le tag sur le nombre de fois qu'il a été utilisé
	 */
	public List<PopularTag> StatisticTag(){
		TypedQuery<Object[]> q = em.createNamedQuery("Tag.findMostPopularTags",Object[].class);
		try{
			List<PopularTag> resultList =  new ArrayList<PopularTag>();
			List<Object[]> results = q.getResultList();
			for (Object[] result : results) {
				resultList.add(new PopularTag((Tag)result[0], (Long)result[1]));
			}
			  
			return resultList;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Tag> findAll(){
		Query q = em.createQuery("SELECT tag FROM Tag tag");
		
		try{
			List<Tag> resultList =  (List<Tag>) q.getResultList();
			return resultList;
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	public void update(Tag tag){
		em.merge(tag);
	}
}