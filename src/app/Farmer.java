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
		ClassLoader classLoader = getClass().getClassLoader();
		File[] f;
		f = new File[8];
		f[0] = new File(classLoader.getResource("mans.png").getFile());
		f[1] = new File(classLoader.getResource("mans2.png").getFile());
		f[2] = new File(classLoader.getResource("mans3.png").getFile());
		f[3] = new File(classLoader.getResource("mans4.png").getFile());
		BufferedImage[] image = new BufferedImage[8];
		image[0] = new BufferedImage(226, 247, BufferedImage.TYPE_INT_ARGB);
		try {
			image[0] = ImageIO.read(f[0]);
			image[1] = ImageIO.read(f[1]);
			image[2] = ImageIO.read(f[2]);
			image[3] = ImageIO.read(f[3]);
			System.out.println("loaded mans");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
