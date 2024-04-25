
package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import utils.Taal;

public class HoofdMenu {

	private ResourceBundle messages;
	private final Scanner scanner;
	private DomeinController dc;

	public HoofdMenu(DomeinController dc) {
		scanner = new Scanner(System.in);
		this.dc = dc;
	}

	public void vraagTaal() {
		int keuze;
		boolean keuzeGeldig;
		do {
			keuze = vraagKeuzeMetHoofdMenu(
					"In which language would you like to play this game?\n1. Dutch\n2. English\n3. French\nPlease indicate your choice:");
			System.out.println();
			switch (keuze) {
				case 1 -> Taal.setTaal("BE");
				case 2 -> Taal.setTaal("EN");
				case 3 -> Taal.setTaal("FR");
			}
			keuzeGeldig = keuze >= 1 && keuze <= 3;
		} while (!keuzeGeldig);
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		vraagKeuze();
	}

	public void vraagKeuze() {
		int keuze;
		do {
			keuze = vraagKeuzeMetHoofdMenu(messages.getString("main_menu"));
			System.out.println();
			switch (keuze) {
				case 1 -> new RegistratieMenu(dc).registreerSpeler();
				case 2 -> {
					boolean spelAangemaakt = false;
					try {
						dc.maakSpelAan();
						spelAangemaakt = true;
					}
					catch (RuntimeException re) {
						System.err.println(messages.getString("no_connection"));
						scanner.nextLine();
					}
					catch (Exception e) {
						System.err.println(messages.getString("error_occurred"));
						scanner.nextLine();
					}
					if (spelAangemaakt)
						vraagKeuzeOverSpel();
				}
			}
		} while (keuze != 3);
	}

	private void vraagKeuzeOverSpel() {
		SpelersToevoegenMenu spelersToevoegenMenu = new SpelersToevoegenMenu(dc);
		SpelSpelenMenu spelSpelenMenu = new SpelSpelenMenu(dc);
		int keuze;
		do {
			keuze = vraagKeuzeMetHoofdMenu(messages.getString("game_menu"));
			System.out.println();
			switch (keuze) {
				case 1 -> spelersToevoegenMenu.voegSpelerToe();
				case 2 -> {
					try {
						dc.startSpel();
						spelSpelenMenu.speelSpel();
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
