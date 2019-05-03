package app;

public class RedoCommand implements Do{

	
UndoRedo undoredo;
	
	public RedoCommand(UndoRedo undoredo) {
		this.undoredo=undoredo;
	}
	
	@Override
	public void execute() {
		undoredo.Redo();
		
	}

}
