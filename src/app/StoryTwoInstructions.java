package app;

public class StoryTwoInstructions implements Instructions {

	String[] instructions = new String[5];
	public StoryTwoInstructions() { 
		instructions[0] = "“Four farmers and their animal "
				+ "need to cross a river in a small boat. "
				+ "The weights of the farmers are 90 kg, "
				+ "80 kg,60 kg and 40 kg respectively,"
				+ " and the weight of the animal is 20 kg.";
		
		instructions[1] = "- The farmer is the only one who can sail the boat."
				+ " He can only take one passenger, in addition to himself.";
		
		instructions[2] = "- You can not leave any two crossers on the same bank"
				+ " if they can eat each other";
		
		instructions[3] = "Can you figure out how to get the farmer, his two "
				+ "animals and his plant to the other side with no losses?";
	}

	public String[] getInstructions() {
		return instructions;
	}

}
