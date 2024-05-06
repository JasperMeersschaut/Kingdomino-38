
package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Spel;
import domein.Speler;
import domein.Vak;
import exceptions.AantalSpelersOngeldigException;
import utils.Landschap;

class SpelTest {

	private Spel spel;

	@BeforeEach
	void setUp() {
		spel = new Spel();
	}

	@Test
	void voegSpelerToe_JuisteWaarden_VoegtSpelerToe() {
		Speler speler = new Speler("TestSpeler", 2000);
		spel.voegSpelerToeAanSpel(speler);
		assertEquals(1, spel.geefSpelers().size());
		assertEquals(speler, spel.geefSpelers().get(0));
	}

	// Testen op spel starten
	@Test
	void startSpel_3Spelers_MaaktSpel() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);
		spel.voegSpelerToeAanSpel(speler1);
		spel.voegSpelerToeAanSpel(speler2);
		spel.voegSpelerToeAanSpel(speler3);
		spel.startSpel();
		assertEquals(3, spel.geefSpelers().size());
		assertEquals(3, spel.geefStartKolom().size());
		assertEquals(33, spel.geefStapel().size());
	}

	@Test
	void startSpel_4Spelers_MaaktSpel() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);
		Speler speler4 = new Speler("Speler 4", 2000);
		spel.voegSpelerToeAanSpel(speler1);
		spel.voegSpelerToeAanSpel(speler2);
		spel.voegSpelerToeAanSpel(speler3);
		spel.voegSpelerToeAanSpel(speler4);
		spel.startSpel();
		assertEquals(4, spel.geefSpelers().size());
		assertEquals(4, spel.geefStartKolom().size());
		assertEquals(44, spel.geefStapel().size());
	}

	@Test
	public void startSpel_2Spelers_ExceptionThrown() {
		Spel spel = new Spel();
		Speler speler1 = new Speler("Speler1", 2000);
		Speler speler2 = new Speler("Speler2", 2000);
		spel.voegSpelerToeAanSpel(speler1);
		spel.voegSpelerToeAanSpel(speler2);
		assertThrows(AantalSpelersOngeldigException.class, () -> spel.startSpel());
	}

	@Test
	void legTegelInKoninkrijk_ValidInput_PlacesTileInKingdom() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);
		spel.voegSpelerToeAanSpel(speler1);
		spel.voegSpelerToeAanSpel(speler2);
		spel.voegSpelerToeAanSpel(speler3);
		spel.startSpel();
		speler1.geefKoninkrijk().legTegelInKoninkrijk(1, 4, 5, 1);
		assertEquals(Landschap.ZAND, speler1.geefKoninkrijk().geefKoninkrijk()[4][5].geefLandschap());
	}

	@Test
	void legTegelInKoninkrijk_InvalidTegelNummer_ThrowsException() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);
		spel.voegSpelerToeAanSpel(speler1);
		spel.voegSpelerToeAanSpel(speler2);
		spel.voegSpelerToeAanSpel(speler3);
		spel.startSpel();
		assertThrows(NullPointerException.class, () -> {
			speler1.geefKoninkrijk().legTegelInKoninkrijk(999, 4, 5, 1);
		});
	}

	@Test
	void legTegelInKoninkrijk_InvalidRichtingTePlaatsen_ThrowsException() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);
		spel.voegSpelerToeAanSpel(speler1);
		spel.voegSpelerToeAanSpel(speler2);
		spel.voegSpelerToeAanSpel(speler3);
		spel.startSpel();
		assertThrows(NullPointerException.class, () -> {
			speler1.geefKoninkrijk().legTegelInKoninkrijk(1, 4, 5, 999);
		});
	}

	@Test
	void berekenScore_gegevensZoalsFoto_BerekentScore() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Vak[][] koningrijk = speler1.geefKoninkrijk().geefKoninkrijk();
		koningrijk[4][5] = new Vak(Landschap.ZAND, 1);
		koningrijk[4][6] = new Vak(Landschap.GRAS, 0);
		koningrijk[4][7] = new Vak(Landschap.GRAS, 0);
		koningrijk[4][8] = new Vak(Landschap.GRAS, 0);
		koningrijk[5][4] = new Vak(Landschap.ZAND, 0);
		koningrijk[5][5] = new Vak(Landschap.ZAND, 0);
		koningrijk[5][6] = new Vak(Landschap.GRAS, 2);
		koningrijk[6][4] = new Vak(Landschap.AARDE, 0);
		koningrijk[6][5] = new Vak(Landschap.WATER, 1);
		koningrijk[6][6] = new Vak(Landschap.WATER, 0);
		koningrijk[6][7] = new Vak(Landschap.BOS, 0);
		koningrijk[7][4] = new Vak(Landschap.AARDE, 0);
		koningrijk[7][5] = new Vak(Landschap.WATER, 0);
		koningrijk[7][6] = new Vak(Landschap.WATER, 0);
		koningrijk[7][7] = new Vak(Landschap.WATER, 1);
		koningrijk[8][4] = new Vak(Landschap.MIJN, 2);
		assertEquals(23, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void berekenScore_LeegKoninkrijk_Returns0() {
		Speler speler1 = new Speler("Speler 1", 2000);
		assertEquals(0, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void berekenScore_EnkeleTegelZonderKroon_Returns0() {
		Speler speler1 = new Speler("Speler 1", 2000);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][2] = new Vak(Landschap.GRAS, 0);
		assertEquals(0, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void berekenScore_EnkeleTegelMetKroon_Returns1() {
		Speler speler1 = new Speler("Speler 1", 2000);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][2] = new Vak(Landschap.GRAS, 1);
		assertEquals(1, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void berekenScore_MeerdereTegelsZonderKronen_Returns0() {
		Speler speler1 = new Speler("Speler 1", 2000);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][2] = new Vak(Landschap.GRAS, 0);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][3] = new Vak(Landschap.GRAS, 0);
		assertEquals(0, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void berekenScore_MeerdereTegelsMetZelfdeKornen_ReturnsScore() {
		Speler speler1 = new Speler("Speler 1", 2000);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][2] = new Vak(Landschap.GRAS, 1);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][3] = new Vak(Landschap.GRAS, 0);
		assertEquals(2, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void berekenScore_MeerdereTegelsVerschillendeKronen_ReturnsScore() {
		Speler speler1 = new Speler("Speler 1", 2000);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][2] = new Vak(Landschap.GRAS, 1);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][3] = new Vak(Landschap.ZAND, 1);
		assertEquals(2, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void berekenScore_NullKoninkrijk_ThrowsException() {
		Speler speler1 = new Speler("Speler 1", 2000);
		assertEquals(0, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void berekenScore_KoninkrijkZonderTegels_Returns0() {
		Speler speler1 = new Speler("Speler 1", 2000);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][2] = new Vak(Landschap.LEEG, 0);
		assertEquals(0, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void berekenScore_KoninkrijkMetEnkelKasteel_Returns0() {
		Speler speler1 = new Speler("Speler 1", 2000);
		speler1.geefKoninkrijk().geefKoninkrijk()[2][2] = new Vak(Landschap.KASTEEL, 0);
		assertEquals(0, speler1.geefKoninkrijk().berekenScore());
	}

	@Test
	void geefWinnaars_1winnaar_ReturnsWinnaar() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);
		spel.voegSpelerToeAanSpel(speler1);
		spel.voegSpelerToeAanSpel(speler2);
		spel.voegSpelerToeAanSpel(speler3);
		spel.startSpel();
		speler1.geefKoninkrijk().geefKoninkrijk()[2][3] = new Vak(Landschap.ZAND, 1);
		List<Speler> winnaars = spel.geefWinnaars();
		assertEquals(1, winnaars.size());
		assertEquals(speler1, winnaars.get(0));
	}

	@Test
	void geefWinnaars_MeerdereWinaars_ReturnsAlleWinnaars() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);
		spel.voegSpelerToeAanSpel(speler1);
		spel.voegSpelerToeAanSpel(speler2);
		spel.voegSpelerToeAanSpel(speler3);
		spel.startSpel();
		speler1.geefKoninkrijk().geefKoninkrijk()[2][3] = new Vak(Landschap.ZAND, 1);
		speler2.geefKoninkrijk().geefKoninkrijk()[2][3] = new Vak(Landschap.ZAND, 1);
		List<Speler> winnaars = spel.geefWinnaars();
		assertEquals(2, winnaars.size());
		assertTrue(winnaars.contains(speler1));
		assertTrue(winnaars.contains(speler2));
	}

}
