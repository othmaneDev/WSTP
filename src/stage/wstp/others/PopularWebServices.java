package stage.wstp.others;

import java.io.Serializable;

import stage.wstp.model.entities.Category;
import stage.wstp.model.entities.Tag;

public class PopularWebServices implements Serializable,Comparable<PopularWebServices> {
	private static final long serialVersionUID = 1L;
	
	private String nomCategory;
	private int nombreWebServices;
	public String getNomCategory() {
		return nomCategory;
	}
	public PopularWebServices(String nomCategory, int nombreWebServices) {
		super();
		this.nomCategory = nomCategory;
		this.nombreWebServices = nombreWebServices;
	}
	public void setNomCategory(String nomcategory) {
		nomCategory = nomcategory;
	}
	public int getNombreWebServices() {
		return nombreWebServices;
	}
	public void setNombreWebServices(int nombreWebServices) {
		this.nombreWebServices = nombreWebServices;
	}
	
	@Override
	public int compareTo(PopularWebServices o) {
		
		int res=this.nombreWebServices-o.getNombreWebServices();
		if(res > 0)
			return -1;
		if(res < 0)
			return 1;
		
		return 0;
	}
	
}
