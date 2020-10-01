package laFac;

public class OffreAdherent extends OffreCategorie {

	private Produit clef;
	
	protected OffreAdherent(float tauxReduc, Produit produitOffre) {
		super(tauxReduc);
		clef = produitOffre;
	}
	
	//Pour le serviceMarketing
	protected Produit getClef(){
		return clef;
	}

	@Override
	//Methode qui applique l'offre et renvoit le taux de reduction de l'offre (en %)
	public float AppliqueOffreProduit(Panier p) {
		return this.getTauxReduc();
	}

	@Override
	public boolean VerifConditionsOffre(Personne p) {
		
		//si les conditions liees a la categorie de l'utilisateur sont OK
		if(p.getCategorie().ReductionCategorieOK(this)) {
			
			if(p.getPanier().ListeProduitPanier().contains(clef)) {
				return true;
			}
			
		}
		
		return false;
	}

	
	
	
}
