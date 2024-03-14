
package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import dto.SpelerDTO;
import dto.TegelDTO;
import utils.Kleur;
import utils.Landschap;
import utils.TegelMaker;

public class Spel {

	private List<SpelerDTO> spelers;
	private TegelMaker tegelMaker = new TegelMaker();
	private List<Tegel> stapel;
	private List<TegelDTO> startKolom = new ArrayList<>();
	private List<TegelDTO> eindKolom = new ArrayList<>();
	private static final int MIN_AANTAL_SPELERS = 3;
	private static final int MAX_AANTAL_SPELER = 4;
	private ResourceBundle messages;

	public Spel(List<SpelerDTO> spelers) {
		setSpelers(spelers);
		stapel = shuffle(tegelMaker.geeftegels());
		vulKolomAan(startKolom);
		vulKolomAan(eindKolom);
		messages = ResourceBundle.getBundle("messages");
	}

	private void setSpelers(List<SpelerDTO> spelers) {
		if (spelers.size() != 3 && spelers.size() != 4)
			throw new IllegalArgumentException(
					String.format(messages.getString("nrplayers_toplay"), MIN_AANTAL_SPELERS, MAX_AANTAL_SPELER));
		this.spelers = spelers;
	}

	private List<Tegel> shuffle(List<Tegel> tegels) {
		Collections.shuffle(tegels);
		return tegels.subList(0, spelers.size() * 12);
	}

	private void vulKolomAan(List<TegelDTO> kolom) {
		List<Tegel> kolomTegels = new ArrayList<>();
		for (int index = 1; index <= spelers.size(); index++) {
			kolomTegels.add(stapel.get(0));
			stapel.remove(0);
		}
		Collections.sort(kolomTegels);
		for (Tegel tegel : kolomTegels)
			kolom.add(new TegelDTO(tegel, null));
	}

	public String toonSpelOverzicht() {
		String overzicht = "";
		overzicht += toonMenuTitel("Spelers:");
		overzicht += toonSpelers();
		overzicht += "\n" + toonMenuTitel("Startkolom:");
		overzicht += toonKolom(startKolom);
		overzicht += "\n" + toonMenuTitel("Eindkolom:");
		overzicht += toonKolom(eindKolom);
		return overzicht;
	}

	private String toonMenuTitel(String menuTitel) {
		String titel = menuTitel + "\n";
		titel += "=".repeat(menuTitel.length()) + "\n";
		return titel;
	}

	private String toonSpelers() {
		TegelDTO tegelSpelerStartKolom = null;
		TegelDTO tegelSpelerEindKolom = null;
		String overzichtSpelers = "";
		for (SpelerDTO speler : spelers) {
			tegelSpelerStartKolom = geefTegelVanSpeler(speler, startKolom);
			tegelSpelerEindKolom = geefTegelVanSpeler(speler, eindKolom);
			overzichtSpelers += String.format("%s - %s%nKoninkrijk:%n%s", speler.speler().getGebruikersnaam(),
					speler.kleur().toString(),
					tegelSpelerStartKolom == null
							? (tegelSpelerEindKolom == null ? "Koning nog niet geplaatst\n\n"
									: "Koning in eindkolom:\n" + toonTegel(tegelSpelerEindKolom))
							: "Koning in startkolom:\n" + toonTegel(tegelSpelerStartKolom));
		}
		return overzichtSpelers;
	}

	private TegelDTO geefTegelVanSpeler(SpelerDTO speler, List<TegelDTO> kolom) {
		TegelDTO tegelSpeler = null;
		for (TegelDTO tegel : kolom)
			if (tegel.spelerDTO() != null && tegel.spelerDTO().kleur() == speler.kleur())
				tegelSpeler = tegel;
		return tegelSpeler;
	}

	private String toonKolom(List<TegelDTO> kolom) {
		String overzichtKolom = "";
		for (int index = 1; index <= spelers.size(); index++)
			overzichtKolom += toonTegel(kolom.get(index - 1));
		return overzichtKolom;
	}

	private String toonTegel(TegelDTO tegelDTO) {
		String tegelVak = "";
		boolean heeftKoning = false;
		Speler spelerOpTegel = null;
		Kleur kleur = null;
		if (tegelDTO.spelerDTO() != null) {
			heeftKoning = tegelDTO.spelerDTO() != null;
			spelerOpTegel = tegelDTO.spelerDTO().speler();
			kleur = tegelDTO.spelerDTO().kleur();
		}
		Tegel tegel = tegelDTO.tegel();
		int nummer = tegel.getNummer();
		Vak vLinks = tegel.getVakLinks();
		Vak vRechts = tegel.getVakRechts();
		Landschap lLinks = vLinks.getLandschap();
		Landschap lRechts = vRechts.getLandschap();
		int aantalKronenLinks = vLinks.getAantalKronen();
		int aantalKronenRechts = vRechts.getAantalKronen();
		tegelVak += String.format(
				" ------- %n|       | Nummer: %d%n|   %s   | %s%n|       | %s %d - %s %d%n ------- %n%n", nummer,
				heeftKoning ? "K" : " ",
				spelerOpTegel == null ? "Tegel is nog niet gekozen."
						: String.format("Speler: %s - %s", spelerOpTegel.getGebruikersnaam(), kleur.toString()),
				lLinks.toString(), aantalKronenLinks, lRechts.toString(), aantalKronenRechts);
		return tegelVak;
	}

	public void kiesTegelStartkolom(SpelerDTO speler, int nr) {
		TegelDTO tegel = null;
		boolean aangepast = false;
		for (int i = 0; i < startKolom.size(); i++) {
			boolean tegelGeldig = false;
			tegel = startKolom.get(i);
			if (tegel.tegel().getNummer() == nr)
				if (tegel.spelerDTO() == null) {
					startKolom.set(i, new TegelDTO(tegel.tegel(), speler));
					aangepast = true;
				} else {
					i--;
					throw new IllegalArgumentException(
							messages.getString("already_taken_tile") + tegel.spelerDTO().speler().getGebruikersnaam());
				}
		}
		if (!aangepast)
			throw new IllegalArgumentException("Ongeldig tegelnummer!");
	}

	public String toonSpelerKeuze(SpelerDTO speler) {
		return String.format(messages.getString("turn_to_choose_tiles") + messages.getString("give_number_chosen_tile"),
				speler.speler().getGebruikersnaam());
	}

}
