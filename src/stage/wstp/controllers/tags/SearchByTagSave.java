package stage.wstp.controllers.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

/**
 * Servlet implementation class SearchByTag
 */
@WebServlet("/SearchByTagSave")
public class SearchByTagSave extends HttpServlet {
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
    public SearchByTagSave() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//recherche d'un web service par son tag
		
		//pour la recherche d'un web service, on simule un web service en créant un web service contenant les tags et leurs poids comme spécifié dans la requête, par la suite on compare ce web service par les autres et on génère un coefficient permettant de les classés par ordre de ressemblance
		
		//création du web service avec les tags de la recherche
		WebService ws = new WebService();
		int voterForAllResult = 0;
		ws.setName("Request");
		ws.setDescription("A search request.");
		
		Category cat = catDAO.findOrCreate("Request");
		
		ws.setCategory(cat);
		
		String tagWeight;
		
		Tag tag;
		String tags = request.getParameter("tags");
		if(tags != null && VerificationSyntaxe.verificationTagWithPoidsAndMeta(tags)){
			String[] wts = tags.split(" ");
			for(String wt : wts){
				String[] wt2 = wt.split(":");
				
				//pour augmenter la pertinence de la recherche on rajoute les synonymes du tag permettant aussi au web service possédant des tags ayant la même signification de ressortir dans les résultats
				//ArrayList<String> synonyms = new ArrayList<String>();
				ArrayList<String> synonyms = null;
				
				try{
					synonyms = WordNetTool.getInstance().getSynonymsTags(wt2[0]);
				}catch(Exception e){
					synonyms = new ArrayList<String>();
				}
				
				if(!synonyms.contains(wt2[0].toLowerCase())){
					synonyms.add(wt2[0].toLowerCase());
				}
				for(String tagName : synonyms){
					
					tagWeight = wt2[1];
				
					tag = tagDAO.findOrCreate(tagName);
					
					WSTagAssociation wsta = new WSTagAssociation();
					wsta.setTag(tag);
					wsta.setWeightSum(Integer.parseInt(tagWeight));
					wsta.setVoters(1);
					wsta.setWebService(ws);
					if(ws.getWstagAssociations() == null)
						ws.setWstagAssociations(new ArrayList<WSTagAssociation>());
					ws.addWstagAssociation(wsta);
				}
			}
		}else{
			request.setAttribute("error",0);
		}
		
		//on récupère les web services puis on les compare au web service de la requête
		List<WebService> wslist = wsDAO.findAll();
		ArrayList<ClassifiedWS> WSClassment = new ArrayList<ClassifiedWS>();

		for(WebService wsi : wslist){
			double ratio = WSCompare.compare(ws,wsi);
			if(ratio > 0){
				voterForAllResult += wsi.getPopularity();
				WSClassment.add(new ClassifiedWS(wsi,ratio));
			}
		}
		
		/*
		 * Mise à jour du nombre de votant total pour permettre son utilisation dans le trie des web services par son nombre de vote
		 * 
		 * */
		for(ClassifiedWS wsClassified : WSClassment){
			wsClassified.updateMarkWithVoters(voterForAllResult);
		}
		
		Collections.sort(WSClassment);
		
		MultiMap multiMap = new MultiValueMap();//Initialisation de la MultiMap 
		for(ClassifiedWS wsClassified : WSClassment){
			
			multiMap.put(wsClassified.getWS().getCategory().getName(),wsClassified);
		}
		Set<String> keys = multiMap.keySet();//On récupère la liste des catégories en relation avec les tags de recherche
		 

	    request.getSession().setAttribute("multiMap", multiMap);
	    request.setAttribute("keys", keys);
		
		request.setAttribute("success",0);
		
		//un fil d'ariane pour se repérer dans la navigation
		request.getSession().setAttribute("chemin", "/SearchByTagInput");
		
		request.setAttribute("content","/WEB-INF/views/searchByTag.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );	
	}
	
	/*
	 * Pour un web service donné il retourne le nombre de personne qui ont édité ce web service.
	 * 
	public int getVoterForAWebServices(WebService ws){
		int resultat = 0;
		for(WSTagAssociation wsa: ws.getWstagAssociations()){
			resultat += wsa.getVoters();
		}
		return resultat;
	}*/

}