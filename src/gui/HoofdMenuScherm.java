
package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Taal;

public class HoofdMenuScherm extends BorderPane {

	private final ResourceBundle messages;
	private final DomeinController dc;
	private final Stage scherm;
	private Button speelSpelKnop;

	public HoofdMenuScherm(DomeinController dc, Stage scherm) {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		this.dc = dc;
		this.scherm = scherm;
		bouwScherm();
	}

	private void bouwScherm() {
		scherm.centerOnScreen();
		VBox menu = bouwMenu();
		StackPane keuzeMenu = bouwKeuzeMenu();
		setTop(menu);
		setCenter(keuzeMenu);
	}

	private VBox bouwMenu() {
		VBox menu = new VBox();
		HBox menuItems = bouwMenuItems();
		Line line = new Line();
		line.setStartX(0);
		line.setEndX(scherm.getWidth());
		line.getStyleClass().add("line");
		menu.getChildren().addAll(menuItems, line);
		widthProperty().addListener(observable -> line.setEndX(scherm.getWidth()));
		return menu;
	}

	private HBox bouwMenuItems() {
		HBox menu = new HBox();
		menu.getStyleClass().add("zandkleur");
		menu.setAlignment(Pos.CENTER);
		menu.setSpacing(scherm.getWidth() - 750);
		Label welkomscherm = new Label(messages.getString("fx_begin_menu"));
		welkomscherm.getStyleClass().addAll("font", "mediumText");
		welkomscherm.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		welkomscherm.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		welkomscherm.setOnMouseClicked(event -> gaNaarWelkomScherm());
		Text kingdomino = new Text("Kingdomino");
		kingdomino.getStyleClass().addAll("titel", "hugeText");
		menu.getChildren().addAll(welkomscherm, kingdomino);
		widthProperty().addListener(observable -> menu.setSpacing(scherm.getWidth() - 750));
		return menu;
	}

	private StackPane bouwKeuzeMenu() {
		StackPane keuzeMenu = new StackPane();
		Image imageWelkom = new Image(getClass().getResourceAsStream("/images/kingdomino/achtergrond.jpg"));
		ImageView imageBackground = new ImageView(imageWelkom);
		imageBackground.setPreserveRatio(true);
		imageBackground.setFitWidth(scherm.getWidth() + 50);
		HBox keuzeKnoppen = bouwKeuzeKnoppen();
		keuzeKnoppen.setAlignment(Pos.CENTER);
		keuzeMenu.getChildren().addAll(imageBackground, keuzeKnoppen);
		widthProperty().addListener(observable -> imageBackground.setFitWidth(scherm.getWidth() + 50));
		return keuzeMenu;
	}

	private HBox bouwKeuzeKnoppen() {
		HBox keuzeKnoppen = new HBox();
		Button registreerSpelerKnop = new Button(messages.getString("fx_register_new_player"));
		registreerSpelerKnop.getStyleClass().addAll("zandkleur", "font", "smallText", "border");
		registreerSpelerKnop.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		registreerSpelerKnop.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		registreerSpelerKnop.setOnAction(event -> gaNaarRegistratieMenuScherm());
		speelSpelKnop = new Button(messages.getString("fx_play_game"));
		speelSpelKnop.getStyleClass().addAll("zandkleur", "font", "smallText", "border");
		speelSpelKnop.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		speelSpelKnop.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		speelSpelKnop.setOnAction(event -> gaNaarSpelersToevoegenScherm());
		keuzeKnoppen.getChildren().addAll(registreerSpelerKnop, speelSpelKnop);
		keuzeKnoppen.setSpacing(150);
		return keuzeKnoppen;
	}

	private void gaNaarWelkomScherm() {
		getScene().setRoot(new WelkomScherm(dc, scherm));
	}

	private void gaNaarRegistratieMenuScherm() {
		getScene().setRoot(new RegistratieScherm(dc, scherm));
	}

	private void gaNaarSpelersToevoegenScherm() {
		try {
			dc.maakSpelAan();
			getScene().setRoot(new SpelersToevoegenScherm(dc, scherm));
		}
		catch (Exception e) {
			speelSpelKnop.setText(messages.getString("no_connection"));
		}
	}

}
