package app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

	@FXML public ImageView imageView;
	@FXML public Button newGame;
	@FXML public Button loadGame;
	@FXML public Button exit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
   		Image image = new Image(new File("mainMenuImage.png").toURI().toString());
		imageView.setImage(image);
	}

	public void newGame(ActionEvent event) throws IOException {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
		Scene scene = new Scene(root, 650, 500);
		window.setScene(scene);
		window.show();
	}
	
	public void loadGame(ActionEvent event) throws IOException {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("LoadGame.fxml"));
		Scene scene = new Scene(root, 650, 500);
		window.setScene(scene);
		window.show();
	}

	public void Exit(ActionEvent event) {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();
	}

}