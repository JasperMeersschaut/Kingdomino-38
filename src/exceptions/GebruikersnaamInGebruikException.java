
package exceptions;

public class GebruikersnaamInGebruikException extends IllegalArgumentException {

	/**
	 * Constructor voor de GebruikersnaamInGebruikException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public GebruikersnaamInGebruikException(String message) {
		super(message);
	}

}
