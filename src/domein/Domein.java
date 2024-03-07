package domein;

// Aaneengesloten Gebied van vakjes van een zelfde landschapstype
public class Domein {

	private int prestigepunten = 0;

	public Domein(Vak[] vakjes) {
		prestigepunten = berekenPunten(vakjes);
	}

	public int berekenPunten(Vak[] vakjes) {
		for (Vak vak : vakjes) {
			prestigepunten += vak.getKronen();
		}
		prestigepunten *= vakjes.length;
		return prestigepunten;
	}
}
