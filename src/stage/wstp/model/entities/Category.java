package stage.wstp.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the Category database table.
 * 
 */
@Entity
@Table(name="Category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idCategory;

	@Column(nullable=false, length=255)
	private String name;

	//bi-directional many-to-one association to WebService
	@OneToMany(mappedBy="category",  cascade = CascadeType.ALL)
	private List<WebService> webServices;

	public Category() {
	}

	public int getIdCategory() {
		return this.idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WebService> getWebServices() {
		return this.webServices;
	}

	public void setWebServices(List<WebService> webServices) {
		this.webServices = webServices;
	}

	public WebService addWebService(WebService webService) {
		getWebServices().add(webService);
		webService.setCategory(this);

		return webService;
	}

	public WebService removeWebService(WebService webService) {
		getWebServices().remove(webService);
		webService.setCategory(null);

		return webService;
	}

}