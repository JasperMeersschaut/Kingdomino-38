
package domein;

import utils.Landschap;

public class Tegel implements Comparable<Tegel> {

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

	@Override
	public int compareTo(Tegel tegel) {
		return Integer.compare(this.nummer, tegel.nummer);
	}

}
