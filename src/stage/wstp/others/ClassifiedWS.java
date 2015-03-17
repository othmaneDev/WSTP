package stage.wstp.others;

import java.text.DecimalFormat;

import stage.wstp.model.entities.WebService;

public class ClassifiedWS implements Comparable<ClassifiedWS> {
	private WebService WS;
	private double mark;
	private String markToWatch;
	
	public ClassifiedWS(WebService WS, double mark){
		this.WS = WS;
		this.mark = mark;
		this.markToWatch ="";
	}
	
	public WebService getWS() {
		return WS;
	}
	public void setWS(WebService wS) {
		WS = wS;
	}
	public double getMark() {
		return mark;
	}
	public void setMark(double mark) {
		this.mark = mark;
	}

	//la note au format String afin de pouvoir l'afficher avec un format spécifique	
	public String getMarkToWatch() {
		return markToWatch;
	}
	public void setMarkToWatch(String markToWatch) {
		this.markToWatch = markToWatch;
	}
	
	/*
	 * Cette fonction permet de prendre en compte le nombre de votant dans la classification par son nombre de vote par rapport au nombre de votant pour l'ensemble des web services présent.
	 * */
	
	public void updateMarkWithVoters(int AllVoter){
		float coeffNbVotes = (float) this.WS.getPopularity()/AllVoter;
		this.mark = this.mark + (double)(WSCompare.WeightNbVotes * coeffNbVotes);
		
		DecimalFormat df = new DecimalFormat("0.00");
		this.markToWatch = df.format(this.mark);
	}
	
	@Override
	public int compareTo(ClassifiedWS cws) {
		double res = mark - cws.mark;
		if(res > 0)
			return -1;
		if(res < 0)
			return 1;
		
		return 0;
	}
	
}
