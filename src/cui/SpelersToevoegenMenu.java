
package cui;

import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import dto.SpelerDTO;
import utils.Taal;

public class SpelersToevoegenMenu {

	private final ResourceBundle messages;
	private final Scanner scanner;
	private final DomeinController dc;

	public SpelersToevoegenMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		scanner = new Scanner(System.in);
		this.dc = dc;
	}

	public void voegSpelerToe() {
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
				System.out.println();
				dc.voegSpelerToeAanSpel(gebruikersnaam, kleur);
				spelerGeldig = true;
			}
			catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			}
			catch (RuntimeException re) {
				System.err.println(messages.getString("no_connection"));
				scanner.nextLine();
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
			beschikbareSpelers += String.format("- %s (%d)%n", speler.gebruikersnaam(), speler.geboortejaar());
		beschikbareSpelers += ("=".repeat(messages.getString("available_existing_players").length()) + "\n");
		return beschikbareSpelers;
	}

	private String toonBeschikbareKleuren() {
		String beschikbareKleuren = "";
		beschikbareKleuren += (messages.getString("available_colours") + "\n");
		beschikbareKleuren += ("=".repeat(messages.getString("available_colours").length()) + "\n");
		for (String kleur : dc.geefBeschikbareKleuren())
			beschikbareKleuren += ("- " + kleur + "\n");
		beschikbareKleuren += ("=".repeat(messages.getString("available_colours").length()) + "\n");
		return beschikbareKleuren;
	}

}
