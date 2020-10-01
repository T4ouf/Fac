package laFac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Panier {
	
	private ArrayList<Produit> ContenuPanier;
	private HashMap<Produit,Integer> MapProduitQuantite;
	private HashMap<Produit,ArrayList<Offre>> MapProduitOffres;
	
	private ArrayList<PanierListener> listeners = new ArrayList<>();
	
	public Panier() {
		
		//Initialisation de la HashMap
		ContenuPanier = new ArrayList<>();
		MapProduitQuantite = new HashMap<Produit, Integer>();
		MapProduitOffres = new HashMap<>();
		
	}
	
	/**
	 * A FINIR (avec les offres)
	 * @param p
	 */
	protected void AjouteProduit(Produit p) {
		
		if(ContenuPanier.contains(p)) {
			MapProduitQuantite.put(p, MapProduitQuantite.get(p)+1);
			
		}
		else {
			MapProduitQuantite.put(p,1);
			MapProduitOffres.put(p, new ArrayList<Offre>());
		}
		
		ContenuPanier.add(p);
		
		// Notify the list of registered listeners
		this.notifyProduitAddedListeners(p);
		
	}
	/*
	protected void AjouteProduit(Produit... produits) {
		
		for(Produit p : produits) {
			AjouteProduit(p);
		}
		
	}
	
	protected void AjouteProduit(ArrayList<Produit> produits) {
		
		for(Produit p : produits) {
			AjouteProduit(p);
		}
		
	}
	*/
	//On suppose que l'offre est applicable sur le panier (Verif par le serviceMarketing avant d'etre ajoutee au panier)
	protected void AjouteOffreAProduit(Produit p, Offre o){
		
		//Si le produit peut recevoir des reductions
		if(p.getCategorieProduit().reducPossible()) {
			
			//si l'offre est deja dans la Map(meme ref dans la map), on ne l'ajoute pas...
			//=> on ne peut pas ajouter plusieur fois la MEME offre a un meme produit (il faut au minimum creer une copie de l'offre)
			if(!MapProduitOffres.get(p).contains(o)) {
				MapProduitOffres.get(p).add(o);
			}
			
		}
	
	}
	
	
	public ArrayList<Produit> ListeProduitPanier(){
		
		ArrayList<Produit> liste = new ArrayList<>();
		
		for(Produit p : ContenuPanier) {

			liste.add(p);
			
		}
		
		return liste;
		
	}
	
	//Verifie si un produit d'une certaine categorie appartient au panier
	protected boolean ContientProduitCategorie(CategorieProduit categorie) {
		
		for(Produit p : ContenuPanier) {
			
			//Si les noms de classe sont identiques
			if(p.getCategorieProduit().getClass().getName().equals(categorie.getClass().getName())){
				return true;
			}
			
		}
			
		return false;
	}
	
	/**
	 * FONCTION OBLIGATOIRE A FAIRE !!
	 * @return
	 */
	public float CalculPrixPanier() {
		
		float res = 0;
		
		for(Produit p : ContenuPanier) {
			
			float valeurProduit = p.getPrix();
			float pourcentageReducTotalProduit = 1.0f;
			
			for(Offre o : MapProduitOffres.get(p)) {	
						
				//System.out.println("\n" + p + "\t" + o + "\n");
				
				//on enchaine les pourcentage de reduc
				pourcentageReducTotalProduit = (pourcentageReducTotalProduit) * (1-o.AppliqueOffreProduit(this));
				//valeurProduit = valeurProduit+((1-o.AppliqueOffreProduit(this))*p.getPrix());
				
				
			}

			//calcul du nouveau prix du produit
			double nouveauPrixProduit;
			
			if(pourcentageReducTotalProduit==0.0f) {
				nouveauPrixProduit = p.getPrix();
			}
			else {
				//System.out.println("Produit : " + p + "\n" + pourcentageReducTotalProduit + "\n" +p.getPrix()+ " - " +(1-pourcentageReducTotalProduit) * p.getPrix() + "\n");
				nouveauPrixProduit = p.getPrix()-((1-pourcentageReducTotalProduit) * p.getPrix());
			}
			
			
			//On ajoute le nouveau prix du produit au total
			res += nouveauPrixProduit;
			
			
		}
		
		// Notify the list of registered listeners
		this.notifyTotalChangedListeners(res);		
		vider();
		return res;
	}
	
	public void vider() {
		
		ContenuPanier.clear();
		
		for(Produit p : MapProduitOffres.keySet()) {
			MapProduitOffres.get(p).clear();
		}
		
		MapProduitOffres.clear();
		MapProduitQuantite.clear();
	}
	
	/////////////////////////methods des Listeners//////////////////////////
		
	public void registerProduitAddedListener (PanierListener listener) {
		// Add the listener to the list of registered listeners
		this.listeners.add(listener);
	}
	
	public void unregisterProduitAddedListener (PanierListener listener) {
		// Remove the listener from the list of the registered listeners
		this.listeners.remove(listener);
	}
	
	protected void notifyProduitAddedListeners (Produit p) {
		
		// Notify each of the listeners in the list of registered listeners
		for(PanierListener l : listeners) {
			l.onProduitAdded(p,this);
		}
		
		//lambda que dans java >= 1.8
		//this.listeners.forEach(listener -> listener.onProduitAdded(p));
	}
	
	protected void notifyTotalChangedListeners (float total) {
	
		// Notify each of the listeners in the list of registered listeners
		for(PanierListener l : listeners) {
			l.onTotalChanged(total);;
		}
		
		//lambda que dans java >= 1.8
		//this.listeners.forEach(listener -> listener.onTotalChanged(total));
	}
	
	///////////////////Fin listener////////////////////////////////////////////
	
	
	
}
