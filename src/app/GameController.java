package app;

import java.util.ArrayList;
import java.util.List;

public class GameController implements IRiverCrossingController {
	private ICrossingStrategy crossingStrategy;

	boolean isBoatOnLeftBank;
	int numberOfSails;

	ArrayList<ICrosser> boatRiders = new ArrayList<ICrosser>();
	ArrayList<ICrosser> leftBankCrossers = new ArrayList<ICrosser>();
	ArrayList<ICrosser> rightBankCrossers = new ArrayList<ICrosser>();

	public GameController(ArrayList<ICrosser> boatRiders, ArrayList<ICrosser> leftBankCrossers,
			ArrayList<ICrosser> rightBankCrossers) {
		isBoatOnLeftBank = true;
	}

	@Override
	public void newGame(ICrossingStrategy gameStrategy) {
		crossingStrategy = gameStrategy;
		leftBankCrossers = (ArrayList<ICrosser>) gameStrategy.getInitialCrossers();
	}

	@Override
	public void resetGame() {
		numberOfSails = 0;
		rightBankCrossers.clear();
		boatRiders.clear();
		leftBankCrossers = (ArrayList<ICrosser>) crossingStrategy.getInitialCrossers();

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
		
		for(ICrosser x : boatRiders) {
			if(x.canSail()) flag = 1;
		}
		if(flag == 0) return false;

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
			return false;
		} 
		else return true;
	}

	@Override
	public void doMove(List<ICrosser> crossers, boolean fromLeftToRightBank) {
		if (fromLeftToRightBank) {
			rightBankCrossers.addAll(boatRiders);
			isBoatOnLeftBank = false;
		}
		else {
			leftBankCrossers.addAll(boatRiders);
			isBoatOnLeftBank = true;
		}

		boatRiders.clear();
		numberOfSails++;

	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRedo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadGame() {
		// TODO Auto-generated method stub

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
