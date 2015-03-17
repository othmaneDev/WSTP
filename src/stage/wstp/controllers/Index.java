package stage.wstp.controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.WebServiceDAO;
import stage.wstp.model.entities.WebService;


/**
 * Servlet implementation class Index
 */
@WebServlet("/")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	TagDAO tagDAO;

    @EJB
    WebServiceDAO wsDAO;
    
	public Index() {
    	super();
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//initialisation des attributs utilisés par la page d'acceuil
		
		//récupération des tags les plus utilisés pour le nuage de tag
		request.setAttribute("TagList",tagDAO.findMostPopularTags());
		//dimension du canvas
		request.setAttribute("width_canvas_cloud",380);
		request.setAttribute("height_canvas_cloud",194);
		request.setAttribute("title_cloud","Most popular Tags");
		//récupération des 8 derniers webservices ajouter/taggué
		List<WebService> wsList = wsDAO.findAllOrderedByDate();
		request.setAttribute("wsList", wsList);

		request.setAttribute("content","/WEB-INF/views/index.jsp");		

		//un fil d'ariane pour se repérer dans la navigation
		request.getSession().setAttribute("chemin", "/Index");
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
	}
	
	
}
