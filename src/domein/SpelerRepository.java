
package domein;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {

	private final SpelerMapper mapper;
	private final ResourceBundle messages;

	public SpelerRepository() {
		mapper = new SpelerMapper();
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
	}

	public void voegToe(Speler speler) {
		if (bestaatSpeler(speler.getGebruikersnaam()))
			throw new GebruikersnaamInGebruikException(messages.getString("username_already_inuse"));
		mapper.voegToe(speler);
	}

	private boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam) != null;
	}

	public Speler geefSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam);
	}

	public List<Speler> geefAlleSpelers() {
		return mapper.geefAlleSpelers();
	}

}
