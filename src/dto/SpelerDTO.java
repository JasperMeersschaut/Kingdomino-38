
package dto;

import domein.Speler;

public record SpelerDTO(String gebruikersnaam, int geboortejaar, String kleur) {

	/**
	 * Constructor voor een SpelerDTO.
	 *
	 * @param speler de speler waarvan de gegevens worden opgeslagen.
	 */
	public SpelerDTO(Speler speler) {
		this(speler.geefGebruikersnaam(), speler.geefGeboortejaar(),
				speler.geefKleur() == null ? null : speler.geefKleur().toString());
	}

}
