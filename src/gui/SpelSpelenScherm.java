
package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.SpelerDTO;
import dto.TegelDTO;
import javafx.geometry.Insets;
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
	public Label error;
	private List<KoninkrijkScherm> koninkrijken;
	private TegelDTO tegelTeLeggen;
	private KoninkrijkScherm koninkrijkScherm;
	private boolean tegelVerwijderd;

	/**
	 * Constructor voor het SpelSpelenScherm.
	 *
	 * @param dc     de domeincontroller.
	 * @param scherm het huidige scherm.
	 */
	public SpelSpelenScherm(DomeinController dc, Stage scherm) {
		messages = ResourceBundle.getBundle("messages", Taal.geefTaal());
		this.dc = dc;
		this.scherm = scherm;
		koninkrijken = new ArrayList<>();
		for (int i = 0; i < dc.geefSpelers().size(); i++)
			koninkrijken.add(new KoninkrijkScherm(dc, dc.geefSpelers().get(i), this));
		setTegelVerwijderd(false);
		bouwScherm();
	}

	/**
	 * Stelt in of de tegelTeLeggen verwijderd is.
	 * 
	 * @param waarde boolean die aangeeft of de tegel verwijderd is.
	 */
	public void setTegelVerwijderd(boolean waarde) {
		tegelVerwijderd = waarde;
	}

	private void bouwScherm() {
		getStyleClass().add("zandkleur");
		scherm.centerOnScreen();
		setPadding(new Insets(10));
		ColumnConstraints col = new ColumnConstraints(300);
		RowConstraints row = new RowConstraints(350);
		getColumnConstraints().addAll(col, new ColumnConstraints(scherm.getWidth() - 640), col);
		getRowConstraints().addAll(row, new RowConstraints(scherm.getHeight() - 760), row);
		legKoninkrijken();
		legTegels();
		setOnMouseEntered(event -> {
			if (!dc.geefEindKolom().isEmpty() && dc.geefStartKolom().stream().allMatch(t -> t.spelerOpTegel() == null))
				dc.vulStapelsEnKolommenAan();
			refresh();
			if (dc.geefEindKolom().isEmpty() && dc.geefStapel().isEmpty()
					&& !dc.geefStartKolom().stream().allMatch(t -> t.spelerOpTegel() == null)) {
				koninkrijkScherm = koninkrijken.get(dc.geefSpelers().indexOf(huidigeSpeler));
				tegelTeLeggen = dc.geefStartKolom().stream()
						.filter(t -> t.spelerOpTegel() != null && t.spelerOpTegel().equals(huidigeSpeler.gebruikersnaam()))
						.findAny().get();
				getScene().setRoot(new TegelLeggenScherm(dc, scherm, tegelTeLeggen, huidigeSpeler, koninkrijkScherm, this));
			}
			if (dc.geefEindKolom().isEmpty() && dc.geefStapel().isEmpty()
					&& dc.geefStartKolom().stream().allMatch(t -> t.spelerOpTegel() == null))
				getScene().setRoot(new ScoreScherm(dc, scherm));
		});
	}

	private void legKoninkrijken() {
		List<SpelerDTO> spelers = dc.geefSpelers();
		for (int i = 0; i < dc.geefSpelers().size(); i++) {
			VBox koninkrijkView = new VBox();
			GridPane koninkrijk = koninkrijken.get(i).geefGeschaaldKoninkrijk();
			Label koninkrijkVanSpeler = new Label(spelers.get(i).gebruikersnaam());
			koninkrijkVanSpeler.getStyleClass().addAll("font", "smallText");
			koninkrijkVanSpeler.setMinWidth(300);
			koninkrijkVanSpeler.setAlignment(Pos.CENTER);
			koninkrijkView.getChildren().addAll(koninkrijk, koninkrijkVanSpeler);
			koninkrijkView.setAlignment(Pos.CENTER_LEFT);
			add(koninkrijkView, koninkrijkPosities[i][0], koninkrijkPosities[i][1]);
		}
	}

	private void legTegels() {
		VBox stapels = new VBox();
		huidigeSpeler = dc.geefHuidigeSpeler();
		stapels.setSpacing(20);
		Label huidigeSpelerLabel = new Label(
				messages.getString("fx_current_player") + " " + huidigeSpeler.gebruikersnaam());
		huidigeSpelerLabel.getStyleClass().addAll("font", "bigText");
		stapels.getChildren().add(huidigeSpelerLabel);
		if (!dc.geefStapel().isEmpty()) {
			ImageView bovensteTegel = new ImageView(new Image(getClass()
					.getResource(
							String.format("/images/dominotegels/tegel_%02d_achterkant.png", dc.geefStapel().get(0).nummer()))
					.toExternalForm()));
			bovensteTegel.setPreserveRatio(true);
			bovensteTegel.setFitWidth(200);
			stapels.getChildren().add(bovensteTegel);
		} else {
			Pane bovensteTegel = new Pane();
			bovensteTegel.setMinHeight(100);
			bovensteTegel.setMinWidth(200);
			bovensteTegel.setMaxHeight(100);
			bovensteTegel.setMaxWidth(200);
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
		if (tegelVerwijderd) {
			error.setText(messages.getString("tile_discarded"));
			error.getStyleClass().remove("error");
		}
		stapels.getChildren().addAll(kolommen, error);
		stapels.setAlignment(Pos.TOP_CENTER);
		add(stapels, 1, 0, 1, 3);
	}

	private void addKolomListener(VBox kolom, boolean isStartKolom) {
		for (int i = 0; i < dc.geefSpelers().size(); i++) {
			TegelDTO tegel = isStartKolom ? dc.geefStartKolom().get(i) : dc.geefEindKolom().get(i);
			ImageView tegelImg = new ImageView(new Image(
					getClass().getResource(String.format("/images/dominotegels/tegel_%02d_voorkant.png", tegel.nummer()))
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
				error.getStyleClass().add("error");
				setTegelVerwijderd(false);
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
					if (isStartKolom) {
						if (dc.geefStartKolom().stream().allMatch(t -> t.spelerOpTegel() != null))
							dc.vulStapelsEnKolommenAan();
						refresh();
					} else {
						koninkrijkScherm = koninkrijken.get(dc.geefSpelers().indexOf(huidigeSpeler));
						tegelTeLeggen = dc.geefStartKolom().stream().filter(
								t -> t.spelerOpTegel() != null && t.spelerOpTegel().equals(huidigeSpeler.gebruikersnaam()))
								.findAny().get();
						getScene().setRoot(
								new TegelLeggenScherm(dc, scherm, tegelTeLeggen, huidigeSpeler, koninkrijkScherm, this));
					}
				}
				catch (IllegalArgumentException iae) {
					error.setText(iae.getMessage());
					error.getStyleClass().add("error");
					setTegelVerwijderd(false);
				}
				catch (Exception e) {
					error.setText(messages.getString("error_occurred"));
					error.getStyleClass().add("error");
					setTegelVerwijderd(false);
				}
			});
		}
	}

	private void refresh() {
		getChildren().clear();
		bouwScherm();
	}

}
