package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageFrame extends JPanel {
	int data[][];
	int WIDTH;
	int HEIGHT;
	JFrame f1;

	public ImageFrame() {
		f1 = new JFrame();
		f1.setLocation(100, 100);
		f1.setVisible(true);
		f1.add(this);
		f1.setResizable(false);
	}

	@SuppressWarnings("rawtypes")
	public void render(Stack parsed, String title) {
		this.data = (int[][]) parsed.pop();
		this.HEIGHT = (int) parsed.pop();
		this.WIDTH = (int) parsed.pop();
		f1.setSize(WIDTH + 6, HEIGHT + 29);
		f1.setTitle(title);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				int mono = data[x][y];
				g.setColor(new Color(mono, mono, mono));
				g.drawLine(x, y, x, y);
			}
		}

	}
}
