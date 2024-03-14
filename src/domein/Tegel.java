
package domein;

public class Tegel implements Comparable<Tegel> {

	private int nummer;
	private Vak vakLinks;
	private Vak vakRechts;

	public Tegel(int nummer, Vak vakLinks, Vak vakRechts) {
		this.nummer = nummer;
		this.vakLinks = vakLinks;
		this.vakRechts = vakRechts;
	}

	public int getNummer() {
		return nummer;
	}

	public Vak getVakLinks() {
		return vakLinks;
	}

	public Vak getVakRechts() {
		return vakRechts;
	}

	@Override
	public int compareTo(Tegel tegel) {
		return Integer.compare(this.nummer, tegel.nummer);
	}

}
