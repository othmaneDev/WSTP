package stage.wstp.controllers.webservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.model.entities.Category;
import stage.wstp.model.entities.WebService;
import stage.wstp.others.ClassifiedWS;
import stage.wstp.others.PopularWebServices;

/**
 * Servlet implementation class StatisticWebService
 */
@WebServlet("/StatisticWebService")
public class StatisticWebService extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	CategoryDAO categoryDAO;
	
	
    
    public StatisticWebService() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> listCategory=new ArrayList<Category>();
		listCategory=categoryDAO.findAll();//On récupère toute les catégories
		//On met dans une  liste "popularWebServiceList" chaque catégorie avec le nombre de webservices qu'elle contient
		List<PopularWebServices> popularWebServiceList=new ArrayList<PopularWebServices>();
		for(Category cat : listCategory){
			if(cat.getWebServices().size()!=0){
				popularWebServiceList.add(new PopularWebServices(cat.getName(), cat.getWebServices().size()));
			}
			
		}
		Collections.sort(popularWebServiceList);
		//On fait un traitement afin de récupérer dans une variable "compteurComparaison" le min des web services contenu dans toutes les catégories
		int compteurComparaison=popularWebServiceList.get(0).getNombreWebServices();
		for(PopularWebServices popularWS:popularWebServiceList){
			if(compteurComparaison >popularWS.getNombreWebServices()){
				compteurComparaison=popularWS.getNombreWebServices();
			}
		}
		//On utilise ici un iterator afin de supprimer les elements de la liste ayant le meme nombre de service que le minimum(compteurComparaison)
		for (Iterator<PopularWebServices> iterator = popularWebServiceList.iterator(); iterator.hasNext(); ) {
			PopularWebServices  popularWS= iterator.next();
		    if (compteurComparaison==popularWS.getNombreWebServices()) {
		        iterator.remove();
		    }
		}
        //On ajout un nouveau element à la liste "Others " ayant comme nombre de services le minimum de toute la liste
		popularWebServiceList.add(new PopularWebServices("Others",compteurComparaison));
	    request.setAttribute("popularWebServiceList", popularWebServiceList);
		request.setAttribute("hauteurStats","500px");
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/StatistiqueWebService.jsp").forward(request, response );
	}
}

	