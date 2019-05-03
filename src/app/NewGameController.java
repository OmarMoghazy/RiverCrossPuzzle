package app;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class NewGameController implements Initializable {
	@FXML private ChoiceBox<String> choiceBox;
	@FXML public Button startButton;
	@FXML public Button backButton;
	
	@FXML private ImageView crosser1;
	@FXML private ImageView crosser2;
	@FXML private ImageView crosser3;
	@FXML private ImageView crosser4;
	@FXML private ImageView crosser5;
	
	ObservableList<String> level = FXCollections.observableArrayList("Story One","Story Two");
	
	public ArrayList<ICrosser> crossers = new ArrayList<ICrosser>();
	
	public void back(ActionEvent event) throws IOException {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Scene scene = new Scene(root, 800, 500);
		window.setScene(scene);
		window.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		choiceBox.setItems(level);
		choiceBox.setValue("Story One");
		
	}
	
	public void startNewGame(ActionEvent event) throws IOException {
        //opens story 1 gui
		if(choiceBox.getSelectionModel().getSelectedItem().equals("Story One")) {
			StoryGUI.setStrategy(new StoryOneCrossingStrategy());
		}
		else {
			StoryGUI.setStrategy(new StoryTwoCrossingStrategy());
		}
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("Story1.fxml"));
		Scene scene = new Scene(root, 800, 600);
		window.setScene(scene);
		window.show();
	}
}
