package domein;

import java.time.LocalDate;

public class Speler {
	private String gebruikersnaam;
	private int geboortejaar;
	private int aantalGewonnen, aantalGespeeld;

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
		if (gebruikersnaam.length() < 6 && gebruikersnaam.isBlank()) {
			throw new IllegalArgumentException(
					"Gebruikersnaam moet minstens 6 karakters bevatten en mag niet leeg zijn.");
		} else {
			this.gebruikersnaam = gebruikersnaam;
		}

	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	private void setGeboortejaar(int geboortejaar) {
		if (geboortejaar - LocalDate.now().getYear() < 6) {

			throw new IllegalArgumentException("Speler moet minstens 6 jaar zijn.");
		} else {
			this.geboortejaar = geboortejaar;
		}
	}

	public int getAantalGewonnen() {
		return aantalGewonnen;
	}

	private void setAantalGewonnen(int aantalGewonnen) {
		if (aantalGewonnen != 0) {
			throw new IllegalArgumentException("Aantal gewonnen moet 0 zijn.");
		} else {
			this.aantalGewonnen = aantalGewonnen;
		}
	}

	public int getAantalGespeeld() {
		return aantalGespeeld;
	}

	private void setAantalGespeeld(int aantalGespeeld) {
		if (aantalGespeeld != 0) {
			throw new IllegalArgumentException("Aantal gespeeld moet 0 zijn.");
		} else {
			this.aantalGespeeld = aantalGespeeld;
		}
	}

}
