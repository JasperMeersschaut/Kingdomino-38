
package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import dto.SpelerDTO;
import dto.TegelDTO;
import dto.VakDTO;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import utils.Kleur;
import utils.Landschap;
import utils.Richting;
import utils.Taal;

public class KoninkrijkScherm extends GridPane {
	
	private final DomeinController dc;
	private SpelerDTO speler;
	private final SpelSpelenScherm spelSpelenScherm;
	private final GridPane geschaaldKoninkrijk;

	public KoninkrijkScherm(DomeinController dc, SpelerDTO speler, SpelSpelenScherm spelSpelenScherm) {
		this.dc = dc;
		this.speler = speler;
		this.spelSpelenScherm = spelSpelenScherm;
		this.geschaaldKoninkrijk = new GridPane();
		bouwScherm();
	}

	private void bouwScherm() {
		setGridLinesVisible(true);
		VakDTO[][] koninkrijk = dc.geefKoninkrijk(speler);
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setMinWidth(85);
		columnConstraints.setMaxWidth(85);
		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setMinHeight(85);
		rowConstraints.setMaxHeight(85);
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
				new Image(getClass().getResourceAsStream(String.format("/images/starttegels/starttegel_%s.png", kleur))));
		kasteel.setPreserveRatio(true);
		kasteel.setFitHeight(85);
		add(kasteel, 4, 4);
		bouwGeschaaldKoninkrijk();
	}

	private void bouwGeschaaldKoninkrijk() {
		geschaaldKoninkrijk.setGridLinesVisible(true);
		VakDTO[][] koninkrijk = dc.geefKoninkrijk(speler);
		ColumnConstraints col = new ColumnConstraints();
		col.setMinWidth(33);
		col.setMaxWidth(33);
		RowConstraints row = new RowConstraints();
		row.setMinHeight(33);
		row.setMaxHeight(33);
		for (int i = 0; i < koninkrijk.length; i++)
			geschaaldKoninkrijk.getColumnConstraints().add(col);
		for (int j = 0; j < koninkrijk[0].length; j++)
			geschaaldKoninkrijk.getRowConstraints().add(row);
		String kleur = switch (Kleur.geefKleur(speler.kleur())) {
			case BLAUW -> "blauw";
			case GEEL -> "geel";
			case GROEN -> "groen";
			case ROOS -> "roos";
		};
		ImageView kasteel = new ImageView(
				new Image(getClass().getResourceAsStream(String.format("/images/starttegels/starttegel_%s.png", kleur))));
		kasteel.setPreserveRatio(true);
		kasteel.setFitHeight(33);
		geschaaldKoninkrijk.add(kasteel, 4, 4);
	}

	public GridPane geefGeschaaldKoninkrijk() {
		return geschaaldKoninkrijk;
	}

	public void legTegelInKoninkrijk(TegelDTO tegel, SpelerDTO speler, int i, int j, Richting richting) {
		ImageView tegelViewSmall = new ImageView(new Image(getClass()
				.getResourceAsStream(String.format("/images/dominotegels/tegel_%02d_voorkant.png", tegel.nummer()))));
		ImageView tegelViewLarge = new ImageView(new Image(getClass()
				.getResourceAsStream(String.format("/images/dominotegels/tegel_%02d_voorkant.png", tegel.nummer()))));
		tegelViewSmall.setPreserveRatio(true);
		tegelViewSmall.setFitHeight(33);
		tegelViewLarge.setPreserveRatio(true);
		tegelViewLarge.setFitHeight(85);
		setHalignment(tegelViewSmall, HPos.CENTER);
		setValignment(tegelViewSmall, VPos.CENTER);
		setHalignment(tegelViewLarge, HPos.CENTER);
		setValignment(tegelViewLarge, VPos.CENTER);
		if (richting.equals(Richting.RECHTS)) {
			tegelViewSmall.setRotate(0);
			tegelViewLarge.setRotate(0);
			geschaaldKoninkrijk.add(tegelViewSmall, j, i, 2, 1);
			add(tegelViewLarge, j, i, 2, 1);
		}
		if (richting.equals(Richting.ONDER)) {
			tegelViewSmall.setRotate(90);
			tegelViewLarge.setRotate(90);
			geschaaldKoninkrijk.add(tegelViewSmall, j, i, 1, 2);
			add(tegelViewLarge, j, i, 1, 2);
		}
		if (richting.equals(Richting.LINKS)) {
			tegelViewSmall.setRotate(180);
			tegelViewLarge.setRotate(180);
			geschaaldKoninkrijk.add(tegelViewSmall, j - 1, i, 2, 1);
			add(tegelViewLarge, j - 1, i, 2, 1);
		}
		if (richting.equals(Richting.BOVEN)) {
			tegelViewSmall.setRotate(270);
			tegelViewLarge.setRotate(270);
			geschaaldKoninkrijk.add(tegelViewSmall, j, i - 1, 1, 2);
			add(tegelViewLarge, j, i - 1, 1, 2);
		}
		plaatsKruisjes();
		getScene().setRoot(spelSpelenScherm);
	}

	private void plaatsKruisjes() {
		VakDTO[][] koninkrijk = dc.geefKoninkrijk(speler);
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[0].length; j++) {
				int finalI = i;
				int finalJ = j;
				if (koninkrijk[i][j].landschap() != null
						&& koninkrijk[i][j].landschap().equals(Landschap.LEEG.toString())) {
					ImageView kruisSmall = new ImageView(new Image(getClass().getResourceAsStream("/images/kruis.png")));
					ImageView kruisLarge = new ImageView(new Image(getClass().getResourceAsStream("/images/kruis.png")));
					kruisSmall.setPreserveRatio(true);
					kruisSmall.setFitHeight(28);
					kruisLarge.setPreserveRatio(true);
					kruisLarge.setFitHeight(80);
					setHalignment(kruisSmall, HPos.CENTER);
					setValignment(kruisSmall, VPos.CENTER);
					setHalignment(kruisLarge, HPos.CENTER);
					setValignment(kruisLarge, VPos.CENTER);
					geschaaldKoninkrijk.add(kruisSmall, finalJ, finalI);
					add(kruisLarge, finalJ, finalI);
				}
			}
	}

}
