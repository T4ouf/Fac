package laFac;

import java.util.ArrayList;

public abstract class Alerte implements PanierListener{
	
	//Lance la verification complete (si l'alerte a ete cree apres le panier)
	public abstract void VerifCompletePanier(Panier p);
	
}
