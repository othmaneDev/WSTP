package stage.wstp.controllers.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.model.daos.WebServiceDAO;
import stage.wstp.model.entities.Category;
import stage.wstp.model.entities.Tag;
import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;
import stage.wstp.others.ClassifiedWS;
import stage.wstp.others.VerificationSyntaxe;
import stage.wstp.others.WSCompare;
import stage.wstp.others.WordNetTool;
import stage.wstp.search.tools.RequestSemantic;
import stage.wstp.search.tools.TamponTagwS;
import stage.wstp.search.tools.TransformAndOrderRequest;
import stage.wstp.search.tools.TransformWebServices;

/**
 * Servlet implementation class SearchByTag
 */
@WebServlet("/SearchByTag")
public class SearchByTag extends HttpServlet {
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
    public SearchByTag() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		TransformAndOrderRequest transformRequest = new TransformAndOrderRequest();
		TransformWebServices transformWS = new TransformWebServices();
		ArrayList<RequestSemantic> requestSemanticList = new ArrayList<RequestSemantic>();
		//recherche d'un web service par son tag
		
		//pour la recherche d'un web service, on simule un web service en créant un web service contenant les tags et leurs poids comme spécifié dans la requête, par la suite on compare ce web service par les autres et on génère un coefficient permettant de les classés par ordre de ressemblance
		
		//création du web service avec les tags de la recherche
		WebService ws = new WebService();
		
		int voterForAllResult = 0;
		String tags = request.getParameter("tags");
		if(tags != null && VerificationSyntaxe.verificationTagWithPoidsAndMeta(tags)){
			String[] wts = tags.split(" ");
			
			requestSemanticList = transformRequest.transformRequest((List<String>) Arrays.asList(wts));
			requestSemanticList = transformRequest.OrderRequest(requestSemanticList);
			transformRequest.reductRequest(requestSemanticList);
			ws = transformRequest.getWebServiceRequest(requestSemanticList, catDAO, tagDAO);
			
		}else{
			request.setAttribute("error",0);
		}
		
		//on récupère les web services puis on les compare au web service de la requête
		List<WebService> wslist = wsDAO.findAll();
		
		 ArrayList<TamponTagwS> res = transformWS.transformWS((List<WebService>) wslist,requestSemanticList);
		 HashMap<Integer,HashMap<String, Double>> resReduc = transformWS.reducTransformWS(res);
		 HashMap<Integer,WebService> listWSTransform = transformWS.getWebServiceFromAllTags(resReduc);
		 ArrayList<ClassifiedWS> WSClassment = new ArrayList<ClassifiedWS>();

		for(Entry<Integer, WebService> wsi :listWSTransform.entrySet()){
			
			double ratio = WSCompare.compare(ws,wsi.getValue());
			if(ratio > 0){
				WebService origin = wsDAO.find(wsi.getKey());
				voterForAllResult += origin.getPopularity();
				WSClassment.add(new ClassifiedWS(origin,ratio));
			}
		}
		
		listWSTransform.clear();
		
		/*
		 * Mise à jour du nombre de votant total pour permettre son utilisation dans le trie des web services par son nombre de vote
		 * 
		 * */
		
		for(ClassifiedWS wsClassified : WSClassment){
			wsClassified.updateMarkWithVoters(voterForAllResult);
		}
		
		Collections.sort(WSClassment);
		
		MultiValueMap multiMap = new MultiValueMap();//Initialisation de la MultiMap 
		for(ClassifiedWS wsClassified : WSClassment){
			
			multiMap.put(wsClassified.getWS().getCategory().getName(),wsClassified);
		}
		Set<String> keys = multiMap.keySet();//On récupère la liste des catégories en relation avec les tags de recherche
		/*for(String key:keys){
			System.out.println("key:"+key);
			System.out.println("Value"+multiMap.get(key));
		}*/
        
	    request.getSession().setAttribute("multiMap", multiMap);
	    request.setAttribute("keys", keys);
	    request.setAttribute("WSClassment", keys);
		request.setAttribute("success",0);
		
		//un fil d'ariane pour se repérer dans la navigation
		request.getSession().setAttribute("chemin", "/SearchByTagInput");
		
		request.setAttribute("content","/WEB-INF/views/searchByTag.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );	
	}

}