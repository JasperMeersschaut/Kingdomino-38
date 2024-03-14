
package domein;

import utils.Landschap;

public class Vak {

	private Landschap landschap;
	private int aantalKronen;

	public Vak(Landschap landschap, int aantalKronen) {
		this.landschap = landschap;
		this.aantalKronen = aantalKronen;
	}

	public Landschap getLandschap() {
		return landschap;
	}

	public int getAantalKronen() {
		return aantalKronen;
	}

}
