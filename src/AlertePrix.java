package laFac;

public class AlertePrix extends Alerte {
	
	private float seuilPrix;
	
	public AlertePrix(float seuilPrix) {
		this.seuilPrix = seuilPrix;
		ServiceMarketing.TraitementCreationAlerte(this);
	}
	
	@Override
	public void onTotalChanged(float t) {
		if (t >= seuilPrix) {
			System.out.println("Alerte : le panier a depasse " + seuilPrix +"e\n");
		}
		
	}

	@Override
	public void onProduitAdded(Produit p,Panier panierEcoute) {
		
		//on ne fait rien...
		
	}

	@Override
	public void VerifCompletePanier(Panier p) {
		
		//rien a faire...
	}

}
