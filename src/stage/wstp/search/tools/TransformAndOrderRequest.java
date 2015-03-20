package stage.wstp.search.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import stage.wstp.model.daos.CategoryDAO;
import stage.wstp.model.daos.TagDAO;
import stage.wstp.model.entities.Category;
import stage.wstp.model.entities.Tag;
import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;
import stage.wstp.others.WordNetTool;

public class TransformAndOrderRequest {

	public TransformAndOrderRequest(){
		
	}
	
	//fonction qui transforme une requête standard de recherche de web services par tag en collection de RequestSemantic
	public ArrayList<RequestSemantic> transformRequest(List<String> requestInitial){
		
		ArrayList<RequestSemantic> requestSemanticList = new ArrayList<RequestSemantic>();
		
		for(String tagInfo : requestInitial){
			String[] tagNameNWeight = tagInfo.split(":");
			String nameTag = tagNameNWeight[0];
			String weightTag = tagNameNWeight[1];
			Set synonyms;
			try{
				List<String> original = WordNetTool.getInstance().getSynonymsTags(nameTag);
				int limits = 2;
				if(original.size()>limits){
					synonyms = new HashSet((List<String>) original.subList(0,limits));
				}else{
					synonyms = new HashSet((ArrayList<String>) original);
				}
				 
			}catch(Exception e){
				synonyms = new HashSet();
			}
			
			synonyms.add(nameTag.toLowerCase());
			
			requestSemanticList.add(new RequestSemantic(nameTag, Integer.parseInt(weightTag), synonyms));
		}
		
		return requestSemanticList;
	}
	
	//fonction qui trie par ordre décroissant les RequestSemantic
	public ArrayList<RequestSemantic> OrderRequest(ArrayList<RequestSemantic> requestInitial){
		
		//Sorting
		Collections.sort(requestInitial, new Comparator<RequestSemantic>() {
		        @Override
		        public int compare(RequestSemantic  rS1, RequestSemantic  rS2)
		        {

		            return  rS2.getWeight()-rS1.getWeight();//fruite1.fruitName.compareTo(fruite2.fruitName);
		        }

		    });
		
		return requestInitial;
	}
	
	//fonction qui réduit le tableau de RequestSemantic en concaténant les requêtes avec des tags indentiques
	public void reductRequest(ArrayList<RequestSemantic> requestSemanticOrder){
		HashMap<Integer, List<String> > hashMapSynon = new HashMap<Integer, List<String> >();
		RequestSemantic rS;
		
		for(int i = 0; i < requestSemanticOrder.size()-1 ;i++){
			rS = requestSemanticOrder.get(i);
			ArrayList<String> synset = new ArrayList<String>();
			for(int y = i+1; y < requestSemanticOrder.size();y++){
				ArrayList<String> rSToA = new ArrayList<String>(rS.getSynonymsArray());
				rSToA.retainAll(requestSemanticOrder.get(y).getSynonymsArray());
				if(rSToA.size()>0){
					synset.addAll(requestSemanticOrder.get(y).getSynonymsArray());
					hashMapSynon.put(i,(List<String>) synset.clone());
					requestSemanticOrder.remove(y);
					i--;
				}
			}
			
		}
		
		Iterator it = hashMapSynon.keySet().iterator();
		
		while (it.hasNext()){
			int cle = (int) it.next();
			for(String synonyms: (List<String>) hashMapSynon.get(cle)){
				requestSemanticOrder.get(cle).getSynonymsArray().add(synonyms);
			}
		}
	}
	
	public WebService getWebServiceRequest(ArrayList<RequestSemantic> requestSemanticList, CategoryDAO catDAO,TagDAO tagDAO){
		
		WebService ws = new WebService();
		ws.setName("Request");
		ws.setDescription("A search request.");
		
		Category cat = catDAO.findOrCreate("Request");
		
		ws.setCategory(cat);
		
		String tagWeight;
		
		Tag tag;
		for(RequestSemantic requestSc:requestSemanticList){
			
			tag = tagDAO.findOrCreate(requestSc.getNameTag());
			
			WSTagAssociation wsta = new WSTagAssociation();
			wsta.setTag(tag);
			wsta.setWeightSum( requestSc.getWeight());
			wsta.setVoters(1);
			wsta.setWebService(ws);
			if(ws.getWstagAssociations() == null)
				ws.setWstagAssociations(new ArrayList<WSTagAssociation>());
			ws.addWstagAssociation(wsta);
		}
		return ws;
	}
	
}
