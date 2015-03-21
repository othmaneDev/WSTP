package stage.wstp.search.tools;

public class TamponTagwS {

	private int idWebService;
	private String tagName;
	private double tagWeight;
	
	public TamponTagwS(int idWebService, String tagName, double tagWeight) {
		this.idWebService = idWebService;
		this.tagName = tagName;
		this.tagWeight = tagWeight;
	}

	public int getIdWebService() {
		return idWebService;
	}

	public void setIdWebService(int idWebService) {
		this.idWebService = idWebService;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public double getTagWeight() {
		return tagWeight;
	}

	public void setTagWeight(double tagWeight) {
		this.tagWeight = tagWeight;
	}
	
	
	
}
