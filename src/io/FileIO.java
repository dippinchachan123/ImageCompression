package io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Stack;

import model.huffman.HuffmanCode;
import view.ImageFrame;

public class FileIO {
	private HuffmanCode<Integer> hc;
	private String rawString;
	public int WIDTH;
	public int HEIGHT;
	public String huffName;

	public FileIO(HuffmanCode<Integer> hc) {
		this.hc = hc;
	}

	@SuppressWarnings("rawtypes")
	public String encodeToFile(Stack chunks, String versionName) {
		final int DATA[][] = (int[][]) chunks.pop();
		final int HEIGHT = (int) chunks.pop();
		final int WIDTH = (int) chunks.pop();
		final String NAME = (String) chunks.pop();

		new File("output").mkdir();
		String outputAddress = "output/" + NAME + ".hff";
		for (int i = 2; new File(outputAddress).exists(); i++)
			outputAddress = "output/" + NAME + "(" + i + ")" + ".hff";

		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputAddress));
			bos.write("huffcode".getBytes());
			bos.write(("IDHR" + "W" + WIDTH + " H" + HEIGHT + " R" + versionName).getBytes());
			bos.write(" IDATA".getBytes());

			StringBuilder sbdata = new StringBuilder();
			for (int x = 0; x < WIDTH; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					String code = hc.getCode(DATA[x][y]);
					sbdata.append(code);
				}
			}
			if (sbdata.length() % 2 != 0)
				sbdata.append("0");
			for (int i = 0; i < sbdata.length(); i += 8) {
				for (int j = 8; j > 0; j--) {
					try {
						String code = sbdata.substring(i, i + j);
						short a = Short.parseShort(code, 2);
						ByteBuffer bytes = ByteBuffer.allocate(2).putShort(a);
						bos.write(bytes.array()[1]);
						break;
					} catch (Exception e) {
					}
				}
			}
			bos.write("IEND".getBytes());

			bos.close();
			System.out.println("Compressed successfully!");
		} catch (Exception e) {
			System.out.println("Compression failed." + e);
			return "";
		}
		return outputAddress;
	}

	public void readHFF(String f) {
		final String FILETAG = "68756666636F6465";
		final String HEADERTAG = "49444852";
		final String DATASTART = "4944415441";
		final String DATAEND = "49454E44";
		System.out.println("Reading: " + f);
		try {
			rawString = BinaryFile.format(BinaryFile.read(f));
			if (!rawString.startsWith(FILETAG)) {
				throw new IOException("Not an HFF!");
			}

			WIDTH = Integer.parseInt(hexToASCII(decodeHelper(HEADERTAG + "57", "20")));
			HEIGHT = Integer.parseInt(hexToASCII(decodeHelper("48", "20")));
			huffName = hexToASCII(decodeHelper("52", "20"));
			rawString = decodeHelper(DATASTART, DATAEND);

			System.out.print("WIDTH: " + WIDTH);
			System.out.print("\tHEIGHT: " + HEIGHT);
			System.out.print("\tHUFFVER: " + huffName + "\n");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void decodeFile(String f) {
		String binData = hexToBin(rawString);
		ArrayList<Integer> al = new ArrayList<Integer>();
		int i, j, k = 0;
		int data[][] = new int[WIDTH][HEIGHT];

		for (i = 0; i < binData.length(); i += j - i) {
			for (j = i; j < binData.length(); j++) {
				String current = binData.substring(i, j);
				Object objBin = hc.getObject(current);
				if (objBin != null) {
					al.add((int) objBin);
					break;
				}
			}
		}

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				data[x][y] = al.get(k++);
			}
		}

		Stack<Object> p1 = new Stack<Object>();
		p1.push(WIDTH);
		p1.push(HEIGHT);
		p1.push(data);
		new ImageFrame().render(p1, f);
		System.out.println("Finished decoding.");

	}

	private String decodeHelper(String startsWith, String endsWith) throws Exception {
		String captured = rawString.split(startsWith)[1].split(endsWith)[0];
		rawString = rawString.split(captured)[1];
		return captured;
	}

	private String hexToBin(String hexString) {
		StringBuilder data = new StringBuilder();
		for (int i = 0; i < hexString.length(); i++) {
			int a = Byte.decode("0X" + hexString.charAt(i));
			String binString = Integer.toBinaryString(a);
			String process = (String.format("%4s", binString)).replace(" ", "0");
			data.append(process);
		}
		return data.toString();
	}

	private String hexToASCII(String hexString) {
		StringBuilder data = new StringBuilder();
		String str = "";
		for (int i = 0; i < hexString.length(); i += 2) {
			try {
				str = hexString.substring(i, i + 2);
			} catch (Exception e) {
				str = hexString.substring(i, i + 1);
			} finally {
				data.append((char) Integer.parseInt(str, 16));
			}
		}
		return data.toString();
	}

}
