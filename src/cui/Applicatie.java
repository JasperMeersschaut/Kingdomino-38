
package cui;

import domein.DomeinController;

public class Applicatie {

	private DomeinController dc;

	public Applicatie(DomeinController dc) {
		this.dc = dc;
	}

	public void start() {
		int keuze;
		do {
			keuze = dc.toonHoofdMenu();
			switch (keuze) {
			case 1 -> dc.registreerSpeler();
			// case 2 -> dc.startNieuwSpel();
			}
		} while (keuze != 3);
	}

}
