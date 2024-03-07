package domein;

public class Spel {

	private Speler[] spelers;
	private Spelstapel stapel;
	private Startkolom startkolom;
	private Eindkolom eindkolom;

	public Spel(Speler[] spelers, Spelstapel stapel, Startkolom startkolom, Eindkolom eindkolom) {
		this.spelers = spelers;
		this.stapel = stapel;
		this.startkolom = startkolom;
		this.eindkolom = eindkolom;
	}

}
