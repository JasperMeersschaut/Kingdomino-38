
package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Taal;

public class SpelersToevoegenScherm extends StackPane {

	private final GridPane gridPane;
	private final ResourceBundle messages;
	private final DomeinController dc;
	private final Stage scherm;
	private ListView<String> lijst;
	private Label gebruiker1;
	private Label gebruiker2;
	private Label gebruiker3;
	private Label gebruiker4;
	private ComboBox<String> kleur1;
	private ComboBox<String> kleur2;
	private ComboBox<String> kleur3;
	private ComboBox<String> kleur4;
	private Button voegSpeler1Toe;
	private Button voegSpeler2Toe;
	private Button voegSpeler3Toe;
	private Button voegSpeler4Toe;
	private Label melding1;
	private Label melding2;
	private Label melding3;
	private Label melding4;
	private Label spelMelding;
	private final ImageView imageLoader = new ImageView(new Image(getClass().getResourceAsStream("/images/loader.gif")));

	/**
	 * Constructor voor het SpelersToevoegenScherm.
	 *
	 * @param dc     de domeincontroller.
	 * @param scherm het huidige scherm.
	 */
	public SpelersToevoegenScherm(DomeinController dc, Stage scherm) {
		gridPane = new GridPane();
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		this.dc = dc;
		this.scherm = scherm;
		bouwScherm();
	}

	private void bouwScherm() {
		scherm.centerOnScreen();
		getStyleClass().add("zandkleur");
		for (int i = 0; i < 4; i++)
			gridPane.getColumnConstraints().add(new ColumnConstraints(scherm.getWidth() / 4));
		gridPane.getRowConstraints().add(new RowConstraints(135));
		for (int i = 0; i < 5; i++)
			gridPane.getRowConstraints().add(new RowConstraints(108));
		VBox menu = bouwMenu();
		gridPane.add(menu, 0, 0, 4, 1);
		gebruiker1 = bouwLabel(messages.getString("fx_player") + " 1: _____");
		gridPane.add(gebruiker1, 1, 1, 1, 1);
		gebruiker2 = bouwLabel(messages.getString("fx_player") + " 2: _____");
		gridPane.add(gebruiker2, 1, 2, 1, 1);
		gebruiker3 = bouwLabel(messages.getString("fx_player") + " 3: _____");
		gridPane.add(gebruiker3, 1, 3, 1, 1);
		gebruiker4 = bouwLabel(messages.getString("fx_player") + " 4: _____");
		gridPane.add(gebruiker4, 1, 4, 1, 1);
		kleur1 = maakComboBox();
		voegMouseClickedEventToe(kleur1);
		VBox kleur1Box = maakVBox(kleur1);
		gridPane.add(kleur1Box, 2, 1, 1, 1);
		kleur2 = maakComboBox();
		voegMouseClickedEventToe(kleur2);
		VBox kleur2Box = maakVBox(kleur2);
		gridPane.add(kleur2Box, 2, 2, 1, 1);
		kleur3 = maakComboBox();
		voegMouseClickedEventToe(kleur3);
		VBox kleur3Box = maakVBox(kleur3);
		gridPane.add(kleur3Box, 2, 3, 1, 1);
		kleur4 = maakComboBox();
		voegMouseClickedEventToe(kleur4);
		VBox kleur4Box = maakVBox(kleur4);
		gridPane.add(kleur4Box, 2, 4, 1, 1);
		voegSpeler1Toe = bouwKnop();
		voegSpeler1Toe.setOnAction(event -> voegSpelerToe(1));
		VBox voegSpeler1ToeBox = maakVBox(voegSpeler1Toe);
		melding1 = maakLabel();
		voegSpeler1ToeBox.getChildren().add(melding1);
		gridPane.add(voegSpeler1ToeBox, 3, 1, 1, 1);
		voegSpeler2Toe = bouwKnop();
		voegSpeler2Toe.setOnAction(event -> voegSpelerToe(2));
		VBox voegSpeler2ToeBox = maakVBox(voegSpeler2Toe);
		melding2 = maakLabel();
		voegSpeler2ToeBox.getChildren().add(melding2);
		gridPane.add(voegSpeler2ToeBox, 3, 2, 1, 1);
		voegSpeler3Toe = bouwKnop();
		voegSpeler3Toe.setOnAction(event -> voegSpelerToe(3));
		VBox voegSpeler3ToeBox = maakVBox(voegSpeler3Toe);
		melding3 = maakLabel();
		voegSpeler3ToeBox.getChildren().add(melding3);
		gridPane.add(voegSpeler3ToeBox, 3, 3, 1, 1);
		voegSpeler4Toe = bouwKnop();
		voegSpeler4Toe.setOnAction(event -> voegSpelerToe(4));
		VBox voegSpeler4ToeBox = maakVBox(voegSpeler4Toe);
		melding4 = maakLabel();
		voegSpeler4ToeBox.getChildren().add(melding4);
		gridPane.add(voegSpeler4ToeBox, 3, 4, 1, 1);
		VBox spelersOverzicht = bouwSpelersOverzicht();
		gridPane.add(spelersOverzicht, 0, 1, 1, 5);
		VBox startSpel = bouwKnopStartSpel();
		gridPane.add(startSpel, 2, 5, 1, 1);
		spelMelding = new Label("");
		spelMelding.setAlignment(Pos.CENTER);
		spelMelding.setWrapText(true);
		spelMelding.getStyleClass().addAll("font", "smallText", "error");
		gridPane.add(spelMelding, 3, 5, 1, 1);
		getChildren().add(gridPane);
	}

	private VBox bouwMenu() {
		VBox menu = new VBox();
		HBox menuItems = bouwMenuItems();
		Line line = new Line();
		line.setStartX(0);
		line.setEndX(scherm.getWidth());
		line.getStyleClass().add("line");
		menu.getChildren().addAll(menuItems, line);
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
		return menu;
	}

	private Label bouwLabel(String inhoud) {
		Label label = new Label(inhoud);
		label.getStyleClass().addAll("font", "mediumText");
		label.setMinWidth(350);
		label.setMinHeight(108);
		label.setAlignment(Pos.CENTER_RIGHT);
		return label;
	}

	private ComboBox<String> maakComboBox() {
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setPromptText(messages.getString("fx_color"));
		comboBox.getStyleClass().addAll("font", "mediumText", "border", "zandkleur", "lijst");
		comboBox.setMinWidth(210);
		comboBox.setMinHeight(60);
		comboBox.setVisibleRowCount(dc.geefBeschikbareKleuren().size());
		return comboBox;
	}

	private Button bouwKnop() {
		Button knop = new Button(messages.getString("fx_add"));
		knop.getStyleClass().addAll("font", "mediumText", "border", "zandkleur");
		knop.setMinWidth(150);
		knop.setMinHeight(50);
		knop.setAlignment(Pos.CENTER_LEFT);
		knop.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		knop.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		return knop;
	}

	private void voegMouseClickedEventToe(ComboBox<String> box) {
		box.setOnMouseClicked(event -> {
			box.getItems().clear();
			box.getItems().addAll(dc.geefBeschikbareKleuren());
			spelMelding.setText("");
		});
	}

	private VBox maakVBox(ComboBox<String> kleur) {
		VBox box = new VBox();
		box.getChildren().add(kleur);
		box.setAlignment(Pos.CENTER);
		return box;
	}

	private VBox maakVBox(Button knop) {
		VBox box = new VBox();
		box.getChildren().add(knop);
		box.setAlignment(Pos.TOP_LEFT);
		return box;
	}

	private Label maakLabel() {
		Label label = new Label("");
		label.setAlignment(Pos.BOTTOM_LEFT);
		label.setWrapText(true);
		label.getStyleClass().addAll("font", "smallText", "error");
		return label;
	}

	private VBox bouwSpelersOverzicht() {
		VBox overzicht = new VBox();
		overzicht.setPadding(new Insets(10));
		lijst = new ListView<>();
		updateLijst();
		lijst.setMinWidth(280);
		lijst.setMinHeight(520);
		lijst.getStyleClass().addAll("lijst", "border", "smallText", "font");
		voegDragDropFunctionaliteitToe(lijst);
		overzicht.getChildren().add(lijst);
		overzicht.setAlignment(Pos.CENTER_RIGHT);
		return overzicht;
	}

	private void voegDragDropFunctionaliteitToe(ListView<String> lijst) {
		lijst.setOnDragDetected(event -> {
			String item = lijst.getSelectionModel().getSelectedItem();
			if (item == null)
				return;
			Dragboard dragboard = lijst.startDragAndDrop(TransferMode.COPY);
			ClipboardContent content = new ClipboardContent();
			content.putString(item.substring(0, item.indexOf(" - ")));
			dragboard.setContent(content);
			spelMelding.setText("");
			event.consume();
		});
		List<Label> gebruikers = List.of(gebruiker1, gebruiker2, gebruiker3, gebruiker4);
		for (Label gebruiker : gebruikers) {
			gebruiker.setOnDragOver(event -> {
				if (event.getGestureSource() != gebruiker && event.getDragboard().hasString())
					event.acceptTransferModes(TransferMode.COPY);
				event.consume();
			});
			gebruiker.setOnDragDropped(event -> {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasString()) {
					gebruiker.setText(gebruiker.getText().substring(0, 10) + db.getString());
					success = true;
				}
				event.setDropCompleted(success);
				event.consume();
			});
		}
	}

	private VBox bouwKnopStartSpel() {
		Button knop = new Button(messages.getString("fx_start_game"));
		knop.getStyleClass().addAll("font", "mediumText", "border", "zandkleur");
		knop.setMinWidth(150);
		knop.setMinHeight(50);
		knop.setAlignment(Pos.CENTER);
		knop.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		knop.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
		knop.setOnAction(event -> startSpel());
		VBox knopBox = new VBox();
		knopBox.setMinWidth(350);
		knopBox.setMinHeight(108);
		knopBox.setAlignment(Pos.CENTER);
		knopBox.getChildren().add(knop);
		return knopBox;
	}

	private void updateLijst() {
		List<SpelerDTO> spelers = dc.geefBeschikbareSpelers();
		lijst.getItems().clear();
		lijst.setItems(FXCollections.observableArrayList(geefSpelers(spelers)));
	}

	private List<String> geefSpelers(List<SpelerDTO> spelers) {
		List<String> spelersList = new ArrayList<>();
		for (SpelerDTO speler : spelers)
			spelersList.add(speler.gebruikersnaam() + " - " + speler.geboortejaar());
		return spelersList;
	}

	private void gaNaarHoofdMenuScherm() {
		getScene().setRoot(new HoofdMenuScherm(dc, scherm));
	}

	private void voegSpelerToe(int index) {
		List<Label> spelers = List.of(gebruiker1, gebruiker2, gebruiker3, gebruiker4);
		List<ComboBox<String>> kleuren = List.of(kleur1, kleur2, kleur3, kleur4);
		List<Button> knoppen = List.of(voegSpeler1Toe, voegSpeler2Toe, voegSpeler3Toe, voegSpeler4Toe);
		List<Label> meldingen = List.of(melding1, melding2, melding3, melding4);
		boolean ingevuld = true;
		if (spelers.get(index - 1).getText().substring(10).length() < 6) {
			meldingen.get(index - 1).setText(String.format(messages.getString("fx_player_empty"), index));
			ingevuld = false;
		}
		if (kleuren.get(index - 1).getValue() == null) {
			meldingen.get(index - 1).setText(messages.getString("fx_color_empty"));
			ingevuld = false;
		}
		if (ingevuld) {
			getChildren().add(imageLoader);
			List<Boolean> disabled = new ArrayList<>();
			for (Button knop : knoppen) {
				disabled.add(knop.isDisabled());
				if (!knop.isDisabled())
					knop.setDisable(true);
			}
			Task<Void> startTask = new Task<>() {

				@Override
				protected Void call() {
					try {
						Platform.runLater(() -> spelMelding.setText(""));
						dc.voegSpelerToeAanSpel(spelers.get(index - 1).getText().substring(10),
								kleuren.get(index - 1).getValue());
						Platform.runLater(() -> {
							spelers.get(index - 1).setDisable(true);
							kleuren.get(index - 1).setDisable(true);
							updateLijst();
							meldingen.get(index - 1).getStyleClass().remove("error");
							meldingen.get(index - 1).setText(String.format(messages.getString("fx_player_added"),
									spelers.get(index - 1).getText().substring(10)));
						});
					}
					catch (IllegalArgumentException iae) {
						Platform.runLater(() -> {
							meldingen.get(index - 1).setText(iae.getMessage());
							knoppen.get(index - 1).setDisable(false);
						});
					}
					catch (Exception e) {
						Platform.runLater(() -> {
							meldingen.get(index - 1).setText(messages.getString("error_occurred"));
							knoppen.get(index - 1).setDisable(false);
						});
					}
					return null;
				}

			};
			startTask.setOnSucceeded(event -> {
				getChildren().remove(imageLoader);
				for (int i = 0; i < knoppen.size(); i++)
					if (!disabled.get(i) && i != index - 1)
						knoppen.get(i).setDisable(false);
			});
			startTask.setOnFailed(event -> {
				getChildren().remove(imageLoader);
				for (int i = 0; i < knoppen.size(); i++)
					if (!disabled.get(i) && i != index - 1)
						knoppen.get(i).setDisable(false);
			});
			new Thread(startTask).start();
		}
	}

	private void startSpel() {
		try {
			dc.startSpel();
			getScene().setRoot(new SpelSpelenScherm(dc, scherm));
		}
		catch (IllegalArgumentException iae) {
			spelMelding.setText(iae.getMessage());
		}
		catch (Exception e) {
			spelMelding.setText(messages.getString("error_occurred"));
		}
	}

}
