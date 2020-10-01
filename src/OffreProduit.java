package laFac;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OffreProduit extends Offre{
	
	private LocalDateTime debut;
	private LocalDateTime fin;
	
	private Produit clef;
	
	//Booleen qui represente si l'offre porte sur la categorie ou sur le produit en particulier
	//true = offre est sur la categorie
	//false = offre est sur le produit
	private boolean offreCategorielle;
	
	
	//FAIRE MAJ PRIX DANS LE PRODUIT DIRECTEMENT (pour offre produit)
	
	protected OffreProduit(float tauxReduc, LocalDateTime debut, LocalDateTime fin, Produit p, boolean categorielle) {
		super(tauxReduc);
		this.debut = debut;
		this.fin = fin;
		clef = p;
		offreCategorielle = categorielle;
		
		//une offre produit s'applique des que l'on l'a cree
		AppliqueOffreProduit(null);
	}
	
	public boolean VerifDate() {
		if(LocalDateTime.now().isAfter(debut) && LocalDateTime.now().isBefore(fin)) {
			return true;
		}
		return false;
	}
	
	public boolean VerifDate(LocalDateTime d) {
		
		if(d.isAfter(debut) && d.isBefore(fin)) {
			return true;
		}
		return false;
	}
	
	//Pour le serviceMarketing
	protected Produit getClef(){
		return clef;
	}
	
	public boolean estOffreCategorielle() {
		return offreCategorielle;
	}
	

	@Override
	//Pour les OffreProduit, on modifie le prix du produit concerne dans le panier (et l'on ne renvoit pas de taux de reduction)
	public float AppliqueOffreProduit(Panier p) {		
		//on renvoit le taux de reduc de l'offre
		return this.getTauxReduc();
		
	}

	@Override
	public boolean VerifConditionsOffre(Personne p) {
				
		//si l'offre porte sur la categorie du produit (et pas le produit)
		if(offreCategorielle) {
			
			if(VerifDate()) {
				return p.getPanier().ContientProduitCategorie(clef.getCategorieProduit());
			}	
			
		}
		else {
			
			if(VerifDate()) {
				
				if(p.getPanier().ListeProduitPanier().contains(clef)) {
					return true;
				}
				
			}	
			
		}
		
		return false;
	}
	
}
