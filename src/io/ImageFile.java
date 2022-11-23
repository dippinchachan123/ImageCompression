package io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class ImageFile extends File {
	int width, height;
	int colorFreq[] = new int[256];
	int colorTable[];
	int imgData[][];
	private boolean success = false;

	public boolean isSuccess() {
		return success;
	}

	public ImageFile(String arg0) {
		super(arg0);
		try {
			if (arg0.endsWith(".hff"))
				success = true;
			else if (!arg0.endsWith(".png")) {
				success = false;
				System.out.println("This file format is not supported.");
			}
			else {
				readFile();
				System.out.println(this.getName() + ": " + width + "x" + height);
				success = true;
			}
		} catch (IOException e) {
			success = false;
			System.err.println(e);
		} catch (NullPointerException e) {
			success = false;
			System.err.println(e);
		}
	}

	private void readFile() throws IOException, NullPointerException {
		BufferedImage buffImage = ImageIO.read(this);
		if (buffImage.getTransparency() == 3) {
			System.out.println("Image has transparency values.");
			throw new NullPointerException();
		}

		height = buffImage.getHeight();
		width = buffImage.getWidth();
		imgData = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int color = buffImage.getRGB(i, j);

				int alpha = (color >> 24) & 255;
				int red = (color >> 16) & 255;
				int green = (color >> 8) & 255;
				int blue = (color) & 255;

				final int lum = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

				alpha = (alpha << 24);
				red = (lum << 16);
				green = (lum << 8);
				blue = lum;

				color = alpha + red + green + blue;
				Color c1 = new Color(color);
				int colorX = c1.getBlue();
				imgData[i][j] = colorX;
				colorFreq[colorX]++;
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[][] getRawData() {
		return imgData;
	}

	public int[] getFreq() {
		return colorFreq;
	}

	public Stack<Object> getChunks() {
		Stack<Object> s1 = new Stack<Object>();
		s1.push(this.getName().split(".png")[0]);
		s1.push(width);
		s1.push(height);
		s1.push(imgData);
		return s1;
	}
}