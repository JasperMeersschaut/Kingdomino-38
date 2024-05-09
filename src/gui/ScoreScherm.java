
package gui;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import domein.DomeinController;
import dto.SpelerDTO;
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
			SpelerDTO speler = dc.geefSpelers().get(i);
			Label spelerLabel = new Label(speler.gebruikersnaam());
			spelerLabel.getStyleClass().addAll("font", "mediumText");
			Label score = new Label(String.format("%s %d", messages.getString("fx_score"), dc.berekenScore(speler)));
			score.getStyleClass().addAll("font", "mediumText");
			Label gewonnen = new Label(dc.geefWinnaars().contains(speler) ? messages.getString("won") : "");
			gewonnen.getStyleClass().addAll("font", "mediumText");
			Label aantalGespeeld = new Label(String.format("%s %d", messages.getString("amount_played"),
					dc.geefAantalGespeeldeEnAantalGewonnenSpellen().get(0).get(i)));
			aantalGespeeld.getStyleClass().addAll("font", "mediumText");
			Label aantalGewonnen = new Label(String.format("%s %d", messages.getString("amount_won"),
					dc.geefAantalGespeeldeEnAantalGewonnenSpellen().get(1).get(i)));
			aantalGewonnen.getStyleClass().addAll("font", "mediumText");
			VBox spelerStats = new VBox();
			spelerStats.getChildren().addAll(spelerLabel, score, gewonnen, aantalGespeeld, aantalGewonnen);
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
