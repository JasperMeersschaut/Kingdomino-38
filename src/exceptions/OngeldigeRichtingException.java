
package exceptions;

public class OngeldigeRichtingException extends IllegalArgumentException {

	/**
	 * Constructor voor de OngeldigeRichtingException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public OngeldigeRichtingException(String message) {
		super(message);
	}

}
