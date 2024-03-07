
package domein;

import java.awt.Menu;

public class DomeinController {
	private static Spel spel;
	private Menu menu;
	private static SpelerRepository spelerRepository;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
		menu = new Menu();
	}

	private static startSpel(){
		spel = new Spel(spelerRepository.geefSpeler());
		spel.start();
	}

}
