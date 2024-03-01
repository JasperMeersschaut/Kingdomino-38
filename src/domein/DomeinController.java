
package domein;

import java.util.Scanner;

public class DomeinController {

	Scanner scanner = new Scanner(System.in);
	private final SpelerRepository spelerRepository;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}

	public void registreerSpeler() { // Registreert de speler volgens gebruikersnaam en geboortejaar
		try {
			System.out.print("Geef een gebruikersnaam op: ");
			String gebruikersnaam = scanner.next();
			System.out.print("Geef een geboortejaar op: ");
			int geboortejaar = scanner.nextInt();
			spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
			System.out.printf("%s is succesvol aangemaakt%n", gebruikersnaam);
		}
		catch (Exception error) {
			System.out.println(error);
			registreerSpeler();
		}
	}

	public int toonHoofdMenu() {
		int keuze;
		System.out.println("1. Registreer nieuwe speler");
		System.out.println("2. Start nieuw spel");
		System.out.println("3. Afsluiten");
		System.out.print("Geef je keuze: ");
		keuze = scanner.nextInt();
		return keuze;
	}

}
