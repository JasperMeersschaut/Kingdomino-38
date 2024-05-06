
package gui;

import domein.DomeinController;
import dto.SpelerDTO;
import dto.TegelDTO;
import dto.VakDTO;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import utils.Kleur;
import utils.Landschap;
import utils.Richting;

public class KoninkrijkScherm extends GridPane {

	private final DomeinController dc;
	private final SpelerDTO speler;
	private final SpelSpelenScherm spelSpelenScherm;
	private final GridPane geschaaldKoninkrijk;
	private ImageView tegelOngeplaatst;

	/**
	 * Constructor voor het KoninkrijkScherm.
	 *
	 * @param dc               de domeincontroller.
	 * @param speler           de eigenaar van het koninkrijk.
	 * @param spelSpelenScherm het SpelSpelenScherm.
	 */
	public KoninkrijkScherm(DomeinController dc, SpelerDTO speler, SpelSpelenScherm spelSpelenScherm) {
		this.dc = dc;
		this.speler = speler;
		this.spelSpelenScherm = spelSpelenScherm;
		this.geschaaldKoninkrijk = new GridPane();
		bouwScherm();
	}

	private void bouwScherm() {
		setGridLinesVisible(true);
		geschaaldKoninkrijk.setGridLinesVisible(true);
		VakDTO[][] koninkrijk = dc.geefKoninkrijk(speler);
		ColumnConstraints colSmall = new ColumnConstraints();
		ColumnConstraints colLarge = new ColumnConstraints();
		colSmall.setMinWidth(33);
		colSmall.setMaxWidth(33);
		colLarge.setMinWidth(85);
		colLarge.setMaxWidth(85);
		RowConstraints rowSmall = new RowConstraints();
		RowConstraints rowLarge = new RowConstraints();
		rowSmall.setMinHeight(33);
		rowSmall.setMaxHeight(33);
		rowLarge.setMinHeight(85);
		rowLarge.setMaxHeight(85);
		for (int i = 0; i < koninkrijk.length; i++) {
			geschaaldKoninkrijk.getColumnConstraints().add(colSmall);
			getColumnConstraints().add(colLarge);
		}
		for (int j = 0; j < koninkrijk[0].length; j++) {
			geschaaldKoninkrijk.getRowConstraints().add(rowSmall);
			getRowConstraints().add(rowLarge);
		}
		String kleur = switch (Kleur.geefKleur(speler.kleur())) {
			case BLAUW -> "blauw";
			case GEEL -> "geel";
			case GROEN -> "groen";
			case ROOS -> "roos";
		};
		ImageView kasteelSmall = new ImageView(
				new Image(getClass().getResourceAsStream(String.format("/images/starttegels/starttegel_%s.png", kleur))));
		ImageView kasteelLarge = new ImageView(
				new Image(getClass().getResourceAsStream(String.format("/images/starttegels/starttegel_%s.png", kleur))));
		kasteelSmall.setPreserveRatio(true);
		kasteelSmall.setFitHeight(33);
		kasteelLarge.setPreserveRatio(true);
		kasteelLarge.setFitHeight(85);
		add(kasteelLarge, 4, 4);
		geschaaldKoninkrijk.add(kasteelSmall, 4, 4);
	}

	/**
	 * Geeft het geschaalde koninkrijk.
	 *
	 * @return het geschaalde koninkrijk.
	 */
	public GridPane geefGeschaaldKoninkrijk() {
		return geschaaldKoninkrijk;
	}

	/**
	 * Legt een tegel in het KoninkrijkScherm.
	 *
	 * @param tegel    de tegel.
	 * @param i        het rijnummer.
	 * @param j        het kolomnummer.
	 * @param richting de richting.
	 */
	public void legTegelInKoninkrijk(TegelDTO tegel, int i, int j, Richting richting) {
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
		tegelViewSmall.setRotate(Richting.geefRichtingsGraden(richting));
		tegelViewLarge.setRotate(Richting.geefRichtingsGraden(richting));
		geschaaldKoninkrijk.add(tegelViewSmall, richting == Richting.LINKS ? j - 1 : j,
				richting == Richting.BOVEN ? i - 1 : i, (richting == Richting.LINKS || richting == Richting.RECHTS) ? 2 : 1,
				(richting == Richting.BOVEN || richting == Richting.ONDER) ? 2 : 1);
		add(tegelViewLarge, richting == Richting.LINKS ? j - 1 : j, richting == Richting.BOVEN ? i - 1 : i,
				(richting == Richting.LINKS || richting == Richting.RECHTS) ? 2 : 1,
				(richting == Richting.BOVEN || richting == Richting.ONDER) ? 2 : 1);
		plaatsKruisjes();
		getScene().setRoot(spelSpelenScherm);
	}

	private void plaatsKruisjes() {
		VakDTO[][] koninkrijk = dc.geefKoninkrijk(speler);
		for (int i = 0; i < koninkrijk.length; i++)
			for (int j = 0; j < koninkrijk[0].length; j++)
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
					geschaaldKoninkrijk.add(kruisSmall, j, i);
					add(kruisLarge, j, i);
				}
	}

	/**
	 * Toont de tegel die geplaatst moet worden.
	 *
	 * @param tegel    de tegel.
	 * @param i        het rijnummer.
	 * @param j        het kolomnummer.
	 * @param richting de richting.
	 */
	public void toonTegel(TegelDTO tegel, int i, int j, Richting richting) {
		if (!(i == 8 && richting.equals(Richting.ONDER)) && !(j == 8 && richting.equals(Richting.RECHTS)))
			try {
				tegelOngeplaatst = new ImageView(new Image(getClass()
						.getResourceAsStream(String.format("/images/dominotegels/tegel_%02d_voorkant.png", tegel.nummer()))));
				tegelOngeplaatst.setPreserveRatio(true);
				tegelOngeplaatst.setFitHeight(85);
				tegelOngeplaatst.setOpacity(0.5);
				setHalignment(tegelOngeplaatst, HPos.CENTER);
				setValignment(tegelOngeplaatst, VPos.CENTER);
				if (richting.equals(Richting.RECHTS)) {
					tegelOngeplaatst.setRotate(0);
					add(tegelOngeplaatst, j, i, 2, 1);
				}
				if (richting.equals(Richting.ONDER)) {
					tegelOngeplaatst.setRotate(90);
					add(tegelOngeplaatst, j, i, 1, 2);
				}
				if (richting.equals(Richting.LINKS)) {
					tegelOngeplaatst.setRotate(180);
					add(tegelOngeplaatst, j - 1, i, 2, 1);
				}
				if (richting.equals(Richting.BOVEN)) {
					tegelOngeplaatst.setRotate(270);
					add(tegelOngeplaatst, j, i - 1, 1, 2);
				}
			}
			catch (Exception ignored) {
			}
	}

	/**
	 * Verbergt de tegel die geplaatst moet worden
	 */
	public void hideTegel() {
		for (Node child : getChildren())
			if (child.equals(tegelOngeplaatst)) {
				getChildren().remove(child);
				break;
			}
	}

}
