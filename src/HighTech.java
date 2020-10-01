package laFac;

public class HighTech extends CategorieProduit{

	public String nom;
	
	public HighTech(String nomProduit) {
		nom = nomProduit;
	}
	
	@Override
	public boolean reducPossible() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		
		if(o==null) {
			return false;
		}
		else if(o==this) {
			return true;
		}
		
		if(o instanceof HighTech) {
			
			HighTech s = (HighTech)o;
			
			return (s.nom==nom);
			
		}
		
		return false;
	}

	@Override
	public boolean testCategorie(CategorieProduit c) {
		
		if(c instanceof HighTech) {
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return nom;
	}

}
