package testen;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Speler;
import exceptions.GeboortejaarOngeldigException;
import exceptions.GebruikersnaamOngeldigException;
import utils.Kleur;

class SpelerTest {

	private Speler speler;

	@Test
	void maakSpeler_alleGegevensCorrect_maaktObject() {
		speler = new Speler("avatar", 2003, 4, 25);
		Assertions.assertEquals("avatar", speler.getGebruikersnaam());
		Assertions.assertEquals(2003, speler.getGeboortejaar());
		Assertions.assertEquals(4, speler.getAantalGewonnen());
		Assertions.assertEquals(25, speler.getAantalGespeeld());
	}

	@Test
	void maakSpeler_correcteGebruikersnaamGeboortejaar_maaktObject() {
		speler = new Speler("avatar", 2003);
		Assertions.assertEquals("avatar", speler.getGebruikersnaam());
		Assertions.assertEquals(2003, speler.getGeboortejaar());
		Assertions.assertEquals(0, speler.getAantalGewonnen());
		Assertions.assertEquals(0, speler.getAantalGespeeld());
	}

	@Test
	void voegSpelerToe_FoutJaar_ThrowException() {

		assertThrows(GeboortejaarOngeldigException.class, () -> {
			Speler speler = new Speler("Speler 1", 2030);
		});
	}

	@Test
	void maakSpeler_NietBestaandeKleur_ThrowException() {
		assertThrows(IllegalArgumentException.class, () -> {
			Speler speler = new Speler("Speler 1", 2000);
			speler.setKleur(Kleur.valueOf("NietBestaandeKleur"));
		});
	}

	@ParameterizedTest
	@ValueSource(strings = { "avatar", "player1", "testUser" })
	void maakSpeler_GeldigeGebruikersnaam_CreatesObject(String gebruikersnaam) {
		Speler speler = new Speler(gebruikersnaam, 2000);
		Assertions.assertEquals(gebruikersnaam, speler.getGebruikersnaam());
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "a", "ab", "abc", "abcd", "abcde" })
	void maakSpeler_OngeldigeGebruikersnaam_ThrowsException(String gebruikersnaam) {
		assertThrows(GebruikersnaamOngeldigException.class, () -> {
			new Speler(gebruikersnaam, 2000);
		});
	}

	@ParameterizedTest
	@ValueSource(ints = { 2000, 1990, 1980 })
	void maakSpeler_GeldigGeboortejaar_CreatesObject(int geboortejaar) {
		Speler speler = new Speler("gebruiker", geboortejaar);
		Assertions.assertEquals(geboortejaar, speler.getGeboortejaar());
	}

	@ParameterizedTest
	@ValueSource(ints = { 2030, 1870, 1800 })
	void maakSpeler_OngeldigGeboortejaar_ThrowsException(int geboortejaar) {
		assertThrows(GeboortejaarOngeldigException.class, () -> {
			new Speler("gebruiker", geboortejaar);
		});
	}

}
