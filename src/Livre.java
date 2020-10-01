package laFac;

public class Livre extends CategorieProduit{
	
	private String titre;
	private String auteur;
	
	public Livre(String titre, String auteur) {
		
		this.titre=titre;
		this.auteur=auteur;
		
	}
	
	public String getTitre() {
		return titre;
	}
	
	public String getAuteur() {
		return auteur;
	}
	

	@Override
	public boolean equals(Object o) {
		
		if(o==null) {
			return false;
		}
		else if(o==this) {
			return true;
		}
		
		if(o instanceof Livre) {
			
			Livre l = (Livre)o;
			
			return (l.titre==titre && l.auteur==auteur);
			
		}

		return false;
	}

	@Override
	public boolean testCategorie(CategorieProduit c) {
		
		if(c instanceof Livre) {
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		
		String res = titre + " - " + auteur ;
		return res;
		
	}

	@Override
	public boolean reducPossible() {
		
		//Pour l'instant un livre ne peut pas avoir de reduction (loi)	
		return false;
	}
	
}
