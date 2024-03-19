
package domein;

public class Tegel {

	private final int nummer;
	private final Vak vakLinks;
	private final Vak vakRechts;

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

}
