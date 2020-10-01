package laFac;

import java.util.HashMap;

//Classe represantant la BD avec les Login/Password/Categorie pour les utilisateurs du site
public class BaseLog {
	
	private static HashMap<String, Categorie> MapPseudoCategorie;
	private static HashMap<String, String> MapPseudoMdP;
	
	private BaseLog(){
		MapPseudoCategorie = new HashMap<String, Categorie>();
		MapPseudoMdP = new HashMap<>();
	}
	
	private static BaseLog INSTANCE = new BaseLog();
	
	/** Point d'acces pour l'instance unique du singleton */
    public static BaseLog getInstance(){   
    	return INSTANCE;
    }
	
	public static boolean VerifPseudoMdP(String pseudo,String MdP) {
		return MapPseudoMdP.get(pseudo).equals(MdP);
	}
	
	public static Categorie getCategoriePseudo(String pseudo) {
		return MapPseudoCategorie.get(pseudo);
	}
	
		
	public static boolean AjoutePseudo(String pseudo, String MdP, Categorie c){
		
		//on n'enregistre pas la categorie par defaut (ici visiteur) dans la base
		if(c.getClass().equals(Categorie.CategorieParDefaut().getClass())) {
			return false;
		}
		
		if(MapPseudoMdP.get(pseudo)!=null) {
			
			//si le MdP correspond a celui de l'utilisateur
			//=> on considere que c'est un changement de categorie de l'utilisateur
			if(MapPseudoMdP.get(pseudo).equals(MdP)) {
				MapPseudoCategorie.put(pseudo, c);
				return true;
			}
			else {
				//Sinon c'est juste quelqu'un qui essaye de prendre le même pseudo qu'un utilisateur
				
				/**TODO : EXCEPTION A CREER */
				System.err.println("PSEUDO DEJA PRIS !");
				return false;
			}
			
			
		}
		//Sinon => il n'est pas dans la base...
		else {
			MapPseudoMdP.put(pseudo, MdP);
			MapPseudoCategorie.put(pseudo, c);
			return true;
		}
		
	}
	
}
