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
@WebServlet("/SignIn")

public class SignIn extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@EJB
	TagDAO tagDAO;
		
	@EJB
	UserDAO userDAO;
	
	@EJB
	WebServiceDAO wsDAO;
	
	public SignIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//récupération du nom de l'utilisateur sur le formulaire
		String name = request.getParameter("userNameInsc");
		if(userDAO.find(name) == null){

			int q1 = 0,q2 = 0,q3 = 0;
			//récupération de la saisie de l'utilisateur sur le formulaire
			if(request.getParameter("q1") != null)
				q1 = Integer.parseInt(request.getParameter("q1"));
			if(request.getParameter("21") != null)
				q2 = Integer.parseInt(request.getParameter("q2"));
			if(request.getParameter("q3") != null)
				q3 = Integer.parseInt(request.getParameter("q3"));
			
			User user = new User();
			
			user.setName(name);
	
			//vérification d'au moins 2 bonnes réponses pour utilisateur expérimenté
			if(q1 + q2 + q3 >= 2){
				user.setLevel(1);
			}
			else{
				user.setLevel(0);
			}
	
			//ajout de l'utilisateur dans la base
			userDAO.add(user);
			request.getSession().setAttribute("user", user);
		}
					
		this.getServletContext().getRequestDispatcher( request.getSession().getAttribute("chemin").toString() ).forward( request, response );
	}
}
