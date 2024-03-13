
package domein;

import java.util.List;

import dto.SpelerDTO;

public class DomeinController {

	private static SpelerRepository spelerRepository;

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

	public void startSpel(List<SpelerDTO> spelers) {
		new Spel(spelers);
	}

}
