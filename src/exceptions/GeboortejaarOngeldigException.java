
package exceptions;

public class GeboortejaarOngeldigException extends IllegalArgumentException {

	/**
	 * Constructor voor de GeboortejaarOngeldigException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public GeboortejaarOngeldigException(String message) {
		super(message);
	}

}
