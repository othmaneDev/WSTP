package stage.wstp.search.tools;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections.map.HashedMap;

import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;

public class TransformWebServices {

	public TransformWebServices(){
		
	}
	
	public ArrayList<TamponTagwS> transformWS(ArrayList<WebService> webServices, ArrayList<RequestSemantic> requestSemanticRef){

		ArrayList<TamponTagwS> allTagWS = new ArrayList<TamponTagwS>();
		
		for(WebService wStoTransform : webServices){
			
			for(WSTagAssociation wsta : wStoTransform.getWstagAssociations()){
				
				allTagWS.add(new TamponTagwS(wStoTransform.getIdWebService(), wsta.getTag().getName(), wsta.getWeight()));
			}
		}
		
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
	
	/*public HashMap<Integer, HashMap<String,Double>> transformWSNReduc(ArrayList<WebService> webServices, ArrayList<RequestSemantic> requestSemanticRef){

		ArrayList<TamponTagwS> allTagWS = new ArrayList<TamponTagwS>();
		HashMap<Integer, HashMap<String,Double>> listTagChangeWS = new HashMap();
		
		for(WebService wStoTransform : webServices){
			
			for(WSTagAssociation wsta : wStoTransform.getWstagAssociations()){
				
				allTagWS.add(new TamponTagwS(wStoTransform.getIdWebService(), wsta.getTag().getName(), wsta.getWeight()));
			}
		}
		
		for(TamponTagwS wsTagList : allTagWS){
			
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
	*/
	
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
	
}
