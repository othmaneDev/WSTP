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
 * Servlet implementation class EditTag
 */
@WebServlet("/EditTagInput")
public class EditTagInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	TagDAO tagDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditTagInput() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tagId = Integer.parseInt(request.getParameter("tagId"));
		
		request.setAttribute("tag",tagDAO.find(tagId));
		
		request.setAttribute("content","/WEB-INF/views/editTag.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
	}

}
