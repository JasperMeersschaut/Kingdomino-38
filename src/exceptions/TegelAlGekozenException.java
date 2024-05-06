
package exceptions;

public class TegelAlGekozenException extends IllegalArgumentException {

	/**
	 * Constructor voor de TegelAlGekozenException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public TegelAlGekozenException(String message) {
		super(message);
	}

}
