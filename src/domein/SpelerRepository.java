
package domein;

import java.util.List;
import java.util.Scanner;

import DTO.AlleSpelersDTO;
import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {
	Scanner scanner = new Scanner(System.in);

	private final SpelerMapper mapper;

	public SpelerRepository() {
		mapper = new SpelerMapper();
	}

	public void voegToe(Speler speler) {
		if (bestaatSpeler(speler.getGebruikersnaam()))
			throw new GebruikersnaamInGebruikException();
		mapper.voegToe(speler);
	}

	private boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam) != null;
	}

	public Speler geefSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam);
	}

	public List<AlleSpelersDTO> toonBeschikbareSpelers() {
		return mapper.geefAlleSpelers();
	}
}
