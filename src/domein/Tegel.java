
package domein;

import utils.Landschap;

public class Tegel {

	private int nummer;
	private Landschap landschapLinks;
	private Landschap landschapRechts;
	private int aantalKronen;

	public Tegel(int nummer, Landschap landschapLinks, Landschap landschapRechts, int aantalKronen) {
		this.nummer = nummer;
		this.landschapLinks = landschapLinks;
		this.landschapRechts = landschapRechts;
		this.aantalKronen = aantalKronen;
	}

	public int getNummer() {
		return nummer;
	}

	public Landschap getLandschapLinks() {
		return landschapLinks;
	}

	public Landschap getLandschapRechts() {
		return landschapRechts;
	}

	public int getAantalKronen() {
		return aantalKronen;
	}

}
