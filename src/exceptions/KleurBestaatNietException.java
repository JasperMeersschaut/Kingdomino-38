
package exceptions;

public class KleurBestaatNietException extends IllegalArgumentException {

	/**
	 * Constructor voor de KleurBestaatNietException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public KleurBestaatNietException(String message) {
		super(message);
	}

}
