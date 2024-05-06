
package exceptions;

public class KleurAlGekozenException extends IllegalArgumentException {

	/**
	 * Constructor voor de KleurAlGekozenException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public KleurAlGekozenException(String message) {
		super(message);
	}

}
