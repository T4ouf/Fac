package laFac;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Spectacle extends CategorieProduit{

	String nom;
	LocalTime duree;
	LocalDateTime date;
	
	public Spectacle(String nomSpectacle, LocalTime dureeSpectacle, LocalDateTime dateSpectacle) {
		
		nom = nomSpectacle;
		duree = dureeSpectacle;
		date = dateSpectacle;
		
	}

	@Override
	public boolean equals(Object o) {
		
		if(o==null) {
			return false;
		}
		else if(o==this) {
			return true;
		}
		
		if(o instanceof Spectacle) {
			
			Spectacle s = (Spectacle)o;
			
			return (s.nom==nom && s.duree.equals(duree) && date.equals(s.date));
			
		}
		
		
		return false;
	}

	@Override
	public boolean testCategorie(CategorieProduit c) {
		
		if(c instanceof Spectacle) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean reducPossible() {
		//un spectacle peut avoir des reductions
		return true;
	}

	@Override
	public String toString() {
		
		String res = nom +
				" :\n\tDate : " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + " - " + date.getHour() + ":" + date.getMinute() +
				"\n\tDuree : " + duree;
		return res;
	}
	
	
}
