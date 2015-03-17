package stage.wstp.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="UserWSAssociation")
public class UserWSAssociation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idUserWSAssociation;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="User_idUser", nullable=false)
	private User user;

	//bi-directional many-to-one association to WebService
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="WebService_idWebService", nullable=false)
	private WebService webService;

	public UserWSAssociation() {
	}

	public int getIdUserWSAssociation() {
		return idUserWSAssociation;
	}

	public void setIdUserWSAssociation(int idUserWSAssociation) {
		this.idUserWSAssociation = idUserWSAssociation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WebService getWebService() {
		return webService;
	}

	public void setWebService(WebService webService) {
		this.webService = webService;
	}

	

}
