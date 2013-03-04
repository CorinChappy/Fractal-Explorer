import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


// Control panel to display the controls bellow the mandelbrot set
@SuppressWarnings("serial")
class ControlPanel extends JPanel{

	Fractal p;

	// The text fields
	private JTextField itDisplay;
	private JTextField minR;
	private JTextField maxR;
	private JTextField minI;
	private JTextField maxI;
	private JSlider itSlider;
	private final ButtonGroup juliaSelection = new ButtonGroup();


	public ControlPanel(Fractal f) {
		this.p = f;


		/* 
		 * Create the control panel
		 *      - Control panel generated with Eclipse visual class builder
		 */
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
		
		itSlider = new JSlider();
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
		
		JLabel lblNewLabel_1 = new JLabel("Julia selection");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridheight = 2;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 5;
		gbc_lblNewLabel_1.gridy = 2;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JRadioButton juliaNone = new JRadioButton("None");
		juliaSelection.add(juliaNone);
		GridBagConstraints gbc_juliaNone = new GridBagConstraints();
		gbc_juliaNone.gridheight = 2;
		gbc_juliaNone.insets = new Insets(0, 0, 5, 5);
		gbc_juliaNone.gridx = 6;
		gbc_juliaNone.gridy = 2;
		add(juliaNone, gbc_juliaNone);
		
		JRadioButton juliaStatic = new JRadioButton("Static");
		juliaSelection.add(juliaStatic);
		juliaStatic.setSelected(true);
		GridBagConstraints gbc_juliaStatic = new GridBagConstraints();
		gbc_juliaStatic.anchor = GridBagConstraints.WEST;
		gbc_juliaStatic.insets = new Insets(0, 0, 5, 5);
		gbc_juliaStatic.gridx = 7;
		gbc_juliaStatic.gridy = 2;
		add(juliaStatic, gbc_juliaStatic);
		
		JRadioButton juliaDynamic = new JRadioButton("Dynamic");
		juliaSelection.add(juliaDynamic);
		GridBagConstraints gbc_juliaDynamic = new GridBagConstraints();
		gbc_juliaDynamic.insets = new Insets(0, 0, 5, 5);
		gbc_juliaDynamic.gridx = 7;
		gbc_juliaDynamic.gridy = 3;
		add(juliaDynamic, gbc_juliaDynamic);
		
		JLabel lblRanges = new JLabel("Ranges");
		GridBagConstraints gbc_lblRanges = new GridBagConstraints();
		gbc_lblRanges.gridwidth = 3;
		gbc_lblRanges.insets = new Insets(0, 0, 5, 5);
		gbc_lblRanges.gridx = 1;
		gbc_lblRanges.gridy = 4;
		add(lblRanges, gbc_lblRanges);
		
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


		// Set the default values
		updateValues();


		// Create the event listeners

		// Toggle the axis display
		showAxis.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					p.displayAxis(true);
				}else{
					p.displayAxis(false);
				}
			}
		});


		// Displaying the value of the slider in the text box
		itSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				itDisplay.setText(""+((JSlider) e.getSource()).getValue()+"");
			}
		});

		// Change the settings and redraw the set
		redraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Set the iterations
				p.setMaxIterations(itSlider.getValue());
				// Set the bounds
				try{
					p.setRealRange(Double.parseDouble(minR.getText()), Double.parseDouble(maxR.getText()));
				}catch(NumberFormatException e){}
				try{
					p.setImaginaryRange(Double.parseDouble(minI.getText()), Double.parseDouble(maxI.getText()));
				}catch(NumberFormatException e){}


				// Repaint the set
				p.repaint();
			}
		});
		
		// Reset to default values
		resetSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.setMaxIterations(100);
				p.setRealRange(-2.0,2.0);
				p.setImaginaryRange(-1.6,1.6);
				p.repaint();
				updateValues();
			}
		});
		
		if(p.getClass().equals(new Mandelbrot().getClass())){
	
			// Julia selection type settings
			class JuliaSelectionListener implements ItemListener{
				// What button is this? 0 = none, 1 = static, 2=dynamic
				private int what;
				
				public JuliaSelectionListener(int w){
					what = w;
				}
	
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED){
						((Mandelbrot) p).changeJuliaSelection(what);
					}
				}
			}
			// Add the ItemListeners
			juliaNone.addItemListener(new JuliaSelectionListener(0));
			juliaStatic.addItemListener(new JuliaSelectionListener(1));
			juliaDynamic.addItemListener(new JuliaSelectionListener(2));
			
		}

	}

	public void updateValues(){
		itSlider.setValue(p.getMaxIterations());
		itDisplay.setText(""+p.getMaxIterations()+"");
		minR.setText(""+p.getMinReal()+"");
		maxR.setText(""+p.getMaxReal()+"");
		minI.setText(""+p.getMinImaginary()+"");
		maxI.setText(""+p.getMaxImaginary()+"");
	}


}