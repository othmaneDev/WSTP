package stage.wstp.others;

/**
 * Vérification syntaxique pour les différentes entrées proposées par l'application notament lors de la saisie des tags pour l'ajout d'un tag sur un webservice
 * @author Emmanuel
 *
 */
public class VerificationSyntaxe {
	
	//vérification de la saisie utilisateur par une expression régulière
	public static boolean verificationTagWithPoidsAndMeta(String expression){
		boolean success = true;
		String[] service = expression.split(" ");
		int i = 0;
		while(i<service.length && success){
			success = service[i].matches("^(\\[[a-zA-Z0-9_-]+\\])*[a-zA-Z_-]+:([0-9]{1,2}|100)$");
			i++;
		}
		return success;
	}
	
	//vérification de la saisie utilisateur par une expression régulière sans les méta-tags
	public static boolean verificationTagWithPoids(String expression){
		
		boolean success = true;
		String[] service = expression.split(" ");
		int i = 0;
		while(i<service.length && success){
			success = service[i].matches("^[a-zA-Z_-]+:([0-9]{1,2}|100)$");
			i++;
		}
		return success;
	}
}
