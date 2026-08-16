package com.retirement.panels;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JButton;

public class PanelPeople extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelPeople() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnNewButton = new JButton("Add Person");
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 178, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, -76, SpringLayout.SOUTH, this);
		add(btnNewButton);

	}
}
