
package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import dto.SpelerDTO;
import dto.TegelDTO;
import dto.VakDTO;
import exceptions.GebruikersnaamAlGekozenException;
import exceptions.KleurAlGekozenException;
import utils.Kleur;
import utils.Taal;

public class DomeinController {

	private final ResourceBundle messages;
	private final SpelerRepository spelerRepository;
	private Spel huidigSpel;

	public DomeinController() {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		spelerRepository = new SpelerRepository();
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
	}

	public void maakSpelAan() {
		huidigSpel = new Spel();
	}

	public List<SpelerDTO> geefBeschikbareSpelers() {
		List<SpelerDTO> beschikbareSpelers = new ArrayList<>();
		for (Speler speler : spelerRepository.getBeschikbareSpelers())
			beschikbareSpelers.add(new SpelerDTO(speler));
		return beschikbareSpelers;
	}

	public List<String> geefBeschikbareKleuren() {
		List<String> geefBeschikbareKleuren = new ArrayList<>();
		for (Kleur kleur : spelerRepository.getBeschikbareKleuren())
			geefBeschikbareKleuren.add(kleur.toString());
		return geefBeschikbareKleuren;
	}

	public void voegSpelerToeAanSpel(String gebruikersnaam, String kleurString) {
		Speler speler = spelerRepository.geefSpeler(gebruikersnaam);
		Kleur kleur = Kleur.geefKleur(kleurString);
		if (!spelerRepository.getBeschikbareSpelers().contains(speler))
			throw new GebruikersnaamAlGekozenException(
					String.format(messages.getString("player_already_chosen"), speler.getGebruikersnaam()));
		if (!spelerRepository.getBeschikbareKleuren().contains(kleur))
			throw new KleurAlGekozenException(String.format(messages.getString("color_already_chosen"), kleur));
		spelerRepository.verwijderSpeler(speler);
		spelerRepository.verwijderKleur(kleur);
		huidigSpel.voegSpelerToe(speler, kleur);
	}

	public void startSpel() {
		huidigSpel.startSpel();
	}

	public List<SpelerDTO> geefSpelers() {
		List<SpelerDTO> spelers = new ArrayList<>();
		for (Speler speler : huidigSpel.getSpelers())
			spelers.add(new SpelerDTO(speler));
		return spelers;
	}

	public List<TegelDTO> geefStartKolom() {
		List<TegelDTO> startKolom = new ArrayList<>();
		for (Tegel tegel : huidigSpel.getStartKolom())
			startKolom.add(new TegelDTO(tegel));
		return startKolom;
	}

	public List<TegelDTO> geefEindKolom() {
		List<TegelDTO> eindKolom = new ArrayList<>();
		for (Tegel tegel : huidigSpel.getEindKolom())
			eindKolom.add(new TegelDTO(tegel));
		return eindKolom;
	}

	public List<TegelDTO> geefStapel() {
		List<TegelDTO> stapel = new ArrayList<>();
		for (Tegel tegel : huidigSpel.getStapel())
			stapel.add(new TegelDTO(tegel));
		return stapel;
	}

	public SpelerDTO geefHuidigeSpeler() {
		return new SpelerDTO(huidigSpel.getHuidigeSpeler());
	}

	public void plaatsKoningOpTegel(SpelerDTO speler, int gekozenTegel) {
		huidigSpel.plaatsKoningOpTegel(speler.gebruikersnaam(), gekozenTegel);
		huidigSpel.setVolgendeSpelerAlsHuidigeSpeler();
	}

	public void vulKolommenAan() {
		huidigSpel.vulKolommenAan();
	}

	public List<List<Integer>> geefScoreOverzicht() {
		List<List<Integer>> scores = new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>()));
		List<List<Speler>> spelers = new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>()));
		for (Speler speler : huidigSpel.getSpelers()) {
			scores.get(0).add(speler.getAantalGespeeld() + 1);
			spelers.get(0).add(speler);
			if (huidigSpel.geefWinnaars().contains(speler)) {
				scores.get(1).add(speler.getAantalGewonnen() + 1);
				spelers.get(1).add(speler);
			} else
				scores.get(1).add(speler.getAantalGewonnen());
		}
		spelerRepository.updateAantalGewonnenEnAantalGespeeld(spelers);
		return scores;
	}

	public List<SpelerDTO> geefWinnaars() {
		List<SpelerDTO> winnaars = new ArrayList<>();
		for (Speler speler : huidigSpel.geefWinnaars())
			winnaars.add(new SpelerDTO(speler));
		return winnaars;
	}

	public VakDTO[][] geefKoninkrijk(SpelerDTO speler) {
		Vak[][] koninkrijk = huidigSpel.geefKoninkrijk(speler.gebruikersnaam());
		VakDTO[][] koninkrijkDTO = new VakDTO[koninkrijk.length][koninkrijk[0].length];
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[i].length; j++)
				koninkrijkDTO[i][j] = new VakDTO(koninkrijk[i][j]);
		return koninkrijkDTO;
	}

	public void legTegelInKoninkrijk(TegelDTO tegel, SpelerDTO huidigeSpeler, String plaats, int richting) {
		huidigSpel.legTegelInKoninkrijk(tegel.nummer(), huidigeSpeler.gebruikersnaam(), plaats, richting);
	}

	public void gooiWeg(TegelDTO tegel, SpelerDTO speler) {
		huidigSpel.gooiWeg(tegel.nummer(), speler.gebruikersnaam());
	}

}
