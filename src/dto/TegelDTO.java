
package dto;

import domein.Tegel;

public record TegelDTO(int nummer, String landschapLinks, int aantalKronenLinks, String landschapRechts,
		int aantalKronenRechts, String spelerOpTegel, String kleur) {

	/**
	 * Constructor voor een TegelDTO.
	 *
	 * @param tegel de tegel waarvan de gegevens worden opgeslagen.
	 */
	public TegelDTO(Tegel tegel) {
		this(tegel.geefTegelNummer(), tegel.geefVakLinks().geefLandschap().toString(),
				tegel.geefVakLinks().geefAantalKronen(), tegel.geefVakRechts().geefLandschap().toString(),
				tegel.geefVakRechts().geefAantalKronen(),
				tegel.geefSpelerOpTegel() == null ? null : tegel.geefSpelerOpTegel().geefGebruikersnaam(),
				tegel.geefSpelerOpTegel() == null ? null : tegel.geefSpelerOpTegel().geefKleur().toString());
	}

}
