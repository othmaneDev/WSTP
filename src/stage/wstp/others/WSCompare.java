package stage.wstp.others;

import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;

public class WSCompare {
	
	/**
	 * coefficient de formule de recherche
	 */
	public static Double WeightPoids = 0.45;
	public static Double WeightNbTags = 0.45;
	public static Double WeightNbVotes = 0.10;
	
	public static double compareOld(WebService req, WebService tgt){
	
		int weightReq = 0;
		int sum = 0;
		
		for(WSTagAssociation reqwsta: req.getWstagAssociations()){
			
			for(WSTagAssociation tgtwsta : tgt.getWstagAssociations()){
				if(tgtwsta.getTag().getIdTag() == reqwsta.getTag().getIdTag()){
					sum+= Math.min(reqwsta.getWeight(), tgtwsta.getWeight());
				}
			}
			
			weightReq+= reqwsta.getWeight();
		}
	
		return (float) sum / (float) weightReq;
	}	
	
	/** 
	 * Modificaiton de la fonction compareOld afin de prendre en compte les modifications suivantes : 
	 * 	- donner plus d'importance au nombre de tag similaire
	 * */
	public static double compare(WebService req, WebService tgt){
		
		int weightReq = 0;
		int sum = 0;
		int numberTagstgt = 0;
		int numberTagsreq = 0;
		
		for(WSTagAssociation reqwsta: req.getWstagAssociations()){
			
			for(WSTagAssociation tgtwsta : tgt.getWstagAssociations()){
				
				if(tgtwsta.getTag().getIdTag() == reqwsta.getTag().getIdTag()){
					sum+= Math.min(reqwsta.getWeight(), tgtwsta.getWeight());
					numberTagstgt += 1;
				}
			}
			
			weightReq+= reqwsta.getWeight();
			numberTagsreq += 1;
		}
		float coeffPoids = (float) sum / (float) (weightReq);
		float coeffNbTags = (float) numberTagstgt / (float) numberTagsreq;
		float coeffWithoutVotes = (float) (WSCompare.WeightPoids * coeffPoids) + (float) (WSCompare.WeightNbTags * coeffNbTags);// (1/(float)numberTagsreq)*(numberTagsreq-numberTagstgt);

		return coeffWithoutVotes;
	}
}