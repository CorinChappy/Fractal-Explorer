import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;


public class FractalExplorer extends JFrame{


	public static void main(String[] args) {
		new FractalExplorer().createMandelbrot();
	}

	
	public FractalExplorer(){
		super("Fractal Explorer");
		this.setSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		//this.pack();
		this.setVisible(true);
	}
	
	public void createMandelbrot(){
		Mandelbrot m = new Mandelbrot();
		this.add(m, BorderLayout.CENTER);
		
	}
	
	
}
