
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
	
		VBox vbox = new VBox();
		vbox.setSpacing(20);
		
		List<SpelerDTO> spelers = dc.geefSpelers();
		for (SpelerDTO speler : spelers) {
			Label label = new Label(String.format(speler.gebruikersnaam() + ": " + dc.berekenScore(speler)));
			vbox.getChildren().add(label);
			label.getStyleClass().addAll("font", "mediumText");
			label.setPadding(new Insets(100,0,0,200));
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
