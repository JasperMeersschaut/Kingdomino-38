
package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dto.SpelerDTO;
import dto.TegelDTO;
import utils.Landschap;

public class Spel {

	private List<SpelerDTO> spelers;
	private List<Tegel> stapel;
	private List<TegelDTO> startKolom = new ArrayList<>();
	private List<TegelDTO> eindKolom = new ArrayList<>();
	private TegelRepository tegelRepository = new TegelRepository();
	private static final int MIN_AANTAL_SPELERS = 3;
	private static final int MAX_AANTAL_SPELER = 4;

	public Spel(List<SpelerDTO> spelers) {
		setSpelers(spelers);
		stapel = shuffle(tegelRepository.geeftegels());
		startSpel();
	}

	private void setSpelers(List<SpelerDTO> spelers) {
		if (spelers.size() != 3 && spelers.size() != 4)
			throw new IllegalArgumentException(
					String.format("Er moeten %d of %d spelers meespelen", MIN_AANTAL_SPELERS, MAX_AANTAL_SPELER));
		this.spelers = spelers;
	}

	private List<Tegel> shuffle(List<Tegel> tegels) {
		Collections.shuffle(tegels);
		return tegels.subList(0, spelers.size() * 12);
	}

	private void vulStartKolomAan() {
		List<Tegel> startKolomTegels = new ArrayList<>();
		for (int index = 1; index <= spelers.size(); index++) {
			startKolomTegels.add(stapel.get(0));
			stapel.remove(0);
		}
		Collections.sort(startKolomTegels);
		for (Tegel tegel : startKolomTegels)
			startKolom.add(new TegelDTO(tegel, null));
	}

	private void startSpel() {
		vulStartKolomAan();
		toonStartSpelSituatie();
		toonSpelSituatie();
	}

	private void toonStartSpelSituatie() {
		;
	}

	private void toonSpelSituatie() {
		System.out.println("Spelers:");
		System.out.println("=======");
		for (SpelerDTO speler : spelers)
			System.out.printf("%s - %s%nKoning in %s%n%n", speler.speler().getGebruikersnaam(), speler.kleur().toString(),
					"");
		System.out.println("\nStartkolom:");
		System.out.println("===========");
		boolean heeftKoning;
		Tegel tegel;
		Landschap lLinks;
		Landschap lRechts;
		int aantalKronen;
		for (int index = 1; index <= spelers.size(); index++) {
			heeftKoning = startKolom.get(index - 1).speler() != null;
			tegel = startKolom.get(index - 1).tegel();
			lLinks = tegel.getLandschapLinks();
			lRechts = tegel.getLandschapRechts();
			aantalKronen = tegel.getAantalKronen();
			System.out.printf(
					" ------- %n|       |%n|   %s   | Landschap: %s-%s  |  Aantal kronen: %d%n|       |%n ------- %n%n",
					heeftKoning ? "K" : " ", lLinks.toString(), lRechts.toString(), aantalKronen);
		}
		System.out.println("Eindkolom:");
		System.out.println("==========");
		System.out.println("------Zelfde als eindkolom");
	}

}
