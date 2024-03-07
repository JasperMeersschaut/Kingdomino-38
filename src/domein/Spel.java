
package domein;

import utils.Kleur;

import java.util.ArrayList;
import java.util.List;

public class Spel {

	private Speler[] spelers;
	private List<Tegel> stapel;
	private TegelRepository tegelRepository = new TegelRepository();
	private static List<Kleur> beschikbareKleuren = Kleur.geefKleuren();
	private static final int MIN_AANTAL_SPELERS = 3;
	private static final int MAX_AANTAL_SPELER = 4;

	public Spel() {
		setSpelers(spelers);
		stapel = tegelRepository.geeftegels();
		beschikbareKleuren = new ArrayList<>();
	}

	public void setSpelers(Speler[] spelers) {
		if (spelers.length < 3 || spelers.length > 4)
			throw new IllegalArgumentException(
					String.format("Er moeten %d of %d spelers meespelen", MIN_AANTAL_SPELERS, MAX_AANTAL_SPELER));
		this.spelers = spelers;
	}

	public static List<Kleur> toonBeschikbareKleuren() {
		return beschikbareKleuren;
	}

	public void start() {

	}
}
