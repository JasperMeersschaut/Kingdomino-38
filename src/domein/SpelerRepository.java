
package domein;

import java.util.List;
import java.util.ResourceBundle;

import exceptions.GebruikersnaamBestaatNietException;
import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {

	private final ResourceBundle messages;
	private final SpelerMapper mapper;

	public SpelerRepository() {
		messages = ResourceBundle.getBundle("messages");
		mapper = new SpelerMapper();
	}

	public void voegToe(Speler speler) {
		if (bestaatSpeler(speler.getGebruikersnaam()))
			throw new GebruikersnaamInGebruikException(messages.getString("username_already_in_use"));
		mapper.voegToe(speler);
	}

	private boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam) != null;
	}

	public Speler geefSpeler(String gebruikersnaam) {
		if (!bestaatSpeler(gebruikersnaam))
			throw new GebruikersnaamBestaatNietException(
					String.format(messages.getString("player_doenst_exist"), gebruikersnaam));
		return mapper.geefSpeler(gebruikersnaam);
	}

	public List<Speler> geefAlleSpelers() {
		return mapper.geefAlleSpelers();
	}

	public void updateAantalGewonnenEnAantalGespeeld(List<List<Speler>> spelers) {
		mapper.updateAantalGewonnenEnAantalGespeeld(spelers);
	}

}
