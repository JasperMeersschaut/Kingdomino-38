
package cui;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class HoofdMenu {

	private final ResourceBundle messages;
	private final Scanner scanner;
	private final RegistratieMenu registratieMenu;
	private final SpelersToevoegenMenu spelMakenMenu;

	public HoofdMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
		scanner = new Scanner(System.in);
		registratieMenu = new RegistratieMenu(dc);
		spelMakenMenu = new SpelersToevoegenMenu(dc);
	}

	public void vraagKeuze() {
		int keuze;
		do {
			keuze = vraagKeuzeMetHoofdMenu();
			System.out.println();
			switch (keuze) {
				case 1 -> registratieMenu.registreerSpeler();
				case 2 -> spelMakenMenu.startNieuwSpel();
			}
		} while (keuze != 3);
	}

	private int vraagKeuzeMetHoofdMenu() {
		int keuze = 0;
		boolean keuzeGeldig = false;
		do
			try {
				System.out.print(messages.getString("main_menu") + " ");
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
