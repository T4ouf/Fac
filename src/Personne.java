package laFac;

import java.util.ArrayList;

import laFac.Categorie;

public class Personne {

	private static int ID=0;
	private int id;
	private Categorie categorie;
	private Panier panier;
	
	public Personne() {
		
		id = ID;
		
		//Initialise la personne avec la categorie par defaut
		categorie = Categorie.CategorieParDefaut();
		panier = new Panier();
				
		ID++;
		ServiceMarketing.TraitementCreationClient(this);
		
	}
	
	public int getId() {
		return id;
	}
	
	//Getter protected pour le panier => doit pouvoir etre recup par le ServiceMarketing
	protected Panier getPanier(){
		return panier;
	}
	
	//Getter protected pour les offres => doit pouvoir etre recup par les OffreCategorie pour verifier la bonne appartenance a la categorie
	protected Categorie getCategorie(){
		return categorie;
	}
	
	public void Identification(String login, String mdp) {
		//On vide le panier a la connexion (pour eviter le probleme des offres liee a une unique categorie)
		this.panier.vider();
		this.categorie = categorie.Identification(login, mdp);
	}
	
	public void Deconnexion() {
		//On vide le panier a la deconnexion (pour eviter le probleme des offres liee a une unique categorie)
		this.panier.vider();
		this.categorie=categorie.Deconnexion();
	}
	
	public void AjouteProduitPanier(Produit p){
		
		panier.AjouteProduit(p);
		
		//Apres avoir ajoute le produit, on verifie si de nouvelles offres s'appliquent au panier
		ServiceMarketing.VerifApplicabiliteOffres(this);
		
	}
	
	public void AjouteProduitPanier(Produit... produits) {
		
		for(Produit p : produits) {
			AjouteProduitPanier(p);
		}
		
	}
	
	public void AjouteProduitPanier(ArrayList<Produit> produits) {
		
		for(Produit p : produits) {
			AjouteProduitPanier(p);
		}
		
	}

	public void AjouteCarteFidelite(CarteFidelite c) {
		
		//Si c'est un adherent, on peut lui ajouter une carte de fidelite
		if(categorie instanceof Adherent) {
			Adherent a = (Adherent)getCategorie();
			a.AjouteCarteFidelite(c);
			panier.registerProduitAddedListener(c);
		}
		
	}
	
	public float PayerPanier() {
		float res = panier.CalculPrixPanier();
		panier.vider();
		return res;
	}
}
