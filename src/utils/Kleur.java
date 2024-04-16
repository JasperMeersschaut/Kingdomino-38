
package utils;

import java.util.ResourceBundle;

import exceptions.KleurBestaatNietException;

public enum Kleur {

	GROEN, BLAUW, ROOS, GEEL;

	@Override
	public String toString() {
		return switch (this.name()) {
			case "GROEN" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("color_green");
			case "BLAUW" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("color_blue");
			case "ROOS" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("color_pink");
			case "GEEL" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("color_yellow");
			default -> this.name().charAt(0) + this.name().substring(1).toLowerCase();
		};
	}

	public static Kleur geefKleur(String kleur) {
		if (!Kleur.bestaatKleur(kleur))
			throw new KleurBestaatNietException(String
					.format(ResourceBundle.getBundle("messages", Taal.getTaal()).getString("color_doenst_exist"), kleur));
		if (kleur.equalsIgnoreCase(ResourceBundle.getBundle("messages", Taal.getTaal()).getString("color_green")))
			return GROEN;
		if (kleur.equalsIgnoreCase(ResourceBundle.getBundle("messages", Taal.getTaal()).getString("color_blue")))
			return BLAUW;
		if (kleur.equalsIgnoreCase(ResourceBundle.getBundle("messages", Taal.getTaal()).getString("color_pink")))
			return ROOS;
		if (kleur.equalsIgnoreCase(ResourceBundle.getBundle("messages", Taal.getTaal()).getString("color_yellow")))
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
