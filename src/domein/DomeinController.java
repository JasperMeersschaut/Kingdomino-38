package domein;

import java.util.Scanner;

public class DomeinController {
	Scanner scanner = new Scanner(System.in);
	private final SpelerRepository spelerRepository;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}

	public void registreerSpeler() { // Registreert de speler volgens gebruikersnaam en geboortejaar
		String gebruikersnaam;
		int geboortejaar;
		System.out.println("Geef een gebruikersnaam op: ");
		gebruikersnaam = scanner.nextLine();
		System.out.println("Geef een geboortejaar op: ");
		geboortejaar = scanner.nextInt();
		Speler nieuweSpeler = new Speler(gebruikersnaam, geboortejaar); // De net opgevraagde gegevens worden in de
																		// nieuweSpeler constructor gestoken
		spelerRepository.voegToe(nieuweSpeler);
	}

	public void startMenu() {
		int keuze;
		do {
			System.out.println("1. Registreer nieuwe speler");
			System.out.println("2. Start nieuw spel");
			System.out.println("3. Afsluiten");
			keuze = scanner.nextInt();
			if (keuze == 1) {
				registreerSpeler();
			}
			if (keuze == 2) {
				Spel spel = new Spel();
			}

		} while (keuze != 3);// Loopt tot de keuze gelijk is aan 3
	}
}
