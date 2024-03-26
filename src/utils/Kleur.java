
package utils;

public enum Kleur {

	GROEN, BLAUW, ROOS, GEEL;

	@Override
	public String toString() {
		return this.name().charAt(0) + this.name().substring(1).toLowerCase();
	}

	public static boolean bestaatKleur(String kleurString) {
		for (Kleur kleur : Kleur.values())
			if (kleur.toString().toLowerCase().equals(kleurString.toLowerCase()))
				return true;
		return false;
	}

}
