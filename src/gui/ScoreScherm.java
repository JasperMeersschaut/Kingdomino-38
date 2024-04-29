
package gui;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Taal;

public class ScoreScherm extends VBox {

	private final ResourceBundle messages;
	private final DomeinController dc;
	private final Stage scherm;

	public ScoreScherm(DomeinController dc, Stage scherm) {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		this.dc = dc;
		this.scherm = scherm;
		bouwScherm();
	}

	private void bouwScherm() {
		// TODO: Linken met rest en testen
		// Vertaling fixen
		List<SpelerDTO> spelers = dc.geefSpelers();
		ListView<String> listView = new ListView<>();
		spelers.forEach(speler -> listView.getItems().add(speler.gebruikersnaam() + ": " + dc.berekenScore(speler)));
		List<SpelerDTO> winnaars = dc.geefWinnaars();
		Label winnaarLabel = new Label(String.format("De %s: %s", winnaars.size() > 1 ? "winnaars zijn" : "winnaar is",
				winnaars.stream().map(winnaar -> winnaar.gebruikersnaam()).collect(Collectors.joining(", "))));
		this.getChildren().addAll(listView, winnaarLabel);
	}

}
