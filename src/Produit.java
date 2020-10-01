package laFac;

public class Produit{
	
	static int ID=0;
	
	private float prix;
	private int ref; // reference d'un produit aka ID
	private CategorieProduit categorie;
	private int points;
	
	/* Pas de constructeur vide possible 
	public Produit(){
		//Appel au constructeur standard
		this(0);
		
	}
	*/
	
	public Produit(int p, CategorieProduit categorieProduit, int pts) throws PrixInvalideException{
		
		if(p<0) {
			throw new PrixInvalideException();
		}
		
		ref = ID;
		prix = p;
		ID++;
		categorie = categorieProduit;
		points = pts;
		ServiceMarketing.TraitementCreationProduit(this);
	}
	
	/* Constructeur par copie utile ? 
	public Produit(Produit p){
		ref = p.ref;
		prix=p.prix;
	}
	*/
	
	public float getPrix() {
		return prix;
	}
	
	public int getPoints() {
		return points;
	}

	protected void setPoints(int pts) {
		points=pts;
	}
	
	//Getter protected pour les offres => doit pouvoir etre recup par les OffreCategorie pour verifier la bonne appartenance a la categorie
	protected CategorieProduit getCategorieProduit(){
		return categorie;
	}
	
	//verfie que les produits sont egaux (en termes d'attributs) pour la verif des offres 
	@Override
	public boolean equals(Object o) {
		
		if(o==null) {
			return false;
		}
		else if(o==this) {
			return true;
		}
		
		if(o instanceof Produit) {
			
			Produit p = (Produit)o;
			
			//on verifie que tout est identique
			//equals de categorie verfie la categorie et les champs specifiques a la categorie
			// => equals doit être redefinie dans chaque CategorieProduit
			return (prix == p.prix && categorie.equals(p.categorie));
			
		}

		return false;
				
		
	}
	
	@Override
	public String toString(){
		
		//String res = "\nProduit : " + prix + "e\t" + categorie.toString();
		String res = categorie.toString() + " (" + prix + "e)";
	
		return res;
		
	}

}
