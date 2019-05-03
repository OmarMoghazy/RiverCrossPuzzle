package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
		rightBankCrossers.clear();
		boatRiders.clear();
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

	public Stack<Move> getRedoStack() {
		return redoStack;
	}

	public void setRedoStack(Stack<Move> redoStack) {
		this.redoStack = redoStack;
	}

	public Stack<Move> getUndoStack() {
		return undoStack;
	}

	public void setUndoStack(Stack<Move> undoStack) {
		this.undoStack = undoStack;
	}

}
