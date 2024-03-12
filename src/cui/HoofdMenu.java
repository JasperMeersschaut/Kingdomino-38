package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HoofdMenu {
	private Scanner scanner = new Scanner(System.in);;
	private ResourceBundle messages = ResourceBundle.getBundle("messages");

	public int toonHoofdMenu() {
		int keuze = 0;
		boolean keuzeGeldig = false;

		try {
			System.out.print(messages.getString("main_menu") );
			keuze = scanner.nextInt();
			scanner.nextLine();
			keuzeGeldig = true;
		} catch (InputMismatchException ime) {
			System.err.print(messages.getString("enter_a_number"));
			scanner.nextLine();
		}
		// all catcher
		catch (Exception e) {
			System.err.println(messages.getString("error_occurred"));
			scanner.nextLine();
		}
		return keuze;
	}
}
