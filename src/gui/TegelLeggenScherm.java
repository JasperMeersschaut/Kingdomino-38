
package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import dto.SpelerDTO;
import dto.TegelDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

    public TegelLeggenScherm(DomeinController dc, Stage scherm, TegelDTO tegelTeLeggen, SpelerDTO huidigeSpeler, KoninkrijkScherm koninkrijk, SpelSpelenScherm spelSpelenScherm) {
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
		koninkrijkScherm.setMinWidth(scherm.getHeight());
		koninkrijkScherm.setMinHeight(scherm.getHeight());
		koninkrijkScherm.setMaxWidth(scherm.getHeight());
		koninkrijkScherm.setMaxHeight(scherm.getHeight());
        koninkrijkScherm.setListeners(tegelTeLeggen,richting);
		getChildren().addAll(bediening, koninkrijkScherm);
	}

    private GridPane bouwBediening() {
		GridPane bediening = new GridPane();
		bediening.setVgap(50);
		bediening.getRowConstraints().addAll(new RowConstraints(scherm.getHeight() * 2 / 3),
				new RowConstraints(scherm.getHeight() / 3));
		bediening.setAlignment(Pos.BOTTOM_CENTER);
		bediening.setMinWidth(scherm.getWidth() / 3);
		ImageView tegel = new ImageView(new Image(getClass().getResourceAsStream(
				String.format("/images/dominotegel/tegel_%02d_voorkant.png", tegelTeLeggen.nummer()))));
		tegel.setPreserveRatio(true);
		tegel.setFitWidth(scherm.getWidth() / 3 - 100);
		double richtingsGraden = switch (richting) {
			case ONDER -> 90;
			case LINKS -> 180;
			case BOVEN -> 270;
			default -> 0;
		};
		tegel.setRotate(richtingsGraden);
		bediening.add(tegel, 0, 0);
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
				getScene().setRoot(spelSpelenScherm);
			}
			catch (IllegalArgumentException iae) {
				error.setText(iae.getMessage());
			}
			catch (Exception e) {
				e.printStackTrace();
				error.setText(messages.getString("error_occurred"));
			}
		});
		knoppen.getChildren().addAll(draaiKnop, weggooiKnop);
		knoppen.setAlignment(Pos.TOP_CENTER);
		error = new Label("");
		error.getStyleClass().addAll("font", "smallText", "error");
		error.setAlignment(Pos.CENTER);
		bedieners.getChildren().addAll(knoppen, error);
		bediening.add(bedieners, 0, 1);
		return bediening;
	}

	private Label maakKnop(String tekst) {
		Label knop = new Label(tekst);
		knop.getStyleClass().addAll("font", "mediumText", "border");
		knop.setMinWidth(200);
		return knop;
	}

	private void refresh() {
		getChildren().clear();
		bouwScherm();
	}

}
