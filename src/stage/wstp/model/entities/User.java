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
@Table(name="Utilisateur")
/*@NamedQueries({
	@NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t"),
	@NamedQuery(name="Tag.findByCategory", query="SELECT DISTINCT t FROM Tag t,WSTagAssociation wst,WebService ws WHERE t.idTag=wst.tag.idTag AND wst.webService.idWebService=ws.idWebService AND ws.category.idCategory=:idCategory")})
*/
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idUser;

	@Column(nullable=false, length=255)
	private String name;

	@Column(nullable=false)
	private int level; //niveau de l'utilisateur -1 non renseigné, 0 utilisateur amateur, 1 utilisateur expérimenté dans les web services

	@OneToMany(mappedBy="user", cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	private List<UserWSAssociation> userWSAssociations;
	
	public User() {
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<UserWSAssociation> getUserWSAssociations() {
		return this.userWSAssociations;
	}

	public void setUserWSAssociation(List<UserWSAssociation> userWSAssociations) {
		this.userWSAssociations = userWSAssociations;
	}
	
	//ajout d'une association entre un utilisateur et un web service
	public UserWSAssociation addUserWSAssociation(UserWSAssociation userWSAssociations) {
		getUserWSAssociations().add(userWSAssociations);
		userWSAssociations.setUser(this);

		return userWSAssociations;
	}

	//suppression d'une association entre un utilisateur et un web service
	public UserWSAssociation removeUserWSAssociation(UserWSAssociation userWSAssociations) {
		getUserWSAssociations().remove(userWSAssociations);
		userWSAssociations.setUser(null);

		return userWSAssociations;
	}

}