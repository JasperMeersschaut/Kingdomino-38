
package utils;

import java.util.ResourceBundle;

import exceptions.KleurBestaatNietException;

public enum Kleur {

	GROEN, BLAUW, ROOS, GEEL;

	/**
	 * Geeft de kleur terug als een string.
	 *
	 * @return de kleur als string.
	 */
	@Override
	public String toString() {
		return switch (this.name()) {
			case "GROEN" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("color_green");
			case "BLAUW" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("color_blue");
			case "ROOS" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("color_pink");
			case "GEEL" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("color_yellow");
			default -> null;
		};
	}

	/**
	 * Geeft de kleur terug die overeenkomt met de opgegeven string.
	 *
	 * @param kleur de kleur in de vorm van een string.
	 * @return de kleur die overeenkomt met de gegeven string.
	 * @throws KleurBestaatNietException als de kleur niet bestaat.
	 */
	public static Kleur geefKleur(String kleur) {
		if (!Kleur.bestaatKleur(kleur))
			throw new KleurBestaatNietException(String
					.format(ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("color_doenst_exist"), kleur));
		if (kleur.equalsIgnoreCase(ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("color_green")))
			return GROEN;
		if (kleur.equalsIgnoreCase(ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("color_blue")))
			return BLAUW;
		if (kleur.equalsIgnoreCase(ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("color_pink")))
			return ROOS;
		if (kleur.equalsIgnoreCase(ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("color_yellow")))
			return GEEL;
		return null;
	}

	private static boolean bestaatKleur(String kleurTeControleren) {
		for (Kleur kleur : Kleur.values())
			if (kleur.toString().equalsIgnoreCase(kleurTeControleren))
				return true;
		return false;
	}

}
