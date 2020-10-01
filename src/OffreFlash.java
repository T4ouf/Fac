package laFac;

import java.util.ArrayList;
import java.util.HashMap;

public class OffreFlash extends Offre{
	
	//Clef de l'offre : 
	//l'offre s'applique si tous les produits present dans la cle sont presents dans le panier de l'utilisateur
	private HashMap<Produit,Integer> ClefProduitOffre;
	
	protected OffreFlash(float tauxReduc) {
		super(tauxReduc);
		ClefProduitOffre = new HashMap<Produit,Integer>();
	}
		
	protected OffreFlash(float tauxReduc, Produit...produits ) {
		super(tauxReduc);
		ClefProduitOffre = new HashMap<Produit,Integer>();
		
		for(Produit p : produits) {
			if(ClefProduitOffre.containsKey(p)) {
				ClefProduitOffre.put(p, ClefProduitOffre.get(p)+1);
			}
			else {
				ClefProduitOffre.put(p,1);
			}
		}
		
	}
	
	protected OffreFlash(float tauxReduc,ArrayList<Produit> produits ) {
		super(tauxReduc);
		ClefProduitOffre = new HashMap<Produit,Integer>();
		
		for(Produit p : produits) {
			if(ClefProduitOffre.containsKey(p)) {
				ClefProduitOffre.put(p, ClefProduitOffre.get(p)+1);
			}
			else {
				ClefProduitOffre.put(p,1);
			}
		}
		
	}
	
	public void AjouteProduit(Produit p) {
		if(ClefProduitOffre.containsKey(p)) {
			ClefProduitOffre.put(p, ClefProduitOffre.get(p)+1);
		}
		else {
			ClefProduitOffre.put(p,1);
		}
	}
	
	protected ArrayList<Produit> getClef(){
		
		ArrayList<Produit> l = new ArrayList<Produit>();
		
		for(Produit p : ClefProduitOffre.keySet()){
			l.add(p);
		}
		
		return l;
	}

	@Override
	public float AppliqueOffreProduit(Panier p) {
		//Appliquer l'offre revient juste a renvoyer le taux de reduc de celle-ci
		return this.getTauxReduc();
	}

	@Override
	public boolean VerifConditionsOffre(Personne personne) {
		
		
		ArrayList<Produit>contenuPanier = personne.getPanier().ListeProduitPanier();
		boolean OK = true;
		//On check combien de fois l'objet a ajouter est dans le panier actuellement
		for(Produit pr : contenuPanier) {
			
				//Pour chaque produit, on doit verifier qu'il est present en meme quantite dans l'offre et dans le panier
				int quantite = 0;
				for(Produit produitPanier : contenuPanier) {
					
					if(pr.equals(produitPanier)) {
						quantite+=1;
					}
					
				}
				
				if(ClefProduitOffre.containsKey(pr)) {
					if(quantite!=ClefProduitOffre.get(pr)) {
						OK = false;
						return false;
					}
				}
				
				
		}
		
		
		//On vérifie la quantite dans le panier pour voir si l'offre s'applique
		if(OK && contenuPanier.containsAll(ClefProduitOffre.keySet())) {
			return true;
		}
		
		return false;
	}
	

}
