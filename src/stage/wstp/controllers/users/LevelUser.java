 package stage.wstp.controllers.users;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*import stage.wstp.model.entities.Category;
import stage.wstp.model.entities.Tag;
import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;
import stage.wstp.others.ClassifiedWS;
import stage.wstp.others.WSCompare;*/



import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.UserDAO;
import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.model.daos.WebServiceDAO;
import stage.wstp.model.entities.User;
import stage.wstp.model.entities.WebService;


@WebServlet("/LevelUser")

public class LevelUser extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@EJB
	WebServiceDAO wsDAO;
	
	@EJB
	CategoryDAO catDAO;
	
	@EJB
	TagDAO tagDAO;
	
	@EJB
	WSTagAssociationDAO wstaDAO;
	
	@EJB
	UserDAO userDAO;
	
	public LevelUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int q1 = 0,q2 = 0,q3 = 0;
		//récupération de la saisie de l'utilisateur sur le formulaire
		if(request.getParameter("q1") != null)
			q1 = Integer.parseInt(request.getParameter("q1"));
		if(request.getParameter("21") != null)
			q2 = Integer.parseInt(request.getParameter("q2"));
		if(request.getParameter("q3") != null)
			q3 = Integer.parseInt(request.getParameter("q3"));
		
		User user = (User) request.getSession().getAttribute("user");

		//vérification d'au moins 2 bonnes réponses pour utilisateur expérimenté
		if(q1 + q2 + q3 >= 2){
			user.setLevel(1);
		}
		else{
			user.setLevel(0);
		}

		//mise à jour des données sur l'utilisateur suite au résultat du formulaire
		userDAO.update(user);
		
		request.getSession().setAttribute("user", user);
		
		//récupération des 8 derniers web services
		List<WebService> wsList = wsDAO.findAllOrderedByDate();
		request.setAttribute("wsList", wsList);
		request.setAttribute("historique","/WEB-INF/views/historique.jsp");
		
		//récupération des données pour peupler le nuage de tag
		request.setAttribute("TagList",tagDAO.findMostPopularTags());
		
		//dimension canvas nuage de cloud
		request.setAttribute("width_canvas_cloud",380);
		request.setAttribute("height_canvas_cloud",194);
		request.setAttribute("title_cloud","Most popular Tags");
		
		request.setAttribute("content","/WEB-INF/views/index.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );

	}
}
