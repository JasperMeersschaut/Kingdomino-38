
package utils;

import java.util.ArrayList;
import java.util.List;

import domein.Tegel;
import domein.Vak;

public class TegelMaker {

	private final List<Tegel> tegels;

	/**
	 * Constructor voor de TegelMaker klasse. Initialiseert de lijst van tegels.
	 */
	public TegelMaker() {
		tegels = new ArrayList<>();
		tegels.add(new Tegel(1, new Vak(Landschap.ZAND, 0), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(2, new Vak(Landschap.ZAND, 0), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(3, new Vak(Landschap.BOS, 0), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(4, new Vak(Landschap.BOS, 0), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(5, new Vak(Landschap.BOS, 0), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(6, new Vak(Landschap.BOS, 0), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(7, new Vak(Landschap.WATER, 0), new Vak(Landschap.WATER, 0)));
		tegels.add(new Tegel(8, new Vak(Landschap.WATER, 0), new Vak(Landschap.WATER, 0)));
		tegels.add(new Tegel(9, new Vak(Landschap.WATER, 0), new Vak(Landschap.WATER, 0)));
		tegels.add(new Tegel(10, new Vak(Landschap.GRAS, 0), new Vak(Landschap.GRAS, 0)));
		tegels.add(new Tegel(11, new Vak(Landschap.GRAS, 0), new Vak(Landschap.GRAS, 0)));
		tegels.add(new Tegel(12, new Vak(Landschap.AARDE, 0), new Vak(Landschap.AARDE, 0)));
		tegels.add(new Tegel(13, new Vak(Landschap.ZAND, 0), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(14, new Vak(Landschap.ZAND, 0), new Vak(Landschap.WATER, 0)));
		tegels.add(new Tegel(15, new Vak(Landschap.ZAND, 0), new Vak(Landschap.GRAS, 0)));
		tegels.add(new Tegel(16, new Vak(Landschap.ZAND, 0), new Vak(Landschap.AARDE, 0)));
		tegels.add(new Tegel(17, new Vak(Landschap.BOS, 0), new Vak(Landschap.WATER, 0)));
		tegels.add(new Tegel(18, new Vak(Landschap.BOS, 0), new Vak(Landschap.GRAS, 0)));
		tegels.add(new Tegel(19, new Vak(Landschap.ZAND, 1), new Vak(Landschap.WATER, 0)));
		tegels.add(new Tegel(20, new Vak(Landschap.ZAND, 1), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(21, new Vak(Landschap.ZAND, 1), new Vak(Landschap.GRAS, 0)));
		tegels.add(new Tegel(22, new Vak(Landschap.ZAND, 1), new Vak(Landschap.AARDE, 0)));
		tegels.add(new Tegel(23, new Vak(Landschap.ZAND, 1), new Vak(Landschap.MIJN, 0)));
		tegels.add(new Tegel(24, new Vak(Landschap.BOS, 1), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(25, new Vak(Landschap.BOS, 1), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(26, new Vak(Landschap.BOS, 1), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(27, new Vak(Landschap.BOS, 1), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(28, new Vak(Landschap.BOS, 1), new Vak(Landschap.WATER, 0)));
		tegels.add(new Tegel(29, new Vak(Landschap.BOS, 1), new Vak(Landschap.GRAS, 0)));
		tegels.add(new Tegel(30, new Vak(Landschap.WATER, 1), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(31, new Vak(Landschap.WATER, 1), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(32, new Vak(Landschap.WATER, 1), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(33, new Vak(Landschap.WATER, 1), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(34, new Vak(Landschap.WATER, 1), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(35, new Vak(Landschap.WATER, 1), new Vak(Landschap.BOS, 0)));
		tegels.add(new Tegel(36, new Vak(Landschap.ZAND, 0), new Vak(Landschap.GRAS, 1)));
		tegels.add(new Tegel(37, new Vak(Landschap.WATER, 0), new Vak(Landschap.GRAS, 1)));
		tegels.add(new Tegel(38, new Vak(Landschap.ZAND, 0), new Vak(Landschap.AARDE, 1)));
		tegels.add(new Tegel(39, new Vak(Landschap.GRAS, 0), new Vak(Landschap.AARDE, 1)));
		tegels.add(new Tegel(40, new Vak(Landschap.MIJN, 1), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(41, new Vak(Landschap.ZAND, 0), new Vak(Landschap.GRAS, 2)));
		tegels.add(new Tegel(42, new Vak(Landschap.WATER, 0), new Vak(Landschap.GRAS, 2)));
		tegels.add(new Tegel(43, new Vak(Landschap.ZAND, 0), new Vak(Landschap.AARDE, 2)));
		tegels.add(new Tegel(44, new Vak(Landschap.GRAS, 0), new Vak(Landschap.AARDE, 2)));
		tegels.add(new Tegel(45, new Vak(Landschap.MIJN, 2), new Vak(Landschap.ZAND, 0)));
		tegels.add(new Tegel(46, new Vak(Landschap.AARDE, 0), new Vak(Landschap.MIJN, 2)));
		tegels.add(new Tegel(47, new Vak(Landschap.AARDE, 0), new Vak(Landschap.MIJN, 2)));
		tegels.add(new Tegel(48, new Vak(Landschap.ZAND, 0), new Vak(Landschap.MIJN, 3)));
	}

	/**
	 * Geeft de lijst van tegels terug.
	 *
	 * @return de lijst van tegels.
	 */
	public List<Tegel> geeftegels() {
		return tegels;
	}

}
