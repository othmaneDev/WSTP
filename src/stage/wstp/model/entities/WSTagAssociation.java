package stage.wstp.model.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the WSTagAssociation database table.
 * 
 */
@Entity
@Table(name="WSTagAssociation")
@NamedQuery(name="WSTagAssociation.findAll", query="SELECT w FROM WSTagAssociation w")
public class WSTagAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idWSTagAssociation;

	@Column(nullable=false)
	private int voters;

	@Column(name="weight_sum", nullable=false)
	private int weightSum;

	//bi-directional many-to-one association to Tag
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Tag_idTag", nullable=false)
	private Tag tag;

	//bi-directional many-to-one association to WebService
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="WebService_idWebService", nullable=false)
	private WebService webService;

	public WSTagAssociation() {
	}

	public int getIdWSTagAssociation() {
		return this.idWSTagAssociation;
	}

	public void setIdWSTagAssociation(int idWSTagAssociation) {
		this.idWSTagAssociation = idWSTagAssociation;
	}

	public int getVoters() {
		return this.voters;
	}

	public void setVoters(int voters) {
		this.voters = voters;
	}

	public int getWeightSum() {
		return this.weightSum;
	}

	public void setWeightSum(int weightSum) {
		this.weightSum = weightSum;
	}
	
	public void incrementWeightSum(int delta){
		if(delta > 100)
			delta = 100;
		this.weightSum += delta;
	}
	
	public double getWeight(){
		if(this.voters == 0)
			return this.weightSum;
		return this.weightSum / this.voters;
	}
	

	public void incrementVoters(int i){
		this.voters+=i;
	}
	
	
	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public WebService getWebService() {
		return this.webService;
	}

	public void setWebService(WebService webService) {
		this.webService = webService;
	}

}