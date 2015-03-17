package stage.wstp.controllers.tags;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.entities.WSTagAssociation;


/**
 * Servlet implementation class Tag
 */
@WebServlet("/Tag")
public class Tag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	TagDAO tagDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tag() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int tagId = Integer.parseInt(request.getParameter("tagId"));
		
		request.setAttribute("tag",tagDAO.find(tagId));
		for(WSTagAssociation wsta: ((stage.wstp.model.entities.Tag)tagDAO.find(tagId)).getWstagAssociations()){
			System.out.println(wsta.getWebService().getIdWebService());
		}
		//un fil d'ariane pour se repérer dans la navigation
		request.getSession().setAttribute("chemin", "/Tag?tagId="+tagId);
		
		request.setAttribute("content","/WEB-INF/views/tag.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
	}

}
