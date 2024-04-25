
package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import exceptions.GebruikersnaamBestaatNietException;
import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;
import utils.Kleur;
import utils.Taal;

public class SpelerRepository {

	private final ResourceBundle messages;
	private final SpelerMapper mapper;
	private final List<Speler> beschikbareSpelers;
	private final List<Kleur> beschikbareKleuren;

	public SpelerRepository() {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		mapper = new SpelerMapper();
		this.beschikbareSpelers = geefAlleSpelers();
		this.beschikbareKleuren = new ArrayList<>(Arrays.asList(Kleur.values()));
	}

	public List<Speler> getBeschikbareSpelers() {
		return beschikbareSpelers;
	}

	public List<Kleur> getBeschikbareKleuren() {
		return beschikbareKleuren;
	}

	public void verwijderSpeler(Speler speler) {
		this.beschikbareSpelers.remove(speler);
	}

	public void verwijderKleur(Kleur kleur) {
		this.beschikbareKleuren.remove(kleur);
	}

	public void voegToe(Speler speler) {
		if (bestaatSpeler(speler.getGebruikersnaam()))
			throw new GebruikersnaamInGebruikException(messages.getString("username_already_in_use"));
		mapper.voegToe(speler);
		beschikbareSpelers.add(speler);
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
