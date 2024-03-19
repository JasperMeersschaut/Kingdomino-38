
package dto;

import domein.Tegel;

public record TegelDTO(Tegel tegel, SpelerDTO spelerDTO) implements Comparable<TegelDTO> {

	@Override
	public int compareTo(TegelDTO tegel) {
		return Integer.compare(this.tegel().getNummer(), tegel.tegel().getNummer());
	}

}
