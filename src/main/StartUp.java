
package main;

import domein.DomeinController;
import gui.WelkomScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartUp extends Application {

	@Override
	public void start(Stage scherm) {
		DomeinController dc = new DomeinController();
		WelkomScherm root = new WelkomScherm(dc, scherm);
		Scene scene = new Scene(root, 1920, 720);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		Font.loadFont(getClass().getResourceAsStream("/fonts/Augusta.ttf"), 10);
		Font.loadFont(getClass().getResourceAsStream("/fonts/Timeless.ttf"), 10);
		scherm.setScene(scene);
		scherm.setResizable(true);
		scherm.setTitle("Kingdomino");
		Image logo = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		scherm.getIcons().add(logo);
//		scherm.initStyle(StageStyle.UNDECORATED);
		scherm.show();
		scherm.setMaximized(true);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
