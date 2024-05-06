
package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import utils.Taal;

public class HoofdMenu {

	private final ResourceBundle messages;
	private final Scanner scanner;
	private final DomeinController dc;

	/**
	 * Constructor voor het HoofdMenu.
	 *
	 * @param dc de domeincontroller.
	 */
	public HoofdMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		scanner = new Scanner(System.in);
		this.dc = dc;
		vraagKeuze();
	}

	private void vraagKeuze() {
		int keuze;
		do {
			keuze = vraagKeuzeMetHoofdMenu(messages.getString("main_menu"));
			System.out.println();
			switch (keuze) {
				case 1 -> new RegistratieMenu(dc);
				case 2 -> {
					try {
						dc.maakSpelAan();
						vraagKeuzeOverSpel();
					}
					catch (RuntimeException re) {
						System.err.println(messages.getString("no_connection"));
						scanner.nextLine();
					}
				}
			}
		} while (keuze != 3);
	}

	private void vraagKeuzeOverSpel() {
		int keuze;
		do {
			keuze = vraagKeuzeMetHoofdMenu(messages.getString("game_menu"));
			System.out.println();
			switch (keuze) {
				case 1 -> new SpelersToevoegenMenu(dc);
				case 2 -> {
					try {
						dc.startSpel();
						new SpelSpelenMenu(dc);
					}
					catch (IllegalArgumentException iae) {
						System.err.println(iae.getMessage());
					}
					catch (Exception e) {
						System.err.println(messages.getString("error_occurred"));
						scanner.nextLine();
					}
				}
			}
		} while (keuze != 3);
	}

	private int vraagKeuzeMetHoofdMenu(String menu) {
		int keuze = 0;
		boolean keuzeGeldig = false;
		do
			try {
				System.out.print(menu + " ");
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

}
