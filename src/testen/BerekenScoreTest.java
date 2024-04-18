
package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import domein.Spel;
import domein.Vak;
import utils.Landschap;

class BerekenScoreTest {

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

}
