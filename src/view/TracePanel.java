package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TracePanel extends JPanel {

	private JTextArea textArea;
	// private PrintStream standardOut;

	public TracePanel() {
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(getBackground());
		textArea.setForeground(Color.BLACK);
		textArea.setTabSize(4);
		textArea.setLineWrap(true);
		textArea.setRows(2);
		textArea.setFont(new Font("Segoe UI", Font.BOLD, 14));
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));

		// keeps reference of standard output stream
		// standardOut = System.out;

		// re-assigns standard output stream and error output stream
		System.setOut(printStream);
		// System.setErr(printStream);

		// creates the GUI
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		add(new JScrollPane(textArea), constraints);
		setVisible(true);
		setBackground(Color.WHITE);
	}

	public class CustomOutputStream extends OutputStream {
		private JTextArea textArea;

		public CustomOutputStream(JTextArea textArea) {
			this.textArea = textArea;
		}

		@Override
		public void write(int b) throws IOException {
			// redirects data to the text area
			textArea.append(String.valueOf((char) b));
			// scrolls the text area to the end of data
			textArea.setCaretPosition(textArea.getDocument().getLength() - 1);
		}
	}
}