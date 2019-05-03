package app;

import java.util.ArrayList;
import java.util.List;

public class StoryTwoCrossingStrategy implements ICrossingStrategy {
	Instructions instructions;
	CrosserFactory crosserFactory = new CrosserFactory();
	ICrosser farmer1 = crosserFactory.newCrosser("FARMER",90,"Weight: 90");
	ICrosser farmer2 = crosserFactory.newCrosser("FARMER",80, "Weight 80");
	ICrosser farmer3 = crosserFactory.newCrosser("FARMER",60,"Weight: 60");
	ICrosser farmer4 = crosserFactory.newCrosser("FARMER",40,"Weight: 40");
	ICrosser herbivore = crosserFactory.newCrosser("HERBIVORE",20,"Weight: 20");
	ArrayList<ICrosser> initialCrossers = new ArrayList<ICrosser>();
	
	public StoryTwoCrossingStrategy() {
		instructions = new StoryTwoInstructions();
	}


	@Override
	public boolean isValid(List<ICrosser> rightBankCrossers, List<ICrosser> leftBankCrossers,
			List<ICrosser> boatRiders) {
		double weight=0.0;
 
		for (ICrosser x : boatRiders) {
			weight=weight+x.getWeight();
		}
		return (weight<=100);
	}

	@Override
	public List<ICrosser> getInitialCrossers() {
		initialCrossers.add(farmer1);
		initialCrossers.add(farmer2);
		initialCrossers.add(farmer3);
		initialCrossers.add(farmer4);
		initialCrossers.add(herbivore);
		return initialCrossers;
		
	}

	@Override
	public String[] getInstructions() {
		return instructions.getInstructions();
	}

}
