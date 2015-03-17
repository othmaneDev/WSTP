package stage.wstp.controllers.webservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.UserDAO;
import stage.wstp.model.daos.UserWSAssociationDAO;
import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.model.daos.WebServiceDAO;
import stage.wstp.model.entities.Tag;
import stage.wstp.model.entities.User;
import stage.wstp.model.entities.UserWSAssociation;
import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;
import stage.wstp.others.VerificationSyntaxe;

/**
 * Servlet implementation class TagWS
 */
@WebServlet("/TagWS")
public class TagWS extends HttpServlet {
    

	/**
	 * 
	 */
	//private static final long serialVersionUID = -4011343197098765451L;
	private static final long serialVersionUID = 1L;

	@EJB
	WebServiceDAO wsDAO;
	
	@EJB
	CategoryDAO catDAO;
	
	@EJB
	TagDAO tagDAO;
	
	@EJB
	WSTagAssociationDAO wstaDAO;
	
	@EJB
	UserWSAssociationDAO userwsDAO;
	
	@EJB
	UserDAO userDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagWS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		WSTagAssociation wsta;
		String tagName;
		int tagWeight;
		int wsId = Integer.parseInt(request.getParameter("wsId"));
		//récupération de l'utilisateur
		User user = (User) request.getSession().getAttribute("user");
		//récupération de l'utilisateur
		WebService ws = wsDAO.find(wsId);
		
		//récupération d'une instance de calendar
		Calendar calendar = Calendar.getInstance();
		
		//résupération du timestamp de l'instant
		java.util.Date now = calendar.getTime();

		//transformation du timestamp de java util en java sql puis converser en timestamp
		java.sql.Timestamp dateDernierTag = new java.sql.Timestamp(now.getTime());
		
		Tag tag;
		String tags = request.getParameter("tags");
		//mise à jour du web service à la date du timestamp 
		ws.setDateDernierTag(dateDernierTag);
		
		//on vérifie la syntaxe de l'entrée utilisateur
		if(tags != null  && VerificationSyntaxe.verificationTagWithPoidsAndMeta(tags)){
			
			//si l'utilisateur n'a pas encore taggué ou qu'il n'est pas l'ajouteur du web service, alors on incrémente le nombre de personne qui ont édité le web service
			if(userwsDAO.findUserWSAssociation(wsId, user.getIdUser()) == null){
				UserWSAssociation  userws = new UserWSAssociation();
				
				userws.setUser(user);
				userws.setWebService(ws);
				userwsDAO.add(userws);
				
				user.addUserWSAssociation(userws);
				userDAO.update(user);
				
				ws.addUserWSAssociation(userws);
				ws.incrementPopularity();
				wsDAO.update(ws);
			}
			
			//on récupère les tags et on les ajoutes au web services
			String[] wts = tags.split(" ");
			for(String wt : wts){
				String[] wt2 = wt.split(":");
				tagName = wt2[0];
				tagWeight = Integer.parseInt(wt2[1]);
			
				tag = tagDAO.find(tagName);
				//si le tag n'existe pas encore on le créé et on ajoute l'association du tag avec le web service
				if(tag == null){
					
					tag = new Tag();
					tag.setName(tagName);
					tagDAO.add(tag);
					
					
					wsta = new WSTagAssociation();
					wsta.setTag(tag);
					wsta.setWeightSum(tagWeight);
					wsta.setVoters(1);
					wsta.setWebService(ws);
					wstaDAO.add(wsta);
					tag.addWstagAssociation(wsta);
					tagDAO.update(tag);
					ws.addWstagAssociation(wsta);
					wsDAO.update(ws);
				}
				//sinon on récupère le tag et on ajoute uniquement l'association
				else{
					
					wsta = wstaDAO.findWSTagAssociation(wsId, tag.getIdTag());
					//si l'association n'existe pas on le créé puis on l'ajoute
					if(wsta == null){
						
						wsta = new WSTagAssociation();
						wsta.setTag(tag);
						wsta.setWeightSum(0);
						wsta.incrementWeightSum(tagWeight);
						wsta.setVoters(1);
						wsta.setWebService(ws);
						wstaDAO.add(wsta);
						tag.addWstagAssociation(wsta);
						tagDAO.update(tag);
						ws.addWstagAssociation(wsta);
						wsDAO.update(ws);
					}
					//si l'association existe on relie juste les deux
					else{
						wsta.incrementWeightSum(tagWeight);
						wsta.incrementVoters(1);
						wstaDAO.update(wsta);
					}
					
				}
			}
			
			request.setAttribute("success",0);
		}
		//si la syntaxe est incorrect alors on renvoie un message d'erreur
		else{
			//sinon on renvoie un message d'erreur
			request.setAttribute("error",0);
		}
		
		//récupération du web service
		request.setAttribute("ws", wsDAO.find(wsId));
		
		//initialisation des tableaux contenant des tags en commun avec le web service spécifié
		ArrayList<WebService> webServiceG = new ArrayList<WebService>();
		ArrayList<Tag> tagsG = new ArrayList<Tag>();
		ArrayList<WSTagAssociation> wstagAG = new ArrayList<WSTagAssociation>();
		
		//récupération des webservices en commun avec celui spécifié
		for(WebService wsCommun:wstaDAO.findWebServiceWithCommunTag(ws)){
			webServiceG.add(wsCommun);
			//récupération des liens des webservices avec leur tags
			for(WSTagAssociation wstad:wsCommun.getWstagAssociations()){
				wstagAG.add(wstad);
			}
		}
		//récupération des tags en relation avec les web services spécifiés
		for(WSTagAssociation wstad:wstagAG){
			if(!tagsG.contains(wstad.getTag())){
				tagsG.add(wstad.getTag());
			}
		}
		
		//initialisation des attributs qui seront utilisés par la page jsp
		request.setAttribute("wsListGraph",webServiceG);
		request.setAttribute("tagListGraph",tagsG);
		request.setAttribute("wstaListGraph",wstagAG);
		
		//récupération de la liste de tag pour le nuage
		request.setAttribute("TagList",tagDAO.findByCategory(ws.getCategory().getIdCategory()));
		//dimension du canvas
		request.setAttribute("width_canvas_cloud",380);
		request.setAttribute("height_canvas_cloud",194);
		request.setAttribute("title_cloud","Most popular Tags for "+ws.getCategory().getName());
		
		request.setAttribute("content","/WEB-INF/views/tagWS.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );	
	}

}
