
package cui;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import dto.SpelerDTO;
import utils.Kleur;

public class SpelersToevoegenMenu {

	private final ResourceBundle messages;
	private final Scanner scanner;
	private final DomeinController dc;
	private final SpelSpelenMenu spelSpelenMenu;

	public SpelersToevoegenMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
		scanner = new Scanner(System.in);
		this.dc = dc;
		spelSpelenMenu = new SpelSpelenMenu(dc, this);
	}

	public void startNieuwSpel() {
		dc.maakSpelAan();
		int keuze;
		do {
			keuze = vraagKeuzeMetSpelMenu();
			System.out.println();
			switch (keuze) {
				case 1 -> voegSpelersToe();
				case 2 -> spelSpelenMenu.speelSpel();
			}
		} while (keuze != 3);
	}

	private int vraagKeuzeMetSpelMenu() {
		int keuze = 0;
		boolean keuzeGeldig = false;
		do
			try {
				System.out.print(messages.getString("game_menu") + " ");
				keuze = scanner.nextInt();
				keuzeGeldig = keuze >= 1 && keuze <= 3;
				if (!keuzeGeldig)
					throw new IllegalArgumentException(messages.getString("choose_valid_number"));
				scanner.nextLine();
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
		while (!keuzeGeldig);
		return keuze;
	}

	private void voegSpelersToe() {
		String gebruikersnaam;
		String kleur;
		boolean spelerGeldig = false;
		do
			try {
				System.out.println(toonBeschikbareSpelers());
				System.out.print(messages.getString("give_name_player_to_add") + " ");
				gebruikersnaam = scanner.nextLine();
				System.out.println(toonBeschikbareKleuren());
				System.out.print(messages.getString("give_color_to_add") + " ");
				kleur = scanner.nextLine();
				dc.voegSpelerToeAanSpel(gebruikersnaam, kleur);
				spelerGeldig = true;
			}
			catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			}
			catch (Exception e) {
				System.err.println(messages.getString("error_occurred"));
				scanner.nextLine();
			}
		while (!spelerGeldig);
	}

	private String toonBeschikbareSpelers() {
		String beschikbareSpelers = "";
		beschikbareSpelers += (messages.getString("available_existing_players") + "\n");
		beschikbareSpelers += ("=".repeat(messages.getString("available_existing_players").length()) + "\n");
		for (SpelerDTO speler : dc.geefBeschikbareSpelers())
			beschikbareSpelers += ("- " + speler.gebruikersnaam() + "\n");
		beschikbareSpelers += ("=".repeat(messages.getString("available_existing_players").length()) + "\n");
		return beschikbareSpelers;
	}

	private String toonBeschikbareKleuren() {
		String beschikbareKleuren = "";
		beschikbareKleuren += (messages.getString("available_colours") + "\n");
		beschikbareKleuren += ("=".repeat(messages.getString("available_colours").length()) + "\n");
		for (Kleur kleur : dc.geefBeschikbareKleuren())
			beschikbareKleuren += ("- " + kleur.toString() + "\n");
		beschikbareKleuren += ("=".repeat(messages.getString("available_colours").length()) + "\n");
		return beschikbareKleuren;
	}

}
