package domein;

public class Vak {

	enum Landschapstype {
		Aarde, Bos, Gras, Mijn, Water, Zand
	};

	private Landschapstype type;
	private int kronen;

	public Vak(Landschapstype type, int kronen) {
		this.type = type;
		this.kronen = kronen;
	}

	public int getKronen() {
		return kronen;
	}

}
