
package domein;

import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.GebruikersnaamInGebruikException;

public class DomeinController {

	Scanner scanner = new Scanner(System.in);
	private final SpelerRepository spelerRepository;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}

	public void registreerSpeler() {
		boolean spelerGeldig = false;
		do
			try {
				System.out.print("Geef een gebruikersnaam op: ");
				String gebruikersnaam = scanner.nextLine();
				System.out.print("Geef een geboortejaar op: ");
				int geboortejaar = scanner.nextInt();
				spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
				System.out.printf("%s is succesvol aangemaakt%n", gebruikersnaam);
				spelerGeldig = true;
			}
			catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				scanner.nextLine();
			}
			catch (InputMismatchException ime) {
				System.err.println("Je hebt een iets verkeerd ingevuld!");
				scanner.nextLine();
			}
			catch (GebruikersnaamInGebruikException gige) {
				System.err.println(gige.getMessage());
				scanner.nextLine();
			}
		while (!spelerGeldig);
	}

	public int toonHoofdMenu() {
		int keuze = 0;
		boolean keuzeGeldig = false;
		do
			try {
				System.out.println("1. Registreer nieuwe speler");
				System.out.println("2. Start nieuw spel");
				System.out.println("3. Afsluiten");
				System.out.print("Geef je keuze: ");
				keuze = scanner.nextInt();
				scanner.nextLine();
				keuzeGeldig = true;
			}
			catch (InputMismatchException ime) {
				System.err.println("Vul een getal in!");
				scanner.nextLine();
			}
		while (!keuzeGeldig);
		return keuze;
	}

}
