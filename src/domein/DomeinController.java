
package domein;

import java.util.ArrayList;
import java.util.List;

import dto.SpelerDTO;
import utils.Kleur;

public class DomeinController {

	private static SpelerRepository spelerRepository;
	private Spel spel;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegToe(new Speler(gebruikersnaam, geboortejaar));
	}

	public void maakSpel() {
		spel = new Spel();
	}

	public List<SpelerDTO> geefBeschikbareSpelers() {
		List<SpelerDTO> alleSpelers = new ArrayList<>();
		for (Speler speler : spelerRepository.geefAlleSpelers())
			alleSpelers.add(new SpelerDTO(speler, null, null));
		return spel.geefBeschikbareSpelers(alleSpelers);
	}

	public boolean isSpelerGeldig(String naam) {
		boolean spelerGeldig = false;
		List<SpelerDTO> alleSpelers = new ArrayList<>();
		for (Speler speler : spelerRepository.geefAlleSpelers())
			alleSpelers.add(new SpelerDTO(speler, null, null));
		for (SpelerDTO spelerDTO : spel.geefBeschikbareSpelers(alleSpelers))
			if (spelerDTO.speler().getGebruikersnaam().equalsIgnoreCase(naam)) {
				spelerGeldig = true;
				break;
			}
		return spelerGeldig;
	}

	public List<Kleur> geefBeschikbareKleuren() {
		return spel.geefBeschikbareKleuren();
	}

	public boolean isKleurGeldig(Kleur kleur) {
		return spel.geefBeschikbareKleuren().contains(kleur);
	}

	public void voegSpelerToe(String naam, Kleur kleur) {
		Vak[][] koningrijk = new Vak[5][5];
		spel.voegSpelerToe(new SpelerDTO(spelerRepository.geefSpeler(naam), kleur, koningrijk));
	}

	public void initialiseerStapels() {
		spel.initialiseerStapels();
	}

	public String toonSpelOverzicht() {
		return spel.toonSpelOverzicht();
	}

	public int geefAantalSpelers() {
		return spel.getSpelers().size();
	}

	public void kiesVolgendeSpeler() {
		spel.kiesVolgendeSpeler();
	}

	public SpelerDTO geefHuidigeSpeler() {
		return spel.getHuidigeSpeler();
	}

	public void kiesTegelStartkolom(int nr) {
		spel.kiesTegelStartkolom(nr);
	}

	public boolean DR_EINDE_SPEL() {
		return spel.getStapel().isEmpty();
	}

	public void vulStartKolomAan() {
		spel.vulStartKolomAan();
	}

	public SpelerDTO geefWinnaar() {
		int hoogsteScore = 0;
		SpelerDTO winnaar = null;
		for (SpelerDTO speler : spel.getSpelers())
			if (spel.berekenScore(speler) > hoogsteScore) {
				hoogsteScore = spel.berekenScore(speler);
				winnaar = speler;
			}
		return winnaar;
	}

}
