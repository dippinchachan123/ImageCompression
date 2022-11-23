package model;

import java.io.File;

import io.FileIO;
import io.ImageFile;
import model.huffman.HuffmanBuilder;
import model.huffman.HuffmanCode;

public class Model {
	private FilePicker fp;
	private ImageFile ia;
	private HuffFM hfm;
	private HuffmanBuilder hb;
	private HuffmanCode<Integer> hc;
	private FileIO fio;

	public Model() {
		hfm = new HuffFM();
		fp = new FilePicker();
		hc = new HuffmanCode<Integer>();
		fio = new FileIO(hc);
	}

	public FilePicker getFP() {
		return fp;
	}

	public ImageFile getIA() {
		return ia;
	}

	public HuffFM getHFM() {
		return hfm;
	}

	public HuffmanBuilder getHB() {
		return hb;
	}

	public void setIA(String location) {
		try {
			ia = new ImageFile(location);
		} catch (NullPointerException npe) {
		}
	}

	public void setHB(int[] freqArray, boolean printOut) {
		hb = new HuffmanBuilder(freqArray, printOut, hc);
	}

	public FileIO getFIO() {
		return fio;
	}

	public void readHFF(String f) {
		fio = new FileIO(hc);
		fio.readHFF(f);
		File huffVersions[] = getHFM().getHuffVersions();
		for (int i = 0; i < huffVersions.length; i++)
			if (huffVersions[i].getName().equals(fio.huffName + ".huff"))
				setHB(hfm.getData(i), false);
		fio.decodeFile(f);
	}
}
