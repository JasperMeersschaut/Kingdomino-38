
package domein;

public class Tegel implements Comparable<Tegel> {

	private final int tegelNummer;
	private final Vak vakLinks;
	private final Vak vakRechts;
	private Speler spelerOpTegel;

	/**
	 * Constructor voor een Tegel.
	 *
	 * @param tegelNummer    het nummer van de tegel.
	 * @param vakLinks  het vak aan de linkerkant van de tegel.
	 * @param vakRechts het vak aan de rechterkant van de tegel.
	 */
	public Tegel(int tegelNummer, Vak vakLinks, Vak vakRechts) {
		this.tegelNummer = tegelNummer;
		this.vakLinks = vakLinks;
		this.vakRechts = vakRechts;
		this.spelerOpTegel = null;
	}

	/**
	 * Geeft het nummer van de tegel.
	 *
	 * @return het nummer van de tegel.
	 */
	public int geefTegelNummer() {
		return tegelNummer;
	}

	/**
	 * Geeft het vak aan de linkerkant van de tegel.
	 *
	 * @return het vak aan de linkerkant van de tegel.
	 */
	public Vak geefVakLinks() {
		return vakLinks;
	}

	/**
	 * Geeft het vak aan de rechterkant van de tegel.
	 *
	 * @return het vak aan de rechterkant van de tegel.
	 */
	public Vak geefVakRechts() {
		return vakRechts;
	}

	/**
	 * Geeft de speler op de tegel.
	 *
	 * @return de speler op de tegel.
	 */
	public Speler geefSpelerOpTegel() {
		return spelerOpTegel;
	}

	/**
	 * Stelt de speler op de tegel in.
	 *
	 * @param spelerOpTegel de speler die op de tegel moet worden ingesteld.
	 */
	public void stelSpelerOpTegelIn(Speler spelerOpTegel) {
		this.spelerOpTegel = spelerOpTegel;
	}

	/**
	 * Vergelijkt deze tegel met een andere tegel.
	 *
	 * @param tegel de andere tegel.
	 * @return een negatief getal, nul of een positief getal als deze tegel kleiner
	 *         is dan, gelijk aan of groter is dan de opgegeven tegel.
	 */
	@Override
	public int compareTo(Tegel tegel) {
		return Integer.compare(this.tegelNummer, tegel.geefTegelNummer());
	}

}
