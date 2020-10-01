package laFac;

public class PrixInvalideException extends Exception {

	@Override
	public String getMessage() {
		return "ERREUR ! PRIX INVALIDE !";
	}
}
