package stage.wstp.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.eclipse.persistence.annotations.QueryRedirectors;

import java.util.List;


/**
 * The persistent class for the Tag database table.
 * 
 */
@Entity
@Table(name="Tag")
@NamedQueries({
	@NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t"),
	@NamedQuery(name="Tag.findMostPopularTags", query="SELECT wsta.tag,SUM(wsta.voters) FROM WSTagAssociation wsta GROUP BY wsta.tag ORDER BY SUM(wsta.voters) DESC"),
	@NamedQuery(name="Tag.findByCategory", query="SELECT DISTINCT t FROM Tag t,WSTagAssociation wst,WebService ws WHERE t.idTag=wst.tag.idTag AND wst.webService.idWebService=ws.idWebService AND ws.category.idCategory=:idCategory")})

public class Tag implements Serializable {
	public Tag(String name) {
		super();
		this.name = name;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idTag;

	@Lob
	private String description;

	@Column(nullable=false, length=255)
	private String name;

	@Column(nullable=false)
	private int popularity;

	//bi-directional many-to-one association to WSTagAssociation
	@OneToMany(mappedBy="tag", cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	private List<WSTagAssociation> wstagAssociations;

	public Tag() {
	}

	public int getIdTag() {
		return this.idTag;
	}

	public void setIdTag(int idTag) {
		this.idTag = idTag;
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

	public List<WSTagAssociation> getWstagAssociations() {
		return this.wstagAssociations;
	}

	public void setWstagAssociations(List<WSTagAssociation> wstagAssociations) {
		this.wstagAssociations = wstagAssociations;
	}

	public WSTagAssociation addWstagAssociation(WSTagAssociation wstagAssociation) {
		getWstagAssociations().add(wstagAssociation);
		wstagAssociation.setTag(this);

		return wstagAssociation;
	}

	public WSTagAssociation removeWstagAssociation(WSTagAssociation wstagAssociation) {
		getWstagAssociations().remove(wstagAssociation);
		wstagAssociation.setTag(null);

		return wstagAssociation;
	}

}