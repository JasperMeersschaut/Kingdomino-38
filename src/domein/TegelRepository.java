
package domein;

import java.util.ArrayList;
import java.util.List;

import utils.Landschap;

public class TegelRepository {

	private List<Tegel> tegels;
	private Spel spel;

	public TegelRepository() {
		tegels = new ArrayList<>();
		tegels.add(new Tegel(1, Landschap.ZAND, Landschap.ZAND, 0));
		tegels.add(new Tegel(2, Landschap.ZAND, Landschap.ZAND, 0));
		tegels.add(new Tegel(3, Landschap.BOS, Landschap.BOS, 0));
		tegels.add(new Tegel(4, Landschap.BOS, Landschap.BOS, 0));
		tegels.add(new Tegel(5, Landschap.BOS, Landschap.BOS, 0));
		tegels.add(new Tegel(6, Landschap.BOS, Landschap.BOS, 0));
		tegels.add(new Tegel(7, Landschap.WATER, Landschap.WATER, 0));
		tegels.add(new Tegel(8, Landschap.WATER, Landschap.WATER, 0));
		tegels.add(new Tegel(9, Landschap.WATER, Landschap.WATER, 0));
		tegels.add(new Tegel(10, Landschap.GRAS, Landschap.GRAS, 0));
		tegels.add(new Tegel(11, Landschap.GRAS, Landschap.GRAS, 0));
		tegels.add(new Tegel(12, Landschap.AARDE, Landschap.AARDE, 0));
		tegels.add(new Tegel(13, Landschap.ZAND, Landschap.BOS, 0));
		tegels.add(new Tegel(14, Landschap.ZAND, Landschap.WATER, 0));
		tegels.add(new Tegel(15, Landschap.ZAND, Landschap.GRAS, 0));
		tegels.add(new Tegel(16, Landschap.ZAND, Landschap.AARDE, 0));
		tegels.add(new Tegel(17, Landschap.BOS, Landschap.WATER, 0));
		tegels.add(new Tegel(18, Landschap.BOS, Landschap.GRAS, 0));
		tegels.add(new Tegel(19, Landschap.ZAND, Landschap.WATER, 1));
		tegels.add(new Tegel(20, Landschap.ZAND, Landschap.BOS, 1));
		tegels.add(new Tegel(21, Landschap.ZAND, Landschap.GRAS, 1));
		tegels.add(new Tegel(22, Landschap.ZAND, Landschap.AARDE, 1));
		tegels.add(new Tegel(23, Landschap.ZAND, Landschap.MIJN, 1));
		tegels.add(new Tegel(24, Landschap.BOS, Landschap.ZAND, 1));
		tegels.add(new Tegel(25, Landschap.BOS, Landschap.ZAND, 1));
		tegels.add(new Tegel(26, Landschap.BOS, Landschap.ZAND, 1));
		tegels.add(new Tegel(27, Landschap.BOS, Landschap.ZAND, 1));
		tegels.add(new Tegel(28, Landschap.BOS, Landschap.WATER, 1));
		tegels.add(new Tegel(29, Landschap.BOS, Landschap.GRAS, 1));
		tegels.add(new Tegel(30, Landschap.WATER, Landschap.ZAND, 1));
		tegels.add(new Tegel(31, Landschap.WATER, Landschap.ZAND, 1));
		tegels.add(new Tegel(32, Landschap.WATER, Landschap.BOS, 1));
		tegels.add(new Tegel(33, Landschap.WATER, Landschap.BOS, 1));
		tegels.add(new Tegel(34, Landschap.WATER, Landschap.BOS, 1));
		tegels.add(new Tegel(35, Landschap.WATER, Landschap.BOS, 1));
		tegels.add(new Tegel(36, Landschap.ZAND, Landschap.GRAS, 1));
		tegels.add(new Tegel(37, Landschap.WATER, Landschap.GRAS, 1));
		tegels.add(new Tegel(38, Landschap.ZAND, Landschap.AARDE, 1));
		tegels.add(new Tegel(39, Landschap.GRAS, Landschap.AARDE, 1));
		tegels.add(new Tegel(40, Landschap.MIJN, Landschap.ZAND, 1));
		tegels.add(new Tegel(41, Landschap.ZAND, Landschap.GRAS, 2));
		tegels.add(new Tegel(42, Landschap.WATER, Landschap.GRAS, 2));
		tegels.add(new Tegel(43, Landschap.ZAND, Landschap.AARDE, 2));
		tegels.add(new Tegel(44, Landschap.GRAS, Landschap.AARDE, 2));
		tegels.add(new Tegel(45, Landschap.MIJN, Landschap.ZAND, 2));
		tegels.add(new Tegel(46, Landschap.AARDE, Landschap.MIJN, 2));
		tegels.add(new Tegel(47, Landschap.AARDE, Landschap.MIJN, 2));
		tegels.add(new Tegel(48, Landschap.ZAND, Landschap.MIJN, 3));
		tegels = shuffleTegels();
	}

	public List<Tegel> geeftegels() {
		return tegels;
	}

	private List<Tegel> shuffleTegels() {
		List<Tegel> tegels = new ArrayList<>();
		int indexVoorTegel;
		for (Tegel tegel : this.tegels) {
			indexVoorTegel = (int) Math.round(Math.random() * tegels.size());
			tegels.add(indexVoorTegel, tegel);
		}
		return tegels;
	}
// Er moet nog toegevoegd worden dat er maar 36 tegels gebruikt worden als er 3 spelers zijn. Moet niet gebeuren in deze klasse maar kan opzich wel. Mss best in spel.java
}
