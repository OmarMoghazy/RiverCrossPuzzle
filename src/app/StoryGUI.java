package app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;

@SuppressWarnings("unused")
public class StoryGUI implements Initializable {

	static ICrossingStrategy crossingStrategy;

	GameController controller = GameController.getInstance();

	@FXML private ImageView boatImageView;
	@FXML private ImageView bgImageView;
	@FXML private ImageView farkhaImageView;
	@FXML private ImageView farmerImageView;
	@FXML private ImageView shalabyImageView;
	@FXML private ImageView oshbImageView;
	@FXML private ImageView crosser5ImageView;
	@FXML private ImageView undoImageView;
	@FXML private ImageView redoImageView;
	@FXML private ImageView backImageView;
	@FXML private ImageView instructionsImageView;
	@FXML private Group shalabyGroup;
	@FXML private Group oshbGroup;
	@FXML private Group farmerGroup;
	@FXML private Group chickenGroup;
	@FXML private Group crosser5Group;
	@FXML private Button go;
	@FXML private Button save;
	@FXML private Button back;
	@FXML private Button undo;
	@FXML private Button reset;
	@FXML private Button redo;
	@FXML private Label farmerlabel;
	@FXML private Label oshblabel;
	@FXML private Label farkhalabel;
	@FXML private Label shalabylabel;
	@FXML private Label crosser5label;
	@FXML private Label scoreLabel;

	private ArrayList<Group> crossersOnBoat = new ArrayList<Group>();
	private ArrayList<Group> crossersOnLeft = new ArrayList<Group>();
	private ArrayList<Group> crossersOnRight = new ArrayList<Group>();

	private ArrayList<ICrosser> crossers = new ArrayList<ICrosser>();

	private boolean boatOnLeft;
	
	ICrosser crosser1;
	ICrosser crosser2;
	ICrosser crosser3;
	ICrosser crosser4;
	ICrosser crosser5;

	@SuppressWarnings("unused")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = GameController.getInstance();
		controller.newGame(crossingStrategy);

		crossersOnLeft.add(shalabyGroup);
		crossersOnLeft.add(oshbGroup);
		crossersOnLeft.add(farmerGroup);
		crossersOnLeft.add(chickenGroup);
		crossersOnLeft.add(crosser5Group);
		boatOnLeft = true;

		crosser1 = controller.leftBankCrossers.get(0);
		Image farmerImage = SwingFXUtils.toFXImage(crosser1.getImages()[0], null);
		farmerImageView.setImage(farmerImage);
		farmerlabel.setText(crosser1.getLabelToBeShown());

		crosser2 = controller.leftBankCrossers.get(1);
		Image shalabyImage = SwingFXUtils.toFXImage(crosser2.getImages()[0], null);
		shalabyImageView.setImage(shalabyImage);
		shalabylabel.setText(crosser2.getLabelToBeShown());

		crosser3 = controller.leftBankCrossers.get(2);
		Image farkhaImage = SwingFXUtils.toFXImage(crosser3.getImages()[0], null);
		farkhaImageView.setImage(farkhaImage);
		farkhalabel.setText(crosser3.getLabelToBeShown());

		crosser4 = controller.leftBankCrossers.get(3);
		Image oshbImage = SwingFXUtils.toFXImage(crosser4.getImages()[0], null);
		oshbImageView.setImage(oshbImage);
		oshblabel.setText(crosser4.getLabelToBeShown());

		boatImageView.setImage(new Image(new File("carnivore.png").toURI().toString()));
		bgImageView.setImage(new Image(new File("background.png").toURI().toString()));
		backImageView.setImage(new Image(new File("back.png").toURI().toString()));
		undoImageView.setImage(new Image(new File("undo.png").toURI().toString()));
		redoImageView.setImage(new Image(new File("undo.png").toURI().toString()));
		instructionsImageView.setImage(new Image(new File("instructions.png").toURI().toString()));
		redoImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

		try {
			crosser5 = controller.leftBankCrossers.get(4);
			Image crosser5img = SwingFXUtils.toFXImage(crosser5.getImages()[0], null);
			crosser5ImageView.setImage(crosser5img);
			crosser5label.setText(crosser5.getLabelToBeShown());
		} catch (IndexOutOfBoundsException e) {
			System.out.println("no");
			crosser5Group.setVisible(false);
		}
	}
	@FXML private void farmerClick() {
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setDuration(Duration.seconds(1));
		translateTransition.setNode(farmerGroup);

		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.seconds(1));
		rotateTransition.setNode(farmerGroup);

		if (crossersOnLeft.contains(farmerGroup) && boatOnLeft) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(180);
				translateTransition.setByY(-20);
				rotateTransition.setByAngle(360);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.add(farmerGroup);
				crossersOnLeft.remove(farmerGroup);
			} else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		}

		else if (crossersOnBoat.contains(farmerGroup)) {
			if (boatOnLeft) {
				translateTransition.setByX(-180);
				translateTransition.setByY(20);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.remove(farmerGroup);
				crossersOnLeft.add(farmerGroup);
			} else {
				translateTransition.setByX(250);
				translateTransition.setByY(60);
				rotateTransition.setByAngle(360);
				translateTransition.play();
				rotateTransition.play();
				farmerImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				crossersOnBoat.remove(farmerGroup);
				crossersOnRight.add(farmerGroup);

				if(crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
				else if(crossersOnRight.size() == 5){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
			}
		} else if (crossersOnRight.contains(farmerGroup)) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(-250);
				translateTransition.setByY(-60);
				translateTransition.play();
				rotateTransition.setByAngle(-360);
				rotateTransition.play();
				farmerImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
				crossersOnBoat.add(farmerGroup);
				crossersOnRight.remove(farmerGroup);
			}
			else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		}
		else {
			System.out.println("tb");
		}
	}

	@FXML private void oshbClick()  {
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.seconds(1));
		rotateTransition.setNode(oshbGroup);
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setDuration(Duration.seconds(1));
		translateTransition.setNode(oshbGroup);

		if (crossersOnLeft.contains(oshbGroup) && boatOnLeft) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(200);
				translateTransition.setByY(-5);
				rotateTransition.setByAngle(360);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.add(oshbGroup);
				crossersOnLeft.remove(oshbGroup);
			} else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else if (crossersOnBoat.contains(oshbGroup)) {
			if (boatOnLeft) {
				translateTransition.setByX(-200);
				translateTransition.setByY(5);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.remove(oshbGroup);
				crossersOnLeft.add(oshbGroup);
			} else {
				translateTransition.setByX(130);
				translateTransition.setByY(20);
				rotateTransition.setByAngle(360);
				oshbImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.remove(oshbGroup);
				crossersOnRight.add(oshbGroup);

				if(crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
				else if(crossersOnRight.size() == 5){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
			}
		} else if (crossersOnRight.contains(oshbGroup)) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(-130);
				translateTransition.setByY(-20);
				rotateTransition.setByAngle(-360);
				oshbImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.add(oshbGroup);
				crossersOnRight.remove(oshbGroup);
			} else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else {
			System.out.println("tb");
		}
	}

	@FXML private void chickenClick() {
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setDuration(Duration.seconds(1));
		translateTransition.setNode(chickenGroup);
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.seconds(1));
		rotateTransition.setNode(chickenGroup);
		if (crossersOnLeft.contains(chickenGroup) && boatOnLeft) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(80);
				translateTransition.setByY(20);
				rotateTransition.setByAngle(360);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.add(chickenGroup);
				crossersOnLeft.remove(chickenGroup);
			} else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else if (crossersOnBoat.contains(chickenGroup)) {
			if (boatOnLeft) {
				translateTransition.setByX(-90);
				translateTransition.setByY(-20);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.remove(chickenGroup);
				crossersOnLeft.add(chickenGroup);
			} else {
				translateTransition.setByX(150);
				translateTransition.setByY(-30);
				rotateTransition.setByAngle(360);
				translateTransition.play();
				rotateTransition.play();
				farkhaImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				crossersOnBoat.remove(chickenGroup);
				crossersOnRight.add(chickenGroup);

				if(crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
				else if(crossersOnRight.size() == 5){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
			}
		} else if (crossersOnRight.contains(chickenGroup) && !boatOnLeft) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(-150);
				translateTransition.setByY(30);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();
				farkhaImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
				crossersOnBoat.add(chickenGroup);
				crossersOnRight.remove(chickenGroup);
			} else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else {
			System.out.println("tb");
		}
	}

	@FXML private void shalabyClick() {
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setDuration(Duration.seconds(1));
		translateTransition.setNode(shalabyGroup);
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.seconds(1));
		rotateTransition.setNode(shalabyGroup);

		if (crossersOnLeft.contains(shalabyGroup) && boatOnLeft) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(20);
				translateTransition.setByY(15);
				rotateTransition.setByAngle(360);
				translateTransition.play();
				rotateTransition.play();
				crossersOnBoat.add(shalabyGroup);
				crossersOnLeft.remove(shalabyGroup);
			} else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else if (crossersOnBoat.contains(shalabyGroup)) {
			if (boatOnLeft) {
				translateTransition.setByX(-20);
				translateTransition.setByY(-15);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.remove(shalabyGroup);
				crossersOnLeft.add(shalabyGroup);
			} else {
				translateTransition.setByX(60);
				translateTransition.setByY(-30);
				rotateTransition.setByAngle(360);
				translateTransition.play();
				rotateTransition.play();
				shalabyImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				crossersOnBoat.remove(shalabyGroup);
				crossersOnRight.add(shalabyGroup);

				if(crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
				else if(crossersOnRight.size() == 5){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
			}
		} else if (crossersOnRight.contains(shalabyGroup)) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(-60);
				translateTransition.setByY(30);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();
				shalabyImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
				crossersOnBoat.add(shalabyGroup);
				crossersOnRight.remove(shalabyGroup);
			} else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else {
			System.out.println("tb");
		}
	}

	@FXML private void crosser5Click(){
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setDuration(Duration.seconds(1));
		translateTransition.setNode(crosser5Group);
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.seconds(1));
		rotateTransition.setNode(crosser5Group);

		if (crossersOnLeft.contains(crosser5Group) && boatOnLeft) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByY(50);
				rotateTransition.setByAngle(360);
				translateTransition.play();
				rotateTransition.play();
				crossersOnBoat.add(crosser5Group);
				crossersOnLeft.remove(crosser5Group);
			} else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else if (crossersOnBoat.contains(crosser5Group)) {
			if (boatOnLeft) {
				//270, 380
				translateTransition.setByY(-50);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.remove(crosser5Group);
				crossersOnLeft.add(crosser5Group);
			} else {
				translateTransition.setByX(60);
				translateTransition.setByY(-30);
				rotateTransition.setByAngle(360);
				translateTransition.play();
				rotateTransition.play();
				crosser5ImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				crossersOnBoat.remove(crosser5Group);
				crossersOnRight.add(crosser5Group);

				if(crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
				else if(crossersOnRight.size() == 5){
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				}
			}
		} else if (crossersOnRight.contains(crosser5Group)) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(-60);
				translateTransition.setByY(30);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();
				crosser5ImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
				crossersOnBoat.add(crosser5Group);
				crossersOnRight.remove(crosser5Group);
			} else{
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else {
			System.out.println("tb");
		}
	}

	@FXML private void resetAction(ActionEvent event) throws IOException {
		controller.resetGame();
		Parent parent = FXMLLoader.load(getClass().getResource("Story1.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();
	}

	@FXML private void backAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();
	}
	private void backToMain(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();
	}

	@FXML private void goAction(ActionEvent event) throws IOException {
		if(crossersOnBoat.contains(farmerGroup)) crossers.add(crosser1);
		if(crossersOnBoat.contains(shalabyGroup)) crossers.add(crosser2);
		if(crossersOnBoat.contains(chickenGroup)) crossers.add(crosser3);
		if(crossersOnBoat.contains(oshbGroup)) crossers.add(crosser4);
		if(crossersOnBoat.contains(crosser5Group)) crossers.add(crosser5);
		if(controller.canMove(crossers, boatOnLeft)) {
			controller.doMove(crossers, boatOnLeft);
			TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(1));
            translateTransition.setNode(boatImageView);
            
            if(boatOnLeft){
                translateTransition.setByX(200);
                translateTransition.play();
                for(int i= 0; i<crossersOnBoat.size(); i++){
                    TranslateTransition translateTransition1 = new TranslateTransition();
                    translateTransition1.setDuration(Duration.seconds(1));
                    translateTransition1.setNode(crossersOnBoat.get(i));
                    translateTransition1.setByX(200);
                    translateTransition1.play();
                }
                boatOnLeft = false;
            }
            else if(!boatOnLeft){
                translateTransition.setByX(-200);
                translateTransition.play();
                for(int i= 0; i<crossersOnBoat.size(); i++){
                    TranslateTransition translateTransition1 = new TranslateTransition();
                    translateTransition1.setDuration(Duration.seconds(1));
                    translateTransition1.setNode(crossersOnBoat.get(i));
                    translateTransition1.setByX(-200);
                    translateTransition1.play();
                }
                boatOnLeft = true;
            }
            scoreLabel.setText(Integer.toString(controller.getNumberOfSails()));
		}
		else if(crossingStrategy instanceof StoryOneCrossingStrategy){
			if(crossersOnLeft.contains(shalabyGroup) && crossersOnLeft.contains(chickenGroup)){
				alertBox("u kidding meeee", "warn2.png");
			}
			else if(crossersOnRight.contains(shalabyGroup) && crossersOnRight.contains(chickenGroup)){
				alertBox("u kidding meeee", "warn2.png");
			}
			else if(crossersOnLeft.contains(oshbGroup) && crossersOnLeft.contains(chickenGroup)){
				alertBox("u kidding meeee", "warn3.png");
			}
			else if(crossersOnRight.contains(oshbGroup) && crossersOnRight.contains(chickenGroup)){
				alertBox("u kidding meeee", "warn3.png");
			}
			else if(!crossersOnBoat.contains(farmerGroup)){
				alertBox("u kidding meeee", "warn1.png");
			}
		}
		else if(crossingStrategy instanceof StoryTwoCrossingStrategy){
			double weight = 0;
			for (ICrosser x : crossers) {
				weight = weight + x.getWeight();
			}
			if(crossersOnBoat.contains(crosser5Group) && crossersOnBoat.size() == 1){
				alertBox("u kidding meeee", "warn1.png");
			}
			else if(weight>100){
				alertBox("u kidding meeee", "warn4.png");
			}
		}
		crossers.clear();
		
	}

	public static void setStrategy(ICrossingStrategy gameStrategy) {
		crossingStrategy = gameStrategy;
	}

	//alertBox
	public static void alertBox(String title, String FileName) {
		Stage alert = new Stage();
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle(title);
		alert.setMinHeight(200);
		alert.setMinWidth(250);

		Image image = new Image(new File(FileName).toURI().toString());
		ImageView imageView = new ImageView(image);
		VBox layout = new VBox();
		layout.getChildren().add(imageView);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		alert.setScene(scene);
		alert.showAndWait();
	}
}

//reset after completion
//crosser 5 transition
//node orientation
//win condition in story 2