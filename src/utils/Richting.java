
package utils;

public enum Richting {

	LINKS, RECHTS, BOVEN, ONDER;

	public static int[][] geefRichtingen() {
		return new int[][] { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
	}

	public static int[] geefRichting(Richting richting) {
		if (richting == null)
			return null;
		return switch (richting) {
			case LINKS -> new int[] { 0, -1 };
			case RECHTS -> new int[] { 0, 1 };
			case BOVEN -> new int[] { -1, 0 };
			case ONDER -> new int[] { 1, 0 };
		};
	}

}
