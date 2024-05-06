
package exceptions;

public class GebruikersnaamAlGekozenException extends IllegalArgumentException {

	/**
	 * Constructor voor de GebruikersnaamAlGekozenException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public GebruikersnaamAlGekozenException(String message) {
		super(message);
	}

}
