package laFac;

public class OffreFidelite extends OffreCategorie {

	protected OffreFidelite(float tauxReduc) {
		super(tauxReduc);
	}

	@Override
	public float AppliqueOffreProduit(Panier p) {
		return this.getTauxReduc();
	}

	@Override
	public boolean VerifConditionsOffre(Personne p) {

		//verif de l'offre sur la bonne categorie (adherent)
		if(p.getCategorie().ReductionCategorieOK(this)) {
			
			Adherent a = (Adherent)p.getCategorie();
			
			for(CarteFidelite c : a.getCartes()) {
				
				//si une des carte a atteint son seuil => l'offre s'applique
				if(c.getPoints()>=c.getSeuil()) {
					return true;
				}
			}
			
			
			
		}
		
		return false;
	}

}
