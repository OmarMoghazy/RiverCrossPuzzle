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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

@SuppressWarnings("unused")
public class StoryGUI implements Initializable {

	static ICrossingStrategy crossingStrategy;

	GameController controller = GameController.getInstance();

	@FXML
	private ImageView boatImageView;
	@FXML
	private ImageView farkhaImageView;
	@FXML
	private ImageView farmerImageView;
	@FXML
	private ImageView shalabyImageView;
	@FXML
	private ImageView oshbImageView;
	@FXML
	private ImageView crosser5ImageView;
	@FXML
	private Group shalabyGroup;
	@FXML
	private Group oshbGroup;
	@FXML
	private Group farmerGroup;
	@FXML
	private Group chickenGroup;
	@FXML
	private Group crosser5Group;
	@FXML
	private Button go;
	@FXML
	private Button reset;
	@FXML
	private Button back;

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
		
		crosser2 = controller.leftBankCrossers.get(1);
		Image shalabyImage = SwingFXUtils.toFXImage(crosser2.getImages()[0], null);
		shalabyImageView.setImage(shalabyImage);

		crosser3 = controller.leftBankCrossers.get(2);
		Image farkhaImage = SwingFXUtils.toFXImage(crosser3.getImages()[0], null);
		farkhaImageView.setImage(farkhaImage);

		crosser4 = controller.leftBankCrossers.get(3);
		Image oshbImage = SwingFXUtils.toFXImage(crosser4.getImages()[0], null);
		oshbImageView.setImage(oshbImage);
		
		try {
			crosser5 = controller.leftBankCrossers.get(4);
			Image crosser5img = SwingFXUtils.toFXImage(crosser5.getImages()[0], null);
			crosser5ImageView.setImage(crosser5img);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("no");
			crosser5Group.setVisible(false);
		}
	}
	@FXML
	private void farmerClick() {
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
			} else
				System.out.println("tb only 2 at a time tb");
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
		} else {
			System.out.println("tb");
		}
	}

	@FXML
	private void oshbClick() {
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
			} else
				System.out.println("tb only 2 at a time tb");
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
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.remove(oshbGroup);
				crossersOnRight.add(oshbGroup);
			}
		} else if (crossersOnRight.contains(oshbGroup)) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(-130);
				translateTransition.setByY(-20);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();

				crossersOnBoat.add(oshbGroup);
				crossersOnRight.remove(oshbGroup);
			} else
				System.out.println("tb only 2 at a time tb");
		} else {
			System.out.println("tb");
		}
	}

	@FXML
	private void chickenClick() {
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
			} else
				System.out.println("tb only 2 at a time tb");
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
				farkhaImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
				crossersOnBoat.remove(chickenGroup);
				crossersOnRight.add(chickenGroup);
			}
		} else if (crossersOnRight.contains(chickenGroup) && !boatOnLeft) {
			if (crossersOnBoat.size() < 2) {
				translateTransition.setByX(-150);
				translateTransition.setByY(30);
				rotateTransition.setByAngle(-360);
				translateTransition.play();
				rotateTransition.play();
				farkhaImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				crossersOnBoat.add(chickenGroup);
				crossersOnRight.remove(chickenGroup);
			} else
				System.out.println("tb only 2 at a time tb");
		} else {
			System.out.println("tb");
		}
	}

	@FXML
	private void shalabyClick() {
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
			} else
				System.out.println("tb only 2 at a time tb");
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
			} else
				System.out.println("tb only 2 at a time tb");
		} else {
			System.out.println("tb");
		}
	}

	@FXML
	private void resetAction(ActionEvent event) throws IOException {
		controller.resetGame();
		Parent parent = FXMLLoader.load(getClass().getResource("Story1.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();
	}

	@FXML
	private void backAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();
	}

	@FXML
	private void goAction(ActionEvent event) throws IOException {
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
		}
		crossers.clear();
		
	}

	public static void setStrategy(ICrossingStrategy gameStrategy) {
		crossingStrategy = gameStrategy;
	}
}
