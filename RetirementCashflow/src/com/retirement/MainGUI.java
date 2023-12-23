package com.retirement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTabbedPane tabbedPane;
	private JPanel panPeople = new JPanel();
	private JPanel panAccounts = new JPanel();
	private JPanel panStreams = new JPanel();
	private JPanel panTaxNI = new JPanel();
	private JPanel panTaxParams;
	private JPanel panNIParams;
	private JTextField textField_LowTAXLimit;
	private JTextField textField_highTAXLimit;
	private JTextField textField_lowTAXRate;
	private JTextField textField_highTAXRate;
	private JTextField textField_LowNILimit;
	private JTextField textField_highNILimit;
	private JTextField textField_lowNIRate;
	private JTextField textField_highNIRate;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		JLabel lblNewLabel = new JLabel("New label");
		panel_1.add(lblNewLabel);
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("People", panPeople);
		tabbedPane.addTab("Accounts", panAccounts);
		tabbedPane.addTab("Streams", panStreams);
		tabbedPane.addTab("Tax & NI", panTaxNI);
		panTaxNI.setLayout(new BoxLayout(panTaxNI, BoxLayout.X_AXIS));
		panTaxParams = new JPanel();
		panTaxNI.add(panTaxParams);
		GridLayout gl_panTaxParams = new GridLayout(4,2);
		gl_panTaxParams.setVgap(10);
		panTaxParams.setLayout(gl_panTaxParams);
		lblNewLabel_1 = new JLabel("Low Limit");
		panTaxParams.add(lblNewLabel_1);
		textField_LowTAXLimit = new JTextField();
		panTaxParams.add(textField_LowTAXLimit);
		textField_LowTAXLimit.setColumns(3);
		
		lblNewLabel_2 = new JLabel("High Limit");
		panTaxParams.add(lblNewLabel_2);
		textField_highTAXLimit = new JTextField();
		panTaxParams.add(textField_highTAXLimit);
		textField_highTAXLimit.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Low Rate");
		panTaxParams.add(lblNewLabel_3);
		textField_lowTAXRate = new JTextField();
		panTaxParams.add(textField_lowTAXRate);
		textField_lowTAXRate.setColumns(10);
		
		lblNewLabel_4 = new JLabel("High Rate");
		panTaxParams.add(lblNewLabel_4);
		textField_highTAXRate = new JTextField();
		panTaxParams.add(textField_highTAXRate);
		textField_highTAXRate.setColumns(10);
		panTaxParams
				.setBorder(new TitledBorder(null, "Tax params", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panNIParams = new JPanel();
		panTaxNI.add(panNIParams);
		panNIParams.setLayout(new GridLayout(4, 2, 0, 10));
		
		lblNewLabel_5 = new JLabel("Low Limit");
		panNIParams.add(lblNewLabel_5);
		textField_LowNILimit = new JTextField();
		panNIParams.add(textField_LowNILimit);
		textField_LowNILimit.setColumns(10);
		
		lblNewLabel_6 = new JLabel("High Limit");
		panNIParams.add(lblNewLabel_6);
		textField_highNILimit = new JTextField();
		panNIParams.add(textField_highNILimit);
		textField_highNILimit.setColumns(10);
		
		lblNewLabel_7 = new JLabel("Low Rate");
		panNIParams.add(lblNewLabel_7);
		textField_lowNIRate = new JTextField();
		panNIParams.add(textField_lowNIRate);
		textField_lowNIRate.setColumns(10);
		
		lblNewLabel_8 = new JLabel("High Rate");
		panNIParams.add(lblNewLabel_8);
		textField_highNIRate = new JTextField();
		panNIParams.add(textField_highNIRate);
		textField_highNIRate.setColumns(10);
		panNIParams.setBorder(new TitledBorder(null, "NI params", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

}
