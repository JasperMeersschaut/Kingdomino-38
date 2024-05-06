
package exceptions;

public class TegelKanNietWeggegooidWordenException extends IllegalArgumentException {

	/**
	 * Constructor voor de TegelKanNietWeggegooidWordenException.
	 *
	 * @param message het bericht dat bij de uitzondering hoort.
	 */
	public TegelKanNietWeggegooidWordenException(String message) {
		super(message);
	}

}
