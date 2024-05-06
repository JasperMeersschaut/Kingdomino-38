
package utils;

import java.util.ResourceBundle;

public enum Landschap {

	AARDE, BOS, GRAS, MIJN, WATER, ZAND, KASTEEL, LEEG;

	/**
	 * Geeft het landschap terug als een string.
	 *
	 * @return het landschap als string.
	 */
	@Override
	public String toString() {
		return switch (this.name()) {
			case "AARDE" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("land_earth");
			case "BOS" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("land_forest");
			case "GRAS" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("land_grass");
			case "MIJN" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("land_mine");
			case "WATER" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("land_water");
			case "ZAND" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("land_sand");
			case "KASTEEL" -> ResourceBundle.getBundle("messages", Taal.geefTaal()).getString("land_castle");
			default -> this.name().charAt(0) + this.name().substring(1).toLowerCase();
		};
	}

}
