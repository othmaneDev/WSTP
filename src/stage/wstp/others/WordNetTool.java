package stage.wstp.others;

import java.util.ArrayList;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
/**
 * 
 * @author Emmanuel
 *
 * Classe utilisant la librairie wordnet afin de renseigner sur un mot en particulier, sa signification...
 * Dans notre cas nous l'utilisons pour la fonctionnalité de recherche de synonyme
 * Etant un outil, permettant juste de répondre à une fonctionnalité, nous l'avons réduit à un singleton.
 * Pour son utilisation nous devons installer l'outil disponible à l'adresse suivante :http://wordnet.princeton.edu/
 */
public class WordNetTool {

	private static WordNetTool tools = null;
	 
	//on informe le systeme ou se trouve le dictionnaire qu'utilise la librairie sur la machine. pour l'utilisation nous devons installer l'outil wordnet
	private WordNetTool(){
		System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict");
	}
	
	public static WordNetTool getInstance(){
		if(tools == null){
			tools = new WordNetTool();
		}
		return tools;
	}
	
	//fonction que l'on utilise pour fournir un synonymes à un mot donné
	public ArrayList<String> getSynonymsTags(String word) {
		ArrayList<String> synonyms = new ArrayList<String>();
		
		try {
			WordNetDatabase database = WordNetDatabase.getFileInstance();
			Synset[] synsets = database.getSynsets(word);
			
			for (int i = 0; i < synsets.length; i++)
			{
				String[] wordForms = synsets[i].getWordForms();
				for (int j = 0; j < wordForms.length; j++)
				{
					wordForms[j] = wordForms[j].replaceAll(" ","_").toLowerCase();
					if(!synonyms.contains(wordForms[j]) && i < 2 ){
						synonyms.add(wordForms[j].toLowerCase());
					}
				}
			}
		} catch (Exception e) {
			synonyms.clear();
		}
		return synonyms;
	}
	
	/*public static void main(String[] args){
		
		System.out.println(getInstance().getSynonymsTags("video"));
	
	}*/
}
