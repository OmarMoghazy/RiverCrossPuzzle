package app;

import java.util.ArrayList;

public class Move {
	private ArrayList<ICrosser> leftBankCrossers = new ArrayList<ICrosser>();
	private ArrayList<ICrosser> boatRiders = new ArrayList<ICrosser>();
	private ArrayList<ICrosser> rightBankCrossers = new ArrayList<ICrosser>();
	
	public ArrayList<ICrosser> getLeftBankCrossers() {
		return leftBankCrossers;
	}
	public void setLeftBankCrossers(ArrayList<ICrosser> leftBankCrossers) {
		this.leftBankCrossers = leftBankCrossers;
	}
	public ArrayList<ICrosser> getBoatRiders() {
		return boatRiders;
	}
	public void setBoatRiders(ArrayList<ICrosser> boatRiders) {
		this.boatRiders = boatRiders;
	}
	public ArrayList<ICrosser> getRightBankCrossers() {
		return rightBankCrossers;
	}
	public void setRightBankCrossers(ArrayList<ICrosser> rightBankCrossers) {
		this.rightBankCrossers = rightBankCrossers;
	}
}
