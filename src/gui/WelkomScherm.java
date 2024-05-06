
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

	/**
	 * Constructor voor het WelkomScherm.
	 *
	 * @param dc     de domeincontroller.
	 * @param scherm het huidige scherm.
	 */
	public WelkomScherm(DomeinController dc, Stage scherm) {
		this.dc = dc;
		this.scherm = scherm;
		bouwScherm();
	}

	private void bouwScherm() {
		scherm.centerOnScreen();
		ImageView imageViewWelkom = new ImageView(
				new Image(getClass().getResourceAsStream("/images/kingdomino/kingdomino.jpg")));
		imageViewWelkom.setPreserveRatio(true);
		imageViewWelkom.setFitWidth(scherm.getWidth());
		HBox talenBox = geefVlaggen();
		talenBox.setAlignment(Pos.BOTTOM_CENTER);
		talenBox.setSpacing(50);
		talenBox.setTranslateY(-50);
		getChildren().addAll(imageViewWelkom, talenBox);
		scherm.widthProperty()
				.addListener((observable, oldValue, newValue) -> imageViewWelkom.setFitWidth(scherm.getWidth()));
	}

	private HBox geefVlaggen() {
		HBox talenBox = new HBox();
		ImageView vlagBE = maakVlag("/images/flags/flagBE.png", "BE");
		ImageView vlagEN = maakVlag("/images/flags/flagUK.png", "EN");
		ImageView vlagFR = maakVlag("/images/flags/flagFR.png", "FR");
		talenBox.getChildren().addAll(vlagBE, vlagEN, vlagFR);
		return talenBox;
	}

	private ImageView maakVlag(String pad, String taal) {
		ImageView vlag = new ImageView(new Image(getClass().getResourceAsStream(pad)));
		vlag.setFitWidth(200);
		vlag.setPreserveRatio(true);
		vlag.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		vlag.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		vlag.setOnMouseClicked(event -> {
			Taal.stelTaalIn(taal);
			gaNaarHoofdMenuScherm();
		});
		return vlag;
	}

	private void gaNaarHoofdMenuScherm() {
		setCursor(Cursor.DEFAULT);
		getScene().setRoot(new HoofdMenuScherm(dc, scherm));
	}

}
