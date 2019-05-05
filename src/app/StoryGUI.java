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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	@FXML private ImageView fly1;
	@FXML private ImageView fly2;
	@FXML private Group shalabyGroup;
	@FXML private Group oshbGroup;
	@FXML private Group farmerGroup;
	@FXML private Group chickenGroup;
	@FXML private Group crosser5Group;
	@FXML private Button reset;
	@FXML private Button go;
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
		fly1.setImage(new Image(new File("fly1.png").toURI().toString()));
		fly2.setImage(new Image(new File("fly1.png").toURI().toString()));

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

	//51 + 180
	//445 -20
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
			} else {
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

				if (crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy) {
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				} else if (crossersOnRight.size() == 5) {
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
			} else {
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else {
			System.out.println("tb");
		}
	}

	//109+200
	//430-5
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
			} else {
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

				if (crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy) {
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				} else if (crossersOnRight.size() == 5) {
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
			} else {
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else {
			System.out.println("tb");
		}
	}

	//150+80
	//405+20
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
			} else {
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

				if (crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy) {
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				} else if (crossersOnRight.size() == 5) {
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
			} else {
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else {
			System.out.println("tb");
		}
	}

	//235+20
	//396+15
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
			} else {
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

				if (crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy) {
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				} else if (crossersOnRight.size() == 5) {
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
			} else {
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else {
			System.out.println("tb");
		}
	}

	//270
	//380+50
	@FXML
	private void crosser5Click() {
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
			} else {
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else if (crossersOnBoat.contains(crosser5Group)) {
			if (boatOnLeft) {
				// 270, 380
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

				if (crossersOnRight.size() == 4 && crossingStrategy instanceof StoryOneCrossingStrategy) {
					System.out.println("K.O");
					alertBox("gg wp", "ggwp.png");
					reset.fire();
				} else if (crossersOnRight.size() == 5) {
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
			} else {
				System.out.println("tb only 2 at a time tb");
				alertBox("u kidding meeee", "warn.png");
			}
		} else {
			System.out.println("tb");
		}
	}

	@FXML
	private void fly1Click(){
		TranslateTransition translateTransition = new TranslateTransition();
		RotateTransition rotateTransition = new RotateTransition();
		translateTransition.setDuration(Duration.seconds(1));
		rotateTransition.setDuration(Duration.seconds(1));
		translateTransition.setNode(fly1);
		rotateTransition.setCycleCount(10);
		rotateTransition.setNode(fly1);
		translateTransition.setByX(-10000);
		translateTransition.setAutoReverse(true);
		rotateTransition.play();
		translateTransition.play();
	}

	@FXML
	private void fly2Click(){
		TranslateTransition translateTransition = new TranslateTransition();
		RotateTransition rotateTransition = new RotateTransition();
		translateTransition.setDuration(Duration.seconds(1));
		rotateTransition.setDuration(Duration.seconds(1));
		translateTransition.setNode(fly2);
		rotateTransition.setNode(fly2);
		rotateTransition.setCycleCount(10);
		translateTransition.setByX(10000);
		translateTransition.setAutoReverse(true);
		rotateTransition.play();
		translateTransition.play();
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
	private void backToMain(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();
	}

	@FXML
	private void goAction(ActionEvent event) throws IOException {
		if (crossersOnBoat.contains(farmerGroup))
			crossers.add(crosser1);
		if (crossersOnBoat.contains(shalabyGroup))
			crossers.add(crosser2);
		if (crossersOnBoat.contains(chickenGroup))
			crossers.add(crosser3);
		if (crossersOnBoat.contains(oshbGroup))
			crossers.add(crosser4);
		if (crossersOnBoat.contains(crosser5Group))
			crossers.add(crosser5);
		if (controller.canMove(crossers, boatOnLeft)) {
			controller.doMove(crossers, boatOnLeft);
			TranslateTransition translateTransition = new TranslateTransition();
			translateTransition.setDuration(Duration.seconds(1));
			translateTransition.setNode(boatImageView);

			if (boatOnLeft) {
				translateTransition.setByX(200);
				translateTransition.play();
				for (int i = 0; i < crossersOnBoat.size(); i++) {
					TranslateTransition translateTransition1 = new TranslateTransition();
					translateTransition1.setDuration(Duration.seconds(1));
					translateTransition1.setNode(crossersOnBoat.get(i));
					translateTransition1.setByX(200);
					translateTransition1.play();
				}
				boatOnLeft = false;
			} else if (!boatOnLeft) {
				translateTransition.setByX(-200);
				translateTransition.play();
				for (int i = 0; i < crossersOnBoat.size(); i++) {
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
		else if (crossingStrategy instanceof StoryOneCrossingStrategy) {
			if (!crossersOnBoat.contains(farmerGroup)) alertBox("u kidding meeee", "warn1.png");
			else if (crossersOnLeft.contains(shalabyGroup) && crossersOnLeft.contains(chickenGroup))  alertBox("u kidding meeee", "warn2.png");
			else if (crossersOnRight.contains(shalabyGroup) && crossersOnRight.contains(chickenGroup))  alertBox("u kidding meeee", "warn2.png");
			else if (crossersOnLeft.contains(oshbGroup) && crossersOnLeft.contains(chickenGroup)) alertBox("u kidding meeee", "warn3.png");
			else if (crossersOnRight.contains(oshbGroup) && crossersOnRight.contains(chickenGroup))  alertBox("u kidding meeee", "warn3.png");
		}
		else if (crossingStrategy instanceof StoryTwoCrossingStrategy) {
			double weight = 0;
			for (ICrosser x : crossers) {
				weight = weight + x.getWeight();
			}
			if (crossersOnBoat.contains(crosser5Group) && crossersOnBoat.size() == 1) alertBox("u kidding meeee", "warn1.png");
			else if (weight > 100) alertBox("u kidding meeee", "warn4.png");
		}
		crossers.clear();
	}
/*
	@FXML
	public void undoAction() {
		if(controller.canUndo()){
			controller.undo();
			int flag = 0;
			if (controller.isBoatOnLeftBank) {
				if (crossersOnLeft.contains(farmerGroup) && !controller.leftBankCrossers.contains(crosser1)) {
					farmerClick();
					flag = 1;
				}
				if (crossersOnLeft.contains(shalabyGroup) && !controller.leftBankCrossers.contains(crosser2)) {
					shalabyClick();
					flag = 1;
				}
				if (crossersOnLeft.contains(chickenGroup) && !controller.leftBankCrossers.contains(crosser3)) {
					chickenClick();
					flag = 1;
				}
				if (crossersOnLeft.contains(oshbGroup) && !controller.leftBankCrossers.contains(crosser4)) {
					oshbClick();
					flag = 1;
				}
				if (crossersOnLeft.contains(crosser5Group) && !controller.leftBankCrossers.contains(crosser5)) {
					crosser5Click();
					flag = 1;
				}

			}
			else {
				if (crossersOnRight.contains(farmerGroup) && !controller.rightBankCrossers.contains(crosser1)) {
					farmerClick();
					flag =1;
				}
				if (crossersOnRight.contains(shalabyGroup) && !controller.rightBankCrossers.contains(crosser2)) {
					shalabyClick();
					flag = 1;
				}
				if (crossersOnRight.contains(chickenGroup) && !controller.rightBankCrossers.contains(crosser3)) {
					chickenClick();
					flag = 1;
				}
				if (crossersOnRight.contains(oshbGroup) && !controller.rightBankCrossers.contains(crosser4)) {
					oshbClick();
					flag = 1;
				}
				if (crossersOnRight.contains(crosser5Group) && !controller.rightBankCrossers.contains(crosser5)) {
					crosser5Click();
					flag = 1;
				}
			}
			if(flag == 1)
				go.fire();
		}
		else
			System.out.println("tb tb tb tb");

	}
	*/
	@FXML
	public void undoAction(ActionEvent e) throws IOException, InterruptedException {
		if(controller.canUndo() /*&& !crossersOnBoat.isEmpty()*/){
			controller.undo();
			if (boatOnLeft) {
				if (crossersOnLeft.contains(farmerGroup) && !controller.leftBankCrossers.contains(crosser1)) {
					farmerImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					farmerGroup.setTranslateX(630);
					farmerGroup.setTranslateY(40);
					crossersOnLeft.remove(farmerGroup);
					crossersOnRight.add(farmerGroup);
				}
				if (crossersOnLeft.contains(oshbGroup) && !controller.leftBankCrossers.contains(crosser4)) {
					oshbImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					oshbGroup.setTranslateX(530);
					oshbGroup.setTranslateY(15);
					crossersOnLeft.remove(oshbGroup);
					crossersOnRight.add(oshbGroup);
				}
				if (crossersOnLeft.contains(chickenGroup) && !controller.leftBankCrossers.contains(crosser3)) {
					farkhaImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					chickenGroup.setTranslateX(430);
					chickenGroup.setTranslateY(-10);
					crossersOnLeft.remove(chickenGroup);
					crossersOnRight.add(chickenGroup);
				}
				if (crossersOnLeft.contains(shalabyGroup) && !controller.leftBankCrossers.contains(crosser2)) {
					shalabyImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					shalabyGroup.setTranslateX(280);
					shalabyGroup.setTranslateY(-15);
					crossersOnLeft.remove(shalabyGroup);
					crossersOnRight.add(shalabyGroup);
				}
				if (crossersOnLeft.contains(crosser5Group) && !controller.leftBankCrossers.contains(crosser5)) {
					crosser5ImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					crosser5Group.setTranslateX(260);
					crosser5Group.setTranslateY(20);
					crossersOnLeft.remove(crosser5Group);
					crossersOnRight.add(crosser5Group);
				}
				crossersOnBoat.clear();
				crossers.clear();
				boatOnLeft = false;
				boatImageView.setTranslateX(200);
			}
			else if(!boatOnLeft){
				if (crossersOnRight.contains(farmerGroup) && !controller.rightBankCrossers.contains(crosser1)) {
					farmerImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					farmerGroup.setTranslateX(0);
					farmerGroup.setTranslateY(0);
					crossersOnLeft.add(farmerGroup);
					crossersOnRight.remove(farmerGroup);
				}
				if (crossersOnRight.contains(oshbGroup) && !controller.rightBankCrossers.contains(crosser4)) {
					oshbImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					oshbGroup.setTranslateX(0);
					oshbGroup.setTranslateY(0);
					crossersOnLeft.add(oshbGroup);
					crossersOnRight.remove(oshbGroup);
				}
				if (crossersOnRight.contains(chickenGroup) && !controller.rightBankCrossers.contains(crosser3)) {
					farkhaImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					chickenGroup.setTranslateX(0);
					chickenGroup.setTranslateY(0);
					crossersOnLeft.add(chickenGroup);
					crossersOnRight.remove(chickenGroup);
				}
				if (crossersOnRight.contains(shalabyGroup) && !controller.rightBankCrossers.contains(crosser2)) {
					shalabyImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					shalabyGroup.setTranslateX(0);
					shalabyGroup.setTranslateY(0);
					crossersOnLeft.add(shalabyGroup);
					crossersOnRight.remove(shalabyGroup);
				}
				if (crossersOnRight.contains(crosser5Group) && !controller.rightBankCrossers.contains(crosser5)) {
					crosser5ImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					crosser5Group.setTranslateX(0);
					crosser5Group.setTranslateY(0);
					crossersOnLeft.add(crosser5Group);
					crossersOnRight.remove(crosser5Group);
				}
				crossersOnBoat.clear();
				crossers.clear();
				boatOnLeft = true;
				boatImageView.setTranslateX(0);
			}
		}
		else{
			System.out.println("display an error or something");
		}
	}
	
	public void redoAction() {
		if(controller.canRedo() /*&& !crossersOnBoat.isEmpty()*/){
			controller.redo();
			if (boatOnLeft) {
				if (crossersOnLeft.contains(farmerGroup) && !controller.leftBankCrossers.contains(crosser1)) {
					farmerImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					farmerGroup.setTranslateX(630);
					farmerGroup.setTranslateY(40);
					crossersOnLeft.remove(farmerGroup);
					crossersOnRight.add(farmerGroup);
				}
				if (crossersOnLeft.contains(oshbGroup) && !controller.leftBankCrossers.contains(crosser4)) {
					oshbImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					oshbGroup.setTranslateX(530);
					oshbGroup.setTranslateY(15);
					crossersOnLeft.remove(oshbGroup);
					crossersOnRight.add(oshbGroup);
				}
				if (crossersOnLeft.contains(chickenGroup) && !controller.leftBankCrossers.contains(crosser3)) {
					farkhaImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					chickenGroup.setTranslateX(430);
					chickenGroup.setTranslateY(-10);
					crossersOnLeft.remove(chickenGroup);
					crossersOnRight.add(chickenGroup);
				}
				if (crossersOnLeft.contains(shalabyGroup) && !controller.leftBankCrossers.contains(crosser2)) {
					shalabyImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					shalabyGroup.setTranslateX(280);
					shalabyGroup.setTranslateY(-15);
					crossersOnLeft.remove(shalabyGroup);
					crossersOnRight.add(shalabyGroup);
				}
				if (crossersOnLeft.contains(crosser5Group) && !controller.leftBankCrossers.contains(crosser5)) {
					crosser5ImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					crosser5Group.setTranslateX(260);
					crosser5Group.setTranslateY(20);
					crossersOnLeft.remove(crosser5Group);
					crossersOnRight.add(crosser5Group);
				}
				crossersOnBoat.clear();
				crossers.clear();
				boatOnLeft = false;
				boatImageView.setTranslateX(200);
			}
			else if(!boatOnLeft){
				if (crossersOnRight.contains(farmerGroup) && !controller.rightBankCrossers.contains(crosser1)) {
					farmerImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					farmerGroup.setTranslateX(0);
					farmerGroup.setTranslateY(0);
					crossersOnLeft.add(farmerGroup);
					crossersOnRight.remove(farmerGroup);
				}
				if (crossersOnRight.contains(oshbGroup) && !controller.rightBankCrossers.contains(crosser4)) {
					oshbImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					oshbGroup.setTranslateX(0);
					oshbGroup.setTranslateY(0);
					crossersOnLeft.add(oshbGroup);
					crossersOnRight.remove(oshbGroup);
				}
				if (crossersOnRight.contains(chickenGroup) && !controller.rightBankCrossers.contains(crosser3)) {
					farkhaImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					chickenGroup.setTranslateX(0);
					chickenGroup.setTranslateY(0);
					crossersOnLeft.add(chickenGroup);
					crossersOnRight.remove(chickenGroup);
				}
				if (crossersOnRight.contains(shalabyGroup) && !controller.rightBankCrossers.contains(crosser2)) {
					shalabyImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					shalabyGroup.setTranslateX(0);
					shalabyGroup.setTranslateY(0);
					crossersOnLeft.add(shalabyGroup);
					crossersOnRight.remove(shalabyGroup);
				}
				if (crossersOnRight.contains(crosser5Group) && !controller.rightBankCrossers.contains(crosser5)) {
					crosser5ImageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
					crosser5Group.setTranslateX(0);
					crosser5Group.setTranslateY(0);
					crossersOnLeft.add(crosser5Group);
					crossersOnRight.remove(crosser5Group);
				}
				crossersOnBoat.clear();
				crossers.clear();
				boatOnLeft = true;
				boatImageView.setTranslateX(0);
			}
		}
		else{
			System.out.println("display an error or something");
		}
	}
	
	public static void setStrategy(ICrossingStrategy gameStrategy) {
		crossingStrategy = gameStrategy;
	}

	// alertBox
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

	@FXML
	public void instructionsAction() {
		Stage alert = new Stage();
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle("Instructions");
		alert.setMinHeight(200);
		alert.setMinWidth(250);
		Label label0 = new Label(controller.getInstructions()[0]);
		Label label1 = new Label(controller.getInstructions()[1]);
		Label label2 = new Label(controller.getInstructions()[2]);
		Label label3 = new Label(controller.getInstructions()[3]);
		VBox layout = new VBox(30);
		layout.getChildren().addAll(label0,label1,label2,label3);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		alert.setScene(scene);
		alert.showAndWait();
	}
	
	public void saveAction() {
		controller.saveGame();
		Stage alert = new Stage();
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle("Success");
		alert.setMinHeight(200);
		alert.setMinWidth(250);
		Label label = new Label("Save Successful");
		VBox layout = new VBox(30);
		layout.getChildren().addAll(label);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		alert.setScene(scene);
		alert.showAndWait();
	}
}