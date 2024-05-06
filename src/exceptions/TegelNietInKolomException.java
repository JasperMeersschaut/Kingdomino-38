
package exceptions;

public class TegelNietInKolomException extends IllegalArgumentException {

	/**
	 * Constructor voor de TegelNietInKolomException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public TegelNietInKolomException(String message) {
		super(message);
	}

}
