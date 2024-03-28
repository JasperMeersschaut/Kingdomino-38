
package cui;

import java.util.*;

import domein.DomeinController;
import dto.SpelerDTO;
import dto.TegelDTO;
import utils.Kleur;
import utils.Landschap;

public class SpelSpelenMenu {

	private final ResourceBundle messages;
	private final Scanner scanner;
	private DomeinController dc;

	public SpelSpelenMenu(DomeinController dc, SpelersToevoegenMenu spelMakenMenu) {
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
		scanner = new Scanner(System.in);
		this.dc = dc;
	}

	public void speelSpel() {
		boolean stapelsAangevuld = false;
		try {
			dc.speelSpel();
			stapelsAangevuld = true;
		}
		catch (IllegalArgumentException iae) {
			System.err.println(iae.getMessage());
		}
		catch (Exception e) {
			System.err.println(messages.getString("error_occurred"));
		}
		if (stapelsAangevuld) {
			System.out.println(toonSpelOverzicht());
			plaatsKoningenOpTegels();
			speelRondes();
			toonScoreOverzicht();
		}
	}

	private void plaatsKoningenOpTegels() {
		int aantalSpelers = dc.geefSpelers().size();
		int aantalKoningenGeplaatst = 0;
		int gekozenTegel;
		do
			try {
				System.out.printf(messages.getString("player_place_king") + " ", dc.geefHuidigeSpeler().gebruikersnaam());
				gekozenTegel = scanner.nextInt();
				dc.plaatsKoningOpTegel(gekozenTegel);
				System.out.println(toonSpelOverzicht());
				aantalKoningenGeplaatst++;
			}
			catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			}
			catch (InputMismatchException ime) {
				System.err.println(messages.getString("enter_number"));
				scanner.nextLine();
			}
			catch (Exception e) {
				System.err.println(messages.getString("error_occurred"));
				scanner.nextLine();
			}
		while (aantalKoningenGeplaatst != aantalSpelers);
	}

	private String toonSpelOverzicht() {
		String overzicht = "";
		overzicht += toonMenuTitel(messages.getString("players"));
		overzicht += toonSpelers();
		overzicht += "\n" + toonMenuTitel(messages.getString("stack"));
		overzicht += toonStapel();
		overzicht += "\n" + toonMenuTitel(messages.getString("startcolumn"));
		overzicht += toonKolom(dc.geefStartKolom());
		overzicht += "\n" + toonMenuTitel(messages.getString("endcolumn"));
		overzicht += toonKolom(dc.geefEindKolom());
		return overzicht;
	}

	private String toonMenuTitel(String menuTitel) {
		String titel = menuTitel + "\n";
		titel += "=".repeat(menuTitel.length()) + "\n";
		return titel;
	}

	private String toonSpelers() {
		TegelDTO tegelSpeler;
		String overzichtSpelers = "";
		List<SpelerDTO> spelers = dc.geefSpelers();
		for (SpelerDTO speler : spelers) {
			tegelSpeler = geefTegelVanSpeler(speler);
			overzichtSpelers += String.format(messages.getString("kingdom"), speler.gebruikersnaam(),
					speler.kleur().toString(),
					tegelSpeler == null ? messages.getString("king_not_placed") : toonTegel(tegelSpeler));
		}
		return overzichtSpelers;
	}

	private TegelDTO geefTegelVanSpeler(SpelerDTO speler) {
		TegelDTO tegelSpeler = null;
		List<TegelDTO> tegelsInKolommen = new ArrayList<>();
		tegelsInKolommen.addAll(dc.geefStartKolom());
		tegelsInKolommen.addAll(dc.geefEindKolom());
		for (TegelDTO tegel : tegelsInKolommen)
			if (tegel.gebruikersnaam() != null && tegel.kleur() == speler.kleur())
				tegelSpeler = tegel;
		return tegelSpeler;
	}

	private String toonStapel() {
		List<TegelDTO> stapel = dc.geefStapel();
		if (stapel.isEmpty())
			return messages.getString("empty_stack");
		String stapelNummers = "";
		for (TegelDTO tegel : stapel)
			stapelNummers += String.format("%d - ", tegel.nummer());
		stapelNummers = stapelNummers.substring(0, stapelNummers.length() - 3) + "\n\n";
		return stapelNummers;
	}

	private String toonKolom(List<TegelDTO> kolom) {
		String overzichtKolom = "";
		for (TegelDTO tegel : kolom)
			overzichtKolom += toonTegel(tegel);
		return overzichtKolom;
	}

	private String toonTegel(TegelDTO tegel) {
		String tegelVak = "";
		boolean heeftKoning = false;
		String spelerOpTegel = null;
		Kleur kleur = null;
		if (tegel.gebruikersnaam() != null) {
			heeftKoning = true;
			spelerOpTegel = tegel.gebruikersnaam();
			kleur = tegel.kleur();
		}
		int nummer = tegel.nummer();
		Landschap lLinks = tegel.landschapLinks();
		Landschap lRechts = tegel.landschapRechts();
		int aantalKronenLinks = tegel.aantalKronenLinks();
		int aantalKronenRechts = tegel.aantalKronenRechts();
		tegelVak += String.format(" " + messages.getString("overview_show_tile"), nummer, heeftKoning ? "K" : " ",
				spelerOpTegel == null ? messages.getString("tile_not_chosen")
						: String.format(messages.getString("player"), spelerOpTegel, kleur.toString()),
				lLinks.toString(), aantalKronenLinks, lRechts.toString(), aantalKronenRechts);
		return tegelVak;
	}

	private void speelRondes() {
		do {
			dc.speelRonde();
			System.out.println(toonSpelOverzicht());
			plaatsKoningenOpTegels();
		} while (!dc.geefStapel().isEmpty());
	}

	private void toonScoreOverzicht() {
		List<Integer> scores = dc.toonScoreOverzicht();
		List<SpelerDTO> winnaars = dc.geefWinnaars();
		List<SpelerDTO> spelers = dc.geefSpelers();
		int grootsteLengte = 0;
		for (SpelerDTO speler : spelers)
			if (speler.gebruikersnaam().length() > grootsteLengte)
				grootsteLengte = speler.gebruikersnaam().length();
		System.out.print(toonMenuTitel(messages.getString("scores")));
		System.out.printf("%8s%20s%20s%19s%n", messages.getString("players"),messages.getString("amount_played"), messages.getString("amount_won"),
				messages.getString("winner"));

		for (int index = 0; index < spelers.size(); index++)
			System.out.printf("%s%s%17s%17s%23s%n", " ".repeat(grootsteLengte - spelers.get(index).gebruikersnaam().length()),
					spelers.get(index).gebruikersnaam(),
					scores.get(2*index),scores.get(2*index+1),
					winnaars.contains(spelers.get(index)) ? "Gewonnen!" : "");
	}

}
