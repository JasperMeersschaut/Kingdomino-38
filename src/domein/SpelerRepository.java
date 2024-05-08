
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
	private List<Speler> beschikbareSpelers;
	private List<Kleur> beschikbareKleuren;

	/**
	 * Constructor voor een SpelerRepository.
	 */
	public SpelerRepository() {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		mapper = new SpelerMapper();
	}

	/**
	 * Geeft een lijst van beschikbare spelers.
	 *
	 * @return een lijst van Speler objecten.
	 */
	public List<Speler> geefBeschikbareSpelers() {
		return beschikbareSpelers;
	}

	/**
	 * Geeft een lijst van beschikbare kleuren.
	 *
	 * @return een lijst van Kleur objecten.
	 */
	public List<Kleur> geefBeschikbareKleuren() {
		return beschikbareKleuren;
	}

	/**
	 * Verwijdert een speler uit de lijst van beschikbare spelers.
	 *
	 * @param speler de speler die moet worden verwijderd.
	 */
	public void verwijderSpelerUitBeschikbareSpelersLijst(Speler speler) {
		this.beschikbareSpelers.remove(speler);
	}

	/**
	 * Verwijdert een kleur uit de lijst van beschikbare kleuren.
	 *
	 * @param kleur de kleur die moet worden verwijderd.
	 */
	public void verwijderKleurUitBeschikbareKleurenLijst(Kleur kleur) {
		this.beschikbareKleuren.remove(kleur);
	}

	/**
	 * Stelt de lijsten van beschikbare spelers en kleuren in.
	 */
	public void stelBeschikbareSpelersEnKleurenLijstenIn() {
		beschikbareSpelers = geefAlleSpelersUitDataBase();
		beschikbareKleuren = new ArrayList<>(Arrays.asList(Kleur.values()));
	}

	/**
	 * Registreert een nieuwe speler.
	 *
	 * @param speler de speler die moet worden geregistreerd.
	 */
	public void registreerSpeler(Speler speler) {
		if (bestaatSpeler(speler.geefGebruikersnaam()))
			throw new GebruikersnaamInGebruikException(messages.getString("username_already_in_use"));
		mapper.voegToe(speler);
	}

	private boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.geefSpelerUitDataBase(gebruikersnaam) != null;
	}

	/**
	 * Geeft een speler uit de database.
	 *
	 * @param gebruikersnaam de gebruikersnaam van de speler.
	 * @return een Speler object.
	 */
	public Speler geefSpelerUitDataBase(String gebruikersnaam) {
		if (!bestaatSpeler(gebruikersnaam))
			throw new GebruikersnaamBestaatNietException(
					String.format(messages.getString("player_doenst_exist"), gebruikersnaam));
		return mapper.geefSpelerUitDataBase(gebruikersnaam);
	}

	/**
	 * Geeft alle spelers uit de database.
	 *
	 * @return een lijst van Speler objecten.
	 */
	public List<Speler> geefAlleSpelersUitDataBase() {
		return mapper.geefAlleSpelersUitDataBase();
	}

	/**
	 * Update het aantal gewonnen en gespeelde spellen voor een lijst van spelers.
	 *
	 * @param spelersGespeeld een lijst van spelers die gespeeld hebben.
	 * @param spelersGewonnen een lijst van spelers die gewonnen hebben.
	 */
	public void updateAantalGewonnenEnAantalGespeeldeSpellen(List<Speler> spelersGespeeld,
			List<Speler> spelersGewonnen) {
		mapper.updateAantalGewonnenEnAantalGespeeldeSpellen(spelersGespeeld, spelersGewonnen);
	}

}
