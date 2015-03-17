package stage.wstp.controllers.webservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.others.ClassifiedWS;


@WebServlet("/WSListByCategory")
public class WSListByCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
    public WSListByCategory() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ClassifiedWS> WSClassment = new ArrayList<ClassifiedWS>();
		String categoryName=request.getParameter("key");
		MultiMap multiMap=new MultiValueMap();
		multiMap=(MultiMap)request.getSession().getAttribute("multiMap");
		/*Récupération de la liste des webServices par rapport à la catégorie séléctionnée*/
		WSClassment=(List<ClassifiedWS>)multiMap.get(categoryName);
		request.setAttribute("WSClassment",WSClassment);
		request.setAttribute("success",0);
		request.setAttribute("content","/WEB-INF/views/WSListByCategory.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
	}

}