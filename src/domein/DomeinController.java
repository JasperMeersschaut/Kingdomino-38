
package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import dto.SpelerDTO;
import dto.TegelDTO;
import exceptions.GebruikersnaamAlGekozenException;
import exceptions.GebruikersnaamBestaatNietException;
import exceptions.KleurAlGekozenException;
import exceptions.KleurBestaatNietException;
import utils.Kleur;

public class DomeinController {

	private final ResourceBundle messages;
	private Spel spel;
	private List<SpelerDTO> bechikbareSpelers;
	private List<Kleur> beschikbareKleuren;
	private static SpelerRepository spelerRepository;

	public DomeinController() {
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
		spelerRepository = new SpelerRepository();
		bechikbareSpelers = new ArrayList<>();
		beschikbareKleuren = new ArrayList<>();
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
	}

	public void maakSpelAan() {
		spel = new Spel();
		bechikbareSpelers.clear();
		List<Speler> alleSpelers = spelerRepository.geefAlleSpelers();
		for (Speler speler : alleSpelers)
			bechikbareSpelers.add(new SpelerDTO(speler.getGebruikersnaam(), null, null));
		beschikbareKleuren.clear();
		Kleur[] alleKleuren = Kleur.values();
		for (Kleur kleur : alleKleuren)
			beschikbareKleuren.add(kleur);
	}

	public List<SpelerDTO> geefBeschikbareSpelers() {
		return bechikbareSpelers;
	}

	public List<Kleur> geefBeschikbareKleuren() {
		return beschikbareKleuren;
	}

	public void voegSpelerToeAanSpel(String gebruikersnaam, String kleur) {
		if (!spelerRepository.bestaatSpeler(gebruikersnaam))
			throw new GebruikersnaamBestaatNietException(
					String.format(messages.getString("player_doenst_exist"), gebruikersnaam));
		if (!Kleur.bestaatKleur(kleur))
			throw new KleurBestaatNietException(String.format(messages.getString("color_doenst_exist"), kleur));
		int spelerIndex = -1;
		boolean spelerGevonden = false;
		for (int index = 0; index < bechikbareSpelers.size(); index++)
			if (!spelerGevonden
					&& bechikbareSpelers.get(index).gebruikersnaam().toLowerCase().equals(gebruikersnaam.toLowerCase())) {
				spelerGevonden = true;
				spelerIndex = index;
			}
		if (!spelerGevonden)
			throw new GebruikersnaamAlGekozenException(
					String.format(messages.getString("player_already_chosen"), gebruikersnaam));
		int kleurIndex = -1;
		boolean kleurVerwijderd = false;
		for (int index = 0; index < beschikbareKleuren.size(); index++)
			if (!kleurVerwijderd && beschikbareKleuren.get(index).toString().toLowerCase().equals(kleur.toLowerCase())) {
				kleurVerwijderd = true;
				kleurIndex = index;
			}
		if (!kleurVerwijderd)
			throw new KleurAlGekozenException(String.format(messages.getString("color_already_chosen"), kleur));
		Vak[][] koninkrijk = new Vak[5][5];
		spel.voegSpelerToe(new SpelerDTO(gebruikersnaam, Kleur.valueOf(kleur.toUpperCase()), koninkrijk));
		bechikbareSpelers.remove(spelerIndex);
		beschikbareKleuren.remove(kleurIndex);
	}

	public void startSpel() {
		spel.startSpel();
	}

	public List<SpelerDTO> geefSpelers() {
		return spel.geefSpelers();
	}

	public SpelerDTO geefHuidigeSpeler() {
		return spel.geefHuidigeSpeler();
	}

	public List<TegelDTO> geefStapel() {
		return spel.geefStapel();
	}

	public List<TegelDTO> geefStartKolom() {
		return spel.geefStartKolom();
	}

	public List<TegelDTO> geefEindKolom() {
		return spel.geefEindKolom();
	}

	public void plaatsKoningOpTegel(int gekozenTegel) {
		spel.plaatsKoningOpTegel(gekozenTegel);
	}

	public void vulEindKolomAan() {
		spel.vulEindKolomAan();
	}

	public List<Integer> geefScores() {
		List<Integer> scores = new ArrayList<>();
		for (SpelerDTO speler : spel.geefSpelers())
			scores.add(spel.berekenScore(speler));
		return scores;
	}

	public List<SpelerDTO> geefWinnaars() {
		return spel.geefWinnaars();
	}

}
