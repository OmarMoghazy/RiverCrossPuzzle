package app;

import java.util.ArrayList;

public class Move {
	private ArrayList<ICrosser> leftBankCrossers = new ArrayList<ICrosser>();
	private ArrayList<ICrosser> rightBankCrossers = new ArrayList<ICrosser>();
	private int numberOfSails;
	private boolean isBoatOnLeftBank;
	
	public Move(ArrayList<ICrosser> leftBankCrossers, ArrayList<ICrosser> rightBankCrossers, int numberOfSails, boolean isBoatOnLeftBank) {
		for(ICrosser x : leftBankCrossers) {
			this.leftBankCrossers.add(x);
		}
		for(ICrosser x : rightBankCrossers) {
			this.rightBankCrossers.add(x);
		}
		this.numberOfSails = numberOfSails;
		this.isBoatOnLeftBank = isBoatOnLeftBank;
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
	public boolean isBoatOnLeftBank() {
		return isBoatOnLeftBank;
	}
	public void setBoatOnLeftBank(boolean isBoatOnLeftBank) {
		this.isBoatOnLeftBank = isBoatOnLeftBank;
	}
}
