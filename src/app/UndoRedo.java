package app;

import java.util.ArrayList;
import java.util.Stack;

public class UndoRedo {
	
	ArrayList<ICrosser> leftBankCrossers = new ArrayList<ICrosser>();
	ArrayList<ICrosser> rightBankCrossers = new ArrayList<ICrosser>();
	private Stack<Move> undoStack = new Stack<Move>();
	private Stack<Move> redoStack= new Stack<Move>();
	boolean isBoatOnLeftBank;
	int numberOfSails;
	
	public UndoRedo(ArrayList<ICrosser> leftBankCrossers, ArrayList<ICrosser> rightBankCrossers, Boolean isBoatOnLeftBank, int numberOfSails) {
		
		this.leftBankCrossers=leftBankCrossers;
		this.rightBankCrossers=rightBankCrossers;
		this.isBoatOnLeftBank = isBoatOnLeftBank;
		this.numberOfSails=numberOfSails;
	}

	public void Redo() {
		
		getUndoStack().push(new Move(leftBankCrossers, rightBankCrossers, numberOfSails, isBoatOnLeftBank));
		Move move = getRedoStack().pop();
		leftBankCrossers = move.getLeftBankCrossers();
		rightBankCrossers = move.getRightBankCrossers();
		isBoatOnLeftBank = move.isBoatOnLeftBank();
		numberOfSails = move.getNumberOfSails();
		
	}
	
	public void Undo() {
		
		getRedoStack().push(new Move(leftBankCrossers, rightBankCrossers, numberOfSails, isBoatOnLeftBank));
		Move move = getUndoStack().pop();
		leftBankCrossers = move.getLeftBankCrossers();
		rightBankCrossers = move.getRightBankCrossers();
		isBoatOnLeftBank = move.isBoatOnLeftBank();
		numberOfSails = move.getNumberOfSails();
	}

	public void AddToStack(Move move)
	{
		getUndoStack().push(move);
	}

	public Stack<Move> getUndoStack() {
		return undoStack;
	}

	public void setUndoStack(Stack<Move> undoStack) {
		this.undoStack = undoStack;
	}

	public Stack<Move> getRedoStack() {
		return redoStack;
	}

	public void setRedoStack(Stack<Move> redoStack) {
		this.redoStack = redoStack;
	}

	public void removeFromStack() {
		undoStack.pop();
	}
}
