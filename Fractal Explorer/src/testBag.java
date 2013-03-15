import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSlider;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


@SuppressWarnings("serial")
public class testBag extends JPanel {
	private JTextField itDisplay;
	private JTextField maxI;
	private JTextField minI;
	private JTextField minR;
	private JTextField maxR;
	private final ButtonGroup juliaSelection = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public testBag() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{74, 93, 37, 99, 34, 51, 52, 50, -23, 0};
		gridBagLayout.rowHeights = new int[]{27, 0, 28, 15, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblOptions = new JLabel("Options");
		GridBagConstraints gbc_lblOptions = new GridBagConstraints();
		gbc_lblOptions.gridwidth = 3;
		gbc_lblOptions.insets = new Insets(0, 0, 5, 5);
		gbc_lblOptions.gridx = 5;
		gbc_lblOptions.gridy = 0;
		add(lblOptions, gbc_lblOptions);
		
		JLabel lblNewLabel = new JLabel("Iterations");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JSlider itSlider = new JSlider();
		itSlider.setValue(100);
		itSlider.setMinimum(10);
		itSlider.setMaximum(1000);
		GridBagConstraints gbc_itSlider = new GridBagConstraints();
		gbc_itSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_itSlider.gridwidth = 3;
		gbc_itSlider.insets = new Insets(0, 0, 5, 5);
		gbc_itSlider.gridx = 1;
		gbc_itSlider.gridy = 1;
		add(itSlider, gbc_itSlider);
		
		JToggleButton showAxis = new JToggleButton("Show axis");
		showAxis.setSelected(true);
		GridBagConstraints gbc_showAxis = new GridBagConstraints();
		gbc_showAxis.gridwidth = 3;
		gbc_showAxis.insets = new Insets(0, 0, 5, 5);
		gbc_showAxis.gridx = 5;
		gbc_showAxis.gridy = 1;
		add(showAxis, gbc_showAxis);
		
		itDisplay = new JTextField();
		itDisplay.setText("100");
		itDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		itDisplay.setEditable(false);
		GridBagConstraints gbc_itDisplay = new GridBagConstraints();
		gbc_itDisplay.fill = GridBagConstraints.HORIZONTAL;
		gbc_itDisplay.gridwidth = 3;
		gbc_itDisplay.insets = new Insets(0, 0, 5, 5);
		gbc_itDisplay.gridx = 1;
		gbc_itDisplay.gridy = 2;
		add(itDisplay, gbc_itDisplay);
		itDisplay.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Enable Caching");
		lblNewLabel_2.setToolTipText("Disable only if there are problems displaying the Fractal");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridheight = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 5;
		gbc_lblNewLabel_2.gridy = 2;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JCheckBox cacheCheckBox = new JCheckBox("");
		cacheCheckBox.setToolTipText("Disable only if there are problems displaying the Fractal");
		cacheCheckBox.setSelected(true);
		GridBagConstraints gbc_cacheCheckBox = new GridBagConstraints();
		gbc_cacheCheckBox.gridheight = 2;
		gbc_cacheCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_cacheCheckBox.gridx = 6;
		gbc_cacheCheckBox.gridy = 2;
		add(cacheCheckBox, gbc_cacheCheckBox);
		
		JLabel lblRanges = new JLabel("Ranges");
		GridBagConstraints gbc_lblRanges = new GridBagConstraints();
		gbc_lblRanges.gridwidth = 3;
		gbc_lblRanges.insets = new Insets(0, 0, 5, 5);
		gbc_lblRanges.gridx = 1;
		gbc_lblRanges.gridy = 4;
		add(lblRanges, gbc_lblRanges);
		
		JLabel lblNewLabel_1 = new JLabel("Julia selection");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridheight = 2;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 5;
		gbc_lblNewLabel_1.gridy = 4;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JRadioButton juliaNone = new JRadioButton("None");
		juliaSelection.add(juliaNone);
		GridBagConstraints gbc_juliaNone = new GridBagConstraints();
		gbc_juliaNone.gridheight = 2;
		gbc_juliaNone.insets = new Insets(0, 0, 5, 5);
		gbc_juliaNone.gridx = 6;
		gbc_juliaNone.gridy = 4;
		add(juliaNone, gbc_juliaNone);
		
		JRadioButton juliaStatic = new JRadioButton("Static");
		juliaSelection.add(juliaStatic);
		juliaStatic.setSelected(true);
		GridBagConstraints gbc_juliaStatic = new GridBagConstraints();
		gbc_juliaStatic.anchor = GridBagConstraints.WEST;
		gbc_juliaStatic.insets = new Insets(0, 0, 5, 5);
		gbc_juliaStatic.gridx = 7;
		gbc_juliaStatic.gridy = 4;
		add(juliaStatic, gbc_juliaStatic);
		
		JLabel lblReal = new JLabel("Real");
		GridBagConstraints gbc_lblReal = new GridBagConstraints();
		gbc_lblReal.anchor = GridBagConstraints.EAST;
		gbc_lblReal.insets = new Insets(0, 0, 5, 5);
		gbc_lblReal.gridx = 0;
		gbc_lblReal.gridy = 5;
		add(lblReal, gbc_lblReal);
		
		minR = new JTextField();
		minR.setText("-2.0");
		GridBagConstraints gbc_minR = new GridBagConstraints();
		gbc_minR.insets = new Insets(0, 0, 5, 5);
		gbc_minR.fill = GridBagConstraints.HORIZONTAL;
		gbc_minR.gridx = 1;
		gbc_minR.gridy = 5;
		add(minR, gbc_minR);
		minR.setColumns(10);
		
		JLabel label = new JLabel("-");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 5;
		add(label, gbc_label);
		
		maxR = new JTextField();
		maxR.setText("2.0");
		GridBagConstraints gbc_maxR = new GridBagConstraints();
		gbc_maxR.insets = new Insets(0, 0, 5, 5);
		gbc_maxR.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxR.gridx = 3;
		gbc_maxR.gridy = 5;
		add(maxR, gbc_maxR);
		maxR.setColumns(10);
		
		JRadioButton juliaDynamic = new JRadioButton("Dynamic");
		juliaSelection.add(juliaDynamic);
		GridBagConstraints gbc_juliaDynamic = new GridBagConstraints();
		gbc_juliaDynamic.insets = new Insets(0, 0, 5, 5);
		gbc_juliaDynamic.gridx = 7;
		gbc_juliaDynamic.gridy = 5;
		add(juliaDynamic, gbc_juliaDynamic);
		
		JLabel lblImaginary = new JLabel("Imaginary");
		GridBagConstraints gbc_lblImaginary = new GridBagConstraints();
		gbc_lblImaginary.anchor = GridBagConstraints.EAST;
		gbc_lblImaginary.insets = new Insets(0, 0, 5, 5);
		gbc_lblImaginary.gridx = 0;
		gbc_lblImaginary.gridy = 6;
		add(lblImaginary, gbc_lblImaginary);
		
		minI = new JTextField();
		minI.setText("-1.6");
		GridBagConstraints gbc_minI = new GridBagConstraints();
		gbc_minI.insets = new Insets(0, 0, 5, 5);
		gbc_minI.fill = GridBagConstraints.HORIZONTAL;
		gbc_minI.gridx = 1;
		gbc_minI.gridy = 6;
		add(minI, gbc_minI);
		minI.setColumns(10);
		
		JLabel label_1 = new JLabel("-");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 6;
		add(label_1, gbc_label_1);
		
		maxI = new JTextField();
		maxI.setText("1.6");
		GridBagConstraints gbc_maxI = new GridBagConstraints();
		gbc_maxI.insets = new Insets(0, 0, 5, 5);
		gbc_maxI.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxI.gridx = 3;
		gbc_maxI.gridy = 6;
		add(maxI, gbc_maxI);
		maxI.setColumns(10);
		
		JLabel lblFractalType = new JLabel("Fractal type");
		GridBagConstraints gbc_lblFractalType = new GridBagConstraints();
		gbc_lblFractalType.insets = new Insets(0, 0, 5, 5);
		gbc_lblFractalType.gridx = 5;
		gbc_lblFractalType.gridy = 7;
		add(lblFractalType, gbc_lblFractalType);
		
		JComboBox fractalSelectionBox = new JComboBox();
		fractalSelectionBox.setModel(new DefaultComboBoxModel(new String[] {"Mandelbrot"}));
		GridBagConstraints gbc_fractalSelectionBox = new GridBagConstraints();
		gbc_fractalSelectionBox.gridwidth = 2;
		gbc_fractalSelectionBox.insets = new Insets(0, 0, 5, 5);
		gbc_fractalSelectionBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_fractalSelectionBox.gridx = 6;
		gbc_fractalSelectionBox.gridy = 7;
		add(fractalSelectionBox, gbc_fractalSelectionBox);
		
		JButton resetSet = new JButton("Reset");
		GridBagConstraints gbc_resetSet = new GridBagConstraints();
		gbc_resetSet.fill = GridBagConstraints.HORIZONTAL;
		gbc_resetSet.insets = new Insets(0, 0, 5, 5);
		gbc_resetSet.gridx = 1;
		gbc_resetSet.gridy = 8;
		add(resetSet, gbc_resetSet);
		
		JButton redraw = new JButton("Generate set");
		GridBagConstraints gbc_redraw = new GridBagConstraints();
		gbc_redraw.anchor = GridBagConstraints.WEST;
		gbc_redraw.gridwidth = 2;
		gbc_redraw.insets = new Insets(0, 0, 5, 5);
		gbc_redraw.gridx = 3;
		gbc_redraw.gridy = 8;
		add(redraw, gbc_redraw);
		
		JLabel label_2 = new JLabel(" ");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 9;
		add(label_2, gbc_label_2);

	}

}
