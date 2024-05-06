
package exceptions;

public class GebruikersnaamBestaatNietException extends IllegalArgumentException {

	/**
	 * Constructor voor de GebruikersnaamBestaatNietException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public GebruikersnaamBestaatNietException(String message) {
		super(message);
	}

}
