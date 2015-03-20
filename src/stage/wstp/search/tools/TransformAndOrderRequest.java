package stage.wstp.search.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import stage.wstp.others.WordNetTool;

public class TransformAndOrderRequest {

	public TransformAndOrderRequest(){
		
	}
	
	//fonction qui transforme une requête standard de recherche de web services par tag en collection de RequestSemantic
	public ArrayList<RequestSemantic> transformRequest(ArrayList<String> requestInitial){
		
		ArrayList<RequestSemantic> requestSemanticList = new ArrayList<RequestSemantic>();
		
		for(String tagInfo : requestInitial){
			
			String[] tagNameNWeight = tagInfo.split(":");
			String nameTag = tagNameNWeight[0];
			String weightTag = tagNameNWeight[1];
			Set synonyms;
			try{
				 synonyms = new HashSet((List<String>) WordNetTool.getInstance().getSynonymsTags(nameTag).subList(0, 4));
			}catch(Exception e){
				 synonyms = new HashSet((ArrayList<String>) WordNetTool.getInstance().getSynonymsTags(nameTag));
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
		ArrayList<String> synset = new ArrayList<String>();
		for(int i = 0; i < requestSemanticOrder.size()-1 ;i++){
			rS = requestSemanticOrder.get(i);
			synset.clear();
			for(int y = i+1; y < requestSemanticOrder.size();y++){
				ArrayList<String> rSToA = new ArrayList<String>(rS.getSynonymsArray());
				rSToA.retainAll(requestSemanticOrder.get(y).getSynonymsArray());
				if(rSToA.size()>0){
					synset.addAll(requestSemanticOrder.get(y).getSynonymsArray());
					requestSemanticOrder.remove(y);
					y--;
				}
			}
			hashMapSynon.put(i,synset);
		}
		
		Iterator it = hashMapSynon.keySet().iterator();
		
		while (it.hasNext()){
			int cle = (int) it.next();
			for(String synonyms: (List<String>) hashMapSynon.get(cle)){
				requestSemanticOrder.get(cle).getSynonymsArray().add(synonyms);
			}
		}
	}
	
}
