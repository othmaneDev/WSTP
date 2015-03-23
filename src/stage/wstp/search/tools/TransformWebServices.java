package stage.wstp.search.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections.map.HashedMap;

import stage.wstp.model.entities.Tag;
import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;
/**
 * Classe qui permet de transformer les web services en utilisant le tableau de correspondance de la requête transformé en RequestSemantic. 
 * @see RequestSemantic
 * @author Emmanuel
 *
 */
public class TransformWebServices {

	public TransformWebServices(){
		
	}
	
	/*
	 * transforme une liste de web service en tableau comprenant l'id du web service, le nom du tag et son poids.
	 * Ensuite compare le nom du tag avec le tableau de correspondance et si matching il y a alors il remplace le nom du tag par le nom du tag de la requête.
	 * */
	public ArrayList<TamponTagwS> transformWS(List<WebService> webServices, ArrayList<RequestSemantic> requestSemanticRef){

		/*transformation en tableau de la liste des web services*/
		ArrayList<TamponTagwS> allTagWS = new ArrayList<TamponTagwS>();
		
		for(WebService wStoTransform : webServices){
			
			for(WSTagAssociation wsta : wStoTransform.getWstagAssociations()){
				
				allTagWS.add(new TamponTagwS(wStoTransform.getIdWebService(), wsta.getTag().getName(), wsta.getWeight()));
			}
		}
		
		/* remplace le nom des tags du web service par celui de la requête si ils ont le même sens*/
		for(TamponTagwS tpTagWS : allTagWS){
			
			int i = 0;
			boolean find = false ;
			while(i < requestSemanticRef.size() && !find ){
				RequestSemantic rS = requestSemanticRef.get(i);
				if(rS.getSynonymsArray().contains(tpTagWS.getTagName())){
					tpTagWS.setTagName(rS.getNameTag());
					find = true;
				}
				i++;
			}
		}
		
		return allTagWS;
	}
	
	/* une fois que l'on a le tableau modifié, il faut le restructurer en liste de web services pour la comparaison,
	 * on utilise une hashMap pour restructurer par web service leurs tags respectifs modifié. Si il y un doublon dans 
	 * un web service on garde le poids le plus élevé. */
	public HashMap<Integer, HashMap<String,Double>> reducTransformWS(ArrayList<TamponTagwS> allTags){
		
		HashMap<Integer, HashMap<String,Double>> listTagChangeWS = new HashMap();
		
		for(TamponTagwS wsTagList : allTags){
		
			if(!listTagChangeWS.containsKey(wsTagList.getIdWebService())){
				
				HashMap<String, Double> tagDetail = new HashMap<String, Double>();
				tagDetail.put(wsTagList.getTagName(), wsTagList.getTagWeight());
				listTagChangeWS.put(wsTagList.getIdWebService(), tagDetail);
			}else{
				
				if(!listTagChangeWS.get(wsTagList.getIdWebService()).containsKey(wsTagList.getTagName())){
					listTagChangeWS.get(wsTagList.getIdWebService()).put(wsTagList.getTagName(), wsTagList.getTagWeight());
				}else{
					if(listTagChangeWS.get(wsTagList.getIdWebService()).get(wsTagList.getTagName()) < wsTagList.getTagWeight() ){
						listTagChangeWS.get(wsTagList.getIdWebService()).put(wsTagList.getTagName(), wsTagList.getTagWeight());
					}
				}
			}
			
		}
		
		return listTagChangeWS;
	}
	
	/* une fois que l'on a finit de restructurer les web services grâce à la HashMap on peut procéder à la création des web services correspondant*/
	public HashMap<Integer,WebService> getWebServiceFromAllTags(HashMap<Integer,HashMap<String, Double>> newTagForWS){
		
		HashMap<Integer,WebService> newWSList = new HashMap<Integer, WebService>();
		
		for( Entry<Integer,HashMap<String,Double>> wSParcours : newTagForWS.entrySet() ){
			
			List<WSTagAssociation> tamponAsso = new ArrayList<WSTagAssociation>();
			WebService wSprim = new WebService();
			
			for(Entry<String,Double> tagOfWS : wSParcours.getValue().entrySet()){
				
				Tag tagToUse = new Tag();
				tagToUse.setName(tagOfWS.getKey());
				WSTagAssociation wsta = new WSTagAssociation();
				wsta.setTag(tagToUse);
				wsta.setWeightSum(tagOfWS.getValue().intValue());
				wsta.setVoters(1);
				wsta.setWebService(wSprim);
				tamponAsso.add(wsta);
			}
			
			wSprim.setWstagAssociations(tamponAsso);
			newWSList.put(wSParcours.getKey(),wSprim);
		}
		
		return newWSList;
	}
	
}
