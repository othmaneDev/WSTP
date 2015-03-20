package stage.wstp.controllers.webservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
		listCategory=categoryDAO.findAll();
		List<PopularWebServices> popularWebServiceList=new ArrayList<PopularWebServices>();
		for(Category cat : listCategory){
			
			popularWebServiceList.add(new PopularWebServices(cat.getName(), cat.getWebServices().size()));
			
		}
		Collections.sort(popularWebServiceList);
	    request.setAttribute("popularWebServiceList", popularWebServiceList);
		request.setAttribute("hauteurStats","500px");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/StatistiqueWebService.jsp" ).forward( request, response );
	}
}

	