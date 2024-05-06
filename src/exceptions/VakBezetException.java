
package exceptions;

public class VakBezetException extends IllegalArgumentException {

	/**
	 * Constructor voor de VakBezetException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public VakBezetException(String message) {
		super(message);
	}

}
