import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class FractalExplorer extends JFrame{

	// SOLUTION MUST BE CHANGED
	JTextArea uspT;

	public static void main(String[] args) {
		new FractalExplorer().createMandelbrot();
	}

	
	public FractalExplorer(){
		super("Fractal Explorer");
		this.setPreferredSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.pack();
		this.setVisible(true);
	}
	
	public void createMandelbrot(){		
		// Create and add the user selected point panel
		JPanel usp = new JPanel(new GridLayout(1,1));
		uspT = new JTextArea();
		usp.add(uspT);
		
		this.add(usp,BorderLayout.NORTH);
		
		// Create and add the editing panel
		JPanel controls = new JPanel(new FlowLayout());
		
		this.add(controls,BorderLayout.SOUTH);
		
		
		// Create and add the display panel
		Mandelbrot m = new Mandelbrot();
		this.add(m, BorderLayout.CENTER);
		
		// Add the Mandelbrot listener 
		m.addMouseListener(new MClicker(m));
		
		m.repaint();
		this.repaint();
	}
	
	
	
	
	
	
	
	
	// Mouse listener for showing the Julia set and complex number (not implemented)
	private class MClicker extends MouseAdapter{
		Mandelbrot m;
		
		public MClicker(Mandelbrot m){
			this.m = m;
		}
		
		public void mouseClicked(MouseEvent e){
			ComplexNumber c = m.getComplex(e.getPoint().x, e.getPoint().y);
			uspT.setText("Complex number: "+c.getReal()+" + "+c.getImaginary()+"i");
		}
		
		
	}
	
}
