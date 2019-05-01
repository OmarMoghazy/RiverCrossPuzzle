package app;

import java.util.ArrayList;
import java.util.List;

public class StoryOneCrossingStrategy implements ICrossingStrategy {
	Instructions instructions;
	CrosserFactory crosserFactory = new CrosserFactory();
	ICrosser farmer = crosserFactory.newCrosser("FARMER", 100, "Eating Rank: " + 4);
	ICrosser carnivore = crosserFactory.newCrosser("CARNIVORE",100,"Eating Rank: " + 2);
	ICrosser herbivore = crosserFactory.newCrosser("HERBIVORE",100,"Eating rank " + 1);
	ICrosser plant = crosserFactory.newCrosser("PLANT",100, "Eating rank " + 0);
	ArrayList<ICrosser> initialCrossers = new ArrayList<ICrosser>();
	
	public StoryOneCrossingStrategy() {
		instructions =  new StoryOneInstructions();
	}

	@Override
	public boolean isValid(List<ICrosser> rightBankCrossers, List<ICrosser> leftBankCrossers,
			List<ICrosser> boatRiders) {
		// boat validation
		if(boatRiders.size() > 2) return false;
		
		// left bank validation
		if(leftBankCrossers.size() == 2) {
			if(Math.abs(leftBankCrossers.get(0).getEatingRank() - leftBankCrossers.get(1).getEatingRank()) == 1) {
				return false;
			}
		}
		
		// right bank validation
				if(rightBankCrossers.size() == 2) {
					if(Math.abs(rightBankCrossers.get(0).getEatingRank() - rightBankCrossers.get(1).getEatingRank()) == 1) {
						return false;
					}
				}
		return true;
	}

	@Override
	public List<ICrosser> getInitialCrossers() {
		initialCrossers.add(farmer);
		initialCrossers.add(carnivore);
		initialCrossers.add(herbivore);
		initialCrossers.add(plant);
		return initialCrossers;
	}

	@Override
	public String[] getInstructions() {
		return instructions.getInstructions();
	}

}
