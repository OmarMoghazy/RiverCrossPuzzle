package app;

public class UndoCommand implements Do{

	UndoRedo undoredo;
	
	public UndoCommand(UndoRedo undoredo) {
		this.undoredo=undoredo;
	}
	
	@Override
	public void execute() {
		undoredo.Undo();
	}

	
}
