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
	}
	
	Julia createJulia(ComplexNumber c, int iter){
		Julia j = new Julia(c,iter);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.add(j, BorderLayout.CENTER);
		this.pack();
		return j;
	}
	
	Julia createJulia(ComplexNumber c){
		Julia j = new Julia(c);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.add(j, BorderLayout.CENTER);
		this.pack();
		return j;
	}
	
	Julia createJulia(){
		Julia j = new Julia();
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.add(j, BorderLayout.CENTER);
		this.pack();
		return j;
	}

	public void createMandelbrot(){	
		this.setPreferredSize(new Dimension(585, 655));
		this.setMinimumSize(new Dimension(535, 575));

		// Create and add the display panel
		Mandelbrot m = new Mandelbrot();
		this.add(m, BorderLayout.CENTER);


		// Create and add the user selected point panel
		JPanel uspC = new JPanel(new GridLayout(1,1));
		JTextArea uspT = new JTextArea("Select a point to see it's complex number");
		uspC.add(uspT);
		this.add(uspC,BorderLayout.NORTH);

		// Add the Mandelbrot listener 
		m.addMouseListener(new MClicker(m,uspT));
		m.addMouseMotionListener(new MClicker(m,uspT));

		// Create and add the editing panel
		JPanel controls = new ControlPanel(m);
		this.add(controls,BorderLayout.SOUTH);
		
		m.setJulia(new FractalExplorer().createJulia());

		this.pack();
		this.setVisible(true);
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
			m.getJulia(c).setFixedComplex(c);
			m.displayJulia();
		}


		public void mouseMoved(MouseEvent e){
			ComplexNumber c = m.getComplex(e.getPoint().x, e.getPoint().y);
			t.setText("Complex number: "+c.getReal()+" + "+c.getImaginary()+"i");
			m.getJulia(c).setFixedComplex(c);
			m.displayJulia();
		}
		
		
	}

}
