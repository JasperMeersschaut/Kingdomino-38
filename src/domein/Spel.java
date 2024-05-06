
package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import exceptions.AantalSpelersOngeldigException;
import exceptions.TegelAlGekozenException;
import exceptions.TegelNietInKolomException;
import utils.Taal;
import utils.TegelMaker;

public class Spel {

	private final ResourceBundle messages;
	private final List<Speler> spelers;
	private List<Tegel> startKolom;
	private List<Tegel> eindKolom;
	private List<Tegel> stapel;
	private Speler huidigeSpeler;

	/**
	 * Constructor voor een Spel.
	 */
	public Spel() {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		spelers = new ArrayList<>();
		stapel = new ArrayList<>();
		startKolom = new ArrayList<>();
		eindKolom = new ArrayList<>();
	}

	/**
	 * Geeft een lijst van spelers in het spel.
	 *
	 * @return een lijst van Spelers.
	 */
	public List<Speler> geefSpelers() {
		return spelers;
	}

	/**
	 * Geeft de startkolom van het spel.
	 *
	 * @return een lijst van Tegels.
	 */
	public List<Tegel> geefStartKolom() {
		return startKolom;
	}

	/**
	 * Geeft de eindkolom van het spel.
	 *
	 * @return een lijst van Tegels.
	 */
	public List<Tegel> geefEindKolom() {
		return eindKolom;
	}

	/**
	 * Geeft de stapel van het spel.
	 *
	 * @return een lijst van Tegels.
	 */
	public List<Tegel> geefStapel() {
		return stapel;
	}

	/**
	 * Geeft de huidige speler van het spel.
	 *
	 * @return een Speler.
	 */
	public Speler geefHuidigeSpeler() {
		return huidigeSpeler;
	}

	/**
	 * Voegt een speler toe aan het spel.
	 *
	 * @param speler de speler die wordt toegevoegd.
	 */
	public void voegSpelerToeAanSpel(Speler speler) {
		spelers.add(speler);
	}

	/**
	 * Start het spel.
	 */
	public void startSpel() {
		if (spelers.size() != 3 && spelers.size() != 4)
			throw new AantalSpelersOngeldigException(messages.getString("invalid_amount_of_players"));
		stapel.addAll(new TegelMaker().geeftegels());
		Collections.shuffle(stapel);
		startKolom = new ArrayList<>(stapel.subList(0, spelers.size()));
		Collections.sort(startKolom);
		stapel = new ArrayList<>(stapel.subList(spelers.size(), spelers.size() * 12));
		Collections.shuffle(spelers);
		huidigeSpeler = spelers.get(0);
	}

	/**
	 * Stelt de volgende speler in als de huidige speler.
	 */
	public void stelVolgendeSpelerAlsHuidigeSpelerIn() {
		if (eindKolom.isEmpty() && !stapel.isEmpty())
			huidigeSpeler = spelers.get((spelers.indexOf(huidigeSpeler) + 1) % spelers.size());
		else {
			boolean alleSpelersHebbenAlGeplaatst = true;
			for (Tegel tegel : startKolom) {
				Speler spelerOpTegel = tegel.geefSpelerOpTegel();
				if (spelerOpTegel != null && spelerOpTegel != huidigeSpeler) {
					huidigeSpeler = spelerOpTegel;
					alleSpelersHebbenAlGeplaatst = false;
					break;
				}
			}
			if (alleSpelersHebbenAlGeplaatst && !eindKolom.isEmpty())
				huidigeSpeler = eindKolom.get(0).geefSpelerOpTegel();
		}
	}

	/**
	 * Plaatst de koning van een speler op een tegel.
	 *
	 * @param gebruikersnaam de gebruikersnaam van de speler.
	 * @param tegelNummer    het nummer van de tegel.
	 */
	public void plaatsKoningOpTegel(String gebruikersnaam, int tegelNummer) {
		Speler koningVanSpeler = spelers.stream().filter(speler -> speler.geefGebruikersnaam().equals(gebruikersnaam))
				.findAny().orElse(null);
		if (koningVanSpeler == null)
			throw new NullPointerException();
		boolean plaatsInStartKolom = eindKolom.isEmpty();
		boolean tegelAlGekozen = (plaatsInStartKolom ? startKolom : eindKolom).stream()
				.anyMatch(tegel -> tegel.geefTegelNummer() == tegelNummer && tegel.geefSpelerOpTegel() != null);
		if (tegelAlGekozen) {
			String spelerOpAlGekozenTegel = (plaatsInStartKolom ? startKolom : eindKolom).stream()
					.filter(tegel -> tegel.geefTegelNummer() == tegelNummer).findAny().get().geefSpelerOpTegel()
					.geefGebruikersnaam();
			throw new TegelAlGekozenException(
					String.format(messages.getString("tile_already_taken"), spelerOpAlGekozenTegel));
		}
		Tegel tegelTeLeggen = (plaatsInStartKolom ? startKolom : eindKolom).stream()
				.filter(tegel -> tegel.geefTegelNummer() == tegelNummer).findAny().orElse(null);
		if (tegelTeLeggen == null)
			throw new TegelNietInKolomException(
					messages.getString(plaatsInStartKolom ? "tile_not_in_startcolumn" : "tile_not_in_endcolumn"));
		tegelTeLeggen.stelSpelerOpTegelIn(koningVanSpeler);
	}

	/**
	 * Vult de stapels en kolommen van het spel aan.
	 */
	public void vulStapelsEnKolommenAan() {
		if (!eindKolom.isEmpty()) {
			startKolom = new ArrayList<>(eindKolom);
			eindKolom.clear();
		}
		if (!stapel.isEmpty()) {
			eindKolom = new ArrayList<>(stapel.subList(0, spelers.size()));
			Collections.sort(eindKolom);
			stapel = new ArrayList<>(stapel.subList(spelers.size(), stapel.size()));
		}
	}

	/**
	 * Geeft de winnaars van het spel.
	 *
	 * @return een lijst van Speler objecten.
	 */
	public List<Speler> geefWinnaars() {
		List<Speler> winnaars = new ArrayList<>();
		int scoreSpeler;
		int aantalTegelsInGrootsteDomeinSpeler;
		int aantalKronenSpeler;
		int scoreWinnaar = 0;
		int aantalTegelsInGrootsteDomeinWinnaar = 0;
		int aantalKronenWinnaar = 0;
		for (Speler speler : spelers) {
			Koninkrijk koninkrijk = speler.geefKoninkrijk();
			scoreSpeler = koninkrijk.berekenScore();
			aantalTegelsInGrootsteDomeinSpeler = koninkrijk.telAantalTegelsInGrootsteDomein();
			aantalKronenSpeler = koninkrijk.telAantalKronen();
			boolean hogereScore = scoreSpeler > scoreWinnaar;
			boolean meerTegelsInGrootsteDomein = scoreSpeler == scoreWinnaar
					&& aantalTegelsInGrootsteDomeinSpeler > aantalTegelsInGrootsteDomeinWinnaar;
			boolean meerKronenInKoninkrijk = scoreSpeler == scoreWinnaar
					&& aantalTegelsInGrootsteDomeinSpeler == aantalTegelsInGrootsteDomeinWinnaar
					&& aantalKronenSpeler > aantalKronenWinnaar;
			boolean zelfdeScoreEnAantalTegelsInGrootsteDomeinEnAantalKronen = scoreSpeler == scoreWinnaar
					&& aantalTegelsInGrootsteDomeinSpeler == aantalTegelsInGrootsteDomeinWinnaar
					&& aantalKronenSpeler == aantalKronenWinnaar;
			if (hogereScore || meerTegelsInGrootsteDomein || meerKronenInKoninkrijk) {
				scoreWinnaar = scoreSpeler;
				aantalTegelsInGrootsteDomeinWinnaar = aantalTegelsInGrootsteDomeinSpeler;
				aantalKronenWinnaar = aantalKronenSpeler;
				winnaars.clear();
				winnaars.add(speler);
			} else
				if (zelfdeScoreEnAantalTegelsInGrootsteDomeinEnAantalKronen)
					winnaars.add(speler);
		}
		return winnaars;
	}

}
