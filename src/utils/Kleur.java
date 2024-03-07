
package utils;

import java.util.ArrayList;
import java.util.List;

public enum Kleur {

	GROEN, BLAUW, ROOS, GEEL;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}

	public static List<Kleur> geefKleuren() {
		List<Kleur> kleuren = new ArrayList<>();
		kleuren.add(GROEN);
		kleuren.add(BLAUW);
		kleuren.add(ROOS);
		kleuren.add(GEEL);
		return kleuren;
	}

}
