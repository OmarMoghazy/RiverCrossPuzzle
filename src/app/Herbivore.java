package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Herbivore implements ICrosser{

	private double weight;
	private String label;
	public static final int eatingRank = 1;
	
	public Herbivore(double weight) {
		super();
		this.weight = weight;
	}

	@Override
	public boolean canSail() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}

	@Override
	public int getEatingRank() {
		// TODO Auto-generated method stub
		return eatingRank;
	}

	@Override
	public BufferedImage[] getImages() {
		BufferedImage[] image = new BufferedImage[8];
		image[0] = new BufferedImage(226, 247, BufferedImage.TYPE_INT_ARGB);
		try {
			int x = (int)(Math.random()*2);
			if(x==0)
				image[0] = ImageIO.read(new File("Chicken.png"));
			else
			image[0] = ImageIO.read(new File("Cow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public ICrosser makeCopy() {
		return new Herbivore(this.weight);
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
