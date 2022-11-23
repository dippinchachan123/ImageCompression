package view;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private JPanel contentPane;
	private JButton btnCompress, btnTrain, btnOpen;
	private JTextField fieldFile, fieldBaseVer;
	private JCheckBox cbOutput, cbRender;
	private JComboBox<File> cbHuffBase;
	private Font fontBasicLbl = new Font("Segoe UI", Font.BOLD, 15);
	private Font fontBasicBtn = new Font("Tahoma", Font.BOLD, 18);
	private JSeparator separator_1, separator_2;

	public GUI() {
		// JFrame
		setTitle("Image Compression using Huffman Coding");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(631, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		// setting contentPane
		contentPane = new JPanel(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		TracePanel tracePanel = new TracePanel();
		tracePanel.setBounds(15, 299, 588, 60);
		contentPane.add(tracePanel);

		JLabel lblHeading = new JLabel("Grayscale PNG Image Compression");
		lblHeading.setBounds(109, 7, 382, 31);
		contentPane.add(lblHeading);
		lblHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeading.setFont(new Font("Candara", Font.BOLD, 25));

		JLabel lblFile = new JLabel("FILE:");
		lblFile.setBounds(15, 50, 33, 21);
		lblFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblFile.setForeground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		lblFile.setFont(fontBasicLbl);
		contentPane.add(lblFile);

		fieldFile = new JTextField();
		fieldFile.setBounds(73, 50, 433, 25);
		fieldFile.setToolTipText("");
		fieldFile.setFont(fontBasicLbl);
		fieldFile.setColumns(10);
		fieldFile.setBackground(Color.WHITE);
		contentPane.add(fieldFile);

		btnOpen = new JButton("OPEN");
		btnOpen.setBounds(518, 44, 85, 32);
		btnOpen.setForeground(new Color(0, 102, 255));
		btnOpen.setFont(fontBasicBtn);
		btnOpen.setFocusable(false);
		btnOpen.setBackground(new Color(255, 255, 204));
		contentPane.add(btnOpen);

		cbRender = new JCheckBox("RENDER");
		cbRender.setSelected(true);
		cbRender.setForeground(Color.BLACK);
		cbRender.setFont(new Font("Tahoma", Font.BOLD, 10));
		cbRender.setFocusable(false);
		cbRender.setBackground(UIManager.getColor("Button.background"));
		cbRender.setBounds(518, 76, 85, 21);
		contentPane.add(cbRender);

		JSeparator separator = new JSeparator();
		separator.setBounds(20, 104, 580, 2);
		contentPane.add(separator);
		JLabel lblHuffBase = new JLabel("BASE VERSION:");
		lblHuffBase.setBounds(30, 118, 109, 21);
		contentPane.add(lblHuffBase);
		lblHuffBase.setHorizontalAlignment(SwingConstants.LEFT);
		lblHuffBase.setForeground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		lblHuffBase.setFont(fontBasicLbl);

		cbHuffBase = new JComboBox<File>();
		cbHuffBase.setBounds(189, 118, 360, 25);
		contentPane.add(cbHuffBase);

		JLabel lblBaseDate = new JLabel("DATE:");
		lblBaseDate.setBounds(30, 155, 109, 21);
		contentPane.add(lblBaseDate);
		lblBaseDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblBaseDate.setForeground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		lblBaseDate.setFont(new Font("Segoe UI", Font.BOLD, 15));

		fieldBaseVer = new JTextField();
		fieldBaseVer.setBounds(189, 155, 360, 25);
		contentPane.add(fieldBaseVer);
		fieldBaseVer.setFont(new Font("Segoe UI", Font.BOLD, 15));
		fieldBaseVer.setEditable(false);
		fieldBaseVer.setColumns(10);
		fieldBaseVer.setBackground(Color.WHITE);

		separator_1 = new JSeparator();
		separator_1.setBounds(20, 188, 580, 2);
		contentPane.add(separator_1);

		btnTrain = new JButton("TRAIN");
		btnTrain.setBounds(68, 202, 130, 40);
		contentPane.add(btnTrain);
		btnTrain.setForeground(new Color(0, 102, 255));
		btnTrain.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnTrain.setFocusable(false);
		btnTrain.setEnabled(false);
		btnTrain.setBackground(new Color(255, 255, 204));
		btnCompress = new JButton("COMPRESS");

		separator_2 = new JSeparator();
		separator_2.setBounds(15, 285, 588, 2);
		contentPane.add(separator_2);
		btnCompress.setBounds(401, 202, 130, 40);
		contentPane.add(btnCompress);
		btnCompress.setForeground(new Color(0, 102, 255));
		btnCompress.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnCompress.setFocusable(false);
		btnCompress.setBackground(new Color(255, 255, 204));
		btnCompress.setEnabled(false);

		cbOutput = new JCheckBox("SHOW OUTPUT");
		cbOutput.setForeground(Color.BLACK);
		cbOutput.setFont(new Font("Tahoma", Font.BOLD, 10));
		cbOutput.setBounds(411, 246, 109, 31);
		cbOutput.setSelected(true);
		contentPane.add(cbOutput);
	}

	public JButton getBtnCompress() {
		return btnCompress;
	}

	public JButton getBtnTrain() {
		return btnTrain;
	}

	public JButton getBtnOpen() {
		return btnOpen;
	}

	public JTextField getFieldFile() {
		return fieldFile;
	}

	public JTextField getFieldBaseVer() {
		return fieldBaseVer;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setHuffList(File[] list) {
		cbHuffBase.removeAllItems();
		cbHuffBase.setModel(new DefaultComboBoxModel(new String[] { "<Create New File>" }));

		for (int i = 0; i < list.length; i++) {
			cbHuffBase.addItem(list[i]);
		}
	}

	public JComboBox<File> getCBHuffBase() {
		return cbHuffBase;
	}

	public JCheckBox getCBOutput() {
		return cbOutput;
	}

	public JCheckBox getCBRender() {
		return cbRender;
	}
}
