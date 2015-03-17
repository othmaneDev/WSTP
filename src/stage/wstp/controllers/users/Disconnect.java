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
import stage.wstp.model.entities.WebService;


	@WebServlet("/Disconnect")

	public class Disconnect extends HttpServlet{
		private static final long serialVersionUID = 1L;
		
		@EJB
		TagDAO tagDAO;
        @EJB
        WebServiceDAO wsDAO;
		@EJB
		UserDAO utilDAO;
		
		public Disconnect() {
	        super();
	        // TODO Auto-generated constructor stub
	    }

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			//déconnection de l'utilisateur
			
			//suppression de la variable de session représentant l'utilisateur
			request.getSession().removeAttribute("user");
			
			//récupération des informations sur le nuage de tag
			request.setAttribute("TagList",tagDAO.findMostPopularTags());
			request.setAttribute("width_canvas_cloud",380);
			request.setAttribute("height_canvas_cloud",194);
			request.setAttribute("title_cloud","Most popular Tags");
			
			//récupération des 8 derniers web services
			List<WebService> wsList = wsDAO.findAllOrderedByDate();
			request.setAttribute("wsList", wsList);
			
			request.setAttribute("content","/WEB-INF/views/index.jsp");
			this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
				
		}
	}

