package stage.wstp.controllers.webservices;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stage.wstp.model.daos.WSTagAssociationDAO;
import stage.wstp.model.daos.WebServiceDAO;
import stage.wstp.model.entities.WebService;
import stage.wstp.others.WSCompare;

/**
 * Servlet implementation class WSCompare
 */
@WebServlet("/WSCompare2")
public class WSCompare2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	WebServiceDAO wsDAO;
	
	@EJB
	WSTagAssociationDAO wstaDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WSCompare2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebService req = wsDAO.find(Integer.parseInt(request.getParameter("reqId")));
		WebService tgt = wsDAO.find(Integer.parseInt(request.getParameter("tgtId")));
	
		/*
		int weightReq = 0;
		int sum = 0;
		
		for(WSTagAssociation reqwsta: req.getWstagAssociations()){
			WSTagAssociation tgtwsta = wstaDAO.findWSTagAssociation(tgt.getIdWebService(), reqwsta.getTag().getIdTag());
			if(tgtwsta != null){
				sum+= Math.min(reqwsta.getWeight(), tgtwsta.getWeight());
			}
			
			weightReq+= reqwsta.getWeight();
		}
		
		response.getWriter().print((float) sum / (float) weightReq);
		 */
	
		response.getWriter().print(WSCompare.compare(req,tgt));
		
	}

}
