
package gui;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Taal;

public class ScoreScherm extends BorderPane {

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
		scherm.centerOnScreen();
		getStyleClass().add("zandkleur");
		Label hoofding = maakHoofdingScore();
		this.setTop(hoofding);
		BorderPane.setAlignment(hoofding, javafx.geometry.Pos.CENTER);
		
		
		// TODO: Linken met rest en testen
		// Vertaling fixen
		
		// werkt
//		List<SpelerDTO> spelers = dc.geefSpelers();
//		ListView<String> listView = new ListView<>();
//		spelers.forEach(speler -> listView.getItems().add(speler.gebruikersnaam() + ": " + dc.berekenScore(speler)));
//		List<SpelerDTO> winnaars = dc.geefWinnaars();
//		Label winnaarLabel = new Label(String.format("De %s: %s", winnaars.size() > 1 ? "winnaars zijn" : "winnaar is",
//				winnaars.stream().map(winnaar -> winnaar.gebruikersnaam()).collect(Collectors.joining(", "))));
//		this.getChildren().addAll(listView, winnaarLabel);
		
		// te testen
		VBox vbox = new VBox();
		vbox.setSpacing(20);
		
		List<SpelerDTO> spelers = dc.geefSpelers();
		for (SpelerDTO speler : spelers) {
			Label label = new Label(String.format(speler.gebruikersnaam() + ": " + dc.berekenScore(speler)));
			vbox.getChildren().add(label);
			label.getStyleClass().addAll("font", "bigText","border");
		}
		this.setCenter(vbox);
	}
	
	private Label maakHoofdingScore() {
	List<SpelerDTO> winnaar = dc.geefWinnaars();
	String naamWinnaar = "";
	
	for (SpelerDTO spelerDTO : winnaar) {
			 naamWinnaar = spelerDTO.gebruikersnaam();
		}
		Label hoofding = new Label(String.format("Proficiat %s", naamWinnaar));
		hoofding.setPadding(new Insets(35,0,0,0));
		hoofding.getStyleClass().addAll("font", "bigText");
		hoofding.setMinWidth(350);
		hoofding.setMinHeight(108);
		
		return hoofding;
	}
	
}
