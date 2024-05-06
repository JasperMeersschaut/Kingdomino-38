
package exceptions;

public class GebruikersnaamOngeldigException extends IllegalArgumentException {

	/**
	 * Constructor voor de GebruikersnaamOngeldigException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public GebruikersnaamOngeldigException(String message) {
		super(message);
	}

}
