package laFac;

public abstract class Offre{
	
	private static int ID=0;
	
	private int id;
	private float tauxReduc;

	protected Offre(){
		this(0.0f);
	}
	
	protected Offre(float tauxReduc) {
		this.tauxReduc = tauxReduc;
		//On dit au service Marketing qu'il faut traiter l'offre nouvellement cree
		ServiceMarketing.TraitementCreationNouvelleOffre(this);
	}
	
	public float getTauxReduc() {
		return tauxReduc;
	}

	protected void setTauxReduc(float tauxReduc) {
		this.tauxReduc = tauxReduc;
	}

	//Methode pour appliquer l'offre
	protected abstract float AppliqueOffreProduit(Panier p);
	
	//Methode pour verifier que l'offre s'applique bien
	public abstract boolean VerifConditionsOffre(Personne p);
	

}
