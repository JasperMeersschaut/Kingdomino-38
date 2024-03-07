
package domein;

import java.util.List;

import utils.Kleur;

public class DomeinController {

	private final SpelerRepository spelerRepository;
	private Spel spel;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
	}

	public static List<Kleur> toonBeschikbareKleuren() {
		return Spel.toonBeschikbareKleuren();
	}

}
