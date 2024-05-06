
package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Taal;

public class RegistratieScherm extends GridPane {

	private final ResourceBundle messages;
	private final DomeinController dc;
	private TextField textFieldGebruikersnaam;
	private Label gebruikersnaamError;
	private TextField textFieldGeboortejaar;
	private Label geboortejaarError;
	private Label registratieMelding;
	private final Stage scherm;

	/**
	 * Constructor voor het RegistratieScherm.
	 *
	 * @param dc     de domeincontroller.
	 * @param scherm het huidige scherm.
	 */
	public RegistratieScherm(DomeinController dc, Stage scherm) {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		this.dc = dc;
		this.scherm = scherm;
		bouwScherm();
	}

	private void bouwScherm() {
		scherm.centerOnScreen();
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("zandkleur");
		ColumnConstraints bigCol = new ColumnConstraints((scherm.getWidth() - 600) / 2);
		ColumnConstraints smallCol = new ColumnConstraints(300);
		getColumnConstraints().addAll(bigCol, smallCol, smallCol, bigCol);
		for (int i = 0; i < 5; i++)
			getRowConstraints().add(new RowConstraints(scherm.getHeight() / 5 - 30));
		widthProperty().addListener(observable -> {
			getChildren().clear();
			getColumnConstraints().clear();
			getRowConstraints().clear();
			bouwScherm();
		});
		VBox menu = bouwMenu();
		add(menu, 0, 0, 4, 1);
		Label spelerRegistreren = new Label(messages.getString("fx_register_player"));
		spelerRegistreren.getStyleClass().addAll("font", "bigText");
		spelerRegistreren.setMinWidth(300);
		spelerRegistreren.setMinHeight(scherm.getHeight() / 5 - 50);
		spelerRegistreren.setAlignment(Pos.TOP_CENTER);
		add(spelerRegistreren, 1, 1, 2, 1);
		Label gebruikersnaam = bouwLabel(messages.getString("fx_username"), Pos.CENTER_RIGHT, false);
		add(gebruikersnaam, 0, 2, 1, 1);
		textFieldGebruikersnaam = new TextField();
		textFieldGebruikersnaam.setMinWidth(550);
		textFieldGebruikersnaam.setMaxWidth(550);
		textFieldGebruikersnaam.setMinHeight(30);
		textFieldGebruikersnaam.onKeyTypedProperty().set(event -> valideerGebruikersnaam());
		VBox textFieldGebruikersnaamBox = new VBox();
		textFieldGebruikersnaamBox.setMinWidth(300);
		textFieldGebruikersnaamBox.setMinHeight(scherm.getHeight() / 5 - 50);
		textFieldGebruikersnaamBox.setAlignment(Pos.CENTER);
		textFieldGebruikersnaamBox.getChildren().add(textFieldGebruikersnaam);
		add(textFieldGebruikersnaamBox, 1, 2, 2, 1);
		gebruikersnaamError = bouwLabel("", Pos.CENTER_LEFT, true);
		add(gebruikersnaamError, 3, 2, 1, 1);
		Label geboortejaar = bouwLabel(messages.getString("fx_birthyear"), Pos.CENTER_RIGHT, false);
		add(geboortejaar, 0, 3, 1, 1);
		geboortejaarError = bouwLabel("", Pos.CENTER_LEFT, true);
		add(geboortejaarError, 3, 3, 1, 1);
		textFieldGeboortejaar = new TextField();
		textFieldGeboortejaar.setMinWidth(550);
		textFieldGeboortejaar.setMaxWidth(550);
		textFieldGeboortejaar.setMinHeight(30);
		textFieldGeboortejaar.onKeyTypedProperty().set(event -> valideerGeboortejaar());
		VBox textFieldGeboortejaarBox = new VBox();
		textFieldGeboortejaarBox.setMinWidth(300);
		textFieldGeboortejaarBox.setMinHeight(scherm.getHeight() / 5 - 50);
		textFieldGeboortejaarBox.setAlignment(Pos.CENTER);
		textFieldGeboortejaarBox.getChildren().add(textFieldGeboortejaar);
		add(textFieldGeboortejaarBox, 1, 3, 2, 1);
		VBox registreerKnop = bouwButton();
		add(registreerKnop, 1, 4, 1, 1);
		registratieMelding = bouwLabel("", Pos.CENTER_LEFT, true);
		registratieMelding.setWrapText(true);
		VBox registratieMeldingBox = new VBox();
		registratieMeldingBox.setMinWidth(300);
		registratieMeldingBox.setMinHeight(scherm.getHeight() / 5 - 50);
		registratieMeldingBox.setAlignment(Pos.CENTER_LEFT);
		registratieMeldingBox.getChildren().add(registratieMelding);
		add(registratieMeldingBox, 2, 4, 2, 1);
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
		Label welkomscherm = new Label(messages.getString("fx_main_menu"));
		welkomscherm.getStyleClass().addAll("font", "mediumText");
		welkomscherm.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		welkomscherm.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		welkomscherm.setOnMouseClicked(event -> gaNaarHoofdMenuScherm());
		Text kingdomino = new Text("Kingdomino");
		kingdomino.getStyleClass().addAll("titel", "hugeText");
		menu.getChildren().addAll(welkomscherm, kingdomino);
		widthProperty().addListener(observable -> menu.setSpacing(scherm.getWidth() - 750));
		return menu;
	}

	private Label bouwLabel(String inhoud, Pos alignment, boolean isError) {
		Label label = new Label(inhoud);
		label.getStyleClass().addAll("font", isError ? "smallText" : "mediumText", isError ? "error" : "");
		label.setMinWidth((scherm.getWidth() - 600) / 2);
		label.setMinHeight(scherm.getHeight() / 5 - 50);
		label.setAlignment(alignment);
		return label;
	}

	private VBox bouwButton() {
		Button registreerKnop = new Button(messages.getString("fx_register_player"));
		registreerKnop.getStyleClass().addAll("font", "mediumText", "border", "zandkleur");
		registreerKnop.setMinWidth(150);
		registreerKnop.setMinHeight(50);
		registreerKnop.setAlignment(Pos.CENTER);
		registreerKnop.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		registreerKnop.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		VBox registreerKnopBox = new VBox();
		registreerKnopBox.setMinWidth(300);
		registreerKnopBox.setMinHeight(scherm.getHeight() / 5 - 50);
		registreerKnopBox.setAlignment(Pos.CENTER);
		registreerKnop.setOnAction(event -> registreerSpeler());
		registreerKnopBox.getChildren().add(registreerKnop);
		return registreerKnopBox;
	}

	private void gaNaarHoofdMenuScherm() {
		getScene().setRoot(new HoofdMenuScherm(dc, scherm));
	}

	private void valideerGebruikersnaam() {
		registratieMelding.setText("");
		try {
			if (textFieldGebruikersnaam.getText().isBlank())
				throw new Exception();
			gebruikersnaamError.setText("");
		}
		catch (Exception e) {
			gebruikersnaamError.setText(messages.getString("invalid_input"));
		}
	}

	private void valideerGeboortejaar() {
		registratieMelding.setText("");
		try {
			if (Integer.parseInt(textFieldGeboortejaar.getText()) < 0)
				throw new Exception();
			geboortejaarError.setText("");
		}
		catch (Exception e) {
			geboortejaarError.setText(messages.getString("invalid_input"));
		}
		if (textFieldGeboortejaar.getText().isBlank())
			geboortejaarError.setText("");
	}

	private void registreerSpeler() {
		try {
			dc.registreerSpeler(textFieldGebruikersnaam.getText(), Integer.parseInt(textFieldGeboortejaar.getText()));
			registratieMelding.getStyleClass().remove("error");
			registratieMelding
					.setText(String.format(messages.getString("registration_success"), textFieldGebruikersnaam.getText()));
			textFieldGebruikersnaam.clear();
			textFieldGeboortejaar.clear();
		}
		catch (NumberFormatException nfe) {
			registratieMelding.setText(messages.getString("invalid_input"));
		}
		catch (IllegalArgumentException iae) {
			registratieMelding.setText(iae.getMessage());
		}
		catch (RuntimeException re) {
			registratieMelding.setText(messages.getString("no_connection"));
		}
		catch (Exception e) {
			registratieMelding.setText(messages.getString("error_occurred"));
		}
	}

}
