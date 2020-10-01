package laFac;

public abstract class CategorieProduit {

	//Methode pour savoir si la categorie de produit peut avoir des offres
	public abstract boolean reducPossible();
	
	//on oblige les sous classes a redefinir equals
	public abstract boolean equals(Object o);
	
	//on oblige les sous-classes a redifinir leur test sur la categorie
	public abstract boolean testCategorie(CategorieProduit c);
	
	//On oblige e redifinir ToString
	public abstract String toString();
	
}
