
package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HoofdMenu {

	private final ResourceBundle messages;
	private final Scanner scanner;

	public HoofdMenu() {
		messages = ResourceBundle.getBundle("messages");
		scanner = new Scanner(System.in);
	}

	public int vraagKeuzeMetHoofdMenu() {
		int keuze = 0;
		boolean keuzeGeldig = false;
		do
			try {
				System.out.print(messages.getString("main_menu"));
				keuze = scanner.nextInt();
				if (keuze < 1 || keuze > 3)
					throw new IllegalArgumentException("Kies een geldig getal!");
				scanner.nextLine();
				keuzeGeldig = true;
			}
			catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			}
			catch (InputMismatchException ime) {
				System.err.println(messages.getString("enter_a_number"));
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
