package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Carnivore implements ICrosser {

	private double weight;
	private static final int eatingRank = 2;
	private String label; 
	
	public Carnivore(double weight) {
		super();
		this.weight = weight;
	}

	@Override
	public boolean canSail() {
		return false;
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
		ClassLoader classLoader = getClass().getClassLoader();
		File[] f;
		f = new File[8];
		f[0] = new File(classLoader.getResource("carnivore.png").getFile());
		BufferedImage[] image = new BufferedImage[8];
		image[0] = new BufferedImage(226, 247, BufferedImage.TYPE_INT_ARGB);
		try {
			image[0] = ImageIO.read(f[0]);
			System.out.println("loaded monster");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public ICrosser makeCopy() {
		return null;
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
