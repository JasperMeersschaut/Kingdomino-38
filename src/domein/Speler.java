
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
	private final int aantalGewonnenSpellen;
	private final int aantalGespeeldeSpellen;
	private Kleur kleur;
	private final Koninkrijk koninkrijk;
	private static final int MIN_LENGTE_GERUIKERSNAAM = 6;
	private static final int MIN_LEEFTIJD = 6;
	private static final int MAX_LEEFTIJD = 150;

	/**
	 * Constructor voor een Speler.
	 *
	 * @param gebruikersnaam de gebruikersnaam van de speler.
	 * @param geboortejaar   het geboortejaar van de speler.
	 */
	public Speler(String gebruikersnaam, int geboortejaar) {
		this(gebruikersnaam, geboortejaar, 0, 0);
	}

	/**
	 * Constructor voor een Speler.
	 *
	 * @param gebruikersnaam         de gebruikersnaam van de speler.
	 * @param geboortejaar           het geboortejaar van de speler.
	 * @param aantalGewonnenSpellen  het aantal gewonnen spellen van de speler.
	 * @param aantalGespeeldeSpellen het aantal gespeelde spellen van de speler.
	 */
	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnenSpellen, int aantalGespeeldeSpellen) {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		stelGebruikersnaamIn(gebruikersnaam);
		stelGeboortejaarIn(geboortejaar);
		this.aantalGewonnenSpellen = aantalGewonnenSpellen;
		this.aantalGespeeldeSpellen = aantalGespeeldeSpellen;
		this.koninkrijk = new Koninkrijk();
	}

	/**
	 * Geeft de gebruikersnaam van de speler.
	 *
	 * @return de gebruikersnaam van de speler.
	 */
	public String geefGebruikersnaam() {
		return gebruikersnaam;
	}

	private void stelGebruikersnaamIn(String gebruikersnaam) {
		if (gebruikersnaam.length() < MIN_LENGTE_GERUIKERSNAAM || gebruikersnaam.isBlank())
			throw new GebruikersnaamOngeldigException(
					String.format(messages.getString("invalid_username"), MIN_LENGTE_GERUIKERSNAAM));
		this.gebruikersnaam = gebruikersnaam;
	}

	/**
	 * Geeft het geboortejaar van de speler.
	 *
	 * @return het geboortejaar van de speler.
	 */
	public int geefGeboortejaar() {
		return geboortejaar;
	}

	private void stelGeboortejaarIn(int geboortejaar) {
		int huidigJaar = LocalDate.now().getYear();
		if (geboortejaar > huidigJaar)
			throw new GeboortejaarOngeldigException(messages.getString("invalid_birthyear"));
		if (huidigJaar - geboortejaar < MIN_LEEFTIJD)
			throw new GeboortejaarOngeldigException(String.format(messages.getString("invalid_age"), MIN_LEEFTIJD));
		if (huidigJaar - geboortejaar > MAX_LEEFTIJD)
			throw new GeboortejaarOngeldigException(messages.getString("invalid_birthyear"));
		this.geboortejaar = geboortejaar;
	}

	/**
	 * Geeft het aantal gewonnen spellen van de speler.
	 *
	 * @return het aantal gewonnen spellen van de speler.
	 */
	public int geefAantalGewonnenSpellen() {
		return aantalGewonnenSpellen;
	}

	/**
	 * Geeft het aantal gespeelde spellen van de speler.
	 *
	 * @return het aantal gespeelde spellen van de speler.
	 */
	public int geefAantalGespeeldeSpellen() {
		return aantalGespeeldeSpellen;
	}

	/**
	 * Geeft de kleur van de speler.
	 *
	 * @return de kleur van de speler.
	 */
	public Kleur geefKleur() {
		return kleur;
	}

	/**
	 * Stelt de kleur van de speler in.
	 *
	 * @param kleur de kleur die moet worden ingesteld.
	 */
	public void stelKleurIn(Kleur kleur) {
		this.kleur = kleur;
	}

	/**
	 * Geeft het koninkrijk van de speler.
	 *
	 * @return het koninkrijk van de speler.
	 */
	public Koninkrijk geefKoninkrijk() {
		return koninkrijk;
	}

	/**
	 * Controleert of een object gelijk is aan deze speler.
	 *
	 * @param speler het object om te vergelijken met deze speler.
	 * @return true als het object gelijk is aan deze speler, anders false.
	 */
	@Override
	public boolean equals(Object speler) {
		if (this == speler)
			return true;
		if (speler == null || this.getClass() != speler.getClass())
			return false;
		Speler spelerObj = (Speler) speler;
		return this.gebruikersnaam.equals(spelerObj.gebruikersnaam) && this.geboortejaar == spelerObj.geboortejaar;
	}

	/**
	 * Geeft een hashcode voor deze speler.
	 *
	 * @return een hashcode voor deze speler.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(gebruikersnaam, geboortejaar);
	}

}
