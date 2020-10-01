package laFac;

public class AlerteProduit extends Alerte{
	
	Produit clef;
	
	public AlerteProduit(Produit p) {
		clef=p;
		ServiceMarketing.TraitementCreationAlerte(this);
	}
	
	@Override
	public void onProduitAdded (Produit p, Panier panierEcoute) {
		
		//cas d'une alerte sur un unique produit
		if(clef.equals(p)) {
			System.out.print("Alerte : le produit " + p.toString()+ " a ete ajoute dans le panier\n");
		}
				
	}

	@Override
	public void onTotalChanged(float total) {
		//On ne fait rien
	}

	@Override
	public void VerifCompletePanier(Panier p) {

		for(Produit produit : p.ListeProduitPanier()){
			
			onProduitAdded(produit, p);
			
		}
		
	}
	
}
