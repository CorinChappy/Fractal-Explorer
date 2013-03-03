import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import net.miginfocom.swing.MigLayout;


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
		this.setPreferredSize(new Dimension(525, 600));
		
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
		
		public ControlPanel(Mandelbrot m){
			p = m;
			
			/* 
			 * Create the control panel
			 *      - Control panel generated with Eclipse visual class builder
			*/
			setLayout(new MigLayout("", "[][grow][][][][][]", "[][][][][][][][]"));
			
			add(new JLabel("Settings"), "flowx,cell 3 0 4 1,alignx center");

			add(new JLabel("Iterations"), "cell 0 1");
			
			JSlider itSlider = new JSlider();
			itSlider.setMaximum(1000);
			itSlider.setMinimum(10);
			add(itSlider, "cell 1 1");
			
			add(new JLabel(""), "cell 2 1");
			
			JToggleButton showAxis = new JToggleButton("Show axis");
			showAxis.setSelected(true);
			add(showAxis, "cell 4 1 2 1");
			
			itDisplay = new JTextField();
			itDisplay.setHorizontalAlignment(SwingConstants.CENTER);
			itDisplay.setEditable(false);
			add(itDisplay, "cell 1 2,alignx left,aligny center");
			itDisplay.setColumns(10);
			
			
			add(new JLabel("Ranges"), "cell 0 3 2 1,alignx center,aligny center");
			
			add(new JLabel("Real"), "cell 0 4,alignx trailing");
			
			minR = new JTextField();
			minR.setText("-2.0");
			add(minR, "flowx,cell 1 4,alignx left");
			minR.setColumns(10);
			
			add(new JLabel(" - "), "cell 1 4");
			
			maxR = new JTextField();
			maxR.setText("2.0");
			add(maxR, "cell 1 4,alignx right");
			maxR.setColumns(10);
			
			add(new JLabel("Imaginary"), "cell 0 5,alignx trailing");
			
			minI = new JTextField();
			minI.setText("-1.6");
			add(minI, "flowx,cell 1 5,alignx left,aligny center");
			minI.setColumns(10);
			
			add(new JLabel(" - "), "cell 1 5");
			
			maxI = new JTextField();
			maxI.setText("1.6");
			add(maxI, "cell 1 5");
			maxI.setColumns(10);
			
			JButton redraw = new JButton("Redraw set");
			add(redraw, "cell 1 7,growx");
			
			
			// Set the default values
			itSlider.setValue(p.getMaxIterations());
			itDisplay.setText(""+p.getMaxIterations()+"");
			
			
			
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
