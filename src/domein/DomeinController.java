
package domein;

import java.util.List;

import DTO.AlleSpelersDTO;
import DTO.SpelerDTO;
import cui.HoofdMenu;

public class DomeinController {
	private Spel spel;
	private static SpelerRepository spelerRepository;
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

	public void startSpel(List<SpelerDTO> spelers) {
		spel = new Spel(spelers);
		spel.start();
	}

	public List<AlleSpelersDTO> toonBeschikbareSpelers() {
		return spelerRepository.toonBeschikbareSpelers();
	}

}
