
package domein;

import java.util.List;
import java.awt.Menu;

import utils.Kleur;

public class DomeinController {
	private static Spel spel;
	private Menu menu;
	private static SpelerRepository spelerRepository;

	private final SpelerRepository spelerRepository;
	private Spel spel;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
		menu = new Menu();
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
	private static startSpel(){
		spel = new Spel(spelerRepository.geefSpeler());
		spel.start();
	}

	public static List<Kleur> toonBeschikbareKleuren() {
		return Spel.toonBeschikbareKleuren();
	}

}
