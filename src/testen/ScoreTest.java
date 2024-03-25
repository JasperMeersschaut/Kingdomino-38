package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domein.Spel;
import domein.Speler;
import domein.Vak;
import dto.SpelerDTO;
import utils.Kleur;
import utils.Landschap;

public class ScoreTest {

	private Spel spel;
	private Speler speler;

	@Test
	void berekenScore_geeftCorrecteScore() {
		Vak[][] koninkrijk = { { new Vak(Landschap.BOS, 1), new Vak(Landschap.BOS, 0), new Vak(Landschap.BOS, 1) },
				{ new Vak(Landschap.GRAS, 0), new Vak(Landschap.GRAS, 2), new Vak(Landschap.GRAS, 1) },
				{ new Vak(Landschap.AARDE, 0), new Vak(Landschap.AARDE, 0), new Vak(Landschap.AARDE, 0) } };

		SpelerDTO speler = new SpelerDTO(new Speler("SamSam", 2004), Kleur.BLAUW, koninkrijk);

		int verwachteScore = 3 * 2 + 3 * 3 + 3 * 0;
		int score = new Spel().berekenScore(speler);

		System.out.println("uiteindelijke score: " + score);

		Assertions.assertEquals(verwachteScore, score);
	}
}
