package stage.wstp.controllers.tags;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stage.wstp.model.daos.TagDAO;

/**
 * Servlet implementation class SearchByTagInput
 */
@WebServlet("/SearchByTagInput")
public class SearchByTagInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	TagDAO tagDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchByTagInput() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tagList",tagDAO.findAll());
		
		request.setAttribute("TagList",tagDAO.findMostPopularTags());
		request.setAttribute("width_canvas_cloud",380);
		request.setAttribute("height_canvas_cloud",194);
		request.setAttribute("title_cloud","Most popular Tags");
		
		//un fil d'ariane pour se repérer dans la navigation
		request.getSession().setAttribute("chemin", "/SearchByTagInput");
		
		request.setAttribute("content","/WEB-INF/views/searchByTagInput.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
	}

}
