package app;

public class StoryOneInstructions implements Instructions {
	
	String [] instructions;
	
	public String[] getInstructions() {
		instructions[0] = "A farmer wants to cross a river and take with him a carnivore, a herbivore and a plant.";
		
		instructions[1] = "- The farmer is the only one who can sail the boat. He can only take one passenger, in addition to himself.";
		
		instructions[2] = "- You can not leave any two crossers on the same bank if they can eat each other";
		
		instructions[3] = "Can you figure out how to get the farmer, his two animals and his plant to the other side with no losses?";
		
		return instructions;
	}
	
}
