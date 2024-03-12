
package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import DTO.SpelerDTO;
import utils.Kleur;

public class Spel {

	private DomeinController dc = new DomeinController();
	Scanner scanner = new Scanner(System.in);
	List<SpelerDTO> spelers;
	private List<Tegel> stapel;
	private List<Tegel> startKolom;
	private List<Tegel> eindKolom;
	private TegelRepository tegelRepository = new TegelRepository();
	private static final int MIN_AANTAL_SPELERS = 3;
	private static final int MAX_AANTAL_SPELER = 4;

	public Spel(List<SpelerDTO> spelers) {
		setSpelers(spelers);
		stapel = tegelRepository.geeftegels();
		startKolom = new ArrayList<>();
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

	public void vulStartKolomAan() {
		int aantal = spelers.size();
		for (int index = 1; index <= aantal; index++) {
			startKolom.add(stapel.getLast());
			stapel.removeLast();
		}
		Collections.sort(stapel);
	}

}
