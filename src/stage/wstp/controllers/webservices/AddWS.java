package stage.wstp.controllers.webservices;

import java.io.IOException;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.daos.UserDAO;
import stage.wstp.model.daos.UserWSAssociationDAO;
import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.model.daos.WebServiceDAO;
import stage.wstp.model.entities.Category;
import stage.wstp.model.entities.Tag;
import stage.wstp.model.entities.User;
import stage.wstp.model.entities.UserWSAssociation;
import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;
import stage.wstp.others.VerificationSyntaxe;

/**
 * Servlet implementation class AddWS
 */
@WebServlet("/AddWS")
@MultipartConfig( location = "C://hrest_files/",fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class AddWS extends HttpServlet {
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
    public AddWS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//récupération des données du formulaire
		String wsName = request.getParameter("wsName");
		String wsURL = request.getParameter("wsURL");
		String wsDescription = request.getParameter("wsDescription");
		String catName = request.getParameter("catName");
		
		//récupération de l'utilisateur
		User user = (User) request.getSession().getAttribute("user");
		
		//récupération d'une instance de calendar
		Calendar calendar = Calendar.getInstance();
		
		//résupération du timestamp de l'instant
		java.util.Date now = calendar.getTime();
        
		//transformation du timestamp de java util en java sql puis converser en timestamp
		java.sql.Timestamp dateDernierTag = new java.sql.Timestamp(now.getTime());

		if(!wsDAO.webServiceNameExist(wsName)){
			//création du web service et initialisation de ses attributs (description, url, ...)
			WebService ws = new WebService();
			ws.setName(wsName);
			ws.setDescription(wsDescription);
			ws.setUrl(wsURL);
			Category cat = catDAO.findOrCreate(catName);
			ws.setCategory(cat);
			ws.setDateDernierTag(dateDernierTag);
			
			wsDAO.add(ws);
			cat.addWebService(ws);
			catDAO.update(cat);
			
			Part part = request.getPart("hrest");
			if(part != null){
				String fileName = part.getSubmittedFileName();
				
				String ext = "";

				int i = fileName.lastIndexOf('.');
				if (i >= 0) {
				    ext = fileName.substring(i+1);
				    ws.setFileName(ws.getIdWebService()+"."+ext);
					part.write(ws.getIdWebService()+"."+ext);
				}
			}
			
			
			
			String tagName;
			String tagWeight;
			
			Tag tag;
			String tags = request.getParameter("tags");
			
			WSTagAssociation wsta;
			
			//on créé un lien entre l'utilisateur et le web service
			UserWSAssociation userws = new UserWSAssociation();
			userws.setUser(user);
			userws.setWebService(ws);
			
			userwsDAO.add(userws);
			ws.addUserWSAssociation(userws);
			ws.setPopularity(1);
			wsDAO.update(ws);
			user.addUserWSAssociation(userws);
			userDAO.update(user);
			
			//on vérifie la syntaxe des tags afin de les ajouter si ils sont correct
			if(tags != null && VerificationSyntaxe.verificationTagWithPoidsAndMeta(tags)){
				String[] wts = tags.split(" ");
				for(String wt : wts){
					String[] wt2 = wt.split(":");
					tagName = wt2[0];
					tagWeight = wt2[1];
				
					tag = tagDAO.findOrCreate(tagName);
					
					wsta = new WSTagAssociation();
					wsta.setTag(tag);
					wsta.setVoters(1);
					wsta.setWeightSum(0);
					wsta.incrementWeightSum(Integer.parseInt(tagWeight));
					wsta.setWebService(ws);
					wstaDAO.add(wsta);
					ws.addWstagAssociation(wsta);
					wsDAO.update(ws);
					tag.addWstagAssociation(wsta);
					tagDAO.update(tag);
				}
				//retourne du success de la création du web service
				request.setAttribute("success",0);
			}else if(tags != null){
				//sinon on renvoie une erreur
				request.setAttribute("error","Web Service has been added without tag, please return to tag "+wsName+" correctly ( tagName:WeightTag )"
						+ "WeightTag is a number between 0 and 100");
			}
			
			
			Tag WS_Tag = new Tag();
			WS_Tag.setName("WS_"+ wsName.replace(" ", "_"));
			tagDAO.add(WS_Tag);
			wsta = new WSTagAssociation();
			wsta.setTag(WS_Tag);
			wsta.setVoters(1);
			wsta.setWeightSum(100);
			wsta.setWebService(ws);
			wstaDAO.add(wsta);
			WS_Tag.addWstagAssociation(wsta);
			tagDAO.update(WS_Tag);
			ws.addWstagAssociation(wsta);
			wsDAO.update(ws);
		}else{
			//retourne un message d'erreur pour avertir l'utilisateur qu'un web service du même nom existe déjà
			request.setAttribute("errorMSTemplate","The web service name "+wsName+" already exists!");
		}
		
		request.setAttribute("TagList",tagDAO.findMostPopularTags());
		request.setAttribute("width_canvas_cloud",380);
		request.setAttribute("height_canvas_cloud",194);
		request.setAttribute("title_cloud","Most popular Tags");
		
		request.setAttribute("content","/WEB-INF/views/addWS.jsp");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/template.jsp" ).forward( request, response );	
	}
}
