
package utils;

import java.util.Locale;

public enum Taal {

	nl("BE"), fr("FR"), en("EN");

	private static Locale taal;

	Taal(String taal) {
		setTaal(taal);
	}

	public static void setTaal(String taal) {
		switch (taal) {
			case "BE":
				Taal.taal = new Locale("nl", "BE");
				break;
			case "FR":
				Taal.taal = new Locale("fr", "FR");
				break;
			default:
				Taal.taal = new Locale("en", "EN");
		}
	}

	public static Locale getTaal() {
		return taal;
	}

}
