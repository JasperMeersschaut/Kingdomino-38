
package cui;

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

	public SpelSpelenMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		scanner = new Scanner(System.in);
		this.dc = dc;
	}

	public void speelSpel() {
		plaatsKoningenOpTegels();
		do {
			dc.vulKolommenAan();
			plaatsKoningenOpTegels();
		} while (!dc.geefStapel().isEmpty());
		dc.vulKolommenAan();
		plaatsKoningenOpTegels();
		System.out.println(toonScoreOverzicht());
	}

	public void plaatsKoningenOpTegels() {
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
					dc.gooiWeg(tegel, huidigeSpeler);
					System.out.println(messages.getString("tile_discarded"));
					break;
				}
				System.out.print(messages.getString("which_direction_to_place") + " ");
				richting = scanner.nextInt();
				if (richting < 1 || richting > 4)
					throw new OngeldigeRichtingException(messages.getString("invalid_direction_number"));
				dc.legTegelInKoninkrijk(tegel, huidigeSpeler, plaats, richting);
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
		String overzichtSpelers = "";
		List<SpelerDTO> spelers = dc.geefSpelers();
		for (SpelerDTO speler : spelers)
			overzichtSpelers += String.format(messages.getString("kingdom"), speler.gebruikersnaam(), speler.kleur());
		return overzichtSpelers;
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
		String stapelNummers = "";
		Collections.reverse(stapel);
		for (TegelDTO tegel : stapel)
			stapelNummers += String.format("%d - ", tegel.nummer());
		stapelNummers = stapelNummers.substring(0, stapelNummers.length() - 3) + "\n\n";
		return stapelNummers;
	}

	private String toonKolom(List<TegelDTO> kolom) {
		String overzichtKolom = "";
		if (kolom.isEmpty())
			return "\n";
		for (TegelDTO tegel : kolom)
			overzichtKolom += toonTegel(tegel) + "\n";
		return overzichtKolom;
	}

	private String toonKoninkrijk(SpelerDTO huidigeSpeler) {
		String koninkrijkString = "  ";
		VakDTO[][] koninkrijk = dc.geefKoninkrijk(huidigeSpeler);
		for (int index = 0; index < koninkrijk.length; index++)
			koninkrijkString += (" ".repeat(5) + index + " ".repeat(4));
		koninkrijkString += "\n  " + (" " + "-".repeat(9)).repeat(koninkrijk[0].length) + "\n";
		int index = 0;
		for (VakDTO[] rij : koninkrijk) {
			koninkrijkString += "  " + ("|" + " ".repeat(9)).repeat(koninkrijk[0].length) + "|\n"
					+ String.format("%d", index++) + " |";
			for (VakDTO vak : rij)
				if (vak.landschap() == null)
					koninkrijkString += " ".repeat(9) + "|";
				else
					if (vak.landschap().equalsIgnoreCase(Landschap.KASTEEL.toString()))
						koninkrijkString += String.format(" %7s |", Landschap.KASTEEL);
					else
						if (vak.landschap().equalsIgnoreCase(Landschap.LEEG.toString()))
							koninkrijkString += " ".repeat(4) + "X" + " ".repeat(4) + "|";
						else
							koninkrijkString += String.format(" %5s %d |", vak.landschap(), vak.aantalKronen());
			koninkrijkString += "\n  |" + (" ".repeat(9) + "|").repeat(koninkrijk[0].length) + "\n  "
					+ (" " + "-".repeat(9)).repeat(koninkrijk[0].length) + "\n";
		}
		return koninkrijkString;
	}

	private String toonScoreOverzicht() {
		String scoreOverzicht = "";
		try {
			List<List<Integer>> scores = dc.geefScoreOverzicht();
			List<SpelerDTO> winnaars = dc.geefWinnaars();
			List<SpelerDTO> spelers = dc.geefSpelers();
			int grootsteLengte = 0;
			for (SpelerDTO speler : spelers)
				if (speler.gebruikersnaam().length() > grootsteLengte)
					grootsteLengte = speler.gebruikersnaam().length();
			System.out.print(toonMenuTitel(messages.getString("scores")));
			System.out.printf("%8s%20s%20s%19s%n", messages.getString("players"), messages.getString("amount_played"),
					messages.getString("amount_won"), messages.getString("winner"));
			for (int index = 0; index < spelers.size(); index++)
				scoreOverzicht += String.format("%s%s%12s%20s%28s%n",
						" ".repeat(grootsteLengte - spelers.get(index).gebruikersnaam().length()),
						spelers.get(index).gebruikersnaam(), scores.get(0).get(index), scores.get(1).get(index),
						winnaars.contains(spelers.get(index)) ? "Gewonnen!" : "");
			scoreOverzicht += "\n";
		}
		catch (RuntimeException re) {
			System.err.println(messages.getString("no_connection"));
			scanner.nextLine();
			scoreOverzicht = "";
		}
		catch (Exception e) {
			System.err.println(messages.getString("error_occurred"));
			scanner.nextLine();
			scoreOverzicht = "";
		}
		return scoreOverzicht;
	}

}
