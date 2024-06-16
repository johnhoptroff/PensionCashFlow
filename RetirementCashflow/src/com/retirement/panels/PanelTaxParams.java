package com.retirement.panels;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import javax.swing.SpringLayout;

import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.Locale;

public class PanelTaxParams extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	private JPanel panTaxParams;
	private JPanel panNIParams;
	private JFormattedTextField textField_LowTAXLimit;
	private JFormattedTextField textField_highTAXLimit;
	private JTextField textField_lowTAXRate;
	private JTextField textField_highTAXRate;
	private JFormattedTextField textField_LowNILimit;
	private JFormattedTextField textField_highNILimit;
	private JTextField textField_lowNIRate;
	private JTextField textField_highNIRate;
	private JLabel lblTax_lowLim;
	private JLabel lblTax_highLim;
	private JLabel lblTax_lowRate;
	private JLabel lblTax_highRate;
	private JLabel lblNI_lowLim;
	private JLabel lblNI_hiLim;
	private JLabel lblNI_LowRate;
	private JLabel lblNI_highRate;
	private SpringLayout springLayout = new SpringLayout();
	private SpringLayout sl_panNIParams = new SpringLayout();
	private SpringLayout sl_panTaxParams = new SpringLayout();
	private JButton buttBrowse = new JButton("Browse...");
	private NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
	private NumberFormatter formatter = new NumberFormatter(format);
	
	public PanelTaxParams() {

		formatter.setAllowsInvalid(true);
		formatter.setOverwriteMode(true);
		
		panNIParams = new JPanel();
		panNIParams.setPreferredSize(new Dimension(250, 250));
		this.add(panNIParams);

		panNIParams.setLayout(sl_panNIParams);

		lblNI_lowLim = new JLabel("Low Limit");

		panNIParams.add(lblNI_lowLim);
		textField_LowNILimit = new JFormattedTextField(formatter);
		textField_LowNILimit.setValue(125.0);
		
		panNIParams.add(textField_LowNILimit);
		textField_LowNILimit.setColumns(10);

		lblNI_hiLim = new JLabel("High Limit");

		panNIParams.add(lblNI_hiLim);
		textField_highNILimit = new JFormattedTextField(formatter);
		textField_highNILimit.setValue(280.0);
		
		panNIParams.add(textField_highNILimit);
		textField_highNILimit.setColumns(10);

		lblNI_LowRate = new JLabel("Low Rate");

		panNIParams.add(lblNI_LowRate);
		textField_lowNIRate = new JTextField();

		panNIParams.add(textField_lowNIRate);
		textField_lowNIRate.setColumns(10);

		lblNI_highRate = new JLabel("High Rate");

		panNIParams.add(lblNI_highRate);
		textField_highNIRate = new JTextField();

		panNIParams.add(textField_highNIRate);
		textField_highNIRate.setColumns(10);
		panNIParams.setBorder(new TitledBorder(null, "NI params", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panTaxParams = new JPanel();
		panTaxParams.setPreferredSize(new Dimension(250, 250));
		this.add(panTaxParams);

		panTaxParams.setLayout(sl_panTaxParams);
		lblTax_lowLim = new JLabel("Low Limit");

		panTaxParams.add(lblTax_lowLim);

		//formatter.setMinimum(12500.0);
		//formatter.setMaximum(10000000.0);

		textField_LowTAXLimit = new JFormattedTextField(formatter);
		textField_LowTAXLimit.setValue(12500.0);

		panTaxParams.add(textField_LowTAXLimit);
		textField_LowTAXLimit.setColumns(10);
		textField_LowTAXLimit.setSize(WIDTH, 20);

		lblTax_highLim = new JLabel("High Limit");

		panTaxParams.add(lblTax_highLim);
		textField_highTAXLimit = new JFormattedTextField(formatter);
		textField_highTAXLimit.setValue(50000.0);
		
		panTaxParams.add(textField_highTAXLimit);
		textField_highTAXLimit.setColumns(10);

		lblTax_lowRate = new JLabel("Low Rate");

		panTaxParams.add(lblTax_lowRate);
		textField_lowTAXRate = new JTextField();

		panTaxParams.add(textField_lowTAXRate);
		textField_lowTAXRate.setColumns(10);

		lblTax_highRate = new JLabel("High Rate");

		panTaxParams.add(lblTax_highRate);
		textField_highTAXRate = new JTextField();

		panTaxParams.add(textField_highTAXRate);
		textField_highTAXRate.setColumns(10);
		panTaxParams
				.setBorder(new TitledBorder(null, "Tax params", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setupPanel();
		setupLayout();
	}

	private void setupPanel() {
		this.setPreferredSize(new Dimension(800, 600));
		this.setLayout(springLayout);
		this.add(buttBrowse);
	}

	private void setupLayout() {
// main container panel that contains both Tax panel and NI panel
		springLayout.putConstraint(SpringLayout.NORTH, panTaxParams, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panTaxParams, 10, SpringLayout.WEST, this);

		springLayout.putConstraint(SpringLayout.NORTH, panNIParams, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panNIParams, -10, SpringLayout.EAST, this);

		springLayout.putConstraint(SpringLayout.NORTH, buttBrowse, 18, SpringLayout.SOUTH, panTaxParams);
		springLayout.putConstraint(SpringLayout.WEST, buttBrowse, 234, SpringLayout.WEST, panTaxParams);

// tax panel
		sl_panTaxParams.putConstraint(SpringLayout.NORTH, lblTax_lowLim, 15, SpringLayout.NORTH, panTaxParams);
		sl_panTaxParams.putConstraint(SpringLayout.WEST, lblTax_lowLim, 6, SpringLayout.WEST, panTaxParams);

		sl_panTaxParams.putConstraint(SpringLayout.NORTH, textField_LowTAXLimit, 0, SpringLayout.NORTH, lblTax_lowLim);
		sl_panTaxParams.putConstraint(SpringLayout.WEST, textField_LowTAXLimit, 10, SpringLayout.EAST, lblTax_lowLim);

		sl_panTaxParams.putConstraint(SpringLayout.NORTH, lblTax_highLim, 45, SpringLayout.NORTH, panTaxParams);
		sl_panTaxParams.putConstraint(SpringLayout.WEST, lblTax_highLim, 6, SpringLayout.WEST, panTaxParams);

		sl_panTaxParams.putConstraint(SpringLayout.NORTH, textField_highTAXLimit, 0, SpringLayout.NORTH,
				lblTax_highLim);
		sl_panTaxParams.putConstraint(SpringLayout.WEST, textField_highTAXLimit, 10, SpringLayout.EAST, lblTax_lowLim);

		sl_panTaxParams.putConstraint(SpringLayout.NORTH, lblTax_lowRate, 75, SpringLayout.NORTH, panTaxParams);
		sl_panTaxParams.putConstraint(SpringLayout.WEST, lblTax_lowRate, 6, SpringLayout.WEST, panTaxParams);

		sl_panTaxParams.putConstraint(SpringLayout.NORTH, textField_lowTAXRate, 0, SpringLayout.NORTH, lblTax_lowRate);
		sl_panTaxParams.putConstraint(SpringLayout.WEST, textField_lowTAXRate, 10, SpringLayout.EAST, lblTax_lowLim);

		sl_panTaxParams.putConstraint(SpringLayout.NORTH, lblTax_highRate, 105, SpringLayout.NORTH, panTaxParams);
		sl_panTaxParams.putConstraint(SpringLayout.WEST, lblTax_highRate, 6, SpringLayout.WEST, panTaxParams);

		sl_panTaxParams.putConstraint(SpringLayout.NORTH, textField_highTAXRate, 0, SpringLayout.NORTH,
				lblTax_highRate);
		sl_panTaxParams.putConstraint(SpringLayout.WEST, textField_highTAXRate, 10, SpringLayout.EAST, lblTax_lowLim);

// NI panel		
		sl_panNIParams.putConstraint(SpringLayout.NORTH, lblNI_lowLim, 15, SpringLayout.NORTH, panNIParams);
		sl_panNIParams.putConstraint(SpringLayout.WEST, lblNI_lowLim, 6, SpringLayout.WEST, panNIParams);

		sl_panNIParams.putConstraint(SpringLayout.NORTH, textField_LowNILimit, 0, SpringLayout.NORTH, lblNI_lowLim);
		sl_panNIParams.putConstraint(SpringLayout.WEST, textField_LowNILimit, 10, SpringLayout.EAST, lblNI_lowLim);

		sl_panNIParams.putConstraint(SpringLayout.NORTH, lblNI_hiLim, 45, SpringLayout.NORTH, panNIParams);
		sl_panNIParams.putConstraint(SpringLayout.WEST, lblNI_hiLim, 6, SpringLayout.WEST, panNIParams);

		sl_panNIParams.putConstraint(SpringLayout.NORTH, textField_highNILimit, 0, SpringLayout.NORTH, lblNI_hiLim);
		sl_panNIParams.putConstraint(SpringLayout.WEST, textField_highNILimit, 10, SpringLayout.EAST, lblNI_lowLim);

		sl_panNIParams.putConstraint(SpringLayout.NORTH, lblNI_LowRate, 75, SpringLayout.NORTH, panNIParams);
		sl_panNIParams.putConstraint(SpringLayout.WEST, lblNI_LowRate, 0, SpringLayout.WEST, lblNI_lowLim);

		sl_panNIParams.putConstraint(SpringLayout.NORTH, textField_lowNIRate, 0, SpringLayout.NORTH, lblNI_LowRate);
		sl_panNIParams.putConstraint(SpringLayout.WEST, textField_lowNIRate, 10, SpringLayout.EAST, lblNI_lowLim);

		sl_panNIParams.putConstraint(SpringLayout.NORTH, lblNI_highRate, 105, SpringLayout.NORTH, panNIParams);
		sl_panNIParams.putConstraint(SpringLayout.WEST, lblNI_highRate, 0, SpringLayout.WEST, lblNI_lowLim);

		sl_panNIParams.putConstraint(SpringLayout.NORTH, textField_highNIRate, 0, SpringLayout.NORTH, lblNI_highRate);
		sl_panNIParams.putConstraint(SpringLayout.WEST, textField_highNIRate, 10, SpringLayout.EAST, lblNI_lowLim);

	}
}
