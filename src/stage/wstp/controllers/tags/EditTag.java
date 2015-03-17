package stage.wstp.controllers.tags;

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
import stage.wstp.model.entities.Tag;

/**
 * Servlet implementation class EditTag
 */
@WebServlet("/EditTag")
public class EditTag extends HttpServlet {
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
    public EditTag() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//récupération de l'id du tag que l'on souhaite éditer
		int tagId = Integer.parseInt(request.getParameter("tagId"));
		String tagDescription = request.getParameter("tagDescription");
		
		Tag tag = tagDAO.find(tagId);
		tag.setDescription(tagDescription);
		tagDAO.update(tag);
		request.setAttribute("tag",tag);
		
		request.setAttribute("content","/WEB-INF/views/tag.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );	
	}
}
