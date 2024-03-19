
package cui;

import domein.DomeinController;

public class Applicatie {

	private final DomeinController dc;
	private final HoofdMenu hoofdMenu;
	private final RegistratieMenu registratieMenu;
	private final SpelMenu spelMenu;

	public Applicatie(DomeinController dc) {
		this.dc = dc;
		hoofdMenu = new HoofdMenu();
		registratieMenu = new RegistratieMenu(this.dc);
		spelMenu = new SpelMenu(this.dc);
	}

	public void startApplicatie() {
		int keuze;
		do {
			keuze = hoofdMenu.vraagKeuzeMetHoofdMenu();
			switch (keuze) {
				case 1 -> registratieMenu.registreerSpeler();
				case 2 -> spelMenu.maakSpel();
			}
		} while (keuze != 3);
	}

}
