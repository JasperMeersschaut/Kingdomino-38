
package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.Taal;

public class WelkomScherm extends StackPane {

	private final DomeinController dc;
	private final Stage scherm;

	public WelkomScherm(DomeinController dc, Stage scherm) {
		this.dc = dc;
		this.scherm = scherm;
		bouwScherm();
	}

	private void bouwScherm() {
		scherm.centerOnScreen();
		ImageView imageViewWelkom = new ImageView(new Image(getClass().getResourceAsStream("/images/kingdomino.jpg")));
		imageViewWelkom.fitWidthProperty().bind(this.scherm.widthProperty());
		imageViewWelkom.setPreserveRatio(true);
		HBox talenBox = geefVlaggen();
		talenBox.setAlignment(Pos.BOTTOM_CENTER);
		talenBox.setSpacing(50);
		talenBox.setTranslateY(-50);
		getChildren().addAll(imageViewWelkom, talenBox);
	}

	private HBox geefVlaggen() {
		HBox talenBox = new HBox();
		ImageView vlagBE = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/flagBE.png")));
		vlagBE.setFitWidth(200);
		vlagBE.setPreserveRatio(true);
		vlagBE.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		vlagBE.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		vlagBE.setOnMouseClicked(event -> {
			Taal.setTaal("BE");
			gaNaarHoofdMenuScherm();
		});
		ImageView vlagEN = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/flagUK.png")));
		vlagEN.setFitWidth(200);
		vlagEN.setPreserveRatio(true);
		vlagEN.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		vlagEN.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		vlagEN.setOnMouseClicked(event -> {
			Taal.setTaal("EN");
			gaNaarHoofdMenuScherm();
		});
		ImageView vlagFR = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/flagFR.png")));
		vlagFR.setFitWidth(200);
		vlagFR.setPreserveRatio(true);
		vlagFR.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		vlagFR.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		vlagFR.setOnMouseClicked(event -> {
			Taal.setTaal("FR");
			gaNaarHoofdMenuScherm();
		});
		talenBox.getChildren().addAll(vlagBE, vlagEN, vlagFR);
		return talenBox;
	}

	private void gaNaarHoofdMenuScherm() {
		setCursor(Cursor.DEFAULT);
		getScene().setRoot(new HoofdMenuScherm(dc, scherm));
	}

}
