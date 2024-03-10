package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HoofdMenu {
	private Scanner scanner = new Scanner(System.in);;
	private ResourceBundle messages = ResourceBundle.getBundle("messages");;

	public int toonHoofdMenu() {
		int keuze = 0;
		boolean keuzeGeldig = false;

		try {
			String menuText = messages.getString("main_menu");
			System.out.print(menuText);
			keuze = scanner.nextInt();
			scanner.nextLine();
			keuzeGeldig = true;
		} catch (InputMismatchException ime) {
			System.err.print("enter_a_number");
			scanner.nextLine();
		}
		// all catcher
		catch (Exception e) {
			System.err.println("error_occurred");
			scanner.nextLine();
		}
		return keuze;
	}
}
