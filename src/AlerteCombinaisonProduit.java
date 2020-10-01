package laFac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AlerteCombinaisonProduit extends Alerte {

	//Represente la liste des produits concernes par l'alerte
	private HashMap<Produit,Integer> clefProduits;
	
	
	public AlerteCombinaisonProduit(ArrayList<Produit> produits) {
				
		clefProduits = new HashMap<Produit,Integer>();
		
		for(Produit p : produits) {
			
			if(clefProduits.containsKey(p)) {
				clefProduits.put(p, clefProduits.get(p)+1);
			}
			else {
				clefProduits.put(p,1);
			}
			
		}
		
		//Doit etre ajoute a chaque constructeur d'offre 
		ServiceMarketing.TraitementCreationAlerte(this);
		
	}
	
	public AlerteCombinaisonProduit(Produit... produits) {
		
		clefProduits = new HashMap<Produit,Integer>();
		
		for(Produit p : produits) {
			
			if(clefProduits.containsKey(p)) {
				clefProduits.put(p, clefProduits.get(p)+1);
			}
			else {
				clefProduits.put(p,1);
			}
			
		}
		
		ServiceMarketing.TraitementCreationAlerte(this);
		
	}
	
	public void AjouteProduit(Produit p){
	
		if(clefProduits.containsKey(p)) {
			clefProduits.put(p, clefProduits.get(p)+1);
		}
		else {
			clefProduits.put(p,1);
		}
			
		
	}
	
	
	
	@Override
	public void onProduitAdded (Produit p, Panier panierEcoute) {
		
		
		if(clefProduits.containsKey(p)==false){
			//produit pas dans l'alerte => l'alerte n'est pas concernee => on ne s'en occupe pas...
		}
		else{
			
			ArrayList<Produit>contenuPanier = panierEcoute.ListeProduitPanier();
			
			boolean OK = true;
			
			int QuantiteProduitDansClefAAjouter = clefProduits.get(p);
			
			int QuantiteProduitDansPanierActuellement = 0;
			
			//On check combien de fois l'objet a ajouter est dans le panier actuellement
			for(Produit pr : contenuPanier) {
				if(pr.equals(p)) {
					QuantiteProduitDansPanierActuellement +=1;
				}
				else {
					//Pour chaque produit, on doit verifier qu'il est present en meme quantite dans alerte et dans le panier
					int quantite = 0;
					for(Produit produitPanier : contenuPanier) {
						
						if(pr.equals(produitPanier)) {
							quantite+=1;
						}
						
					}
					if(quantite!=clefProduits.get(pr)) {
						OK = false;
						return;
					}
					
				}
			
			}
			
			
			//On vérifie la quantite dans le panier pour eviter d'afficher plusieurs fois l'alerte pour un meme panier
			if(OK && contenuPanier.containsAll(clefProduits.keySet()) && QuantiteProduitDansClefAAjouter==QuantiteProduitDansPanierActuellement) {
				System.out.print("Alerte : les produits " + clefProduits.keySet() + " (Quantite : " + clefProduits.values()+ ")" + " sont contenus dans le panier\n");
			}
			

			
		}
				
	}

	@Override
	public void onTotalChanged(float total) {
		
		//ne fait rien...
		
	}

	@Override
	public void VerifCompletePanier(Panier p) {
		
		for(Produit produit : p.ListeProduitPanier()){
			
			onProduitAdded(produit,p);
			
		}
		
		
	}
	
}
