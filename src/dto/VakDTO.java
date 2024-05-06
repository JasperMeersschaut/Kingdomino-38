
package dto;

import domein.Vak;

public record VakDTO(String landschap, int aantalKronen) {

	/**
	 * Constructor voor een VakDTO.
	 *
	 * @param vak het vak waarvan de gegevens worden opgeslagen.
	 */
	public VakDTO(Vak vak) {
		this(vak == null ? null : vak.geefLandschap().toString(), vak == null ? 0 : vak.geefAantalKronen());
	}

}
