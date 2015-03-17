package stage.wstp.controllers.webservices;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.model.daos.WebServiceDAO;

/**
 * Servlet implementation class AddWSInput
 */
@WebServlet("/WSList")
public class WSList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	WebServiceDAO wsDAO;
	
	@EJB
	CategoryDAO catDAO;
	
	@EJB
	TagDAO tagDAO;
	
	@EJB
	WSTagAssociationDAO wstaDAO;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WSList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("wsList", wsDAO.findAll());
		/*request.setAttribute("tagList",tagDAO.findAll());
		request.setAttribute("wstaList",wstaDAO.findAll());*/
		request.setAttribute("content","/WEB-INF/views/WSList.jsp");
		
		//un fil d'ariane pour se repérer dans la navigation
		request.getSession().setAttribute("chemin", "/WSList");
				
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
	}

}