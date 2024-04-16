
package domein;

public class Tegel implements Comparable<Tegel> {

	private final int tegelNummer;
	private final Vak vakLinks;
	private final Vak vakRechts;
	private Speler spelerOpTegel;

	public Tegel(int nummer, Vak vakLinks, Vak vakRechts) {
		this.tegelNummer = nummer;
		this.vakLinks = vakLinks;
		this.vakRechts = vakRechts;
		this.spelerOpTegel = null;
	}

	public int getTegelNummer() {
		return tegelNummer;
	}

	public Vak getVakLinks() {
		return vakLinks;
	}

	public Vak getVakRechts() {
		return vakRechts;
	}

	@Override
	public int compareTo(Tegel t) {
		return Integer.compare(this.tegelNummer, t.getTegelNummer());
	}

	public Speler getSpelerOpTegel() {
		return spelerOpTegel;
	}

	public void setSpelerOpTegel(Speler spelerOpTegel) {
		this.spelerOpTegel = spelerOpTegel;
	}

}
