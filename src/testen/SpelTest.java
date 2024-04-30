package testen;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Spel;
import domein.Speler;
import domein.Vak;
import exceptions.AantalSpelersOngeldigException;
import utils.Kleur;
import utils.Landschap;

class SpelTest {
	private Spel spel;

	@BeforeEach
	void setUp() {
		spel = new Spel();
	}

	// Testen op spelers toevoegen
	@Test
	void voegSpelerToe_JuisteWaarden_VoegtSpelerToe() {
		Speler speler = new Speler("TestSpeler", 2000);
		spel.voegSpelerToe(speler, Kleur.GROEN);
		assertEquals(1, spel.getSpelers().size());
		assertEquals(speler, spel.getSpelers().get(0));
	}

	// Testen op spel starten
	@Test
	void startSpel_3Spelers_MaaktSpel() {

		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);

		spel.voegSpelerToe(speler1, Kleur.GROEN);
		spel.voegSpelerToe(speler2, Kleur.BLAUW);
		spel.voegSpelerToe(speler3, Kleur.GEEL);
		spel.startSpel();

		assertEquals(3, spel.getSpelers().size());
		assertEquals(3, spel.getStartKolom().size());
		assertEquals(33, spel.getStapel().size());
	}

	@Test
	void startSpel_4Spelers_MaaktSpel() {

		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);
		Speler speler4 = new Speler("Speler 4", 2000);

		spel.voegSpelerToe(speler1, Kleur.GROEN);
		spel.voegSpelerToe(speler2, Kleur.BLAUW);
		spel.voegSpelerToe(speler3, Kleur.GEEL);
		spel.voegSpelerToe(speler4, Kleur.ROOS);
		spel.startSpel();

		assertEquals(4, spel.getSpelers().size());
		assertEquals(4, spel.getStartKolom().size());
		assertEquals(44, spel.getStapel().size());
	}

	@Test
	public void startSpel_2Spelers_ExceptionThrown() {

		Spel spel = new Spel();
		Speler speler1 = new Speler("Speler1", 2000);
		Speler speler2 = new Speler("Speler2", 2000);
		spel.voegSpelerToe(speler1, Kleur.GROEN);
		spel.voegSpelerToe(speler2, Kleur.BLAUW);

		assertThrows(AantalSpelersOngeldigException.class, () -> {
			spel.startSpel();
		});
	}

	// Testen op tegels leggen
//	@Test
//	void legTegelInKoninkrijk_ValidInput_PlacesTileInKingdom() {
//		Speler speler1 = new Speler("Speler 1", 2000);
//		Speler speler2 = new Speler("Speler 2", 2000);
//		Speler speler3 = new Speler("Speler 3", 2000);
//
//		spel.voegSpelerToe(speler1, Kleur.GROEN);
//		spel.voegSpelerToe(speler2, Kleur.BLAUW);
//		spel.voegSpelerToe(speler3, Kleur.GEEL);
//		spel.startSpel();
//
//		spel.legTegelInKoninkrijk(1, "Speler 1", "44", 1);
//
//        assertEquals(1, speler1.getKoninkrijk()[4][4].getLandschap().AARDE);
//	}

	@Test
	void legTegelInKoninkrijk_InvalidTegelNummer_ThrowsException() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);

		spel.voegSpelerToe(speler1, Kleur.GROEN);
		spel.voegSpelerToe(speler2, Kleur.BLAUW);
		spel.voegSpelerToe(speler3, Kleur.GEEL);
		spel.startSpel();
		assertThrows(NullPointerException.class, () -> {
			spel.legTegelInKoninkrijk(999, "Speler 1", "44", 1); // 999 is an invalid tegelNummer
		});
	}

	@Test
	void legTegelInKoninkrijk_InvalidTegelVanSpelerString_ThrowsException() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);

		spel.voegSpelerToe(speler1, Kleur.GROEN);
		spel.voegSpelerToe(speler2, Kleur.BLAUW);
		spel.voegSpelerToe(speler3, Kleur.GEEL);
		spel.startSpel();
		assertThrows(NullPointerException.class, () -> {
			spel.legTegelInKoninkrijk(1, "Speler 4", "44", 1); // InvalidSpeler does not exist
		});
	}

	@Test
	void legTegelInKoninkrijk_InvalidRichtingTePlaatsen_ThrowsException() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);

		spel.voegSpelerToe(speler1, Kleur.GROEN);
		spel.voegSpelerToe(speler2, Kleur.BLAUW);
		spel.voegSpelerToe(speler3, Kleur.GEEL);
		spel.startSpel();
		assertThrows(NullPointerException.class, () -> {
			spel.legTegelInKoninkrijk(1, "Speler 1", "44", 999); // 999 is an invalid richtingTePlaatsen
		});
	}

	// Testen op score berekenen
	@Test
	void berekenScore_gegevensZoalsFoto_BerekentScore() {
		Vak[][] koningrijk = new Vak[5][5];
		koningrijk[0][1] = new Vak(Landschap.ZAND, 1);
		koningrijk[0][2] = new Vak(Landschap.GRAS, 0);
		koningrijk[0][3] = new Vak(Landschap.GRAS, 0);
		koningrijk[0][4] = new Vak(Landschap.GRAS, 0);
		koningrijk[1][0] = new Vak(Landschap.ZAND, 0);
		koningrijk[1][1] = new Vak(Landschap.ZAND, 0);
		koningrijk[1][2] = new Vak(Landschap.GRAS, 2);
		koningrijk[2][0] = new Vak(Landschap.AARDE, 0);
		koningrijk[2][1] = new Vak(Landschap.WATER, 1);
		koningrijk[2][2] = new Vak(Landschap.WATER, 0);
		koningrijk[2][3] = new Vak(Landschap.BOS, 0);
		koningrijk[3][0] = new Vak(Landschap.AARDE, 0);
		koningrijk[3][1] = new Vak(Landschap.WATER, 0);
		koningrijk[3][2] = new Vak(Landschap.WATER, 0);
		koningrijk[3][3] = new Vak(Landschap.WATER, 1);
		koningrijk[4][0] = new Vak(Landschap.MIJN, 2);
		assertEquals(23, new Spel().berekenScore(koningrijk));
	}

	@Test
	void berekenScore_LeegKoninkrijk_Returns0() {
		Vak[][] koninkrijk = new Vak[5][5];
		assertEquals(0, spel.berekenScore(koninkrijk));
	}

	@Test
	void berekenScore_EnkeleTegelZonderKroon_Returns0() {
		Vak[][] koningrijk = new Vak[5][5];
		koningrijk[2][2] = new Vak(Landschap.GRAS, 0);
		assertEquals(0, spel.berekenScore(koningrijk));
	}

	@Test
	void berekenScore_EnkeleTegelMetKroon_Returns1() {
		Vak[][] koninkrijk = new Vak[5][5];
		koninkrijk[2][2] = new Vak(Landschap.GRAS, 1);
		assertEquals(1, spel.berekenScore(koninkrijk));
	}

	@Test
	void berekenScore_MeerdereTegelsZonderKronen_Returns0() {
		Vak[][] koninkrijk = new Vak[5][5];
		koninkrijk[2][2] = new Vak(Landschap.GRAS, 0);
		koninkrijk[2][3] = new Vak(Landschap.GRAS, 0);
		assertEquals(0, spel.berekenScore(koninkrijk));
	}

	@Test
	void berekenScore_MeerdereTegelsMetZelfdeKornen_ReturnsScore() {
		Vak[][] koninkrijk = new Vak[5][5];
		koninkrijk[2][2] = new Vak(Landschap.GRAS, 1);
		koninkrijk[2][3] = new Vak(Landschap.GRAS, 0);
		assertEquals(2, spel.berekenScore(koninkrijk));
	}

	@Test
	void berekenScore_MeerdereTegelsVerschillendeKronen_ReturnsScore() {
		Vak[][] koninkrijk = new Vak[5][5];
		koninkrijk[2][2] = new Vak(Landschap.GRAS, 1);
		koninkrijk[2][3] = new Vak(Landschap.ZAND, 1);
		assertEquals(2, spel.berekenScore(koninkrijk));
	}

	@Test
	void berekenScore_NullKoninkrijk_ThrowsException() {
		Vak[][] koninkrijk = null;
		assertThrows(NullPointerException.class, () -> {
			spel.berekenScore(koninkrijk);
		});
	}

	@Test
	void berekenScore_KoninkrijkZonderTegels_Returns0() {
		Vak[][] koninkrijk = new Vak[5][5];
		koninkrijk[2][2] = new Vak(Landschap.LEEG, 0);
		assertEquals(0, spel.berekenScore(koninkrijk));
	}

	@Test
	void berekenScore_KoninkrijkMetEnkelKasteel_Returns0() {
		Vak[][] koninkrijk = new Vak[5][5];
		koninkrijk[2][2] = new Vak(Landschap.KASTEEL, 0);
		assertEquals(0, spel.berekenScore(koninkrijk));
	}

	// Geef winnaar testen
	@Test
	void geefWinnaars_1winnaar_ReturnsWinnaar() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);

		spel.voegSpelerToe(speler1, Kleur.GROEN);
		spel.voegSpelerToe(speler2, Kleur.BLAUW);
		spel.voegSpelerToe(speler3, Kleur.GEEL);
		spel.startSpel();
		// Speler 1 de meeste score geven
		Vak[][] koninkrijk = new Vak[5][5];
		koninkrijk[2][3] = new Vak(Landschap.ZAND, 1);
		speler1.setKoninkrijk(koninkrijk);

		List<Speler> winnaars = spel.geefWinnaars();
		assertEquals(1, winnaars.size());
		assertEquals(speler1, winnaars.get(0));
	}

	@Test
	void geefWinnaars_MeerdereWinaars_ReturnsAlleWinnaars() {
		Speler speler1 = new Speler("Speler 1", 2000);
		Speler speler2 = new Speler("Speler 2", 2000);
		Speler speler3 = new Speler("Speler 3", 2000);

		spel.voegSpelerToe(speler1, Kleur.GROEN);
		spel.voegSpelerToe(speler2, Kleur.BLAUW);
		spel.voegSpelerToe(speler3, Kleur.GEEL);
		spel.startSpel();

		Vak[][] koninkrijk = new Vak[5][5];
		koninkrijk[2][3] = new Vak(Landschap.ZAND, 1);
		speler1.setKoninkrijk(koninkrijk);
		speler2.setKoninkrijk(koninkrijk);

		List<Speler> winnaars = spel.geefWinnaars();
		assertEquals(2, winnaars.size());
		assertTrue(winnaars.contains(speler1));
		assertTrue(winnaars.contains(speler2));
	}

}