
package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import dto.SpelerDTO;
import dto.TegelDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import utils.Richting;
import utils.Taal;

public class TegelLeggenScherm extends HBox {

	private final ResourceBundle messages;
	private final DomeinController dc;
	private final Stage scherm;
	private final TegelDTO tegelTeLeggen;
	private final SpelerDTO huidigeSpeler;
	private final KoninkrijkScherm koninkrijkScherm;
	private final SpelSpelenScherm spelSpelenScherm;
	private Richting richting = Richting.RECHTS;
	private Label error;

	public TegelLeggenScherm(DomeinController dc, Stage scherm, TegelDTO tegelTeLeggen, SpelerDTO huidigeSpeler,
			KoninkrijkScherm koninkrijk, SpelSpelenScherm spelSpelenScherm) {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		this.dc = dc;
		this.scherm = scherm;
		this.tegelTeLeggen = tegelTeLeggen;
		this.huidigeSpeler = huidigeSpeler;
		this.koninkrijkScherm = koninkrijk;
		this.spelSpelenScherm = spelSpelenScherm;
		bouwScherm();
	}

	private void bouwScherm() {
		scherm.centerOnScreen();
		getStyleClass().add("zandkleur");
		GridPane bediening = bouwBediening();
		GridPane koninkrijk = new GridPane();
		ColumnConstraints col = new ColumnConstraints(85);
		RowConstraints row = new RowConstraints(85);
		koninkrijk.getColumnConstraints().addAll(col, col, col, col, col, col, col, col, col);
		koninkrijk.getRowConstraints().addAll(row, row, row, row, row, row, row, row, row);
		koninkrijk.add(koninkrijkScherm, 0, 0, 9, 9);
		for (int i = 0; i < dc.geefKoninkrijk(huidigeSpeler).length; i++)
			for (int j = 0; j < dc.geefKoninkrijk(huidigeSpeler)[0].length; j++) {
				int finalI = i;
				int finalJ = j;
				Pane vak = new Pane();
				vak.setPrefSize(85, 85);
				vak.setOnMouseClicked(event -> {
					try {
						dc.legTegelInKoninkrijk(tegelTeLeggen, huidigeSpeler, "" + finalI + finalJ, switch (richting) {
							case BOVEN -> 3;
							case LINKS -> 2;
							case RECHTS -> 1;
							case ONDER -> 4;
						});
						koninkrijkScherm.legTegelInKoninkrijk(tegelTeLeggen, huidigeSpeler, finalI, finalJ, richting);
					}
					catch (IllegalArgumentException iae) {
						error.setText(iae.getMessage());
					}
					catch (Exception e) {
						e.printStackTrace();
						error.setText(messages.getString("error_occurred"));
					}
				});
				koninkrijk.add(vak, finalJ, finalI);
			}
		getChildren().addAll(bediening, koninkrijk);
	}

	private GridPane bouwBediening() {
		GridPane bediening = new GridPane();
		bediening.setVgap(50);
		bediening.getRowConstraints().addAll(new RowConstraints(scherm.getHeight() / 5),
				new RowConstraints(scherm.getHeight() * 2 / 5), new RowConstraints(scherm.getHeight() / 4));
		bediening.setAlignment(Pos.TOP_CENTER);
		bediening.setMinWidth(scherm.getWidth() / 3);
		Label huidigeSpelerLabel = new Label(
				messages.getString("fx_current_player") + " " + huidigeSpeler.gebruikersnaam());
		huidigeSpelerLabel.setMinWidth(scherm.getWidth() / 3);
		huidigeSpelerLabel.getStyleClass().addAll("font", "mediumText");
		huidigeSpelerLabel.setAlignment(Pos.TOP_CENTER);
		huidigeSpelerLabel.setTextAlignment(TextAlignment.CENTER);
		bediening.add(huidigeSpelerLabel, 0, 0);
		ImageView tegel = new ImageView(new Image(getClass().getResourceAsStream(
				String.format("/images/dominotegels/tegel_%02d_voorkant.png", tegelTeLeggen.nummer()))));
		tegel.setPreserveRatio(true);
		tegel.setFitWidth(scherm.getWidth() / 3 - 100);
		double richtingsGraden = switch (richting) {
			case ONDER -> 90;
			case LINKS -> 180;
			case BOVEN -> 270;
			default -> 0;
		};
		tegel.setRotate(richtingsGraden);
		HBox tegelView = new HBox(tegel);
		tegelView.setAlignment(Pos.CENTER);
		bediening.add(tegelView, 0, 1);
		VBox bedieners = new VBox();
		bedieners.setSpacing(20);
		HBox knoppen = new HBox();
		knoppen.setSpacing(50);
		Label draaiKnop = maakKnop("draai");
		draaiKnop.setOnMouseClicked(event -> {
			richting = switch (richting) {
				case ONDER -> Richting.LINKS;
				case LINKS -> Richting.BOVEN;
				case BOVEN -> Richting.RECHTS;
				default -> Richting.ONDER;
			};
			refresh();
		});
		Label weggooiKnop = maakKnop("weggooien");
		weggooiKnop.setOnMouseClicked(event -> {
			try {
				dc.gooiWeg(tegelTeLeggen, huidigeSpeler);
				spelSpelenScherm.setTegelVerwijderd(true);
				getScene().setRoot(spelSpelenScherm);
			}
			catch (IllegalArgumentException iae) {
				error.setText(iae.getMessage());
			}
			catch (Exception e) {
				error.setText(messages.getString("error_occurred"));
			}
		});
		knoppen.getChildren().addAll(draaiKnop, weggooiKnop);
		knoppen.setAlignment(Pos.TOP_CENTER);
		error = new Label("");
		error.getStyleClass().addAll("font", "smallText", "error");
		error.setMinWidth(scherm.getWidth() / 3);
		error.setAlignment(Pos.CENTER);
		bedieners.getChildren().addAll(knoppen, error);
		bediening.add(bedieners, 0, 2);
		return bediening;
	}

	private Label maakKnop(String tekst) {
		Label knop = new Label(tekst);
		knop.getStyleClass().addAll("font", "mediumText", "border");
		knop.setMinWidth(200);
		knop.setAlignment(Pos.TOP_CENTER);
		knop.setTextAlignment(TextAlignment.CENTER);
		return knop;
	}

	private void refresh() {
		getChildren().clear();
		bouwScherm();
	}

}
