
package utils;

import java.util.ResourceBundle;

public enum Landschap {

	AARDE, BOS, GRAS, MIJN, WATER, ZAND, KASTEEL, LEEG;

	@Override
	public String toString() {
		return switch (this.name()) {
			case "AARDE" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("land_earth");
			case "BOS" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("land_forest");
			case "GRAS" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("land_grass");
			case "MIJN" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("land_mine");
			case "WATER" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("land_water");
			case "ZAND" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("land_sand");
			case "KASTEEL" -> ResourceBundle.getBundle("messages", Taal.getTaal()).getString("land_castle");
			default -> this.name().charAt(0) + this.name().substring(1).toLowerCase();
		};
	}

}
