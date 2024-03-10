
package domein;

import java.util.List;

import cui.Applicatie;
import cui.HoofdMenu;
import utils.Kleur;

public class DomeinController {
	private Spel spel;
	private static SpelerRepository spelerRepository;
	private Applicatie applicatie = new Applicatie();
	HoofdMenu hoofdMenu = new HoofdMenu();

	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}

	public int toonHoofdMenu() {

		return hoofdMenu.toonHoofdMenu();
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
	}

	private void startSpel() {
		spel = new Spel();
		spel.start();
	}

	public static List<Kleur> toonBeschikbareKleuren() {
		return Spel.toonBeschikbareKleuren();
	}

}
