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

import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.model.daos.WebServiceDAO;
import stage.wstp.model.entities.Tag;
import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;

/**
 * Servlet implementation class AddWSInput
 */
@WebServlet("/TagWSInput")
public class TagWSInput extends HttpServlet {
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
    public TagWSInput() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int wsId = Integer.parseInt(request.getParameter("wsId"));
		WebService ws = wsDAO.find(wsId);
		
		//initialisation des tableaux contenant des tags en commun avec le web service spécifié
		ArrayList<WebService> webService = new ArrayList<WebService>();
		ArrayList<Tag> tags = new ArrayList<Tag>();
		ArrayList<WSTagAssociation> wstagA = new ArrayList<WSTagAssociation>();
		
		//récupération des webservices en commun avec celui spécifié
		for(WebService wsCommun:wstaDAO.findWebServiceWithCommunTag(ws)){
			webService.add(wsCommun);
			//récupération des liens des webservices avec leur tags
			for(WSTagAssociation wsta:wsCommun.getWstagAssociations()){
				wstagA.add(wsta);
			}
		}
		//récupération des tags en relation avec les web services spécifiés
		for(WSTagAssociation wsta:wstagA){
			if(!tags.contains(wsta.getTag())){
				tags.add(wsta.getTag());
			}
		}
		
		//initialisation des attributs qui seront utilisés par la page jsp
		request.setAttribute("wsListGraph",webService);
		request.setAttribute("tagListGraph",tags);
		request.setAttribute("wstaListGraph",wstagA);

		//récupération des données du web service qu'on souhaite taggué
		request.setAttribute("ws",ws);
		request.setAttribute("tagList",tagDAO.findAll());
		
		//initialisation du nuage de tag avec les tags utilisés par la catégorie du web service
		request.setAttribute("TagList",tagDAO.findByCategory(ws.getCategory().getIdCategory()));
		request.setAttribute("TagListRedirect",tagDAO.findMostPopularTags());
		//définition de la dimension du canvas d'affichage du nuage de tag
		request.setAttribute("width_canvas_cloud",380);
		request.setAttribute("height_canvas_cloud",194);
		
		
		request.setAttribute("title_cloud","Most popular Tags for "+ws.getCategory().getName());
		
		List<WebService> wsList = wsDAO.findAllOrderedByDate();
		request.setAttribute("wsList", wsList);
		//System.out.println("chemin = "+request.getSession().getAttribute("chemin"));
		request.setAttribute("content","/WEB-INF/views/tagWS.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );
	}

}