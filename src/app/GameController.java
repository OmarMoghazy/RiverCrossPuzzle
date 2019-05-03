package app;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class GameController implements IRiverCrossingController {
	private ICrossingStrategy crossingStrategy;
	private static GameController instance;
	private Stack<Move> undoStack = new Stack<Move>();
	private Stack<Move> redoStack = new Stack<Move>();

	boolean isBoatOnLeftBank;
	int numberOfSails;

	ArrayList<ICrosser> boatRiders = new ArrayList<ICrosser>();
	ArrayList<ICrosser> leftBankCrossers = new ArrayList<ICrosser>();
	ArrayList<ICrosser> rightBankCrossers = new ArrayList<ICrosser>();

	private GameController() {
	}

	public static synchronized GameController getInstance() {
		if (instance == null)
			instance = new GameController();
		return instance;
	}

	@Override
	public void newGame(ICrossingStrategy gameStrategy) {
		crossingStrategy = gameStrategy;
		leftBankCrossers = (ArrayList<ICrosser>) gameStrategy.getInitialCrossers();
		isBoatOnLeftBank = true;
	}

	@Override
	public void resetGame() {
		numberOfSails = 0;
		leftBankCrossers.clear();
		rightBankCrossers.clear();
		boatRiders.clear();
		if (crossingStrategy instanceof StoryOneCrossingStrategy)
			crossingStrategy = new StoryOneCrossingStrategy();
		else if (crossingStrategy instanceof StoryTwoCrossingStrategy)
			crossingStrategy = new StoryTwoCrossingStrategy();
		leftBankCrossers = (ArrayList<ICrosser>) crossingStrategy.getInitialCrossers();
		isBoatOnLeftBank = true;
	}

	@Override
	public String[] getInstructions() {
		return crossingStrategy.getInstructions();
	}

	@Override
	public List<ICrosser> getCrossersOnRightBank() {
		return rightBankCrossers;
	}

	@Override
	public List<ICrosser> getCrossersOnLeftBank() {
		return leftBankCrossers;
	}

	@Override
	public boolean isBoatOnTheLeftBank() {
		return isBoatOnLeftBank;
	}

	@Override
	public int getNumberOfSails() {
		return numberOfSails;
	}

	@Override
	public boolean canMove(List<ICrosser> crossers, boolean fromLeftToRightBank) {
		Move move = new Move(leftBankCrossers, rightBankCrossers, numberOfSails, isBoatOnLeftBank);
		undoStack.add(move);
		int flag = 0;
		if (fromLeftToRightBank) {
			for (ICrosser x : crossers) {
				leftBankCrossers.remove(x);
			}
		} else {
			for (ICrosser x : crossers) {
				rightBankCrossers.remove(x);
			}
		}
		for (ICrosser x : crossers) {
			boatRiders.add(x);
		}

		for (ICrosser x : boatRiders) {
			if (x.canSail()) {
				flag = 1;
				break;
			}
		}
		if (flag == 0)
			return false;
		if (!crossingStrategy.isValid(rightBankCrossers, leftBankCrossers, boatRiders)) {
			if (fromLeftToRightBank) {
				for (ICrosser x : crossers) {
					leftBankCrossers.add(x);
				}
			} else {
				for (ICrosser x : crossers) {
					rightBankCrossers.add(x);
				}
			}
			for (ICrosser x : crossers) {
				boatRiders.remove(x);
			}
			undoStack.remove(move);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void doMove(List<ICrosser> crossers, boolean fromLeftToRightBank) {
		if (fromLeftToRightBank) {
			rightBankCrossers.addAll(crossers);
			boatRiders.clear();
			isBoatOnLeftBank = false;
		} else {
			leftBankCrossers.addAll(crossers);
			boatRiders.clear();
			isBoatOnLeftBank = true;
		}

		boatRiders.clear();
		numberOfSails++;

	}

	@Override
	public boolean canUndo() {
		return !undoStack.isEmpty();
	}

	@Override
	public boolean canRedo() {
		return !redoStack.isEmpty();
	}

	@Override
	public void undo() {
		redoStack.push(new Move(leftBankCrossers, rightBankCrossers, numberOfSails, isBoatOnLeftBank));
		Move move = undoStack.pop();
		leftBankCrossers = move.getLeftBankCrossers();
		rightBankCrossers = move.getRightBankCrossers();
		isBoatOnLeftBank = move.isBoatOnLeftBank();
		numberOfSails = move.getNumberOfSails();
	}

	@Override
	public void redo() {
		undoStack.push(new Move(leftBankCrossers, rightBankCrossers, numberOfSails, isBoatOnLeftBank));
		Move move = redoStack.pop();
		leftBankCrossers = move.getLeftBankCrossers();
		rightBankCrossers = move.getRightBankCrossers();
		isBoatOnLeftBank = move.isBoatOnLeftBank();
		numberOfSails = move.getNumberOfSails();
	}

	@Override
	public void saveGame() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			

			StringBuilder xmlStringBuilder = new StringBuilder();

			xmlStringBuilder.append("<?xml version=?> <class> </class>");
			ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
			//document = builder.parse("SavedGame.xml");

			Element Story = document.createElement("Story");
			Element RightBankers = document.createElement("RightBankers");
			Element LeftBankers = document.createElement("LeftBankers");
			Element Score = document.createElement("Score");
			Element BoatPosition = document.createElement("BoatPosition");

			document.appendChild(Story);
			Story.appendChild(RightBankers);
			Story.appendChild(LeftBankers);
			Story.appendChild(Score);
			Story.appendChild(BoatPosition);

			if (crossingStrategy instanceof StoryOneCrossingStrategy) {
				Story.appendChild(document.createTextNode("Story One"));
				for (ICrosser x : rightBankCrossers) {
					if (x instanceof Plant)
						RightBankers.appendChild(document.createElement("Plant"));
					else if (x instanceof Carnivore)
						RightBankers.appendChild(document.createElement("Carnicore"));
					else if (x instanceof Herbivore)
						RightBankers.appendChild(document.createElement("Herbivore"));
					else
						RightBankers.appendChild(document.createElement("Farmer"));
				}

				for (ICrosser x : leftBankCrossers) {
					if (x instanceof Plant)
						LeftBankers.appendChild(document.createElement("Plant"));
					else if (x instanceof Carnivore)
						LeftBankers.appendChild(document.createElement("Carnicore"));
					else if (x instanceof Herbivore)
						LeftBankers.appendChild(document.createElement("Herbivore"));
					else
						LeftBankers.appendChild(document.createElement("Farmer"));
				}
			}

			else {
				Story.appendChild(document.createTextNode("Story Two"));
				for (ICrosser x : rightBankCrossers) {
					String w = Double.toString(x.getWeight());
					RightBankers.appendChild(document.createElement(w));
				}
				for (ICrosser x : leftBankCrossers) {
					String w = Double.toString(x.getWeight());
					LeftBankers.appendChild(document.createElement(w));
				}

			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File("SavedGame.xml"));

			// If you use
			// StreamResult result = new StreamResult(System.out);
			// the output will be pushed to the standard output ...
			// You can use that for debugging

			transformer.transform(domSource, streamResult);

			System.out.println("Done creating XML File");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void loadGame() {
	}

	@Override
	public List<List<ICrosser>> solveGame() {
		// TODO Auto-generated method stub
		return null;
	}

	public ICrossingStrategy getCrossingStrategy() {
		return crossingStrategy;
	}

	public void setCrossingStrategy(ICrossingStrategy crossingStrategy) {
		this.crossingStrategy = crossingStrategy;
	}

}
