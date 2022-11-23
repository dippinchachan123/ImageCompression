package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class HuffFM {
	private File huffVersions[];
	private int huffFrequencies[][];
	private String dateArray[];
	private int selectedHuff = -1;

	public HuffFM() {
		initiate();
	}

	public void initiate() {
		File huffFolder = new File("huffLib");
		if (!huffFolder.exists())
			huffFolder.mkdir();
		else {
			huffVersions = huffFolder.listFiles();
		}

		dateArray = new String[huffVersions.length];
		scanDates();
		readVersions();
		selectedHuff = -1;
	}

	public File[] getHuffVersions() {
		return huffVersions;
	}

	public int[][] getHuffFrequencies() {
		return huffFrequencies;
	}

	private void scanDates() {
		for (int i = 0; i < huffVersions.length; i++) {
			Date date = new Date(huffVersions[i].lastModified());
			SimpleDateFormat df2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
			String dateText = df2.format(date);
			dateArray[i] = dateText;
		}
	}

	private void readVersions() {
		huffFrequencies = new int[huffVersions.length][256];
		for (int i = 0; i < huffVersions.length; i++) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(huffVersions[i].getCanonicalPath()));
				String s;
				for (int j = 0; (s = in.readLine()) != null; j++) {
					huffFrequencies[i][j] = Integer.parseInt(s);
				}
				in.close();
			} catch (Exception e) {
			}
		}
	}

	public String getDate(int i) {
		if (i <= -1)
			return "";
		return dateArray[i];
	}

	public int[] getData(int i) {
		return huffFrequencies[i];
	}

	public int[] getSelectedData() {
		return huffFrequencies[selectedHuff];
	}

	public void createVersion(int array[]) {
		// get date and time
		Date system = new Date(System.currentTimeMillis());
		SimpleDateFormat sys2 = new SimpleDateFormat("MMddyyHHmmss");

		// Create file name and file object
		StringBuilder sb = new StringBuilder("");
		sb.append("huffman").append(sys2.format(system));

		fileWriter(array, sb.toString());
	}

	public void modifyVersion(int array[]) {
		for (int i = 0; i < 256; i++)
			huffFrequencies[selectedHuff][i] += array[i];
		fileWriter(huffFrequencies[selectedHuff], huffVersions[selectedHuff].getName().split(".huff")[0]);
	}

	public void fileWriter(int array[], String fileName) {
		File outputFile;
		try {
			String val = JOptionPane.showInputDialog("Enter file name: ", fileName);
			if (!val.equals("")) {
				if (val.length() > 3)
					outputFile = new File("huffLib/" + val + ".huff");
				else
					throw new Exception("TRAIN HUFFVER FAILED: Use a longer huffname.");
			} else
				throw new Exception();

			PrintWriter out = new PrintWriter(outputFile);
			for (int i = 0; i < array.length; i++)
				out.println(array[i]);
			out.close();
			System.out.println("HUFF Version trained: " + outputFile.getName());
		} catch (Exception e) {
			if (e.getMessage() != null)
				System.out.println(e.getMessage());
			else
				System.out.println("TRAIN HUFFVER FAILED: Operation cancelled.");
		}
	}

	public int getSelectedIndex() {
		return selectedHuff;
	}

	public void setSelectedIndex(int i) {
		if (i <= -1)
			selectedHuff = -1;
		else
			selectedHuff = i;
	}

	public File getSelectedFile() {
		return huffVersions[selectedHuff];
	}
}
