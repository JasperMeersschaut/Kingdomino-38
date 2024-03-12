package cui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DTO.AlleSpelersDTO;
import DTO.SpelerDTO;
import domein.DomeinController;
import utils.Kleur;

public class SpelMenu {
	List<SpelerDTO> spelers = new ArrayList<>();
	DomeinController dc = new DomeinController();
	List<AlleSpelersDTO> alleSpelers = dc.toonBeschikbareSpelers();

	Scanner scanner = new Scanner(System.in);

	public String getSpelerNaam() {
		System.out.print("Geef de naam van de speler die je wilt toevoegen: ");

		String naam = scanner.nextLine();
		alleSpelers.removeIf(speler -> speler.gebruikersnaam().equals(naam));
		return naam;
	}

	public Kleur getSpelerKleur(List<Kleur> beschikbareKleuren) {
		System.out.println("Beschikbare kleuren: " + beschikbareKleuren);
		System.out.print("Geef de kleur van de speler die je wilt toevoegen: ");
		Kleur kleur = Kleur.valueOf(scanner.nextLine().toUpperCase());
		beschikbareKleuren.remove(kleur);
		return kleur;
	}

	public void voegSpelerToe(String naam, Kleur kleur) {
		spelers.add(new SpelerDTO(naam, kleur));
	}

	public boolean vraagNieuweSpeler(int i) {
		if (i < 4 && i > 2) {
			System.out.println("Wil je nog een speler toevoegen? (ja/nee)");
			String antwoord = scanner.nextLine();
			return !antwoord.equals("nee");
		}
		return true;
	}

	public void spelerToevoegen() {
		int i = 0;
		List<Kleur> beschikbareKleuren = Kleur.geefKleuren();
		do {
			i++;
			toonBeschikbareSpelers();
			String naam = getSpelerNaam();
			Kleur kleur = getSpelerKleur(beschikbareKleuren);
			voegSpelerToe(naam, kleur);
		} while (vraagNieuweSpeler(i) && i <= 4);
		dc.startSpel(spelers);
	}

	private void toonBeschikbareSpelers() {
		System.out.println("Beschikbare bestaande speler spelers: ");
		for (AlleSpelersDTO speler : alleSpelers) {
			System.out.println(speler.gebruikersnaam());
		}
	}
}
