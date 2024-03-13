package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import DTO.SpelerDTO;
import DTO.TegelDTO;
import utils.Kleur;
import utils.Landschap;

public class Spel {

	private List<SpelerDTO> spelers;
	private List<Tegel> stapel;
	private List<TegelDTO> startKolom = new ArrayList<>();
	private List<TegelDTO> eindKolom = new ArrayList<>();
	private TegelRepository tegelRepository = new TegelRepository();
	private static final int MIN_AANTAL_SPELERS = 3;
	private static final int MAX_AANTAL_SPELER = 4;
	private ResourceBundle messages;
	Scanner scanner = new Scanner(System.in);

	public Spel(List<SpelerDTO> spelers) {
		setSpelers(spelers);
		stapel = shuffle(tegelRepository.geeftegels());
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
		overzicht += toonMenuTitel(messages.getString("players"));
		overzicht += toonSpelers();
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
		TegelDTO tegelSpeler = null;
		String overzichtSpelers = "";
		for (SpelerDTO speler : spelers) {
			int indexStartKolom = -1;
			int index = 0;
			for (TegelDTO tegel : startKolom) {
				if (tegel.spelerDTO() != null && tegel.spelerDTO().kleur() == speler.kleur()) {
					indexStartKolom = index;
					tegelSpeler = tegel;
				}
				index++;
			}
			int indexEindKolom = -1;
			index = 0;
			for (TegelDTO tegel : eindKolom) {
				if (tegel.spelerDTO() != null && tegel.spelerDTO().kleur() == speler.kleur()) {
					indexEindKolom = index;
					tegelSpeler = tegel;
				}
				index++;
			}
			overzichtSpelers += String.format(messages.getString("kingdom"), speler.speler().getGebruikersnaam(),
					speler.kleur().toString(),
					indexStartKolom == -1
							? (indexEindKolom == -1 ? messages.getString("king_not_placed")
									: messages.getString("in_endcolumn") + toonTegel(tegelSpeler))
							: messages.getString("in_startcolumn") + toonTegel(tegelSpeler));
		}
		return overzichtSpelers;
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
		Speler speler = null;
		Kleur kleur = null;
		if (tegelDTO.spelerDTO() != null) {
			heeftKoning = tegelDTO.spelerDTO() != null;
			speler = tegelDTO.spelerDTO().speler();
			kleur = tegelDTO.spelerDTO().kleur();
		}
		Tegel tegel;
		Landschap lLinks;
		Landschap lRechts;
		int aantalKronen;
		tegel = tegelDTO.tegel();
		lLinks = tegel.getLandschapLinks();
		lRechts = tegel.getLandschapRechts();
		aantalKronen = tegel.getAantalKronen();
		tegelVak += String.format(messages.getString("overview_show_tile"),
				speler == null ? messages.getString("tile_notyet_chosen")
						: String.format(messages.getString("player"), speler.getGebruikersnaam(), kleur.toString()),
				heeftKoning ? "K" : " ", lLinks.toString(), lRechts.toString(), aantalKronen);
		return tegelVak;
	}

	public void kiesWillekeurigeKoning() {
		// Alle spelers die nog geen koning plaatsten
		List<SpelerDTO> spelersZonderTegel = new ArrayList(); // maakt array voor alle spelers die nog geen tegel gelegt
																// hebben
		boolean isGeplaatst; // boolean voor of koning al geplaatst is
		for (SpelerDTO speler : spelers) {
			isGeplaatst = false;
			for (TegelDTO tegel : this.startKolom) {
				// Checken of speler al koning plaatste
				if (tegel.spelerDTO() == speler) {
					isGeplaatst = true;
					break;
				}
			}
			// Speler toevoegen aan lijst indien die nog geen koning plaatste
			if (!isGeplaatst) {
				spelersZonderTegel.add(speler);
			}
		}

		// null teruggeven indien alle koningen geplaatst zijn
		if (spelersZonderTegel.isEmpty()) {
			throw new IllegalArgumentException(messages.getString("all_kings_placed")); // buiten loop plaatsen
		}

		// Willekeurige spelerDTO teruggeven waarvan de speler nog geen koning plaatste,
		// totdat alle koningen geplaatst zijn
		Random random = new Random();
		do {
			int randomIndex = random.nextInt(spelersZonderTegel.size());
			kiesTegelStartkolom(startKolom, spelersZonderTegel.get(randomIndex));
			spelersZonderTegel.remove(randomIndex);
		} while (spelersZonderTegel.size() != 0);
	}

	public void kiesTegelStartkolom(List<TegelDTO> startkolom, SpelerDTO speler) { // moet nog ergens opgeroepen worden
		System.out.printf(messages.getString("turn_to_choose_tiles") + startkolom, speler.speler().getGebruikersnaam());
		TegelDTO tegel = null;
		boolean tegelGekozen = false;
		do {
			System.out.print(messages.getString("give_number_chosen_tile"));
			int nr = scanner.nextInt();
			for (int i = 0; i < startkolom.size(); i++) {
				tegel = startkolom.get(i);
				if (tegel.tegel().getNummer() == nr) {
					if (tegel.spelerDTO() == null) {
						startkolom.remove(i);
						startkolom.add(i, new TegelDTO(tegel.tegel(), speler));
						tegelGekozen = true;
					} else {
						System.out.println(messages.getString("already_taken_tile")
								+ tegel.spelerDTO().speler().getGebruikersnaam());
						break;
					}
				}
			}
		} while (!tegelGekozen);
	}
}
