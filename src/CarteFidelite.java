package laFac;

public class CarteFidelite  implements PanierListener{
	
	private int points;
	private int seuil;
	private Personne proprietaire;
	
	//L'ajout de points est possible que si le seuil n'est pas depasse
	private boolean AjoutPointsPossible;
	
	
	//On suppose qu'une offre fidelite donne toujours 10% de reduction
	private static float TAUX_REDUC_FIDELITE = 0.1f;
	
	public CarteFidelite(int seuil, Personne proprietaire) {
		super();
		this.points = 0;
		this.seuil = seuil;
		this.proprietaire=proprietaire;
		AjoutPointsPossible = true;
	}

	@Override
	 public void onProduitAdded (Produit p,Panier panierEcoute) {

		if(AjoutPointsPossible) {
			
			this.points+=p.getPoints();

			//si le seuil est depasse, on cree alors automatiquement l'offre fidelite
			if(points>=seuil){
				
				//On a atteint le seuil => il faut depenser les points pour pouvoir en ajouter a nouveau
				AjoutPointsPossible = false;
				
				OffreFidelite o = new OffreFidelite(TAUX_REDUC_FIDELITE);
				//On ajoute l'offre que l'on vient de creer a l'ensemble des offres
				ServiceMarketing.AjouteOffrePanier(proprietaire, o);	
				
			}
		}
		
		
		
		
	}
	
	@Override
	public void onTotalChanged (float total) {
		
	}
	
	public int getPoints() {
		return points;
	}
	
	public void resetCarte() {
		points=0;
		AjoutPointsPossible=true;
	}

	public void setPoints(int pts) {
		this.points = pts;
	}
	
	public int getSeuil() {
		return seuil;
	}
	
}
