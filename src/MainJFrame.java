import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.pdfbox.pdmodel.PDDocument;

import tools.CombinationUnlocker;
import tools.FileOperator;

public class MainJFrame {

	private JFrame frame;
	private File selectedFile;
	private JLabel fileNameLabel;
	private JLabel filePathLabel;
	private JLabel fileSizeLabel;
	private JTextField setPasswordTextField;
	private JTextField removePasswordText;
	private JTextField lowerLimitTextField;
	private JTextField upperLimitTextField;
	private Boolean lockStatus;
	private JLabel statusValueLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame window = new MainJFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainJFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 737, 537);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 39, 729, 64);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("File Name");
		lblNewLabel.setBounds(12, 12, 70, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("File Path");
		lblNewLabel_1.setBounds(12, 39, 70, 15);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("File Size");
		lblNewLabel_2.setBounds(311, 12, 70, 15);
		panel.add(lblNewLabel_2);
		
		fileNameLabel = new JLabel("");
		fileNameLabel.setBounds(94, 12, 205, 15);
		panel.add(fileNameLabel);
		
		filePathLabel = new JLabel("");
		filePathLabel.setBounds(83, 39, 634, 15);
		panel.add(filePathLabel);
		
		fileSizeLabel = new JLabel("");
		fileSizeLabel.setBounds(378, 12, 161, 15);
		panel.add(fileSizeLabel);
		
		JLabel statusLabel = new JLabel("Status :");
		statusLabel.setBounds(551, 12, 56, 15);
		panel.add(statusLabel);
		
		statusValueLabel = new JLabel("");
		statusValueLabel.setBounds(612, 12, 70, 15);
		panel.add(statusValueLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 109, 729, 394);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 729, 394);
		panel_1.add(tabbedPane);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Lock", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Enter password");
		lblNewLabel_3.setBounds(21, 26, 126, 28);
		panel_3.add(lblNewLabel_3);
		
		setPasswordTextField = new JTextField();
		setPasswordTextField.setBounds(151, 31, 174, 19);
		panel_3.add(setPasswordTextField);
		setPasswordTextField.setColumns(10);
		
		JButton setPasswordBtn = new JButton("Set Password");
		setPasswordBtn.setBounds(21, 66, 304, 25);
		panel_3.add(setPasswordBtn);
		setPasswordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = setPasswordTextField.getText();
				if (password.isEmpty()) {
					return;
				}
				FileOperator.lockFile(selectedFile, password);
				setPasswordTextField.setText("");
				lockStatus = false;
				extractFileMetadata();
			}
		});
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Unlock", null, panel_4, null);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Enter password");
		lblNewLabel_4.setBounds(23, 24, 124, 25);
		panel_4.add(lblNewLabel_4);
		
		removePasswordText = new JTextField();
		removePasswordText.setBounds(159, 27, 218, 19);
		panel_4.add(removePasswordText);
		removePasswordText.setColumns(10);
		
		JButton removePasswordBtn = new JButton("Remove Password");
		removePasswordBtn.setBounds(23, 56, 354, 25);
		panel_4.add(removePasswordBtn);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Bruteforce", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Password Lower Limit");
		lblNewLabel_5.setBounds(12, 12, 176, 15);
		panel_5.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Password Upper Limit");
		lblNewLabel_6.setBounds(12, 39, 176, 15);
		panel_5.add(lblNewLabel_6);
		
		lowerLimitTextField = new JTextField();
		lowerLimitTextField.setBounds(186, 10, 114, 19);
		panel_5.add(lowerLimitTextField);
		lowerLimitTextField.setColumns(10);
		
		upperLimitTextField = new JTextField();
		upperLimitTextField.setBounds(186, 37, 114, 19);
		panel_5.add(upperLimitTextField);
		upperLimitTextField.setColumns(10);
		
		JLabel tryingTextField = new JLabel("New label");
		tryingTextField.setBounds(12, 94, 700, 15);
		panel_5.add(tryingTextField);
		
		JButton startBruteForceBtn = new JButton("Start Bruteforce");
		startBruteForceBtn.setBounds(12, 121, 176, 25);
		panel_5.add(startBruteForceBtn);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Wordlist", null, panel_6, null);
		panel_6.setLayout(null);
		
		JButton btnPickWordlist = new JButton("Select Wordlist");
		btnPickWordlist.setBounds(12, 12, 180, 25);
		panel_6.add(btnPickWordlist);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setBounds(12, 49, 658, 15);
		panel_6.add(lblNewLabel_7);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_2.setBounds(0, 0, 729, 35);
		frame.getContentPane().add(panel_2);
		
		JButton fileChooserButton = new JButton("Choose File");
		fileChooserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					System.out.println("Selected file: " + fileChooser.getSelectedFile().getAbsolutePath());
					selectedFile = fileChooser.getSelectedFile();
					extractFileMetadata();
				}
			}
		});
		removePasswordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = removePasswordText.getText();
				if (password.isEmpty()) {
					return;
				}
				FileOperator.unlockFile(selectedFile, password);
				removePasswordText.setText("");
				lockStatus = true;
				extractFileMetadata();
			}
		});
		startBruteForceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int lowerLimit = Integer.parseInt(lowerLimitTextField.getText());
				int upperLimit = Integer.parseInt(upperLimitTextField.getText());
				CombinationUnlocker cu = new CombinationUnlocker(selectedFile, lowerLimit, upperLimit, tryingTextField);
				cu.unlock();
			}
		});
		btnPickWordlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					System.out.println("Selected file: " + fileChooser.getSelectedFile().getAbsolutePath());
					File wordlistFile = fileChooser.getSelectedFile();
					if (wordlistFile.exists() && !wordlistFile.isDirectory() && wordlistFile.getAbsolutePath().endsWith(".txt")) {
						CombinationUnlocker cu = new CombinationUnlocker(selectedFile, wordlistFile, lblNewLabel_7);
						cu.unlock();
					}
				}
			}
		});
		panel_2.add(fileChooserButton);
	}


	void extractFileMetadata() {
		if (selectedFile == null) {
			return;
		}
		fileNameLabel.setText("File name : " + selectedFile.getName());
		filePathLabel.setText("File path : " + selectedFile.getAbsolutePath());
		fileSizeLabel.setText("File size : " + selectedFile.length() + " bytes");
		try {
			PDDocument document = PDDocument.load(selectedFile);
			lockStatus = true;
			statusValueLabel.setText("Unlocked");
		}catch (Exception e) {
			System.out.println("File is encrypted!");
			lockStatus = false;
			statusValueLabel.setText("Locked");
		}
	}
}
