
package cui;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import dto.SpelerDTO;

public class Applicatie {

	private ResourceBundle messages;
	private DomeinController dc;
	Scanner scanner = new Scanner(System.in);
	private HoofdMenu hoofdMenu = new HoofdMenu();
	private RegistreerMenu registreerMenu;
	private SpelMenu spelMenu;

	public Applicatie(DomeinController dc) {
		this.dc = dc;
		messages = ResourceBundle.getBundle("messages", Locale.getDefault());
		registreerMenu = new RegistreerMenu(this.dc);
		spelMenu = new SpelMenu(this.dc);
	}

	public void start() {
		int keuze;
		do {
			keuze = hoofdMenu.toonHoofdMenu();
			switch (keuze) {
				case 1 -> registreerMenu.registreerSpeler();
				case 2 -> {
					List<SpelerDTO> spelers = spelMenu.vraagSpelersEnKleuren();
					dc.startSpel(spelers);
				}
			}
		} while (keuze != 3);
	}

}
