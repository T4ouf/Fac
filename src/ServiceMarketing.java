package laFac;

import java.util.ArrayList;

//Classe qui genere, stocke et ajoute les offres (quand elles s'appliquent) des paniers
//Singleton
public class ServiceMarketing{

	private ServiceMarketing(){
		OffresEnCours=new ArrayList<>();
		Alertes = new ArrayList<>();	
		Clients = new ArrayList<>();
		Catalogue = new ArrayList<>();
	}
	
	private static ServiceMarketing INSTANCE = new ServiceMarketing();
	
	/** Point d'acces pour l'instance unique du singleton */
    public static ServiceMarketing getInstance(){   
    	return INSTANCE;
    }
	
	private static ArrayList<Offre> OffresEnCours;
	private static ArrayList<Alerte> Alertes;
	private static ArrayList<Personne> Clients;
	private static ArrayList<Produit> Catalogue;
	
	//Methode qui dit au service marketing ce qu'il doit faire avec les alertes nouvellement crees
	public static void TraitementCreationAlerte(Alerte a) {
		
		Alertes.add(a);
		for(Personne personne : Clients) {
			
			personne.getPanier().registerProduitAddedListener(a);
			a.VerifCompletePanier(personne.getPanier());
		}
		
	}
	
	//Methode qui dit au service marketing ce qu'il doit faire avec les personnes nouvellement crees
	protected static void TraitementCreationClient(Personne p) {
		
		Clients.add(p);
		for(Alerte a : Alertes) {
			p.getPanier().registerProduitAddedListener(a);
		}
	}

	//Methode qui dit au service marketing ce qu'il doit faire avec les produits nouvellement crees
	protected static void TraitementCreationProduit(Produit p) {
		
		Catalogue.add(p);
	}
	
	//Methode qui dit au service marketing ce qu'il doit faire avec les offres nouvellement crees
	public static void TraitementCreationNouvelleOffre(Offre offre) {
		//On l'ajoute a la liste des offres en cours
		OffresEnCours.add(offre);
	}
	
	
	//Cette methode sert juste a caster le bon type d'offre pour faire la bonne verification
	protected static void AjouteOffrePanier(Personne personne, Offre o){
				
		/** PB : SI PRODUIT PLUSIEUR FOIS DANS PANIER => OFFRE S'APPLIQUE A QUEL PRODUIT ? 
		 * 	=> A un seul produit : celui dans la Hashmap*/
		
		if(o instanceof OffreAdherent){
			
			OffreAdherent offre = (OffreAdherent)o;
			AjouteOffrePanier(personne, offre);
		}
		else if(o instanceof OffreFidelite){
			
			OffreFidelite offre = (OffreFidelite)o;
			AjouteOffrePanier(personne, offre);
		}
		else if(o instanceof OffreFlash){
			
			OffreFlash offre = (OffreFlash)o;
			AjouteOffrePanier(personne, offre);
		}
		else if(o instanceof OffrePersonnel){
			
			OffrePersonnel offre = (OffrePersonnel)o;
			AjouteOffrePanier(personne, offre);
			
		}
		else if(o instanceof OffreProduit){
			OffreProduit offre = (OffreProduit)o;
			AjouteOffrePanier(personne, offre);
		}
		else{
			//CAS POUR OU L'OFFRE N'EST PAS CONNUE PAR LE SERVICE MARKETING => ERREUR
			System.err.println("ERREUR ! Tentative d'ajout d'une offre inconnue par le service marketing !");
		}
		
	}
	
	//TODO AJOUTER LA METHODE SUIVANTE POUR CHAQUE TYPE D'OFFRE (en fonction de sa clef)	
	private static void AjouteOffrePanier(Personne personne, OffreAdherent offre) {
		
		if(offre.VerifConditionsOffre(personne)){
			
			personne.getPanier().AjouteOffreAProduit(offre.getClef(), offre);
			
		}
		
	}
	
	private static void AjouteOffrePanier(Personne personne, OffreFidelite offre) {
		
		if(offre.VerifConditionsOffre(personne)){
			
			//Le cast est ok puisque les conditions de l'offre sont OK
			Adherent a = (Adherent)personne.getCategorie();

			for(CarteFidelite c : a.getCartes()) {
				
				//On reset la carte lorsque l'on a ajoute l'offre au panier
				if(c.getPoints()>=c.getSeuil()) {
					c.resetCarte();
					break;
				}
			}
			
			
			//l'offre de fidelite s'applique a tous les produits du panier
			for(Produit p : personne.getPanier().ListeProduitPanier()){
				
				personne.getPanier().AjouteOffreAProduit(p, offre);
				
			}
			
		}
		
	}
	
	private static void AjouteOffrePanier(Personne personne, OffreFlash offre) {
		
		if(offre.VerifConditionsOffre(personne)){
			
			for(Produit p : offre.getClef()){
				
				personne.getPanier().AjouteOffreAProduit(p, offre);
				
			}
			
		}
		
	}

	private static void AjouteOffrePanier(Personne personne, OffrePersonnel offre) {
		
		if(offre.VerifConditionsOffre(personne)){
			
			personne.getPanier().AjouteOffreAProduit(offre.getClef(), offre);
			
		}
		
	}	

	private static void AjouteOffrePanier(Personne personne, OffreProduit offre) {
		
		if(offre.VerifConditionsOffre(personne)){
			
			personne.getPanier().AjouteOffreAProduit(offre.getClef(), offre);
			
		}
		
	}
	
	//Methode qui verifie si dans l'ensemble des offres, il y en a qui peuvent s'appliquer a une personne (a son panier)
	public static void VerifApplicabiliteOffres(Personne personne){
		
		//Pour vérifier si l'offre s'applique, on essaye de l'ajouter au panier (si elle est deja dans le panier, le panier ne l'ajoutera pas)		
		for(Offre offre : OffresEnCours){
			AjouteOffrePanier(personne, offre);
		}
		
	}
	
}
