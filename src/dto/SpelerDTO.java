
package dto;

import domein.Speler;

public record SpelerDTO(String gebruikersnaam, int geboortejaar, String kleur) {

	public SpelerDTO(Speler speler) {
		this(speler.getGebruikersnaam(), speler.getGeboortejaar(),
				speler.getKleur() == null ? null : speler.getKleur().toString());
	}

}
