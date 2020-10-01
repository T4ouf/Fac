package laFac;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class _Application {
	
	//Methode qui initialise l'application et effectue les tests fonctionnels
	private static void _INIT() {
	
		//INIT de la BD des login/MdP
		BaseLog.AjoutePseudo("Murphy", "123456",new Personnel("Murphy","123456"));
		BaseLog.AjoutePseudo("Frederic_Voisin_LRI", "ADA_ForEver!",new Adherent("Frederic_Voisin_LRI","ADA_ForEver!"));
		
		
		//Init de l'application avec quelques entrees
		
		//On va partager un meme contenu pour 2 produits differents
		Livre l4 = new Livre("Foreigner", "C.J. Cherryh");	
		
		
		Produit finaleCDM=null;
		Produit livreCapital=null;
		Produit livreCompil=null;
		Produit livreAda=null;
		Produit livreAliens=null;
		Produit livreAliensCollector=null;
		Produit ecran=null;
		Produit spectacleDrole=null;
		Produit spectacleTragique=null;
		
		try {
			finaleCDM = new Produit(250,new Spectacle("Finale Coupe Du Monde 2018", LocalTime.of(1, 30),LocalDateTime.of(2018, 07, 15, 16, 30)),50);
			livreCapital = new Produit(20,new Livre("Le Capital","Karl Marx"),5);
			livreCompil = new Produit(15,new Livre("Calculatrices digitales du dechiffrage de formules logicomathématiques"
														+ " par la machine meme dans la conception du programme",
														"Corrado Böhm"),20);
			livreAda = new Produit(75,new Livre("Programming in Ada 2012","John Barnes"),40);
			livreAliens = new Produit(20,l4,10);
			livreAliensCollector = new Produit(40,l4,25);
			ecran = new Produit(300, new HighTech("Sony KDL32RE400"), 100);
			
			
			spectacleDrole = new Produit(10,
					new Spectacle("Soutenance Projet de Specialite",LocalTime.of(0, 10),LocalDateTime.of(2018, 12, 06, 10, 30))
					,0);
			
			spectacleTragique = new Produit(25,
					new Spectacle("Soutenance Projet de Compilation",LocalTime.of(0, 30),LocalDateTime.of(2019, 01, 20, 8, 15))
					,0);
		}catch(PrixInvalideException e) {
			
			System.err.println(e.getMessage() + "\nLES OBJETS NE SONT PAS INITIALISES CORRECTEMENT !\nfin des test...");
			//Les objets ne sont pas correctement crees => les tests ne peuvent pas continuer...
			System.exit(1);
		}
		
		//client adherent
		Personne client1 = new Personne();
		client1.Identification("Frederic_Voisin_LRI", "ADA_ForEver!");
		client1.AjouteCarteFidelite(new CarteFidelite(50, client1));
		
		//client membre du personnel
		Personne client2 = new Personne();
		client2.Identification("Murphy", "123456");
		
		//client anonyme
		Personne client3 = new Personne();
		
		/** TEST 1 : Reduction impossible (sur livre par exemple) */
		System.out.println("\nTEST 1 :\n");
		client1.AjouteProduitPanier(livreCompil,livreAda);
		System.out.println("Payement panier client1 (2 livres): " + client1.PayerPanier()); //Pas de reduc sur les livres malgre le seuil de points depasse
			
		/** TEST 2 : Combinaison de reductions (differentes offres) */
		System.out.println("\nTEST 2 :\n");
		OffreProduit reducEcran = new OffreProduit(0.5f, LocalDateTime.of(2018,01, 01, 00, 00, 01), LocalDateTime.of(2019,01, 01, 00, 00, 01), ecran, false);
		OffreAdherent reducAdherentEcran = new OffreAdherent(0.25f, ecran);
		
		//on enchaine les reducs sur l'ecran (ici le client1 a 3 reduc, (offreProduit + offreAdherent + offreFidelite)
		client1.AjouteProduitPanier(ecran);		
		//ici les reduc s'appliquent car le produit peut avoir des reduc (et que l'on a encore depasse le seuil de fidelite)
		System.out.println("Payement panier client1 (ecran avec reduc): " +client1.PayerPanier());
		
		
		/** TEST 3 : alertes */
		System.out.println("\nTEST 3 :\n");
		
		AlerteCombinaisonProduit a = new AlerteCombinaisonProduit(ecran,livreCapital); //Petit test avec la creation d'une alerte
		AlerteCombinaisonProduit a2 = new AlerteCombinaisonProduit(livreCapital,ecran,livreCapital); //Petit test avec la creation d'une alerte
		AlertePrix ap = new AlertePrix(180);
		AlerteProduit aprod = new AlerteProduit(livreAliens);
		
		//Ici on va lever la premiere alerte et pas la 2e (quantite dans le panier pas OK)
		System.out.println("AVANT AJOUT PRODUITS :");
		client2.AjouteProduitPanier(livreCapital,ecran, livreAliens); //Ajout des produits de l'alerte au panier => On declenche l'alerte
		System.out.println("FIN AJOUT PRODUITS");
		System.out.println("Payement panier client2 (livre + ecran avec reduc): " +client2.PayerPanier());//Pas de reduc ici car le 2e client n'est pas un adherent
		
		
		/** TEST 4 : des offrePersonnel avec une alerte*/
		System.out.println("\nTEST 4 :\n");
		
		OffrePersonnel reducCE = new OffrePersonnel(0.5f,finaleCDM);
		OffrePersonnel reducCE2 = new OffrePersonnel(0.5f,livreAliens);
		
		client2.AjouteProduitPanier(finaleCDM,livreAliens);
		System.out.println("Payement panier client2 (livre + spectacle avec reduc): " + client2.PayerPanier());
		
		/** TEST 5 : une offre flash qui correspond a l'alerte non levee en test3 */
		System.out.println("\nTEST 5 :\n");
		
		OffreFlash reducMult = new OffreFlash(0.5f,livreCapital,ecran,livreCapital);
		
		client2.AjouteProduitPanier(livreCapital,ecran,livreCapital);
			
		//ici la reduc ne s'applique que sur l'ecran (car on ne peut pas l'appliquer sur les livres
		//On cumule les 2 reduc sur la TV (offre produit et l'apartenance a l'offre flash)
		System.out.println("Payement panier client2 (livres + ecran avec reduc): " + client2.PayerPanier());
		
		
		/** TEST 6 : Accesibilite de certaines offres a certaines categories de client */
		System.out.println("\nTEST 6 :\n");
		
		OffreAdherent offreAdSpectacleTragique = new OffreAdherent(0.1f,spectacleTragique);
		OffrePersonnel offrePersSpectacleTragique = new OffrePersonnel(0.2f,spectacleTragique);
		
		client1.AjouteProduitPanier(spectacleTragique);
		client2.AjouteProduitPanier(spectacleTragique);
		client3.AjouteProduitPanier(spectacleTragique);
		
		System.out.println("Prix du spectacle : " + spectacleTragique.getPrix());
		System.out.println("Payement panier client1 (Adherent reduc 10%): " + client1.PayerPanier());
		System.out.println("Payement panier client1 (Personnel reduc 20%): " + client2.PayerPanier());
		System.out.println("Payement panier client3 (Visiteur : pas de reduc categorie): " + client3.PayerPanier());
	}
	
	public static void main(String[] args) {
		
		_INIT();
		
		//TODO : TEST FONCTIONNEL		
		//CLIENTS.get(0).AjouteProduitPanier(produits);
		
		
	}

}
