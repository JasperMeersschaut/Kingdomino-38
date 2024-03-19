
package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import dto.SpelerDTO;
import utils.Kleur;

public class SpelMenu {

	private final ResourceBundle messages;
	private final DomeinController dc;
	private final Scanner scanner;

	public SpelMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages");
		this.dc = dc;
		scanner = new Scanner(System.in);
	}

	public void maakSpel() {
		dc.maakSpel();
		for (int index = 1; index <= 4; index++)
			if (index == 4) {
				System.out.print(messages.getString("want_to_add_player"));
				String antwoord = scanner.nextLine();
				if (!antwoord.equals(messages.getString("yes")))
					break;
				else
					vulSpelerEnKleurAan();
			} else
				vulSpelerEnKleurAan();
		speelSpel();
	}

	private void vulSpelerEnKleurAan() {
		String naam = null;
		boolean naamGeldig = false;
		String beschikbareSpelers = toonBeschikbareSpelers();
		do
			try {
				System.out.println(beschikbareSpelers);
				System.out.println(messages.getString("give_name_added_player"));
				naam = scanner.nextLine();
				if (!dc.isSpelerGeldig(naam))
					throw new IllegalArgumentException(messages.getString("user_already_chosen"));
				naamGeldig = true;
			}
			catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			}
			catch (InputMismatchException ime) {
				System.err.println(messages.getString("user_doesnt_exist"));
				scanner.nextLine();
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
				scanner.nextLine();
			}
		while (!naamGeldig);
		Kleur kleur = null;
		boolean kleurGeldig = false;
		do
			try {
				System.out.println(toonBeschikbareKleuren());
				System.out.println(messages.getString("give_player_colour"));
				switch (scanner.nextLine()) {
					case "groen" -> kleur = Kleur.GROEN;
					case "blauw" -> kleur = Kleur.BLAUW;
					case "roos" -> kleur = Kleur.ROOS;
					case "geel" -> kleur = Kleur.GEEL;
					default -> kleur = null;
				}
				if (kleur == null)
					throw new IllegalArgumentException(messages.getString("choose_valid_colour"));
				if (!dc.isKleurGeldig(kleur))
					throw new IllegalArgumentException(messages.getString("colour_cant_be_picked"));
				kleurGeldig = true;
			}
			catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			}
			catch (InputMismatchException ime) {
				System.err.println(messages.getString("invalid_input"));
				scanner.nextLine();
			}
			catch (Exception e) {
				System.err.println(messages.getString("error_occurred"));
				scanner.nextLine();
			}
		while (!kleurGeldig);
		dc.voegSpelerToe(naam, kleur);
	}

	private String toonBeschikbareSpelers() {
		String beschikbareSpelers = "";
		beschikbareSpelers += (messages.getString("available_existing_players") + "\n");
		beschikbareSpelers += ("=".repeat(messages.getString("available_existing_players").length()) + "\n");
		for (SpelerDTO speler : dc.geefBeschikbareSpelers())
			beschikbareSpelers += ("- " + speler + "\n");
		beschikbareSpelers += ("=".repeat(messages.getString("available_existing_players").length()) + "\n");
		return beschikbareSpelers;
	}

	private String toonBeschikbareKleuren() {
		String beschikbareKleuren = "";
		beschikbareKleuren += (messages.getString("available_colours") + "\n");
		beschikbareKleuren += ("=".repeat(messages.getString("available_colours").length()) + "\n");
		for (Kleur kleur : dc.geefBeschikbareKleuren())
			beschikbareKleuren += ("- " + kleur + "\n");
		beschikbareKleuren += ("=".repeat(messages.getString("available_colours").length()) + "\n");
		return beschikbareKleuren;
	}

	private void speelSpel() {
		dc.initialiseerStapels();
		vulSpelersOpBord();
		do
			speelRonde();
		while (!dc.DR_EINDE_SPEL());
		System.out.printf("De winnaar van dit spel is %s!%n%n", dc.geefWinnaar());
	}

	private void vulSpelersOpBord() {
		boolean gekozen;
		for (int i = 0; i < dc.geefAantalSpelers(); i++) {
			gekozen = false;
			dc.kiesVolgendeSpeler();
			do
				try {
					System.out.println(dc.toonSpelOverzicht());
					System.out.println(toonSpelerKeuze());
					int nr = scanner.nextInt();
					dc.kiesTegelStartkolom(nr);
					gekozen = true;
				}
				catch (IllegalArgumentException ie) {
					System.err.println(ie.getMessage());
				}
				catch (Exception e) {
					System.err.println(messages.getString("error_occured"));
				}
			while (!gekozen);
		}
	}

	private String toonSpelerKeuze() {
		return String.format(messages.getString("turn_to_choose_tiles") + messages.getString("give_number_chosen_tile"),
				dc.geefHuidigeSpeler());
	}

	private void speelRonde() {
		dc.vulStartKolomAan();
		System.out.println(dc.toonSpelOverzicht());
		System.out.println("speelt ronde...\n");
		for (int index = 0; index <= dc.geefAantalSpelers(); index++)
			System.out.println("speelt beurt...\n");
	}

}
