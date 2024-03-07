
package cui;

import java.util.InputMismatchException;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.GebruikersnaamInGebruikException;

public class Applicatie {

	private DomeinController dc;
	Scanner scanner = new Scanner(System.in);
	private HoofdMenu hoofdMenu;

	public Applicatie(DomeinController dc) {
		this.dc = dc;
		hoofdMenu = new HoofdMenu();
	}

	public void start() {
		int keuze;
		do {
			keuze = toonHoofdMenu();
			switch (keuze) {
			case 1 -> registreerSpeler();
			case 2 -> startNieuwSpel();
			}
		} while (keuze != 3);
	}

	public int toonHoofdMenu() {
		return hoofdMenu.toonHoofdMenu();
	}

	public void registreerSpeler() {
		boolean spelerGeldig = false;
		do
			try {
				System.out.print("Geef een gebruikersnaam op: ");
				String gebruikersnaam = scanner.nextLine();
				System.out.print("Geef een geboortejaar op: ");
				int geboortejaar = scanner.nextInt();
				dc.registreerSpeler(gebruikersnaam, geboortejaar);
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
				System.err.println("Er is een probleem opgetreden!");
				scanner.nextLine();
			}
		while (!spelerGeldig);
	}

	public void startNieuwSpel() {
		System.out.println("Beschikbare kleuren: ");
		// int index = 1;
		// for (Kleur kleur : dc.toonBeschikbareKleuren())
		// System.out.println("\t" + String.format("%d: %s ", index++, kleur));
	}

}
