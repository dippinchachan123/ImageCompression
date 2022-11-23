package model;

import java.io.File;

import javax.swing.JFileChooser;

public class FilePicker {
	private File file;

	public void chooseFile() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(fc);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
			file = null;
		}
	}

	public void chooseFile(String location) {
		file = new File(location);

		if (!file.isFile()) {
			file = null;
			chooseFile();
		}
	}

	public File getFile() {
		return file;
	}
}
