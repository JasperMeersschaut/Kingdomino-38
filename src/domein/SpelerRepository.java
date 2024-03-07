
package domein;

import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {
	Scanner scanner = new Scanner(System.in);

	private final SpelerMapper mapper;

	public SpelerRepository() {
		mapper = new SpelerMapper();
	}

	public void voegToe(Speler speler) {
		if (bestaatSpeler(speler.getGebruikersnaam()))
			throw new GebruikersnaamInGebruikException();
		mapper.voegToe(speler);
	}

	private boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam) != null;
	}

	public void registreerSpeler() {
		boolean spelerGeldig = false;

		do
			try {
				System.out.print("Geef een gebruikersnaam op: ");
				String gebruikersnaam = scanner.nextLine();
				System.out.print("Geef een geboortejaar op: ");
				int geboortejaar = scanner.nextInt();
				voegToe(new Speler(gebruikersnaam, geboortejaar));
				System.out.printf("%s is succesvol aangemaakt%n", gebruikersnaam);
				spelerGeldig = true;
			} catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException ime) {
				System.err.println("Je hebt een iets verkeerd ingevuld!");
				scanner.nextLine();
			} catch (GebruikersnaamInGebruikException gige) {
				System.err.println(gige.getMessage());
				scanner.nextLine();
			} catch (Exception e) {
				System.err.println("Er is iets fout gelopen");
				scanner.nextLine();
			}
		while (!spelerGeldig);
	}

	public Speler geefSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam);
	}
}
