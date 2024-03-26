
package domein;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {

	private final ResourceBundle messages;
	private final SpelerMapper mapper;

	public SpelerRepository() {
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
		mapper = new SpelerMapper();
	}

	public void voegToe(Speler speler) {
		if (bestaatSpeler(speler.getGebruikersnaam()))
			throw new GebruikersnaamInGebruikException(messages.getString("username_already_in_use"));
		mapper.voegToe(speler);
	}

	public boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam) != null;
	}
	public Speler geefSpeler(String gebruikersnaam) {
	 	 return mapper.geefSpeler(gebruikersnaam);
	}
public void updateAantalGewonnen(String gebruikersnaam){
		mapper.updateAantalGewonnen(gebruikersnaam);
}
public void	updateAantalGespeeld(List<String> gebruikersnaam){
		mapper.updateAantalGespeeld(gebruikersnaam);
	}


	public List<Speler> geefAlleSpelers() {
		return mapper.geefAlleSpelers();
	}

}
