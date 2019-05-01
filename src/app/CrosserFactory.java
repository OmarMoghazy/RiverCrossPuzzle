package app;

public class CrosserFactory {
	
	ICrosser newCrosser(String type, double weight, String label) {
		if(type.equalsIgnoreCase("FARMER")) {
			ICrosser farmer = new Farmer(weight);
			farmer.setLabelToBeShown(label);
			return farmer;
		}
		else if(type.equalsIgnoreCase("CARNIVORE")) {
			ICrosser carnivore = new Carnivore(weight);
			carnivore.setLabelToBeShown(label);
			return carnivore;
		}
		else if(type.equalsIgnoreCase("HERBIVORE")) {
			ICrosser herbivore = new Herbivore(weight);
			herbivore.setLabelToBeShown(label);
			return herbivore;
		}
		else if(type.equalsIgnoreCase("PLANT")) {
			ICrosser plant = new Plant(weight);
			plant.setLabelToBeShown(label);
			return plant;
		}
		else return null;
	}
}