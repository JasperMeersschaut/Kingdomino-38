
package cui;

import java.util.Collections;
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
					speelSpel(spelers);
				}
			}
		} while (keuze != 3);
	}

	private void speelSpel(List<SpelerDTO> spelers) {
		dc.maakSpelAan(spelers);
		System.out.println(dc.toonSpelOverzicht());
		int loopSize = spelers.size();
		Collections.shuffle(spelers);
		SpelerDTO speler;
		boolean gekozen = false;
		for (int i = 0; i < loopSize; i++) {
			speler = spelers.get(i);
			do
				try {
					System.out.println(dc.toonSpelerKeuze(speler));
					int nr = scanner.nextInt();
					dc.kiesTegelStartkolom(speler, nr);
					System.out.println(dc.toonSpelOverzicht());
					gekozen = true;
				}
				catch (IllegalArgumentException ie) {
					System.err.println(ie.getMessage());
				}
				catch (Exception e) {
					System.err.println(messages.getString("error_occured"));
				}
			while (!gekozen);
		}
	}

}
