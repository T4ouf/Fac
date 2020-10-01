package laFac;

public interface PanierListener {
	public abstract void onProduitAdded (Produit p,Panier panierEcoute);
	public abstract void onTotalChanged (float total);
}
