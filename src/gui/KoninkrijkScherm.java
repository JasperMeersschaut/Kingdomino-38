
package gui;

import domein.DomeinController;
import dto.SpelerDTO;
import dto.TegelDTO;
import dto.VakDTO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import utils.Kleur;
import utils.Richting;

public class KoninkrijkScherm extends GridPane {

	private final DomeinController dc;
	private SpelerDTO speler;
	private final SpelSpelenScherm spelSpelenScherm;

	public KoninkrijkScherm(DomeinController dc, SpelerDTO speler, SpelSpelenScherm spelSpelenScherm) {
		this.dc = dc;
		this.speler = speler;
		this.spelSpelenScherm = spelSpelenScherm;
		bouwScherm();
	}

	private void bouwScherm() {
		setGridLinesVisible(true);
		VakDTO[][] koninkrijk = dc.geefKoninkrijk(speler);
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setMinWidth(90);
		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setMinHeight(90);
		for (int i = 0; i < koninkrijk.length; i++)
			getColumnConstraints().add(columnConstraints);
		for (int j = 0; j < koninkrijk[0].length; j++)
			getRowConstraints().add(rowConstraints);
		String kleur = switch (Kleur.geefKleur(speler.kleur())) {
			case BLAUW -> "blauw";
			case GEEL -> "geel";
			case GROEN -> "groen";
			case ROOS -> "roos";
		};
		ImageView kasteel = new ImageView(
				new Image(getClass().getResourceAsStream(String.format("/images/starttegel/starttegel_%s.png", kleur))));
		kasteel.setPreserveRatio(true);
		kasteel.setFitHeight(90);
		add(kasteel, 4, 4);
	}

	public void setListeners(TegelDTO tegel, Richting richting) {
		for (int i = 0; i < dc.geefKoninkrijk(speler).length; i++)
			for (int j = 0; j < dc.geefKoninkrijk(speler)[0].length; j++) {
				int finalI = i;
				int finalJ = j;
				Pane vak = new Pane();
				vak.setPrefSize(90, 90);
				vak.setOnMouseClicked(event -> {
					dc.legTegelInKoninkrijk(tegel, speler, "" + finalI + finalJ, switch (richting) {
						case BOVEN -> 3;
						case LINKS -> 2;
						case RECHTS -> 1;
						case ONDER -> 4;
					});
					ImageView tegelView = new ImageView(new Image(getClass().getResourceAsStream(
							String.format("/images/dominotegel/tegel_%02d_voorkant.png", tegel.nummer()))));
					tegelView.setPreserveRatio(true);
					tegelView.setFitHeight(90);
					if (richting.equals(Richting.RECHTS)) {
						tegelView.setRotate(0);
						add(tegelView, finalJ, finalI, 2, 1);
					}
					if (richting.equals(Richting.ONDER)) {
						tegelView.setRotate(90);
						add(tegelView, finalJ, finalI, 1, 2);
					}
					if (richting.equals(Richting.LINKS)) {
						tegelView.setRotate(180);
						add(tegelView, finalJ - 1, finalI, 2, 1);
					}
					if (richting.equals(Richting.BOVEN)) {
						tegelView.setRotate(270);
						add(tegelView, finalJ, finalI - 1, 1, 2);
					}
					getChildren().forEach(c -> {
						if (c instanceof Pane p)
							p.setOnMouseClicked(null);
					});
					getScene().setRoot(spelSpelenScherm);
				});
				add(vak, j, i);
			}
	}

}
