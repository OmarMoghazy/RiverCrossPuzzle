package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Plant implements ICrosser {

	private double weight;
	private static final int eatingRank = 0;
	private String label;
	
	public Plant(double weight) {
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
		ClassLoader classLoader = getClass().getClassLoader();
		File[] f;
		f = new File[8];
		f[0] = new File(classLoader.getResource("oshb.png").getFile());
		BufferedImage[] image = new BufferedImage[8];
		image[0] = new BufferedImage(226, 247, BufferedImage.TYPE_INT_ARGB);
		try {
			image[0] = ImageIO.read(f[0]);
			System.out.println("loaded oshb");
		} catch (IOException e) {
			e.printStackTrace();}
		return image;
		}

	@Override
	public ICrosser makeCopy() {
		// TODO Auto-generated method stub
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
