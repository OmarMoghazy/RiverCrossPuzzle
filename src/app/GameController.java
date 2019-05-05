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
	boolean isBoatOnLeftBank;
	int numberOfSails;
	private Stack<Move> undoStack = new Stack<Move>();
	private Stack<Move> redoStack = new Stack<Move>();

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
		Move move = new Move(leftBankCrossers, rightBankCrossers);
		undoStack.push(move);
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
			undoStack.pop();
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
		redoStack.push(new Move(leftBankCrossers, rightBankCrossers));
		Move move = undoStack.pop();
		this.leftBankCrossers.clear();
		this.leftBankCrossers.addAll(move.getLeftBankCrossers());
		this.rightBankCrossers.clear();
		this.rightBankCrossers.addAll(move.getRightBankCrossers());
	}

	@Override
	public void redo() {
		undoStack.push(new Move(leftBankCrossers, rightBankCrossers));
		Move move = redoStack.pop();
		this.leftBankCrossers.clear();
		this.leftBankCrossers.addAll(move.getLeftBankCrossers());
		this.rightBankCrossers.clear();
		this.rightBankCrossers.addAll(move.getRightBankCrossers());
	}

	@Override
	public void saveGame() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			StringBuilder xmlStringBuilder = new StringBuilder();
			xmlStringBuilder.append("<?xml version=?> <class> </class>");
			new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
			Element Story = document.createElement("Story");
			Element RightBankers = document.createElement("RightBankers");
			Element LeftBankers = document.createElement("LeftBankers");
			Element Score = document.createElement("Score");
			Element BoatPosition = document.createElement("BoatPosition");
			document.appendChild(Story);
			Element StoryLevel = document.createElement("StoryLevel");

			Story.appendChild(StoryLevel);

			if (this.crossingStrategy instanceof StoryOneCrossingStrategy) {
				StoryLevel.appendChild(document.createTextNode("Story One,"));
				Story.appendChild(Score);
				Score.appendChild(document.createTextNode(Integer.toString(numberOfSails) + ","));
				Story.appendChild(BoatPosition);
				BoatPosition.appendChild(document.createTextNode(Boolean.toString(isBoatOnLeftBank) + ","));

				Story.appendChild(LeftBankers);

				for (ICrosser x : leftBankCrossers) {
					if (x instanceof Plant) {
						LeftBankers.appendChild(document.createTextNode("LPlant,"));
					} else if (x instanceof Carnivore) {
						LeftBankers.appendChild(document.createTextNode("LCarnivore,"));
					} else if (x instanceof Herbivore) {
						LeftBankers.appendChild(document.createTextNode("LHerbivore,"));
					} else {
						LeftBankers.appendChild(document.createTextNode("LFarmer,"));
					}
				}
				Story.appendChild(RightBankers);

				for (ICrosser x : rightBankCrossers) {
					if (x instanceof Plant) {
						RightBankers.appendChild(document.createTextNode("RPlant,"));
					} else if (x instanceof Carnivore) {
						RightBankers.appendChild(document.createTextNode("RCarnivore,"));
					} else if (x instanceof Herbivore) {
						RightBankers.appendChild(document.createTextNode("RHerbivore,"));
					} else {
						RightBankers.appendChild(document.createTextNode("RFarmer,"));
					}
				}
				RightBankers.appendChild(document.createTextNode(","));

			} else {
				Story.appendChild(document.createTextNode("Story Two,"));
				Story.appendChild(Score);
				Score.appendChild(document.createTextNode(Integer.toString(numberOfSails) + ","));
				Story.appendChild(BoatPosition);
				BoatPosition.appendChild(document.createTextNode(Boolean.toString(isBoatOnLeftBank) + ","));
				Story.appendChild(LeftBankers);
				for (ICrosser x : rightBankCrossers) {
					String w = Double.toString(x.getWeight());
					RightBankers.appendChild(document.createTextNode(w));
				}
				Story.appendChild(RightBankers);
				for (ICrosser x : leftBankCrossers) {
					String w = Double.toString(x.getWeight());
					LeftBankers.appendChild(document.createTextNode(w));
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File("SavedGame.xml"));
			transformer.transform(domSource, streamResult);
			System.out.println("Done creating XML File");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadGame() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		ArrayList<ICrosser> toRemove = new ArrayList<ICrosser>();
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document = builder.parse("SavedGame.xml");
			String StoryData = document.getElementsByTagName("Story").item(0).getTextContent();
			System.out.println(StoryData);
			ArrayList<String> Data = new ArrayList<String>();
			for (String val : StoryData.split(",")) {
				System.out.println(val);
				Data.add(val);
			}
			if (Data.get(0).equals("Story One"))
				crossingStrategy = new StoryOneCrossingStrategy();
			else
				crossingStrategy = new StoryTwoCrossingStrategy();
			ArrayList<ICrosser> allCrossers = new ArrayList<ICrosser>();
			allCrossers.addAll(crossingStrategy.getInitialCrossers());

			numberOfSails = Integer.parseInt(Data.get(1));

			if (Data.get(2).equals("true"))
				isBoatOnLeftBank = true;
			else
				isBoatOnLeftBank = false;

			for (int j = 3; j < Data.size(); j++) {
				if (Data.get(j).substring(1).equals("Farmer")) {

					for (ICrosser x : allCrossers) {
						if (x instanceof Farmer) {
							toRemove.add(x);
							if (Data.get(j).substring(0, 1).equals("L"))
								leftBankCrossers.add(x);
							else
								rightBankCrossers.add(x);
						}
					}
					allCrossers.removeAll(toRemove);
					toRemove.clear();
				} 
				else if (Data.get(j).substring(1).equals("Carnivore")) {
					for (ICrosser x : allCrossers) {
						if (x instanceof Carnivore) {
							toRemove.add(x);
							if (Data.get(j).substring(0, 1).equals("L"))
								leftBankCrossers.add(x);
							else
								rightBankCrossers.add(x);
						}
					}
					allCrossers.removeAll(toRemove);
					toRemove.clear();
				} else if (Data.get(j).substring(1).equals("Herbivore")) {
					for (ICrosser x : allCrossers) {
						if (x instanceof Herbivore) {
							toRemove.add(x);
							if (Data.get(j).substring(0, 1).equals("L"))
								leftBankCrossers.add(x);
							else
								rightBankCrossers.add(x);
						}
					}
					allCrossers.removeAll(toRemove);
					toRemove.clear();
				} else if (Data.get(j).substring(1).equals("Plant")) {
					for (ICrosser x : allCrossers) {
						if (x instanceof Plant) {
							toRemove.add(x);
							if (Data.get(j).substring(0, 1).equals("L"))
								leftBankCrossers.add(x);
							else
								rightBankCrossers.add(x);
						}
					}
					allCrossers.removeAll(toRemove);
					toRemove.clear();
				}
			}

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
