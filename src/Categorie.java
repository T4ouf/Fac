package laFac;

public abstract class Categorie {
	
	//Methode Handle() du design pattern state
	//renvoit le booleen correspondant a l'applicabilite de l'offre pour la personne
	public abstract boolean ReductionCategorieOK(OffreCategorie o);
	
	//Methode pour l'arrivee d'un visiteur
	static protected Categorie CategorieParDefaut() {
		return new Visiteur();
	}
	
	//Methode pour changer de categorie
	protected Categorie Identification(String login, String mdp) {
		
		//utilisateur dans la base du site
		if(BaseLog.VerifPseudoMdP(login, mdp)) {
			return BaseLog.getCategoriePseudo(login);
		}
		//simple visiteur
		else {
			return CategorieParDefaut();
		}
		
	}
	
	//Methode pour repasser visiteur (on se deconnecte)
	protected Categorie Deconnexion() {
		return CategorieParDefaut();
	}
}
