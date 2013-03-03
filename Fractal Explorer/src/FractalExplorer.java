import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;


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
		
		this.pack();
		this.setVisible(true);
	}
	
	public void createMandelbrot(){		
		// Create and add the display panel
		Mandelbrot m = new Mandelbrot();
		this.add(m, BorderLayout.CENTER);
		
		
		// Create and add the user selected point panel
		JPanel usp = new JPanel(new GridLayout(1,1));
		JTextArea uspT = new JTextArea();
		usp.add(uspT);
		this.add(usp,BorderLayout.NORTH);
		
		// Add the Mandelbrot listener 
		m.addMouseListener(new MClicker(m,uspT));
		
		// Create and add the editing panel
		JPanel controls = new ControlPanel(m);
		this.add(controls,BorderLayout.SOUTH);
		
		
		m.repaint();
		this.repaint();
	}
	
	
	
	// Control panel to display the controls bellow the mandelbrot set
	private class ControlPanel extends JPanel{
		
		Mandelbrot m;
		
		public ControlPanel(Mandelbrot m){
			this.m = m;
			
			
			
			
			
		}
		
		
		
	}
	
	
	
	
	// Mouse listener for showing the Julia set and complex number (not implemented)
	private class MClicker extends MouseAdapter{
		Mandelbrot m;
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
