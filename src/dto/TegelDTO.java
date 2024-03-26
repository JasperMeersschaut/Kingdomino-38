
package dto;

import domein.Tegel;
import utils.Kleur;
import utils.Landschap;

public record TegelDTO(int nummer, Landschap landschapLinks, int aantalKronenLinks, Landschap landschapRechts,
		int aantalKronenRechts, String gebruikersnaam, Kleur kleur) implements Comparable<TegelDTO> {

	public TegelDTO(Tegel tegel, String gebruikersnaam, Kleur kleur) {
		this(tegel.getNummer(), tegel.getVakLinks().getLandschap(), tegel.getVakLinks().getAantalKronen(),
				tegel.getVakRechts().getLandschap(), tegel.getVakRechts().getAantalKronen(), gebruikersnaam, kleur);
	}

	@Override
	public int compareTo(TegelDTO tegel) {
		return Integer.compare(this.nummer, tegel.nummer);
	}

}
