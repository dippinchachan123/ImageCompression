import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;
import view.GUI;

public class Controller {

	private GUI theView;
	private Model theModel;

	public Controller(GUI gui, Model model, String... args) {
		this.theView = gui;
		this.theModel = model;

		ProcessListener pl = new ProcessListener();
		LoadListener ll = new LoadListener();

		theView.getBtnTrain().addActionListener(pl);
		theView.getCBHuffBase().addActionListener(pl);
		theView.getBtnCompress().addActionListener(pl);

		theView.getBtnOpen().addActionListener(ll);
		theView.getFieldFile().addActionListener(ll);

		theView.setHuffList(theModel.getHFM().getHuffVersions());
		theView.repaint();

		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				theView.getFieldFile().setText(args[i]);
				if (args[i].endsWith(".hff"))
					theModel.readHFF(args[i]);
			}
		}
	}

	private class LoadListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (theView.getFieldFile().getText().isEmpty())
				theModel.getFP().chooseFile();
			else
				theModel.getFP().chooseFile(theView.getFieldFile().getText());

			theModel.getHFM().initiate();
			theView.setHuffList(theModel.getHFM().getHuffVersions());

			try {
				theModel.setIA(theModel.getFP().getFile().toString());
				theView.getFieldFile().setForeground(Color.BLUE);
				theView.getFieldFile().setText(theModel.getFP().getFile().toString());

				if (theModel.getIA().isSuccess() == false)
					throw new NullPointerException();

				if (theModel.getIA().getName().endsWith(".hff")) {
					theView.getBtnTrain().setEnabled(false);
					theView.getBtnCompress().setEnabled(false);
					theView.getCBRender().setSelected(true);
					theView.getCBRender().setEnabled(false);
					theModel.readHFF(theModel.getIA().getPath());
				} else {
					theView.getCBRender().setEnabled(true);
					theView.getBtnCompress().setEnabled(true);
					theView.getBtnTrain().setEnabled(true);
					if (theView.getCBRender().isSelected()) {
						new view.ImageFrame().render(theModel.getIA().getChunks(), theModel.getIA().getName());
					}
				}
			} catch (NullPointerException arg) {
				theView.getFieldFile().setForeground(Color.GRAY);
				theModel.setIA(null);
				theView.getBtnCompress().setEnabled(false);
				theView.getBtnTrain().setEnabled(false);
			}
		}
	}

	private class ProcessListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == theView.getCBHuffBase()) {
				int huffBase = theView.getCBHuffBase().getSelectedIndex() - 1;
				String date = theModel.getHFM().getDate(huffBase);
				theView.getFieldBaseVer().setText(date);
				theModel.getHFM().setSelectedIndex(huffBase);

				try {
					if (!theModel.getIA().isSuccess() || huffBase <= -1 || theModel.getIA().getName().endsWith(".hff"))
						theView.getBtnCompress().setEnabled(false);
					else
						theView.getBtnCompress().setEnabled(true);
				} catch (NullPointerException npe) {
					theView.getBtnCompress().setEnabled(false);
				}
			} else if (e.getSource() == theView.getBtnTrain()) {
				int selectedHuff = theView.getCBHuffBase().getSelectedIndex();
				if (selectedHuff == 0)
					theModel.getHFM().createVersion(theModel.getIA().getFreq());
				else
					theModel.getHFM().modifyVersion(theModel.getIA().getFreq());
				theModel.getHFM().initiate();
				theView.setHuffList(theModel.getHFM().getHuffVersions());
			} else if (e.getSource() == theView.getBtnCompress()) {
				theModel.setHB(theModel.getHFM().getSelectedData(), false);
				String outputAddress = theModel.getFIO().encodeToFile(theModel.getIA().getChunks(),
						theModel.getHFM().getSelectedFile().getName().split(".huff")[0]);
				if (theView.getCBOutput().isSelected() && !outputAddress.equals(""))
					theModel.readHFF(outputAddress);
			}
		}
	}
}
