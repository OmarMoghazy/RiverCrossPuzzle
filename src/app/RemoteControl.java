package app;

public class RemoteControl {
	
	Do Command;
	
	public void setCommand(Do Command)
	{
		this.Command=Command;
		
	}
	
	public void PressButton() {
		Command.execute();
	}

}
