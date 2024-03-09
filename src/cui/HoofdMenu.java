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

		try {
			String menuText = messages.getString("main_menu");
	        System.out.println(menuText);
			keuze = scanner.nextInt();
			scanner.nextLine();
			keuzeGeldig = true;
		} catch (InputMismatchException ime) {
			System.err.println("Vul een getal in!");
			scanner.nextLine();
		}
		// all catcher
		catch (Exception e) {
			System.err.println("Er is iets fout gelopen");
			scanner.nextLine();
		}
		return keuze;
	}
}
