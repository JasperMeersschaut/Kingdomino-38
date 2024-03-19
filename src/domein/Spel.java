
package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import dto.SpelerDTO;
import dto.TegelDTO;
import utils.Kleur;
import utils.Landschap;
import utils.TegelMaker;

public class Spel {

	private final List<SpelerDTO> spelers;
	private SpelerDTO huidigeSpeler;
	private final TegelMaker tegelMaker;
	private List<TegelDTO> stapel;
	private final List<TegelDTO> startKolom;
	private final List<TegelDTO> eindKolom;
	private final ResourceBundle messages;

	public Spel() {
		messages = ResourceBundle.getBundle("messages");
		spelers = new ArrayList<>();
		tegelMaker = new TegelMaker();
		stapel = new ArrayList<>();
		startKolom = new ArrayList<>();
		eindKolom = new ArrayList<>();
	}

	public void voegSpelerToe(SpelerDTO speler) {
		spelers.add(speler);
	}

	public List<SpelerDTO> getSpelers() {
		return spelers;
	}

	public List<TegelDTO> getStapel() {
		return stapel;
	}

	public SpelerDTO getHuidigeSpeler() {
		return huidigeSpeler;
	}

	public void kiesVolgendeSpeler() {
		if (huidigeSpeler == null || spelers.indexOf(huidigeSpeler) == spelers.size())
			huidigeSpeler = spelers.get(0);
		else
			huidigeSpeler = spelers.get(spelers.indexOf(huidigeSpeler) + 1);
	}

	public List<SpelerDTO> geefBeschikbareSpelers(List<SpelerDTO> alleSpelers) {
		List<SpelerDTO> beschikbareSpelers = new ArrayList<>(alleSpelers);
		for (SpelerDTO speler : spelers)
			for (int index = 0; index < beschikbareSpelers.size(); index++)
				if (beschikbareSpelers.get(index).equals(speler))
					beschikbareSpelers.remove(beschikbareSpelers.get(index));
		return beschikbareSpelers;
	}

	public List<Kleur> geefBeschikbareKleuren() {
		List<Kleur> beschikbareKleuren = new ArrayList<>(Arrays.asList(Kleur.values()));
		for (SpelerDTO speler : spelers)
			beschikbareKleuren.remove(speler.kleur());
		return beschikbareKleuren;
	}

	public void vulStapelAan() {
		List<TegelDTO> tegelDTOS = new ArrayList<>();
		for (Tegel tegel : tegelMaker.geeftegels())
			tegelDTOS.add(new TegelDTO(tegel, null));
		Collections.shuffle(tegelDTOS);
		stapel = tegelDTOS.subList(0, spelers.size() * 12);
	}

	public void vulStartKolomAan() {
		startKolom.clear();
		for (TegelDTO tegel : eindKolom)
			startKolom.add(tegel);
		eindKolom.clear();
		for (int index = 1; index <= spelers.size(); index++) {
			eindKolom.add(stapel.get(0));
			stapel.remove(0);
		}
		Collections.sort(eindKolom);
	}

	public void vulEindKolomAan() {
		for (int index = 1; index <= spelers.size(); index++) {
			eindKolom.add(stapel.get(0));
			stapel.remove(0);
		}
		Collections.sort(eindKolom);
	}

	public void initialiseerStapels() {
		vulStapelAan();
		vulEindKolomAan();
		vulStartKolomAan();
		Collections.shuffle(spelers);
	}

	public String toonSpelOverzicht() {
		String overzicht = "";
		overzicht += toonMenuTitel(messages.getString("players"));
		overzicht += toonSpelers();
		overzicht += "\n" + toonMenuTitel("Stapel:");
		overzicht += toonStapel();
		overzicht += "\n" + toonMenuTitel(messages.getString("startcolumn"));
		overzicht += toonKolom(startKolom);
		overzicht += "\n" + toonMenuTitel(messages.getString("endcolumn"));
		overzicht += toonKolom(eindKolom);
		return overzicht;
	}

	private String toonMenuTitel(String menuTitel) {
		String titel = menuTitel + "\n";
		titel += "=".repeat(menuTitel.length()) + "\n";
		return titel;
	}

	private String toonSpelers() {
		TegelDTO tegelSpelerStartKolom;
		TegelDTO tegelSpelerEindKolom;
		String overzichtSpelers = "";
		for (SpelerDTO speler : spelers) {
			tegelSpelerStartKolom = geefTegelVanSpeler(speler, startKolom);
			tegelSpelerEindKolom = geefTegelVanSpeler(speler, eindKolom);
			overzichtSpelers += String.format(messages.getString("kingdom"), speler.speler().getGebruikersnaam(),
					speler.kleur().toString(),
					tegelSpelerStartKolom == null
							? (tegelSpelerEindKolom == null ? messages.getString("king_not_placed")
									: messages.getString("in_endcolumn") + toonTegel(tegelSpelerEindKolom))
							: messages.getString("in_startcolumn") + toonTegel(tegelSpelerStartKolom));
		}
		return overzichtSpelers;
	}

	private String toonStapel() {
		if (stapel.isEmpty())
			return "De stapel is leeg";
		String stapel = "";
		for (TegelDTO tegel : this.stapel) {
			stapel += String.format("%d", tegel.tegel().getNummer());
			if (this.stapel.get(this.stapel.size() - 1) != tegel)
				stapel += " - ";
		}
		stapel += "\n\n";
		return stapel;
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
		SpelerDTO spelerOpTegel = null;
		Kleur kleur = null;
		if (tegelDTO.spelerDTO() != null) {
			heeftKoning = true;
			spelerOpTegel = tegelDTO.spelerDTO();
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
		tegelVak += String.format(messages.getString("overview_show_tile"), nummer, heeftKoning ? "K" : " ",
				spelerOpTegel == null ? messages.getString("tile_notyet_chosen")
						: String.format(messages.getString("player"), spelerOpTegel, kleur.toString()),
				lLinks.toString(), aantalKronenLinks, lRechts.toString(), aantalKronenRechts);
		return tegelVak;
	}

	public void kiesTegelStartkolom(int nr) {
		TegelDTO tegel;
		boolean aangepast = false;
		for (int i = 0; i < startKolom.size(); i++) {
			tegel = startKolom.get(i);
			if (tegel.tegel().getNummer() == nr)
				if (tegel.spelerDTO() == null) {
					startKolom.set(i, new TegelDTO(tegel.tegel(), huidigeSpeler));
					aangepast = true;
				} else
					throw new IllegalArgumentException(messages.getString("already_taken_tile") + tegel.spelerDTO());
		}
		if (!aangepast)
			throw new IllegalArgumentException("Ongeldig tegelnummer!");
	}

	public int berekenScore(SpelerDTO speler) {
		int score = 0;
		// bereken de score
		return score;
	}

}
