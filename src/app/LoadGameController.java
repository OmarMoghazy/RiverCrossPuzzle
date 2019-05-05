package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoadGameController {
	
	@FXML
	public void loadGame(ActionEvent e) throws IOException {
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		GameController controller = GameController.getInstance();
		controller.loadGame();
		StoryGUI.crossingStrategy = new StoryOneCrossingStrategy();
		Parent root = FXMLLoader.load(getClass().getResource("Story1.fxml"));
		Scene scene = new Scene(root, 800, 600);
		window.setScene(scene);
		window.show();
	}
	@FXML
	public void back(ActionEvent event) throws IOException {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Scene scene = new Scene(root, 800, 600);
		window.setScene(scene);
		window.show();
	}
	
}
