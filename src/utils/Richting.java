
package utils;

public enum Richting {

	LINKS, RECHTS, BOVEN, ONDER;

	/**
	 * Geeft de richtingen terug als een 2D array van indices.
	 *
	 * @return de richtingen als een 2D array van indices.
	 */
	public static int[][] geefRichtingen() {
		return new int[][] { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
	}

	/**
	 * Geeft de richtingsindices terug die overeenkomt met de opgegeven richting.
	 *
	 * @param richting de richting om om te zetten.
	 * @return de richtingsindices die overeenkomen met de gegeven richting.
	 */
	public static int[] geefRichting(Richting richting) {
		if (richting == null)
			return null;
		return switch (richting) {
			case LINKS -> new int[] { 0, -1 };
			case RECHTS -> new int[] { 0, 1 };
			case BOVEN -> new int[] { -1, 0 };
			case ONDER -> new int[] { 1, 0 };
		};
	}

	/**
	 * Geeft de graden van de richting terug.
	 *
	 * @param richting de richting om om te zetten.
	 * @return de graden van de richting.d
	 */
	public static double geefRichtingsGraden(Richting richting) {
		if (richting == null)
			return 0;
		return switch (richting) {
			case LINKS -> 180;
			case RECHTS -> 0;
			case BOVEN -> 270;
			case ONDER -> 90;
		};
	}

}
