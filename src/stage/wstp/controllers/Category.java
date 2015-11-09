package stage.wstp.controllers;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;


/**
 * Servlet implementation class Category
 */
@WebServlet("/Category")
public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	CategoryDAO catDAO;
	
	@EJB
	TagDAO tagDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Category() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int catId = Integer.parseInt(request.getParameter("catId"));
		
		request.setAttribute("cat",catDAO.find(catId));
		System.out.println(tagDAO.findByCategory(catId));
		request.setAttribute("content","/WEB-INF/views/category.jsp");
		
		//un fil d'ariane pour se repérer dans la navigation
		request.getSession().setAttribute("chemin", "/Category?catId="+catId);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
	}

}
