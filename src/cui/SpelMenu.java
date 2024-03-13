
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
				System.out.print(messages.getString("want_to_add_player"));
				String antwoord = scanner.nextLine();
				if (!antwoord.equals(messages.getString("yes")))
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
				System.out.print(messages.getString("give_name_added_player"));
				naam = scanner.nextLine();
				if (dc.geefSpeler(naam) == null)
					throw new IllegalArgumentException(messages.getString("user_doesnt_exist"));
				for (Speler player : beschikbareSpelers)
					if (player.getGebruikersnaam().toLowerCase().equals(naam.toLowerCase()))
						naamGeldig = true;
				if (!naamGeldig)
					throw new IllegalArgumentException(messages.getString("user_already_chosen"));
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
				System.out.print(messages.getString("give_player_colour"));
				switch (scanner.nextLine()) {
				case "groen" -> kleur = Kleur.GROEN;
				case "blauw" -> kleur = Kleur.BLAUW;
				case "roos" -> kleur = Kleur.ROOS;
				case "geel" -> kleur = Kleur.GEEL;
				default -> kleur = null;
				}
				if (kleur == null)
					throw new IllegalArgumentException(messages.getString("choose_valid_colour"));
				for (Kleur color : beschikbareKleuren)
					if (color == kleur)
						kleurGeldig = true;
				if (!kleurGeldig)
					throw new IllegalArgumentException(messages.getString("colour_cant_be_picked"));
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
		System.out.println(messages.getString("available_existing_players"));
		System.out.println("=====================================");
		for (Speler speler : beschikbareSpelers)
			System.out.println("- " + speler.getGebruikersnaam());
		System.out.println("=====================================");
	}

	private void toonBeschikbareKleuren(Kleur[] beschikbareKleuren) {
		System.out.println(messages.getString("available_colours"));
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
			throw new IllegalArgumentException(messages.getString("all_kings_placed"));
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
		System.out.printf(messages.getString("turn_to_choose_tiles") + startkolom,
                speler.speler().getGebruikersnaam());
		TegelDTO tegel = null;
		boolean tegelGekozen = false;
		do {
			System.out.print(messages.getString("give_number_chosen_tile"));
			int nr = scanner.nextInt();
			for (int i = 0; i < startkolom.size(); i++) {
				tegel = startkolom.get(i);
				if (tegel.tegel().getNummer() == nr) {
					if (tegel.spelerDTO() == null) {
						startkolom.remove(i);
						startkolom.add(i, new TegelDTO(tegel.tegel(), speler));
						tegelGekozen = true;
					} else {
						System.out.println(messages.getString("already_taken_tile")
								+ tegel.spelerDTO().speler().getGebruikersnaam());
						break;
					}
				}
			}
		} while (!tegelGekozen);
	}

}
