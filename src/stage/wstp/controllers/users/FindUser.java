package stage.wstp.controllers.users;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.UserDAO;
import stage.wstp.model.daos.WebServiceDAO;
import stage.wstp.model.entities.User;
import stage.wstp.model.entities.WebService;

/**
 * Servlet implementation class FindOrCreateUser
 */
@WebServlet("/FindUser")

public class FindUser extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@EJB
	TagDAO tagDAO;
		
	@EJB
	UserDAO userDAO;
	@EJB
	WebServiceDAO wsDAO;
	public FindUser() {
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
		User utilisateur = userDAO.find(name);
		
		if(utilisateur != null){
			//initialisation de l'utilisateur dans la session
			request.getSession().setAttribute("user", utilisateur);
		}else{
			request.setAttribute("errorMSTemplate","User not found, you must to subscribe!");
		}
		
		this.getServletContext().getRequestDispatcher( request.getSession().getAttribute("chemin").toString() ).forward( request, response );
			
	}
}
