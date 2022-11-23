package model.huffman;

import java.util.ArrayList;

public class HuffmanCode<G> {
	private ArrayList<G> obj;
	private ArrayList<Integer> frequency;
	private ArrayList<String> code;

	public HuffmanCode() {
		obj = new ArrayList<G>();
		frequency = new ArrayList<Integer>();
		code = new ArrayList<String>();
	}

	public void addToList(G obj, int frequency, String code) {
		this.obj.add(obj);
		this.frequency.add(frequency);
		this.code.add(code);
	}

	public int getFrequency(G arg0) {
		try {
			return frequency.get(obj.indexOf(arg0));
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("HuffmanCode.getFrequency(): Item does not exist.");
		}
		return -1;
	}

	public String getCode(int arg0) {
		try {
			return code.get(obj.indexOf(arg0));
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("HuffmanCode.getCode(): Item does not exist.");
		}
		return "";
	}

	public Object getObject(String bin) {
		int index = code.indexOf(bin);
		if (index == -1)
			return null;
		else
			return obj.get(index);
	}

	public void printTable() {
		System.out.println("HUFFMAN CODE TABLE");
		System.out.println("-----------------------------------------");
		System.out.printf("%7s%12s%15s", "Object", "Frequency", "Code\n");
		System.out.println("-----------------------------------------");
		for (int i = 0; i < obj.size(); i++)
			System.out.printf("%5s%12s%15s\n", (char) ((int) obj.get(i)), frequency.get(i), code.get(i));
	}

}