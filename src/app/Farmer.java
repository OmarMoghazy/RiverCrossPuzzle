package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Farmer implements ICrosser {
	private double weight;
	private static final int eatingRank = 4;
	private String label;
	
	public static int getEatingrank() {
		return eatingRank;
	}



	public Farmer(double weight) {
		super();
		this.weight = weight;
	}



	@Override
	public boolean canSail() {
		return true;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public int getEatingRank() {
		return eatingRank;
	}

	@Override
	public BufferedImage[] getImages() {
		BufferedImage[] image = new BufferedImage[8];
		image[0] = new BufferedImage(226, 247, BufferedImage.TYPE_INT_ARGB);
		try {
			image[0] = ImageIO.read(new File("Farmer.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public ICrosser makeCopy() {
		return new Farmer(this.weight);
	}

	@Override
	public void setLabelToBeShown(String label) {
		this.label = label;
	}

	@Override
	public String getLabelToBeShown() {
		
		return label;
	}

}
