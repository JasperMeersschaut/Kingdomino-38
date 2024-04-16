
package dto;

import domein.Tegel;

public record TegelDTO(int nummer, String landschapLinks, int aantalKronenLinks, String landschapRechts,
		int aantalKronenRechts, String spelerOpTegel, String kleur) {

	public TegelDTO(Tegel tegel) {
		this(tegel.getTegelNummer(), tegel.getVakLinks().getLandschap().toString(), tegel.getVakLinks().getAantalKronen(),
				tegel.getVakRechts().getLandschap().toString(), tegel.getVakRechts().getAantalKronen(),
				tegel.getSpelerOpTegel() == null ? null : tegel.getSpelerOpTegel().getGebruikersnaam(),
				tegel.getSpelerOpTegel() == null ? null : tegel.getSpelerOpTegel().getKleur().toString());
	}

}
