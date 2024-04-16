
package gui;

import domein.DomeinController;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpelSpelenMenuScherm extends Pane {

	private final DomeinController dc;
	private final Stage scherm;

	public SpelSpelenMenuScherm(DomeinController dc, Stage scherm) {
		this.dc = dc;
		this.scherm = scherm;
		dc.startSpel();
		bouwScherm();
	}

	private void bouwScherm() {
		scherm.centerOnScreen();
		getStyleClass().add("zandkleur");
	}

}
