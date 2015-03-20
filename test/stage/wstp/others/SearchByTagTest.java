package stage.wstp.others;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import stage.wstp.model.entities.Tag;
import stage.wstp.model.entities.WSTagAssociation;
import stage.wstp.model.entities.WebService;
import stage.wstp.search.tools.RequestSemantic;
import stage.wstp.search.tools.TamponTagwS;
import stage.wstp.search.tools.TransformAndOrderRequest;
import stage.wstp.search.tools.TransformWebServices;
/**
 * 
 * @author Emmanuel
 *
 */
public class SearchByTagTest {

	
	ArrayList<RequestSemantic> requestSemanticList;
	TransformAndOrderRequest testTransform;
	TransformWebServices testTransformWS;
	ArrayList<WebService> listOfWS;
	
	@Before 
	public void initialize() {
		
		testTransform = new TransformAndOrderRequest();
		
		List<String> listVideo = new ArrayList<String>();
		listVideo.add("video");
		listVideo.add("picture");
		listVideo.add("video_recording");
		
		List<String> listImage = new ArrayList<String>();
        listImage.add("image");
        listImage.add("icon");
        listImage.add("mental_image");
        listImage.add("picture");
        
        List<String> listPicture = new ArrayList<String>();
        listPicture.add("ikon");
        listPicture.add("image");
        listPicture.add("icon");
        listPicture.add("picture");
        
        List<String> listSocial = new ArrayList<String>();
        listSocial.add("sociable");
        listSocial.add("social");
        listSocial.add("mixer");
        listSocial.add("societal");
        
		requestSemanticList = new ArrayList<RequestSemantic>();
		
		requestSemanticList.add(new RequestSemantic("video",100, new HashSet<String>(listVideo)));		
		requestSemanticList.add(new RequestSemantic("image",40, new HashSet<String>(listImage)));
		requestSemanticList.add(new RequestSemantic("picture",70, new HashSet<String>(listPicture)));
		requestSemanticList.add(new RequestSemantic("social",60, new HashSet<String>(listSocial)));
    }
	
	@Before
	public void initializeWS(){
		
		testTransformWS = new TransformWebServices();
		listOfWS = new ArrayList<WebService>();
		
		List<String> ws1 = new ArrayList<String>();
		ws1.add("music");
		ws1.add("social");
		ws1.add("video");
		ws1.add("image");
        
        List<String> ws2 = new ArrayList<String>();
        ws2.add("image");
        ws2.add("picture");
        ws2.add("audio");
        ws2.add("ikon");
        
        List<String> ws3 = new ArrayList<String>();
        ws3.add("lunch");
        ws3.add("add");
        ws3.add("diet");
        ws3.add("dinner");
        
		List<List<String>> listTagPerWS = new ArrayList<List<String>>();
		listTagPerWS.add(ws1);
		listTagPerWS.add(ws2);
		listTagPerWS.add(ws3);
		
		for(int i = 0; i< 3; i++){
		
			WebService ws = new WebService();
			ws.setIdWebService(i+2);
			for(int y = 0 ;y< listTagPerWS.get(i).size();y++){
				
				WSTagAssociation wsta = new WSTagAssociation();
				Tag tag = new Tag();
				tag.setName(listTagPerWS.get(i).get(y));
				wsta.setTag(tag);
				wsta.setWeightSum((int) (Math.random()*100));
				wsta.setVoters(1);
				wsta.setWebService(ws);
				if(ws.getWstagAssociations() == null)
					ws.setWstagAssociations(new ArrayList<WSTagAssociation>());
				ws.addWstagAssociation(wsta);
			}
			listOfWS.add(ws);
		}
	}
	
	@Test
	public void TestTransformRequest(){
				
		ArrayList<String> requestInit = new ArrayList<String>();
		requestInit.add("video:100");
		requestInit.add("image:40");
		requestInit.add("picture:70");
		requestInit.add("social:60");
		
		
		ArrayList<RequestSemantic> testT = testTransform.transformRequest(requestInit);
		
		assertTrue("failure - Array transform failed  ",requestSemanticList.toString().equals(testT.toString()));
	}
	
	@Test
	public void TestOrderRequest(){
		
		ArrayList<RequestSemantic> testT = testTransform.OrderRequest(requestSemanticList);
		
		String test = "[video , 100 , [video, picture, video_recording], picture , 70 , [ikon, image, icon, picture], social , 60 , [sociable, social, mixer, societal], image , 40 , [image, icon, mental_image, picture]]";
		
		assertTrue("failure - Array order failed  ",testT.toString().equals(test));
	}
	
	@Test
	public void TestreducRequest(){
		
		testTransform.reductRequest(requestSemanticList);
		assertTrue("failure - Array reduction failed  ",requestSemanticList.toString().equals("[video , 100 , [ikon, image, icon, video, mental_image, picture, video_recording], social , 60 , [sociable, social, mixer, societal]]"));
	}
	
	@Test
	public void TesttransformWS(){
		
		testTransform.reductRequest(requestSemanticList);
		
		List<String> confirmws = new ArrayList<String>();
		confirmws.add("music");
		confirmws.add("social");
		confirmws.add("video");
		confirmws.add("video");
        
		confirmws.add("video");
		confirmws.add("video");
		confirmws.add("audio");
		confirmws.add("video");
        
		confirmws.add("lunch");
        confirmws.add("add");
        confirmws.add("diet");
        confirmws.add("dinner");
        
        ArrayList<TamponTagwS> res = testTransformWS.transformWS(listOfWS,requestSemanticList);
		
        for(int y = 0; y < res.size() ;y++){
			assertTrue("failure - unable to transform WebServiceList",confirmws.get(y).equals(res.get(y).getTagName()));
		}
		
        
	}
	
	@Test
	public void TestReductRequest(){
		
		testTransform.reductRequest(requestSemanticList);
		
		ArrayList<TamponTagwS> res = testTransformWS.transformWS(listOfWS,requestSemanticList);
		
        HashMap<Integer,HashMap<String, Double>> resReduc = testTransformWS.reducTransformWS(res);

        List<String> resKey = new ArrayList<String>();
        resKey.add("music");
        resKey.add("social");
        resKey.add("video");
        resKey.add("video");
        resKey.add("audio");
        resKey.add("add");
        resKey.add("lunch");
        resKey.add("diet");
        resKey.add("dinner");
        int j = 0;
        
        for(Entry<Integer, HashMap<String, Double>> resReducRes : resReduc.entrySet()){
        	for(Entry<String, Double> r : resReducRes.getValue().entrySet()){
        		assertTrue("failure - unable to transform WebServiceList",r.getKey().equals(resKey.get(j)));
        		j++;
        	}
		}
        
	}
	
	@Test
	public void TestgetWebServiceFromAllTags(){
		
		testTransform.reductRequest(requestSemanticList);
		
		ArrayList<TamponTagwS> res = testTransformWS.transformWS(listOfWS,requestSemanticList);
		
        HashMap<Integer,HashMap<String, Double>> resReduc = testTransformWS.reducTransformWS(res);
		
        HashMap<Integer,WebService> finalWS= testTransformWS.getWebServiceFromAllTags(resReduc);
        List<Integer>sizeRes = new ArrayList<Integer>();
        sizeRes.add(3);
        sizeRes.add(2);
        sizeRes.add(4);
        int i = 0;
        for(Entry<Integer, WebService> wsFin : finalWS.entrySet()){
        	assertTrue("Failed to create webService from all Tag",wsFin.getValue().getWstagAssociations().size() == sizeRes.get(i));
        	i++;
        }
     }
	
	
}
