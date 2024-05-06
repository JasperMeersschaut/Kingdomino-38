
package domein;

import utils.Landschap;

public class Vak {

	private final Landschap landschap;
	private final int aantalKronen;

	/**
	 * Constructor voor een Vak.
	 *
	 * @param landschap    het landschap van het vak.
	 * @param aantalKronen het aantal kronen in het vak.
	 */
	public Vak(Landschap landschap, int aantalKronen) {
		this.landschap = landschap;
		this.aantalKronen = aantalKronen;
	}

	/**
	 * Geeft het landschap van het vak.
	 *
	 * @return het landschap van het vak.
	 */
	public Landschap geefLandschap() {
		return landschap;
	}

	/**
	 * Geeft het aantal kronen in het vak.
	 *
	 * @return het aantal kronen in het vak.
	 */
	public int geefAantalKronen() {
		return aantalKronen;
	}

}
