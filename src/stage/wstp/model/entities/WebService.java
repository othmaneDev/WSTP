package stage.wstp.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * The persistent class for the WebService database table.
 * 
 */
@Entity
@Table(name="WebService")
@NamedQuery(name="WebService.findAll", query="SELECT w FROM WebService w")
public class WebService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idWebService;

	@Lob
	private String description;

	@Column(name="fileName",length=255)
	private String fileName;
	
	@Column(nullable=false, length=255)
	private String name;
	
	@Column(nullable=false)
	private int popularity;

	@Column(name="URL", length=255)
	private String url;
    
	@Column(name="date_dernier_tag")
	private Timestamp dateDernierTag;
	
	public Timestamp getDateDernierTag() {
		return dateDernierTag;
	}

	public void setDateDernierTag(Timestamp dateDernierTag) {
		this.dateDernierTag = dateDernierTag;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUserWSAssociations(List<UserWSAssociation> userWSAssociations) {
		this.userWSAssociations = userWSAssociations;
	}



	//bi-directional many-to-one association to WSTagAssociation
	@OneToMany(mappedBy="webService")
	private List<WSTagAssociation> wstagAssociations;

	@OneToMany(mappedBy="webService")
	private List<UserWSAssociation> userWSAssociations;
	
	//bi-directional many-to-one association to Category
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Category_idCategory", nullable=false)
	private Category category;

	public WebService() {
	}

	public int getIdWebService() {
		return this.idWebService;
	}

	public void setIdWebService(int idWebService) {
		this.idWebService = idWebService;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopularity() {
		return this.popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	
	public void incrementPopularity(){
		setPopularity(getPopularity()+1);
		
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<WSTagAssociation> getWstagAssociations() {
		return this.wstagAssociations;
	}
	

	public List<WSTagAssociation> getSortedWSTAS(){
			List<WSTagAssociation> list =  getWstagAssociations();
			Collections.sort(list,new Comparator<WSTagAssociation>(){
				public int compare(WSTagAssociation wsta1,WSTagAssociation wsta2){
					return (int) (wsta2.getWeight() - wsta1.getWeight());
				}
			});
			return list;
		}

	public void setWstagAssociations(List<WSTagAssociation> wstagAssociations) {
		this.wstagAssociations = wstagAssociations;
	}

	public WSTagAssociation addWstagAssociation(WSTagAssociation wstagAssociation) {
		getWstagAssociations().add(wstagAssociation);
		wstagAssociation.setWebService(this);

		return wstagAssociation;
	}

	public WSTagAssociation removeWstagAssociation(WSTagAssociation wstagAssociation) {
		getWstagAssociations().remove(wstagAssociation);
		wstagAssociation.setWebService(null);

		return wstagAssociation;
	}

	/* User */
	public List<UserWSAssociation> getUserWSAssociations() {
		return this.userWSAssociations;
	}

	public void setUserWSAssociation(List<UserWSAssociation> userWSAssociations) {
		this.userWSAssociations = userWSAssociations;
	}
	
	public UserWSAssociation addUserWSAssociation(UserWSAssociation userWSAssociations) {
		getUserWSAssociations().add(userWSAssociations);
		userWSAssociations.setWebService(this);

		return userWSAssociations;
	}

	public UserWSAssociation removeUserWSAssociation(UserWSAssociation userWSAssociations) {
		getUserWSAssociations().remove(userWSAssociations);
		userWSAssociations.setWebService(null);

		return userWSAssociations;
	}
	/**/
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	//vérifie qu'un tag contenant un méta existe dans l'association
	public boolean isMetaTagExist(){
		boolean res = false;
		int i = 0;
		while(i < this.wstagAssociations.size() && !res){
			
			if(this.wstagAssociations.get(i).getTag().getName().matches("^(\\[[a-zA-Z0-9_-]+\\])[a-zA-Z_-]+$")){
				
				res = true;
			}
			i++;
		}
		return res;
	}
	
	//vérifie qu'un tag simple existe dans une association
	public boolean isTagExist(){
		boolean res = false;
		int i = 0;
		while(i < this.wstagAssociations.size() && !res){
			if(this.wstagAssociations.get(i).getTag().getName().matches("^[a-zA-Z_-]+$")){
				res = true;
			}
			i++;
		}
		return res;
	}
	
	//return uniquement la liste des tags simples ordonné
	public List<WSTagAssociation> getWSTASOnlyTag(){
		List<WSTagAssociation> list =  getWstagAssociations();
		Collections.sort(list,new Comparator<WSTagAssociation>(){
			public int compare(WSTagAssociation wsta1,WSTagAssociation wsta2){
				return (int) (wsta2.getWeight() - wsta1.getWeight());
			}
		});
		List<WSTagAssociation> listOnlyTag =  new ArrayList<WSTagAssociation>();
		for(WSTagAssociation wsta : list){
			if(wsta.getTag().getName().matches("^[a-zA-Z_-]+$")){
				listOnlyTag.add(wsta);
			}
		}
		return listOnlyTag;
	}
	
	//return uniquement la liste des tags contenant un meta ordonné
	public List<WSTagAssociation> getWSTASOnlyMetaTag(){
		List<WSTagAssociation> list =  getWstagAssociations();
		Collections.sort(list,new Comparator<WSTagAssociation>(){
			public int compare(WSTagAssociation wsta1,WSTagAssociation wsta2){
				return (int) (wsta2.getWeight() - wsta1.getWeight());
			}
		});
		List<WSTagAssociation> listOnlyTag =  new ArrayList<WSTagAssociation>();
		for(WSTagAssociation wsta : list){
			if(wsta.getTag().getName().matches("^(\\[[a-zA-Z0-9_-]+\\])[a-zA-Z_-]+$")){
				listOnlyTag.add(wsta);
			}
		}
		return listOnlyTag;
	}

}