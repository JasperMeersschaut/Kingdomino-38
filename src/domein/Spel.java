
package domein;

import java.util.List;
import java.util.Scanner;

import DTO.SpelerDTO;
import utils.Kleur;

public class Spel {
	private DomeinController dc = new DomeinController();
	Scanner scanner = new Scanner(System.in);
	List<SpelerDTO> spelers;
	private List<Tegel> stapel;
	private TegelRepository tegelRepository = new TegelRepository();
	private static final int MIN_AANTAL_SPELERS = 3;
	private static final int MAX_AANTAL_SPELER = 4;

	public Spel(List<SpelerDTO> spelers) {
		setSpelers(spelers);
		stapel = tegelRepository.geeftegels();
	}

	private void setSpelers(List<SpelerDTO> spelers) {
		if (this.spelers.size() < 3 || this.spelers.size() > 4)
			throw new IllegalArgumentException(
					String.format("Er moeten %d of %d spelers meespelen", MIN_AANTAL_SPELERS, MAX_AANTAL_SPELER));
		this.spelers = this.spelers;
	}

	public static List<Kleur> toonBeschikbareKleuren() {
		return Kleur.geefKleuren();
	}

	public void start() {

	}
	// Er moet nog toegevoegd worden dat er maar 36 tegels gebruikt worden als er 3
	// spelers zijn. Moet niet gebeuren in deze klasse maar kan opzich wel. Mss best
	// in spel.java
}
