package stage.wstp.search.tools;

import java.io.Serializable;
import java.util.Set;

/**
 * Classe afin de structurer la requête de l'utilisateur en rajoutant à chaque tag de la requête son poids et un tableau de synonyme afin de l'utilisé pour réaliser une recherche plus poussé qu'une simple comparaison de chaine de caractère.
 * @author Emmanuel
 *
 */
public class RequestSemantic implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nameTag;
	private int weight;
	private Set synonymsArray;
	
	public RequestSemantic(String nameTag, int weight, Set synonymsArray){
		
		this.nameTag = nameTag;
		this.weight = weight;
		this.synonymsArray = synonymsArray;
	}

	public String getNameTag() {
		return nameTag;
	}

	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Set getSynonymsArray() {
		return synonymsArray;
	}

	public void setSynonymsArray(Set synonymsArray) {
		this.synonymsArray = synonymsArray;
	}
	
	@Override
	public String toString(){
		return nameTag+" , "+weight+" , "+synonymsArray.toString();
	}
	
}
