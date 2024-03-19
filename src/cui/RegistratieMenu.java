
package cui;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.GebruikersnaamInGebruikException;

public class RegistratieMenu {

	private final Scanner scanner;
	private final ResourceBundle messages;
	private final DomeinController dc;

	public RegistratieMenu(DomeinController dc) {
		this.dc = dc;
		scanner = new Scanner(System.in);
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
	}

	public void registreerSpeler() {
		boolean gebruikersnaamGeldig = false;
		String gebruikersnaam = null;
		int geboortejaar;
		do
			try {
				System.out.print(messages.getString("enter_username"));
				gebruikersnaam = scanner.nextLine();
				System.out.print(messages.getString("enter_birth_year"));
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
			catch (GebruikersnaamInGebruikException gige) {
				System.err.println(gige.getMessage());
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
