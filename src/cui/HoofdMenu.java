
package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HoofdMenu {

	private Scanner scanner;
	private ResourceBundle messages;

	public HoofdMenu() {
		scanner = new Scanner(System.in);
		messages = ResourceBundle.getBundle("messages");
	}

	public int toonHoofdMenu() {
		int keuze = 0;
		boolean keuzeGeldig = false;
		do
			try {
				System.out.print(messages.getString("main_menu"));
				keuze = scanner.nextInt();
				scanner.nextLine();
				keuzeGeldig = true;
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
