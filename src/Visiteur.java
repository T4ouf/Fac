package laFac;

public class Visiteur extends Categorie{

	@Override
	public boolean ReductionCategorieOK(OffreCategorie o) {
		//Un visiteur n'a pas le droit aux offres categories
		return false;
	}
	
}
