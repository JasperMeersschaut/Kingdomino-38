
package exceptions;

public class AantalSpelersOngeldigException extends IllegalArgumentException {

	/**
	 * Constructor voor de AantalSpelersOngeldigException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public AantalSpelersOngeldigException(String message) {
		super(message);
	}

}
