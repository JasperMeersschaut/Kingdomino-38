
package cui;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class RegistratieMenu {

	private final ResourceBundle messages;
	private final Scanner scanner;
	private final DomeinController dc;

	public RegistratieMenu(DomeinController dc) {
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
		this.dc = dc;
		scanner = new Scanner(System.in);
	}

	public void registreerSpeler() {
		boolean gebruikersnaamGeldig = false;
		String gebruikersnaam = null;
		int geboortejaar;
		do
			try {
				System.out.print(messages.getString("enter_username") + " ");
				gebruikersnaam = scanner.nextLine();
				System.out.print(messages.getString("enter_birth_year") + " ");
				geboortejaar = scanner.nextInt();
				dc.registreerSpeler(gebruikersnaam, geboortejaar);
				gebruikersnaamGeldig = true;
			}
			catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				scanner.nextLine();
			}
			catch (InputMismatchException ime) {
				System.err.println(messages.getString("invalid_input"));
				scanner.nextLine();
			}
			catch (Exception e) {
				System.err.println(messages.getString("error_occurred"));
				scanner.nextLine();
			}
		while (!gebruikersnaamGeldig);
		System.out.printf(messages.getString("registration_success"), gebruikersnaam);
	}

}
