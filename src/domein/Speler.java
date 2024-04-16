
package domein;

import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import exceptions.GeboortejaarOngeldigException;
import exceptions.GebruikersnaamOngeldigException;
import utils.Kleur;
import utils.Taal;

public class Speler {

	private final ResourceBundle messages;
	private String gebruikersnaam;
	private int geboortejaar;
	private int aantalGewonnen;
	private int aantalGespeeld;
	private Kleur kleur;
	private Vak[][] koninkrijk;
	private static final int MIN_LENGTE_GERUIKERSNAAM = 6;
	private static final int MIN_LEEFTIJD = 6;
	private static final int MAX_LEEFTIJD = 150;

	public Speler(String gebruikersnaam, int geboortejaar) {
		this(gebruikersnaam, geboortejaar, 0, 0);
	}

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld) {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
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
			throw new GebruikersnaamOngeldigException(
					String.format(messages.getString("invalid_username"), MIN_LENGTE_GERUIKERSNAAM));
		else
			this.gebruikersnaam = gebruikersnaam;
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	private void setGeboortejaar(int geboortejaar) {
		int huidigJaar = LocalDate.now().getYear();
		if (geboortejaar > huidigJaar)
			throw new GeboortejaarOngeldigException(messages.getString("invalid_birthyear"));
		if (huidigJaar - geboortejaar < MIN_LEEFTIJD)
			throw new GeboortejaarOngeldigException(String.format(messages.getString("invalid_age"), MIN_LEEFTIJD));
		if (huidigJaar - geboortejaar > MAX_LEEFTIJD)
			throw new GeboortejaarOngeldigException(messages.getString("invalid_birthyear"));
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

	public Kleur getKleur() {
		return kleur;
	}

	public void setKleur(Kleur kleur) {
		this.kleur = kleur;
	}

	public Vak[][] getKoninkrijk() {
		return koninkrijk;
	}

	public void setKoninkrijk(Vak[][] koninkrijk) {
		this.koninkrijk = koninkrijk;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || this.getClass() != o.getClass())
			return false;
		Speler speler = (Speler) o;
		return this.gebruikersnaam.equals(speler.gebruikersnaam) && this.geboortejaar == speler.geboortejaar;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gebruikersnaam, geboortejaar);
	}

}
