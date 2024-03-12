
package cui;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import domein.Spel;
import exceptions.GebruikersnaamInGebruikException;

public class Applicatie {

	private ResourceBundle messages;
	private DomeinController dc;
	Scanner scanner = new Scanner(System.in);
	private HoofdMenu hoofdMenu = new HoofdMenu();

	public Applicatie() {

	}

	public Applicatie(DomeinController dc) {
		this.dc = dc;
		Locale locale = Locale.getDefault(); // de juiste resource-bundel op basis van de huidige taalinstellingen
		messages = ResourceBundle.getBundle("messages", locale); 
	}

	public void start() {
		int keuze;
		do {
			keuze = dc.toonHoofdMenu();
			switch (keuze) {
			case 1 -> registreerSpeler();
			case 2 -> startNieuwSpel();
			}
		} while (keuze != 3);
	}

	public void registreerSpeler() {
		boolean spelerGeldig = false;
		do {
			try {
				System.out.print(messages.getString("enter_username"));
				String gebruikersnaam = scanner.nextLine();
				System.out.print(messages.getString("enter_birth_year"));
				int geboortejaar = scanner.nextInt();
				dc.registreerSpeler(gebruikersnaam, geboortejaar);
				System.out.printf(messages.getString("registration_success"), gebruikersnaam);
				spelerGeldig = true;
			} catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException ime) {
				System.err.println(messages.getString("invalid_input"));
				scanner.nextLine();
			} catch (GebruikersnaamInGebruikException gige) {
				System.err.println(gige.getMessage());
				scanner.nextLine();
			} catch (Exception e) {
				System.err.println(messages.getString("error_occurred"));
				scanner.nextLine();
			}
		} while (!spelerGeldig);
	}

	public void startNieuwSpel() {
		Spel spel = new Spel();
		System.out.println(messages.getString("available_colours"));
//		 int index = 1;
//		 for (Kleur kleur : dc.toonBeschikbareKleuren())
//		 System.out.println("\t" + String.format("%d: %s ", index++, kleur));
//		
	}

}
