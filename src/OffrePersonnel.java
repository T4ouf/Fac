package laFac;
import laFac.Produit;

public class OffrePersonnel extends OffreCategorie {

	//Offre personnel que sur un produit en particulier
	private Produit clef;
	
	protected OffrePersonnel(float tauxReduc, Produit clef) {
		super(tauxReduc);
		this.clef = clef;
	}
	
	//Pour le serviceMarketing
	protected Produit getClef(){
		return clef;
	}

	@Override
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
