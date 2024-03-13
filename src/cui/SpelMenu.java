
package cui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import domein.Speler;
import dto.SpelerDTO;
import dto.TegelDTO;
import utils.Kleur;

public class SpelMenu {

	private Scanner scanner;
	private ResourceBundle messages;
	private DomeinController dc;
	private List<SpelerDTO> spelers;

	public SpelMenu(DomeinController dc) {
		this.dc = dc;
		scanner = new Scanner(System.in);
		messages = ResourceBundle.getBundle("messages");
		spelers = new ArrayList<>();
	}

	public List<SpelerDTO> vraagSpelersEnKleuren() {
		List<Speler> beschikbareSpelers = dc.geefAlleSpelers();
		Kleur[] beschikbareKleuren = Kleur.values();
		for (int index = 1; index <= 4; index++)
			if (index == 4) {
				System.out.print("Wil je nog een speler toevoegen? (ja/nee): ");
				String antwoord = scanner.nextLine();
				if (!antwoord.equals("ja"))
					index++;
				else
					spelerEnKleurToevoegenAanSpelers(beschikbareSpelers, beschikbareKleuren);
			} else
				spelerEnKleurToevoegenAanSpelers(beschikbareSpelers, beschikbareKleuren);
		return spelers;
	}

	public void spelerEnKleurToevoegenAanSpelers(List<Speler> beschikbareSpelers, Kleur[] beschikbareKleuren) {
		String naam = geefSpeler(beschikbareSpelers);
		Kleur kleur = geefKleur(beschikbareKleuren);
		spelers.add(new SpelerDTO(dc.geefSpeler(naam), kleur));
		for (int index = 0; index < Kleur.values().length; index++)
			if (Kleur.values()[index].toString().equals(kleur.toString()))
				beschikbareKleuren[index] = null;
		for (int index = 0; index < beschikbareSpelers.size(); index++)
			if (beschikbareSpelers.get(index).getGebruikersnaam().toLowerCase().equals(naam.toLowerCase()))
				beschikbareSpelers.remove(index);
	}

	private String geefSpeler(List<Speler> beschikbareSpelers) {
		boolean naamGeldig = false;
		String naam = null;
		do
			try {
				toonBeschikbareSpelers(beschikbareSpelers);
				System.out.print("Geef de naam van de speler die je wilt toevoegen: ");
				naam = scanner.nextLine();
				if (dc.geefSpeler(naam) == null)
					throw new IllegalArgumentException("Gebruiker bestaat niet!");
				for (Speler player : beschikbareSpelers)
					if (player.getGebruikersnaam().toLowerCase().equals(naam.toLowerCase()))
						naamGeldig = true;
				if (!naamGeldig)
					throw new IllegalArgumentException("Gebruiker is al gekozen!");
			} catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			} catch (InputMismatchException ime) {
				System.err.println(messages.getString("invalid_input"));
				scanner.nextLine();
			} catch (Exception e) {
				System.err.println(messages.getString("error_occurred"));
				scanner.nextLine();
			}
		while (!naamGeldig);
		return naam;
	}

	private Kleur geefKleur(Kleur[] beschikbareKleuren) {
		boolean kleurGeldig = false;
		Kleur kleur = null;
		do
			try {
				toonBeschikbareKleuren(beschikbareKleuren);
				System.out.print("Geef de kleur van de speler die je wilt toevoegen: ");
				switch (scanner.nextLine()) {
				case "groen" -> kleur = Kleur.GROEN;
				case "blauw" -> kleur = Kleur.BLAUW;
				case "roos" -> kleur = Kleur.ROOS;
				case "geel" -> kleur = Kleur.GEEL;
				default -> kleur = null;
				}
				if (kleur == null)
					throw new IllegalArgumentException("Kies een geldige kleur!");
				for (Kleur color : beschikbareKleuren)
					if (color == kleur)
						kleurGeldig = true;
				if (!kleurGeldig)
					throw new IllegalArgumentException("Kleur kan niet meer gekozen worden!");
			} catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			} catch (InputMismatchException ime) {
				System.err.println(messages.getString("invalid_input"));
				scanner.nextLine();
			} catch (Exception e) {
				System.err.println(messages.getString("error_occurred"));
				scanner.nextLine();
			}
		while (!kleurGeldig);
		return kleur;
	}

	private void toonBeschikbareSpelers(List<Speler> beschikbareSpelers) {
		System.out.println("Beschikbare bestaande spelers:");
		System.out.println("=====================================");
		for (Speler speler : beschikbareSpelers)
			System.out.println("- " + speler.getGebruikersnaam());
		System.out.println("=====================================");
	}

	private void toonBeschikbareKleuren(Kleur[] beschikbareKleuren) {
		System.out.println("Beschikbare kleuren:");
		System.out.println("====================");
		for (Kleur kleur : beschikbareKleuren)
			if (kleur != null)
				System.out.println("- " + kleur.toString());
		System.out.println("====================");
	}

	public void kiesWillekeurigeKoning(List<TegelDTO> startkolom) {
		// Alle spelers die nog geen koning plaatsten
		List<SpelerDTO> spelersZonderTegel = new ArrayList();
		boolean isGeplaatst;
		for (SpelerDTO speler : spelers) {
			isGeplaatst = false;
			for (TegelDTO tegel : startkolom) {
				// Checken of speler al koning plaatste
				if (tegel.spelerDTO() == speler) {
					isGeplaatst = true;
					break;
				}
			}
			// Speler toevoegen aan lijst indien die nog geen koning plaatste
			if (!isGeplaatst) {
				spelersZonderTegel.add(speler);
			}
		}

		// null teruggeven indien alle koningen geplaatst zijn
		if (spelersZonderTegel.isEmpty()) {
			throw new IllegalArgumentException("Alle koningen zijn geplaatst");
		}

		// Willekeurige spelerDTO teruggeven waarvan de speler nog geen koning plaatste,
		// totdat alle koningen geplaatst zijn
		Random random = new Random();
		do {
			int randomIndex = random.nextInt(spelersZonderTegel.size());
			kiesTegelStartkolom(startkolom, spelersZonderTegel.get(randomIndex));
			spelersZonderTegel.remove(randomIndex);
		} while (spelersZonderTegel.size() != 0);
	}

	public void kiesTegelStartkolom(List<TegelDTO> startkolom, SpelerDTO speler) { // moet nog ergens opgeroepen worden
		System.out.println("Het is aan" + speler.speler().getGebruikersnaam()
				+ "om een tegel te kiezen. \n Beschikbare tegels: " + startkolom);
		TegelDTO tegel = null;
		boolean tegelGekozen = false;
		do {
			System.out.print("Geef het nummer van de tegel die je kiest: ");
			int nr = scanner.nextInt();
			for (int i = 0; i < startkolom.size(); i++) {
				tegel = startkolom.get(i);
				if (tegel.tegel().getNummer() == nr) {
					if (tegel.spelerDTO() == null) {
						startkolom.remove(i);
						startkolom.add(i, new TegelDTO(tegel.tegel(), speler));
						tegelGekozen = true;
					} else {
						System.out.println("Deze tegel is al in beslag genomen door "
								+ tegel.spelerDTO().speler().getGebruikersnaam());
						break;
					}
				}
			}
		} while (!tegelGekozen);
	}

}
