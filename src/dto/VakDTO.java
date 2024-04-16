
package dto;

import domein.Vak;

public record VakDTO(String landschap, int aantalKronen) {

	public VakDTO(Vak vak) {
		this(vak == null ? null : vak.getLandschap().toString(), vak == null ? 0 : vak.getAantalKronen());
	}

}
