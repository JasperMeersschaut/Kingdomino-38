
package dto;

import domein.Speler;
import domein.Vak;
import utils.Kleur;

public record SpelerDTO(Speler speler, Kleur kleur, Vak[][] koninkrijk) {

	public boolean equals(SpelerDTO speler) {
		return this.speler.getGebruikersnaam().equalsIgnoreCase(speler.speler().getGebruikersnaam());
	}

	@Override
	public String toString() {
		return speler.getGebruikersnaam();
	}

}
