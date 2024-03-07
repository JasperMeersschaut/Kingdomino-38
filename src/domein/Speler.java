
package domein;

import java.time.LocalDate;

public class Speler {

	private String gebruikersnaam;
	private int geboortejaar;
	private int aantalGewonnen, aantalGespeeld;
	private static final int MIN_LENGTE_GERUIKERSNAAM = 6;
	private static final int MIN_LEEFTIJD = 6;
	private static final int MAX_LEEFTIJD = 150;

	public Speler(String gebruikersnaam, int geboortejaar) {
		this(gebruikersnaam, geboortejaar, 0, 0);
	}

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		setAantalGewonnen(aantalGewonnen);
		setAantalGespeeld(aantalGespeeld);
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	private void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam.length() < MIN_LENGTE_GERUIKERSNAAM || gebruikersnaam.isBlank())
			throw new IllegalArgumentException(
					String.format("Gebruikersnaam moet minstens %d karakters bevatten en mag niet leeg zijn.",
							MIN_LENGTE_GERUIKERSNAAM));
		else
			this.gebruikersnaam = gebruikersnaam;
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	private void setGeboortejaar(int geboortejaar) {
		int huidigJaar = LocalDate.now().getYear();
		if (huidigJaar - geboortejaar < MIN_LEEFTIJD)
			throw new IllegalArgumentException(String.format("Speler moet minstens %d jaar zijn.", MIN_LEEFTIJD));
		if (huidigJaar - geboortejaar > MAX_LEEFTIJD)
			throw new IllegalArgumentException("Geboortejaar is niet geldig.");
		this.geboortejaar = geboortejaar;
	}

	public int getAantalGewonnen() {
		return aantalGewonnen;
	}

	public void setAantalGewonnen(int aantalGewonnen) {
		this.aantalGewonnen = aantalGewonnen;
	}

	public int getAantalGespeeld() {
		return aantalGespeeld;
	}

	public void setAantalGespeeld(int aantalGespeeld) {
		this.aantalGespeeld = aantalGespeeld;
	}

}
