
package utils;

import java.util.Locale;

public enum Taal {

	nl("BE"), fr("FR"), en("EN");

	private static Locale taal;

	Taal(String taal) {
		stelTaalIn(taal);
	}

	/**
	 * Stelt de taal in op basis van de gegeven taalcode.
	 *
	 * @param taal de taalcode om in te stellen.
	 */
	public static void stelTaalIn(String taal) {
		switch (taal) {
			case "BE":
				Taal.taal = new Locale("nl", "BE");
				break;
			case "FR":
				Taal.taal = new Locale("fr", "FR");
				break;
			case "EN":
				Taal.taal = new Locale("en", "EN");
				break;
			default:
				Taal.taal = Locale.getDefault();
				break;
		}
	}

	/**
	 * Geeft de huidige ingestelde taal terug.
	 *
	 * @return de huidige ingestelde taal.
	 */
	public static Locale geefTaal() {
		return taal;
	}

}
