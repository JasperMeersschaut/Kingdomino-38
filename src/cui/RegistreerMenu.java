
package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.GebruikersnaamInGebruikException;

public class RegistreerMenu {

	private Scanner scanner;
	private ResourceBundle messages;
	private DomeinController dc;

	public RegistreerMenu(DomeinController dc) {
		this.dc = dc;
		scanner = new Scanner(System.in);
		messages = ResourceBundle.getBundle("messages");
	}

	public void registreerSpeler() {
		boolean spelerGeldig = false;
		do
			try {
				System.out.print(messages.getString("enter_username"));
				String gebruikersnaam = scanner.nextLine();
				System.out.print(messages.getString("enter_birth_year"));
				int geboortejaar = scanner.nextInt();
				dc.registreerSpeler(gebruikersnaam, geboortejaar);
				System.out.printf(messages.getString("registration_success"), gebruikersnaam);
				spelerGeldig = true;
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
		while (!spelerGeldig);
	}

}
