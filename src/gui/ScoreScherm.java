
package gui;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Taal;

public class ScoreScherm extends VBox {

	private final ResourceBundle messages;
	private final DomeinController dc;
	private final Stage scherm;

	/**
	 * Constructor voor het ScoreScherm.
	 *
	 * @param dc     de domeincontroller.
	 * @param scherm het huidige scherm.
	 */
	public ScoreScherm(DomeinController dc, Stage scherm) {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		this.dc = dc;
		this.scherm = scherm;
		bouwScherm();
	}

	private void bouwScherm() {
		scherm.centerOnScreen();
		getStyleClass().add("zandkleur");
		setAlignment(Pos.CENTER);
		setSpacing(100);
		HBox spelers = new HBox();
		for (int i = 0; i < dc.geefSpelers().size(); i++) {
			Label speler = new Label(dc.geefSpelers().get(i).gebruikersnaam());
			speler.getStyleClass().addAll("font", "mediumText");
			Label score = new Label(
					String.format("%s %d", messages.getString("fx_score"), dc.berekenScore(dc.geefSpelers().get(i))));
			score.getStyleClass().addAll("font", "mediumText");
			Label gewonnen = new Label(
					dc.geefWinnaars().contains(dc.geefSpelers().get(i)) ? messages.getString("fx_won") : "");
			gewonnen.getStyleClass().addAll("font", "mediumText");
			VBox spelerStats = new VBox();
			spelerStats.getChildren().addAll(speler, score, gewonnen);
			spelerStats.setAlignment(Pos.CENTER);
			spelerStats.setSpacing(10);
			spelers.getChildren().add(spelerStats);
		}
		spelers.setAlignment(Pos.CENTER);
		spelers.setSpacing(150);
		getChildren().add(spelers);
		Button homeKnop = new Button(
				Arrays.stream(messages.getString("fx_main_menu").split("\n")).collect(Collectors.joining(" ")));
		homeKnop.getStyleClass().addAll("zandkleur", "font", "mediumText", "border");
		homeKnop.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		homeKnop.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		homeKnop.setOnAction(event -> getScene().setRoot(new HoofdMenuScherm(dc, scherm)));
		homeKnop.setAlignment(Pos.CENTER);
		getChildren().add(homeKnop);
	}

}
