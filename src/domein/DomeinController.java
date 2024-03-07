
package domein;

import java.awt.Menu;

public class DomeinController {
	private Spel spel;
	private Menu menu;
	private static SpelerRepository spelerRepository;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
		menu = new Menu();
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
	}

	private void startSpel() {
		spel = new Spel();
		spel.start();
	}

//	public static List<Kleur> toonBeschikbareKleuren() {
//		return Spel.toonBeschikbareKleuren();
//	}

}
