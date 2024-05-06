
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

	/**
	 * Constructor voor een DomeinController.
	 */
	public DomeinController() {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		spelerRepository = new SpelerRepository();
	}

	/**
	 * Geeft een lijst van beschikbare spelers.
	 *
	 * @return een lijst van spelers als SpelerDTO's.
	 */
	public List<SpelerDTO> geefBeschikbareSpelers() {
		return spelerRepository.geefBeschikbareSpelers().stream().map(SpelerDTO::new).toList();
	}

	/**
	 * Geeft een lijst van beschikbare kleuren.
	 *
	 * @return een lijst van kleuren als strings.
	 */
	public List<String> geefBeschikbareKleuren() {
		return spelerRepository.geefBeschikbareKleuren().stream().map(Kleur::toString).toList();
	}

	/**
	 * Geeft een lijst van spelers in het huidige spel.
	 *
	 * @return een lijst van spelers als SpelerDTO's.
	 */
	public List<SpelerDTO> geefSpelers() {
		return huidigSpel.geefSpelers().stream().map(SpelerDTO::new).toList();
	}

	/**
	 * Geeft de startkolom van het huidige spel.
	 *
	 * @return een lijst van tegels als TegelDTO's.
	 */
	public List<TegelDTO> geefStartKolom() {
		return huidigSpel.geefStartKolom().stream().map(TegelDTO::new).toList();
	}

	/**
	 * Geeft de eindkolom van het huidige spel.
	 *
	 * @return een lijst van tegels als TegelDTO's.
	 */
	public List<TegelDTO> geefEindKolom() {
		return huidigSpel.geefEindKolom().stream().map(TegelDTO::new).toList();
	}

	/**
	 * Geeft de stapel van het huidige spel.
	 *
	 * @return een lijst van tegels als TegelDTO's.
	 */
	public List<TegelDTO> geefStapel() {
		return new ArrayList<>(huidigSpel.geefStapel().stream().map(TegelDTO::new).toList());
	}

	/**
	 * Geeft de huidige speler van het spel.
	 *
	 * @return een SpelerDTO.
	 */
	public SpelerDTO geefHuidigeSpeler() {
		return new SpelerDTO(huidigSpel.geefHuidigeSpeler());
	}

	/**
	 * Geeft het koninkrijk van een bepaalde speler.
	 *
	 * @param speler de speler waarvan het koninkrijk wordt opgevraagd.
	 * @return een 2D array van VakDTO's.
	 */
	public VakDTO[][] geefKoninkrijk(SpelerDTO speler) {
		Speler spelerValue = huidigSpel.geefSpelers().stream()
				.filter(sp -> sp.geefGebruikersnaam().equals(speler.gebruikersnaam())).findAny().get();
		return Arrays.stream(spelerValue.geefKoninkrijk().geefKoninkrijk())
				.map(row -> Arrays.stream(row).map(VakDTO::new).toArray(VakDTO[]::new)).toArray(VakDTO[][]::new);
	}

	/**
	 * Geeft de winnaars van het huidige spel.
	 *
	 * @return een lijst van speler(s) als SpelerDTO's.
	 */
	public List<SpelerDTO> geefWinnaars() {
		return huidigSpel.geefWinnaars().stream().map(SpelerDTO::new).toList();
	}

	/**
	 * Registreert een nieuwe speler.
	 *
	 * @param gebruikersnaam de gebruikersnaam van de nieuwe speler.
	 * @param geboortejaar   het geboortejaar van de nieuwe speler.
	 */
	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		spelerRepository.registreerSpeler(new Speler(gebruikersnaam, geboortejaar));
	}

	/**
	 * Maakt een nieuw spel aan.
	 */
	public void maakSpelAan() {
		huidigSpel = new Spel();
		spelerRepository.stelBeschikbareSpelersEnKleurenLijstenIn();
	}

	/**
	 * Voegt een speler toe aan het huidige spel.
	 *
	 * @param gebruikersnaam de gebruikersnaam van de speler.
	 * @param kleur          de kleur van de speler.
	 */
	public void voegSpelerToeAanSpel(String gebruikersnaam, String kleur) {
		Speler speler = spelerRepository.geefSpelerUitDataBase(gebruikersnaam);
		Kleur kleurValue = Kleur.geefKleur(kleur);
		if (!spelerRepository.geefBeschikbareSpelers().contains(speler))
			throw new GebruikersnaamAlGekozenException(
					String.format(messages.getString("player_already_chosen"), speler.geefGebruikersnaam()));
		if (!spelerRepository.geefBeschikbareKleuren().contains(kleurValue))
			throw new KleurAlGekozenException(String.format(messages.getString("color_already_chosen"), kleurValue));
		speler.stelKleurIn(kleurValue);
		spelerRepository.verwijderSpelerUitBeschikbareSpelersLijst(speler);
		spelerRepository.verwijderKleurUitBeschikbareKleurenLijst(kleurValue);
		huidigSpel.voegSpelerToeAanSpel(speler);
	}

	/**
	 * Start het huidige spel.
	 */
	public void startSpel() {
		huidigSpel.startSpel();
	}

	/**
	 * Vult de stapels en kolommen van het huidige spel aan.
	 */
	public void vulStapelsEnKolommenAan() {
		huidigSpel.vulStapelsEnKolommenAan();
	}

	/**
	 * Plaatst de koning van een speler op een tegel.
	 *
	 * @param speler      de speler wiens koning wordt geplaatst.
	 * @param tegelNummer het nummer van de tegel waarop de koning wordt geplaatst.
	 */
	public void plaatsKoningOpTegel(SpelerDTO speler, int tegelNummer) {
		huidigSpel.plaatsKoningOpTegel(speler.gebruikersnaam(), tegelNummer);
		huidigSpel.stelVolgendeSpelerAlsHuidigeSpelerIn();
	}

	/**
	 * Legt een tegel in het koninkrijk van een speler.
	 *
	 * @param tegel    de tegel die wordt gelegd.
	 * @param speler   de speler wiens koninkrijk de tegel ontvangt.
	 * @param rij      de rij waarin de tegel wordt gelegd.
	 * @param kolom    de kolom waarin de tegel wordt gelegd.
	 * @param richting de richting waarin de tegel wordt gelegd.
	 */
	public void legTegelInKoninkrijk(TegelDTO tegel, SpelerDTO speler, int rij, int kolom, int richting) {
		Speler spelerValue = huidigSpel.geefSpelers().stream()
				.filter(sp -> sp.geefGebruikersnaam().equals(speler.gebruikersnaam())).findAny().get();
		spelerValue.geefKoninkrijk().legTegelInKoninkrijk(tegel.nummer(), rij, kolom, richting);
		for (Tegel t : huidigSpel.geefStartKolom())
			if (t.geefSpelerOpTegel() != null && t.geefSpelerOpTegel().equals(spelerValue)) {
				t.stelSpelerOpTegelIn(null);
				break;
			}
		if (huidigSpel.geefEindKolom().isEmpty() && huidigSpel.geefStapel().isEmpty())
			huidigSpel.stelVolgendeSpelerAlsHuidigeSpelerIn();
	}

	/**
	 * Gooit een te leggen tegel weg.
	 *
	 * @param tegel  de tegel die wordt weggegooid.
	 * @param speler de speler die de tegel weggooit.
	 */
	public void gooiTegelTeLeggenWeg(TegelDTO tegel, SpelerDTO speler) {
		Speler spelerValue = huidigSpel.geefSpelers().stream()
				.filter(sp -> sp.geefGebruikersnaam().equals(speler.gebruikersnaam())).findAny().get();
		spelerValue.geefKoninkrijk().gooiWeg(tegel.nummer());
		for (Tegel t : huidigSpel.geefStartKolom())
			if (t.geefSpelerOpTegel() != null && t.geefSpelerOpTegel().equals(spelerValue)) {
				t.stelSpelerOpTegelIn(null);
				break;
			}
		if (huidigSpel.geefEindKolom().isEmpty() && huidigSpel.geefStapel().isEmpty())
			huidigSpel.stelVolgendeSpelerAlsHuidigeSpelerIn();
	}

	/**
	 * Bereken de score van een speler.
	 *
	 * @param speler de speler wiens score wordt berekend.
	 * @return de score van de speler.
	 */
	public int berekenScore(SpelerDTO speler) {
		Speler spelerValue = huidigSpel.geefSpelers().stream()
				.filter(sp -> sp.geefGebruikersnaam().equals(speler.gebruikersnaam())).findAny().get();
		return spelerValue.geefKoninkrijk().berekenScore();
	}

	/**
	 * Geeft het aantal gespeelde en gewonnen spellen.
	 *
	 * @return een lijst van lijsten met het aantal gespeelde en gewonnen spellen.
	 */
	public List<List<Integer>> geefAantalGespeeldeEnAantalGewonnenSpellen() {
		List<List<Integer>> scores = new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>()));
		List<List<Speler>> spelers = new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>()));
		for (Speler speler : huidigSpel.geefSpelers()) {
			scores.get(0).add(speler.geefAantalGespeeldeSpellen());
			spelers.get(0).add(speler);
			if (huidigSpel.geefWinnaars().contains(speler)) {
				scores.get(1).add(speler.geefAantalGewonnenSpellen() + 1);
				spelers.get(1).add(speler);
			} else
				scores.get(1).add(speler.geefAantalGewonnenSpellen());
		}
		spelerRepository.updateAantalGewonnenEnAantalGespeeldeSpellen(spelers);
		return scores;
	}

}
