package stage.wstp.others;

import java.io.Serializable;

import stage.wstp.model.entities.Tag;

public class PopularTag implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Tag tag;
	private Long nombreOccur;
	
	public PopularTag(Tag tag, Long nombreOccur){
		this.tag = tag;
		this.nombreOccur = nombreOccur;
	}
	
	public Tag getTag() {
		return tag;
	}
	
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public Long getNombreOccur() {
		return nombreOccur;
	}
	
	public void setNombreOccur(Long nombreOccur) {
		this.nombreOccur = nombreOccur;
	}
}
