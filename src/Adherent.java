package laFac;

import java.util.ArrayList;

public class Adherent extends Categorie{
	
	/*Garder login et mdp ici ?*/
	private String login;
	private String mdp;
	private ArrayList<CarteFidelite> cartesFidelite;
	
	public Adherent(String pseudo, String MdP) {
		super();
		login = pseudo;
		mdp = MdP;
		cartesFidelite = new ArrayList<>();
	}
	
	public Adherent(String login, String mdp, ArrayList<CarteFidelite> cartesFidelite) {
		
		super();
		this.login = login;
		this.mdp = mdp;
		this.cartesFidelite = cartesFidelite;
	}
	
	public void AjouteCarteFidelite(CarteFidelite c){
		
		cartesFidelite.add(c);
		
	}
	
	protected ArrayList<CarteFidelite> getCartes() {
		
		ArrayList<CarteFidelite> l = new ArrayList<CarteFidelite>();
		
		for(CarteFidelite c : cartesFidelite) {
			l.add(c);
		}
		
		return l;
	}
	
	@Override
	public boolean ReductionCategorieOK(OffreCategorie o) {
				
		
		//On ajoute toutes les categorie d'offre e laquelle a droit un adherent)
		if(o instanceof OffreAdherent) {
			return true;
		}
		else if(o instanceof OffreFidelite){
			return true;
		}
		
		
		return false;
	}
}
