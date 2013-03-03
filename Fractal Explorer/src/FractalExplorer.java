import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;


@SuppressWarnings("serial")
public class FractalExplorer extends JFrame{

	public static void main(String[] args) {
		new FractalExplorer().createMandelbrot();
	}


	public FractalExplorer(){
		super("Fractal Explorer");
		this.setPreferredSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.setVisible(true);
	}

	public void createMandelbrot(){	
		this.setPreferredSize(new Dimension(585, 655));
		this.setMinimumSize(new Dimension(535, 575));

		// Create and add the display panel
		Mandelbrot m = new Mandelbrot();
		this.add(m, BorderLayout.CENTER);


		// Create and add the user selected point panel
		JPanel usp = new JPanel(new GridLayout(1,1));
		JTextArea uspT = new JTextArea("Select a point to see it's complex number");
		usp.add(uspT);
		this.add(usp,BorderLayout.NORTH);

		// Add the Mandelbrot listener 
		m.addMouseListener(new MClicker(m,uspT));

		// Create and add the editing panel
		JPanel controls = new ControlPanel(m);
		this.add(controls,BorderLayout.SOUTH);


		m.repaint();
		this.repaint();
		this.pack();
	}



	// Control panel to display the controls bellow the mandelbrot set
	private class ControlPanel extends JPanel{

		Mandelbrot p;

		// The text fields
		private JTextField itDisplay;
		private JTextField minR;
		private JTextField maxR;
		private JTextField minI;
		private JTextField maxI;
		private JSlider itSlider;


		public ControlPanel(Mandelbrot m) {
			this.p = m;


			/* 
			 * Create the control panel
			 *      - Control panel generated with Eclipse visual class builder
			 */
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{74, 93, 37, 99, 34, 51, 136, 37, 0};
			gridBagLayout.rowHeights = new int[]{27, 0, 28, 15, 0, 0, 0, 0, 0, 0, 0};
			gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			setLayout(gridBagLayout);

			JLabel lblOptions = new JLabel("Options");
			GridBagConstraints gbc_lblOptions = new GridBagConstraints();
			gbc_lblOptions.gridwidth = 2;
			gbc_lblOptions.insets = new Insets(0, 0, 5, 0);
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
			gbc_showAxis.insets = new Insets(0, 0, 5, 5);
			gbc_showAxis.gridx = 6;
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

			JButton redraw = new JButton("Readraw set");
			GridBagConstraints gbc_redraw = new GridBagConstraints();
			gbc_redraw.fill = GridBagConstraints.HORIZONTAL;
			gbc_redraw.gridwidth = 3;
			gbc_redraw.insets = new Insets(0, 0, 5, 5);
			gbc_redraw.gridx = 1;
			gbc_redraw.gridy = 8;
			add(redraw, gbc_redraw);

			JLabel label_2 = new JLabel(" ");
			GridBagConstraints gbc_label_2 = new GridBagConstraints();
			gbc_label_2.insets = new Insets(0, 0, 0, 5);
			gbc_label_2.gridx = 0;
			gbc_label_2.gridy = 9;
			add(label_2, gbc_label_2);

			
			
			
			
			// Set the default values
			itSlider.setValue(p.getMaxIterations());
			itDisplay.setText(""+p.getMaxIterations()+"");
			minR.setText(""+p.getMinReal()+"");
			maxR.setText(""+p.getMaxReal()+"");
			minI.setText(""+p.getMinImaginary()+"");
			maxI.setText(""+p.getMaxImaginary()+"");
			
			
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

		}
		
		


	}




	// Mouse listener for showing the Julia set and complex number (not implemented)
	private class MClicker extends MouseAdapter{
		private Mandelbrot m;
		JTextComponent t;

		public MClicker(Mandelbrot m, JTextComponent t){
			this.m = m;
			this.t = t;
		}

		public void mouseClicked(MouseEvent e){
			ComplexNumber c = m.getComplex(e.getPoint().x, e.getPoint().y);
			t.setText("Complex number: "+c.getReal()+" + "+c.getImaginary()+"i");
		}


	}

}
