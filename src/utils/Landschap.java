
package utils;

public enum Landschap {

	AARDE, BOS, GRAS, MIJN, WATER, ZAND;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}

}
