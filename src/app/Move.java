package app;

import java.util.ArrayList;

public class Move {
	private ArrayList<ICrosser> leftBankCrossers = new ArrayList<ICrosser>();
	private ArrayList<ICrosser> rightBankCrossers = new ArrayList<ICrosser>();
	private int numberOfSails;
	
	public Move(ArrayList<ICrosser> leftBankCrossers, ArrayList<ICrosser> rightBankCrossers, int numberOfSails) {
		this.leftBankCrossers = leftBankCrossers;
		this.rightBankCrossers = rightBankCrossers;
		this.numberOfSails = numberOfSails;
	}
	public ArrayList<ICrosser> getLeftBankCrossers() {
		return leftBankCrossers;
	}
	public void setLeftBankCrossers(ArrayList<ICrosser> leftBankCrossers) {
		this.leftBankCrossers = leftBankCrossers;
	}
	public ArrayList<ICrosser> getRightBankCrossers() {
		return rightBankCrossers;
	}
	public void setRightBankCrossers(ArrayList<ICrosser> rightBankCrossers) {
		this.rightBankCrossers = rightBankCrossers;
	}
	public int getNumberOfSails() {
		return numberOfSails;
	}
	public void setNumberOfSails(int numberOfSails) {
		this.numberOfSails = numberOfSails;
	}
}
