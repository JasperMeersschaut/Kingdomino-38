
package gui;

import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import utils.Kleur;
import utils.Taal;

public class SpelSpelenScherm extends GridPane {

	private final ResourceBundle messages;
	private final DomeinController dc;
	private final Stage scherm;
	private final int[][] koninkrijkPosities = { { 0, 0 }, { 2, 0 }, { 0, 2 }, { 2, 2 } };
	private SpelerDTO huidigeSpeler;
	private Label error;
	private List<KoninkrijkScherm> koninkrijken;
	private TegelDTO tegelTeLeggen;
	private KoninkrijkScherm koninkrijkScherm;

	public SpelSpelenScherm(DomeinController dc, Stage scherm) {
		messages = ResourceBundle.getBundle("messages", Taal.getTaal());
		this.dc = dc;
		this.scherm = scherm;
		koninkrijken = new ArrayList<>();
		for (int i = 0; i < dc.geefSpelers().size(); i++)
			koninkrijken.add(new KoninkrijkScherm(dc, dc.geefSpelers().get(i), this));
		bouwScherm();
	}

	public void bouwScherm() {
		getStyleClass().add("zandkleur");
		scherm.centerOnScreen();
		ColumnConstraints col = new ColumnConstraints(300);
		RowConstraints row = new RowConstraints(300);
		getColumnConstraints().addAll(col, new ColumnConstraints(scherm.getWidth() - 630), col);
		getRowConstraints().addAll(row, new RowConstraints(scherm.getHeight() - 680), row);
		legKoninkrijken();
		legTegels();
		setOnMouseEntered(event -> {
			if (!dc.geefEindKolom().isEmpty() && dc.geefStartKolom().stream().allMatch(t -> t.spelerOpTegel() == null))
				dc.vulKolommenAan();
			refresh();
		});
	}

	private void legKoninkrijken() {
		List<SpelerDTO> spelers = dc.geefSpelers();
		for (int i = 0; i < dc.geefSpelers().size(); i++) {
			VBox koninkrijkView = new VBox();
			Pane koninkrijk = new Pane(koninkrijken.get(i));
			koninkrijk.setScaleX(0.37);
			koninkrijk.setScaleY(0.37);
			koninkrijk.setMinWidth(300);
			koninkrijk.setMinHeight(300);
			koninkrijk.setMaxWidth(300);
			koninkrijk.setMaxHeight(300);
			koninkrijk.setTranslateX(-80);
			koninkrijk.setTranslateY(-60);
			Label koninkrijkVanSpeler = new Label(spelers.get(i).gebruikersnaam());
			koninkrijkVanSpeler.getStyleClass().addAll("font", "smallText");
			koninkrijkVanSpeler.setMinWidth(300);
			koninkrijkVanSpeler.setAlignment(Pos.CENTER);
			koninkrijkView.getChildren().addAll(koninkrijk, koninkrijkVanSpeler);
			add(koninkrijkView, koninkrijkPosities[i][0], koninkrijkPosities[i][1]);
		}
	}

	private void legTegels() {
		VBox stapels = new VBox();
		huidigeSpeler = dc.geefHuidigeSpeler();
		stapels.setSpacing(50);
		Label huidigeSpelerLabel = new Label("Huidige speler: " + dc.geefHuidigeSpeler().gebruikersnaam());
		huidigeSpelerLabel.getStyleClass().addAll("font", "bigText");
		stapels.getChildren().add(huidigeSpelerLabel);
		if (!dc.geefStapel().isEmpty()) {
			ImageView bovensteTegel = new ImageView(new Image(getClass()
					.getResource(
							String.format("/images/dominotegel/tegel_%02d_achterkant.png", dc.geefStapel().get(0).nummer()))
					.toExternalForm()));
			bovensteTegel.setPreserveRatio(true);
			bovensteTegel.setFitWidth(200);
			stapels.getChildren().add(bovensteTegel);
		}
		HBox kolommen = new HBox();
		VBox startKolom = new VBox();
		startKolom.setSpacing(20);
		startKolom.setMinWidth(200);
		VBox eindKolom = new VBox();
		eindKolom.setSpacing(20);
		eindKolom.setMinWidth(200);
		addKolomListener(startKolom, true);
		if (!dc.geefEindKolom().isEmpty())
			addKolomListener(eindKolom, false);
		kolommen.getChildren().addAll(startKolom, eindKolom);
		kolommen.setSpacing(100);
		kolommen.setAlignment(Pos.CENTER);
		error = new Label("");
		error.getStyleClass().addAll("font", "mediumText", "error");
		stapels.getChildren().addAll(kolommen, error);
		stapels.setAlignment(Pos.TOP_CENTER);
		add(stapels, 1, 0, 1, 3);
	}

	private void addKolomListener(VBox kolom, boolean isStartKolom) {
		for (int i = 0; i < dc.geefSpelers().size(); i++) {
			TegelDTO tegel = isStartKolom ? dc.geefStartKolom().get(i) : dc.geefEindKolom().get(i);
			ImageView tegelImg = new ImageView(new Image(
					getClass().getResource(String.format("/images/dominotegel/tegel_%02d_voorkant.png", tegel.nummer()))
							.toExternalForm()));
			tegelImg.setPreserveRatio(true);
			tegelImg.setFitWidth(200);
			StackPane tegelView = new StackPane();
			tegelView.getChildren().add(tegelImg);
			kolom.getChildren().add(tegelView);
			if (tegel.spelerOpTegel() != null) {
				Color kleur = switch (Kleur.geefKleur(tegel.kleur())) {
					case BLAUW -> Color.BLUE;
					case GEEL -> Color.YELLOW;
					case ROOS -> Color.PINK;
					case GROEN -> Color.LIMEGREEN;
				};
				Circle koning = new Circle(20, kleur);
				tegelView.getChildren().add(koning);
			}
			tegelImg.setOnMouseClicked(event -> {
				try {
					dc.plaatsKoningOpTegel(huidigeSpeler, tegel.nummer());
					Color kleur = switch (Kleur.geefKleur(huidigeSpeler.kleur())) {
						case BLAUW -> Color.BLUE;
						case GEEL -> Color.YELLOW;
						case GROEN -> Color.LIMEGREEN;
						case ROOS -> Color.PINK;
					};
					Circle koning = new Circle(20, kleur);
					tegelView.getChildren().add(koning);
					koninkrijkScherm = koninkrijken.get(dc.geefSpelers().indexOf(huidigeSpeler));
					tegelTeLeggen = dc.geefStartKolom().stream()
							.filter(t -> t.spelerOpTegel() != null && t.spelerOpTegel().equals(huidigeSpeler.gebruikersnaam()))
							.findAny().get();
					if (isStartKolom && dc.geefStartKolom().stream().allMatch(t -> t.spelerOpTegel() != null))
						dc.vulKolommenAan();
					refresh();
					if (!isStartKolom)
						getScene().setRoot(
								new TegelLeggenScherm(dc, scherm, tegelTeLeggen, huidigeSpeler, koninkrijkScherm, this));
				}
				catch (IllegalArgumentException iae) {
					error.setText(iae.getMessage());
				}
				catch (Exception e) {
					e.printStackTrace();
					error.setText(messages.getString("error_occurred"));
				}
			});
		}
	}

	private void refresh() {
		getChildren().clear();
		bouwScherm();
	}

}
