
package cui;

import domein.DomeinController;

public class StartUp {

	public static void main(String[] args) {
		new Applicatie(new DomeinController()).start();
	}

}