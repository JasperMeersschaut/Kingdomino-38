
package domein;

import utils.Kleur;

import java.util.List;

public class Spel {

	private Speler[] spelers;
	private List<Tegel> stapel;
	private TegelRepository tegelRepository = new TegelRepository();
	private static final int MIN_AANTAL_SPELERS = 3;
	private static final int MAX_AANTAL_SPELER = 4;

	public Spel() {
		setSpelers(spelers);
		stapel = tegelRepository.geeftegels();
	}

	public void setSpelers(Speler[] spelers) { // @Kjell: Is dit geen private setter?
		if (spelers.length < 3 || spelers.length > 4)
			throw new IllegalArgumentException(
					String.format("Er moeten %d of %d spelers meespelen", MIN_AANTAL_SPELERS, MAX_AANTAL_SPELER));
		this.spelers = spelers;
	}

	public static List<Kleur> toonBeschikbareKleuren() {
		return Kleur.geefKleuren();
	}

	public void start() {

	}
}
