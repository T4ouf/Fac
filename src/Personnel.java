package laFac;

public class Personnel extends Categorie{
	
	private String login;
	private String mdp;
	
	public Personnel(String pseudo, String MdP) {
		login = pseudo;
		mdp = MdP;
	}
	
	@Override
	public boolean ReductionCategorieOK(OffreCategorie o) {
		
		if(o instanceof OffrePersonnel) {
			return true;
		}
		return false;
	}
	
}
