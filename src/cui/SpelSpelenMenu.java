
package cui;

import static java.lang.Integer.parseInt;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import dto.SpelerDTO;
import dto.TegelDTO;
import dto.VakDTO;
import exceptions.OngeldigeRichtingException;
import utils.Landschap;
import utils.Taal;

public class SpelSpelenMenu {

	private final ResourceBundle messages;
	private final Scanner scanner;
	private final DomeinController dc;

	/**
	 * Constructor voor het SpelSpelenMenu.
	 *
	 * @param dc de domeincontroller.
	 */
	public SpelSpelenMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		scanner = new Scanner(System.in);
		this.dc = dc;
		speelSpel();
	}

	private void speelSpel() {
		plaatsKoningenOpTegels();
		do {
			dc.vulStapelsEnKolommenAan();
			plaatsKoningenOpTegels();
		} while (!dc.geefStapel().isEmpty());
		dc.vulStapelsEnKolommenAan();
		plaatsKoningenOpTegels();
		new ScoreMenu(dc);
	}

	private void plaatsKoningenOpTegels() {
		int aantalSpelers = dc.geefSpelers().size();
		int aantalSpelersGespeeld = 0;
		int gekozenTegel;
		SpelerDTO huidigeSpeler;
		TegelDTO vorigeTegel = null;
		do
			try {
				System.out.print(toonSpelOverzicht());
				huidigeSpeler = dc.geefHuidigeSpeler();
				if (!dc.geefEindKolom().isEmpty() || !dc.geefStapel().isEmpty()) {
					System.out.printf(messages.getString("player_place_king") + " ", huidigeSpeler.gebruikersnaam());
					gekozenTegel = scanner.nextInt();
					System.out.println();
					dc.plaatsKoningOpTegel(huidigeSpeler, gekozenTegel);
				}
				if (!dc.geefEindKolom().isEmpty() || (dc.geefEindKolom().isEmpty() && dc.geefStapel().isEmpty())) {
					for (TegelDTO tegel : dc.geefStartKolom())
						if (tegel.spelerOpTegel() != null && tegel.spelerOpTegel().equals(huidigeSpeler.gebruikersnaam()))
							vorigeTegel = tegel;
					legTegelInKoninkrijk(vorigeTegel, huidigeSpeler);
				}
				aantalSpelersGespeeld++;
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
		while (aantalSpelersGespeeld != aantalSpelers);
	}

	private void legTegelInKoninkrijk(TegelDTO tegel, SpelerDTO huidigeSpeler) {
		boolean tegelGeplaatst = false;
		String plaats;
		int richting;
		do
			try {
				System.out.println(toonKoninkrijk(huidigeSpeler));
				System.out.printf(messages.getString("which_box_to_place") + " ", toonTegel(tegel));
				plaats = scanner.next();
				if (plaats.equals("weggooien") || plaats.equals("discard") || plaats.equals("défausse")) {
					dc.gooiTegelWeg(tegel, huidigeSpeler);
					System.out.println(messages.getString("tile_discarded"));
					break;
				}
				if (plaats.equals("score")) {
					new ScoreMenu(dc);
					break;
				}
				System.out.print(messages.getString("which_direction_to_place") + " ");
				richting = scanner.nextInt();
				if (richting < 1 || richting > 4)
					throw new OngeldigeRichtingException(messages.getString("invalid_direction_number"));
				dc.legTegelInKoninkrijk(tegel, huidigeSpeler, parseInt(plaats) / 10, parseInt(plaats) % 10, richting);
				System.out.println();
				tegelGeplaatst = true;
				System.out.println(toonKoninkrijk(huidigeSpeler));
			}
			catch (NumberFormatException nfe) {
				System.err.println(messages.getString("wrong_indices"));
			}
			catch (IllegalArgumentException | NullPointerException iae) {
				System.err.println(iae.getMessage());
				scanner.nextLine();
			}
			catch (IndexOutOfBoundsException ioobe) {
				System.err.println(messages.getString("not_enough_indices"));
				scanner.nextLine();
			}
			catch (Exception e) {
				System.err.println(messages.getString("error_occurred"));
				scanner.nextLine();
			}
		while (!tegelGeplaatst);
	}

	private String toonSpelOverzicht() {
		String overzicht = "";
		overzicht += toonMenuTitel(messages.getString("players"));
		overzicht += toonSpelers();
		overzicht += toonMenuTitel(messages.getString("stack"));
		overzicht += toonStapel();
		overzicht += toonMenuTitel(messages.getString("startcolumn"));
		overzicht += toonKolom(dc.geefStartKolom());
		overzicht += toonMenuTitel(messages.getString("endcolumn"));
		overzicht += toonKolom(dc.geefEindKolom());
		return overzicht;
	}

	private String toonMenuTitel(String menuTitel) {
		String titel = menuTitel + "\n";
		titel += "=".repeat(menuTitel.length()) + "\n";
		return titel;
	}

	private String toonSpelers() {
		StringBuilder overzichtSpelers = new StringBuilder();
		List<SpelerDTO> spelers = dc.geefSpelers();
		for (SpelerDTO speler : spelers)
			overzichtSpelers.append(String.format(messages.getString("kingdom"), speler.gebruikersnaam(), speler.kleur()));
		return overzichtSpelers.toString();
	}

	private String toonTegel(TegelDTO tegel) {
		String tegelVak = "";
		boolean heeftKoning = false;
		String spelerOpTegel = null;
		String kleur = null;
		if (tegel.spelerOpTegel() != null) {
			heeftKoning = true;
			spelerOpTegel = tegel.spelerOpTegel();
			kleur = tegel.kleur();
		}
		int nummer = tegel.nummer();
		String lLinks = tegel.landschapLinks();
		String lRechts = tegel.landschapRechts();
		int aantalKronenLinks = tegel.aantalKronenLinks();
		int aantalKronenRechts = tegel.aantalKronenRechts();
		tegelVak += String.format(" " + messages.getString("overview_show_tile"), nummer, heeftKoning ? "K" : " ",
				spelerOpTegel == null ? messages.getString("tile_not_chosen")
						: String.format(messages.getString("player"), spelerOpTegel, kleur),
				lLinks, aantalKronenLinks, lRechts, aantalKronenRechts);
		return tegelVak;
	}

	private String toonStapel() {
		List<TegelDTO> stapel = dc.geefStapel();
		if (stapel.isEmpty())
			return messages.getString("empty_stack");
		StringBuilder stapelNummers = new StringBuilder();
		Collections.reverse(stapel);
		for (TegelDTO tegel : stapel)
			stapelNummers.append(String.format("%d - ", tegel.nummer()));
		stapelNummers = new StringBuilder(stapelNummers.substring(0, stapelNummers.length() - 3) + "\n\n");
		return stapelNummers.toString();
	}

	private String toonKolom(List<TegelDTO> kolom) {
		StringBuilder overzichtKolom = new StringBuilder();
		if (kolom.isEmpty())
			return "\n";
		for (TegelDTO tegel : kolom)
			overzichtKolom.append(toonTegel(tegel)).append("\n");
		return overzichtKolom.toString();
	}

	private String toonKoninkrijk(SpelerDTO huidigeSpeler) {
		StringBuilder koninkrijkString = new StringBuilder("  ");
		VakDTO[][] koninkrijk = dc.geefKoninkrijk(huidigeSpeler);
		for (int index = 0; index < koninkrijk.length; index++)
			koninkrijkString.append(" ".repeat(6)).append(index).append(" ".repeat(4));
		koninkrijkString.append("\n  ").append((" " + "-".repeat(10)).repeat(koninkrijk[0].length)).append("\n");
		int index = 0;
		for (VakDTO[] rij : koninkrijk) {
			koninkrijkString.append("  ").append(("|" + " ".repeat(10)).repeat(koninkrijk[0].length)).append("|\n")
					.append(String.format("%d", index++)).append(" |");
			for (VakDTO vak : rij)
				if (vak.landschap() == null)
					koninkrijkString.append(" ".repeat(10)).append("|");
				else
					if (vak.landschap().equalsIgnoreCase(Landschap.KASTEEL.toString()))
						koninkrijkString.append(String.format(" %8s |", Landschap.KASTEEL));
					else
						if (vak.landschap().equalsIgnoreCase(Landschap.LEEG.toString()))
							koninkrijkString.append(" ".repeat(5)).append("X").append(" ".repeat(4)).append("|");
						else
							koninkrijkString.append(String.format(" %6s %d |", vak.landschap(), vak.aantalKronen()));
			koninkrijkString.append("\n  |").append((" ".repeat(10) + "|").repeat(koninkrijk[0].length)).append("\n  ")
					.append((" " + "-".repeat(10)).repeat(koninkrijk[0].length)).append("\n");
		}
		return koninkrijkString.toString();
	}

}
