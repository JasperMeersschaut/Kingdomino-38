package gui;

import domein.DomeinController;
import dto.TegelDTO;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class KoningkrijkScherm extends GridPane {

	private final DomeinController dc;
	private final Stage scherm;
	private Label draaiKnop;


	public KoningkrijkScherm(DomeinController dc, Stage scherm) {
		this.dc = dc;
		this.scherm = scherm;
		bouwScherm();

	}

	private void bouwScherm() {
		// nodes en instellen aanmaken
		scherm.centerOnScreen();
		getStyleClass().add("zandkleur");
		bouwGrid();
		maakDraaiKnop();
		setGridLinesVisible(true);
		 maakDraaiBareTegel();
		
	}

	private void bouwGrid() {
		
		Button draaiKnop = new Button();
		GridPane koninkRijk = new GridPane();

		for (int col = 0; col < 3; col++) {
			getColumnConstraints().add(new ColumnConstraints(scherm.getWidth()/3));
		}

		for (int col = 0; col < 9; col++) {
			koninkRijk.getColumnConstraints().add(new ColumnConstraints(scherm.getWidth()/9));
			koninkRijk.getRowConstraints().add(new RowConstraints(scherm.getHeight()/9));

		}

		koninkRijk.setGridLinesVisible(true);

		add(koninkRijk, 1, 0, 2, 1);
	}

	private Label maakDraaiKnop() {
		draaiKnop = new Label("Draai");
		draaiKnop.getStyleClass().addAll("font", "mediumText","border");
		draaiKnop.setPadding(new Insets(20,50,20,50));
		draaiKnop.setTranslateX(200);
		draaiKnop.setTranslateY(250);
		add(draaiKnop,0,0,1,1);
		return draaiKnop;
	}
	
	private ImageView maakDraaiBareTegel() {  // parameter van de tegel
		Image idominoTegel = new Image(getClass().getResourceAsStream("/images/dominotegel/tegel_01_voorkant.png"));
		ImageView ivdominoTegel = new ImageView(idominoTegel);
		getChildren().add(ivdominoTegel);
		
		ivdominoTegel.setFitWidth(500);
	    ivdominoTegel.setFitHeight(500);
	    
	    GridPane.setHalignment(ivdominoTegel, HPos.CENTER); // centreert de tegel in de eerste kolom
	    
	    ivdominoTegel.setTranslateY(-150);
	    
		ivdominoTegel.setPreserveRatio(true); // zorgt ervoor dat verhouding zelde blijft

		return null;
	}
}
