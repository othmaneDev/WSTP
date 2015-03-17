package stage.wstp.controllers.users;

import java.io.IOException;
/*import java.util.ArrayList;
import java.util.Collections;
import java.util.List;*/

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.UserDAO;
import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.model.daos.WebServiceDAO;
/*import stage.wstp.model.entities.Category;
import stage.wstp.model.entities.Tag;*/
import stage.wstp.model.entities.User;
/*import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;
import stage.wstp.others.ClassifiedWS;
import stage.wstp.others.WSCompare;*/
import stage.wstp.model.entities.WebService;

/**
 * Servlet implementation class FindOrCreateUser
 */
@WebServlet("/FindOrCreateUser")

public class FindOrCreateUser extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@EJB
	TagDAO tagDAO;
		
	@EJB
	UserDAO utilDAO;
	@EJB
	WebServiceDAO wsDAO;
	public FindOrCreateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//récupération du nom de l'utilisateur sur le formulaire
		String name = request.getParameter("userName");
		
		//si on trouve l'utilisateur dans la base alors on le retourne, sinon on le créé puis on le retourne
		User utilisateur = utilDAO.findOrCreate(name);
		
		//initialisation de l'utilisateur dans la session
		request.getSession().setAttribute("user", utilisateur);
		
		//récupération des données pour peupler le nuage de tag
		request.setAttribute("TagList",tagDAO.findMostPopularTags());
		
		//dimension canvas nuage de cloud
		request.setAttribute("width_canvas_cloud",380);
		request.setAttribute("height_canvas_cloud",194);
		request.setAttribute("title_cloud","Most popular Tags");
		
		//les 8 derniers web services en activité
		List<WebService> wsList = wsDAO.findAllOrderedByDate();
		request.setAttribute("wsList", wsList);
		request.setAttribute("historique","/WEB-INF/views/historique.jsp");
			
		//request.setAttribute("content","/WEB-INF/views/index.jsp");
		//this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
			
	}
}
