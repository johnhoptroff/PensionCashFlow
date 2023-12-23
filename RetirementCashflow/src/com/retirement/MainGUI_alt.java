package com.retirement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class MainGUI_alt {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI_alt window = new MainGUI_alt();
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
	public MainGUI_alt() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	    JTextArea ta=new JTextArea(200,200);  
	    JPanel panePeople=new JPanel();  
	    panePeople.add(ta);  
	    JPanel paneTax=new JPanel();  
	    JPanel paneStreams=new JPanel();  
	    JPanel paneAccounts=new JPanel(); 
	    JTabbedPane tp=new JTabbedPane(JTabbedPane.TOP);  
	    tp.setBounds(50,50,600,200);  
	    tp.add("People",panePeople);  
	    tp.add("Tax & NI",paneTax);
	    paneTax.setLayout(new GridLayout(0, 4, 0, 0));
	    tp.add("Streams",paneStreams);  
	    tp.add("Accounts",paneAccounts); 
	    frame.getContentPane().add(tp);  
	    frame.setSize(800,400);  
	    frame.getContentPane().setLayout(null);  
	    frame.setVisible(true);  
		frame.getContentPane().add(tp, BorderLayout.CENTER);
		
		textField_1 = new JTextField();
		textField_1.setBounds(199, 277, 148, 28);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField = new JTextField();
		textField.setBounds(513, 277, 121, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("High Threashold");
		lblNewLabel_1.setBounds(67, 274, 148, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Low Limit");
		lblNewLabel.setBounds(409, 280, 148, 22);
		frame.getContentPane().add(lblNewLabel);
	}
}
