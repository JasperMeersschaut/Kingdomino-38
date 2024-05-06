
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

	/**
	 * Constructor voor het SpelersToevoegenMenu.
	 *
	 * @param dc de domeincontroller.
	 */
	public SpelersToevoegenMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		scanner = new Scanner(System.in);
		this.dc = dc;
		voegSpelerToe();
	}

	private void voegSpelerToe() {
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
		while (!spelerGeldig);
	}

	private String toonBeschikbareSpelers() {
		StringBuilder beschikbareSpelers = new StringBuilder();
		beschikbareSpelers.append(messages.getString("available_existing_players")).append("\n");
		beschikbareSpelers.append("=".repeat(messages.getString("available_existing_players").length())).append("\n");
		for (SpelerDTO speler : dc.geefBeschikbareSpelers())
			beschikbareSpelers.append(String.format("- %s (%d)%n", speler.gebruikersnaam(), speler.geboortejaar()));
		beschikbareSpelers.append("=".repeat(messages.getString("available_existing_players").length())).append("\n");
		return beschikbareSpelers.toString();
	}

	private String toonBeschikbareKleuren() {
		StringBuilder beschikbareKleuren = new StringBuilder();
		beschikbareKleuren.append(messages.getString("available_colours")).append("\n");
		beschikbareKleuren.append("=".repeat(messages.getString("available_colours").length())).append("\n");
		for (String kleur : dc.geefBeschikbareKleuren())
			beschikbareKleuren.append("- ").append(kleur).append("\n");
		beschikbareKleuren.append("=".repeat(messages.getString("available_colours").length())).append("\n");
		return beschikbareKleuren.toString();
	}

}
