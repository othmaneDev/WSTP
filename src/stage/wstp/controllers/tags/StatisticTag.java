package stage.wstp.controllers.tags;

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
import stage.wstp.others.PopularTag;

/**
 * Servlet implementation class EditTag
 */
@WebServlet("/StatisticTag")
public class StatisticTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	
	@EJB
	TagDAO tagDAO;
	
	@EJB
	WSTagAssociationDAO wstaDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatisticTag() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int compteurNonPopularTag=0;
		List<PopularTag> popularTagList=new ArrayList<PopularTag>();
		List<PopularTag> popularTagListNew=new ArrayList<PopularTag>();
		popularTagList=tagDAO.StatisticTag();
		for(PopularTag popularTag:popularTagList){
			if(popularTag.getNombreOccur()>1){
				popularTagListNew.add(new PopularTag(popularTag.getTag(),popularTag.getNombreOccur()));
			}
			else{
				compteurNonPopularTag++;
			}
		}
		if (compteurNonPopularTag!=0){
			popularTagListNew.add(new PopularTag(new Tag("Others"),1L));
		}
		request.setAttribute("hauteurStats","500px");
		request.setAttribute("tagListStats",popularTagListNew);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/StatistiqueTag.jsp" ).forward( request, response );	
	}
}
