
package main;

import cui.HoofdMenu;
import domein.DomeinController;

public class StartUp {

	public static void main(String[] args) {
		new HoofdMenu(new DomeinController()).vraagKeuze();
	}

}
