package com.retirement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.retirement.panels.PanelAccounts;
import com.retirement.panels.PanelPeople;
import com.retirement.panels.PanelTaxParams;

import javax.swing.JTabbedPane;
import javax.swing.JTable;


public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTabbedPane tabbedPane;
	private JPanel panPeople; // in a separate class
	private JPanel panAccounts; // in a separate class
	private JPanel panStreams; // in a separate class
	private JPanel panTaxNI; // in a separate class


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
		initialize();
		createEvents();
	}
	private void createEvents() {
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
	}

	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 550);
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
		panPeople = new PanelPeople();
		tabbedPane.add("People", panPeople);
		
		panAccounts = new PanelAccounts();
		tabbedPane.addTab("Accounts", panAccounts);
		

		tabbedPane.addTab("Streams", panStreams);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		panTaxNI = new PanelTaxParams();
		tabbedPane.addTab("Tax & NI", panTaxNI);
		//panTaxNI.setLayout(new SpringLayout());
	}

}
