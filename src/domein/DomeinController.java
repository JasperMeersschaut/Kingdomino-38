
package domein;

import java.util.List;

import DTO.SpelerDTO;

public class DomeinController {

	private static SpelerRepository spelerRepository;
	private Spel spel;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
	}

	public Speler geefSpeler(String gebruikersnaam) {
		return spelerRepository.geefSpeler(gebruikersnaam);
	}

	public List<Speler> geefAlleSpelers() {
		return spelerRepository.geefAlleSpelers();
	}

	public void maakSpelAan(List<SpelerDTO> spelers) {
		spel = new Spel(spelers);
	}

	public String toonSpelOverzicht() {
		return spel.toonSpelOverzicht();
	}

	public void kiesWillekeurigeKoning() {
		spel.kiesWillekeurigeKoning();
	}
}
