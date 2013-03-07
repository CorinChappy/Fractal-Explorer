import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;


@SuppressWarnings("serial")
public class FractalExplorer extends JFrame{
	
	// Stores the fractal (and control panel, if it exists)
	Fractal F;
	ControlPanel control;

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
		F = j;
		return j;
	}
	
	Julia createJulia(ComplexNumber c){
		return this.createJulia(c, Fractal.DEFAULT_ITERATIONS);
	}
	
	Julia createJulia(){
		return this.createJulia(new ComplexNumber(0,0), Fractal.DEFAULT_ITERATIONS);
	}

	public Mandelbrot createMandelbrot(){	
		this.setPreferredSize(new Dimension(585, 655));
		this.setMinimumSize(new Dimension(535, 575));

		// Create and add the display panel
		Mandelbrot m = new Mandelbrot();
		this.add(m, BorderLayout.CENTER);


		// Create and add the user selected point panel
		JPanel uspC = new JPanel(new GridLayout(1,1));
		JTextArea uspT = new JTextArea("Select a point to see its complex number");
		uspC.add(uspT);
		this.add(uspC,BorderLayout.NORTH);

		// Add the Mandelbrot & zoomListeners listener
		MandelClicker l1 = new MandelClicker(m,uspT);
		m.addMouseListener(l1);
		m.addMouseMotionListener(l1);
		ZoomListener l2 = new ZoomListener(m);
		m.addMouseListener(l2);
		m.addMouseMotionListener(l2);

		// Create and add the editing panel
		ControlPanel controls = new ControlPanel(m);
		this.add(controls,BorderLayout.SOUTH);
		
		m.setJulia(new FractalExplorer().createJulia());

		this.pack();
		this.setVisible(true);
		F = m;
		this.control = controls;
		return m;
	}




	// Mouse listener for showing the Julia set and complex number (not implemented)
	private class MandelClicker extends MouseAdapter{
		private Mandelbrot m;
		JTextComponent t;
		

		public MandelClicker(Mandelbrot m, JTextComponent t){
			this.m = m;
			this.t = t;
		}

		public void mouseClicked(MouseEvent e){
			ComplexNumber c = m.getComplex(e.getPoint().x, e.getPoint().y);
			t.setText("Complex number: "+c.getReal()+" + "+c.getImaginary()+"i");
			if(m.getJuliaSelection() != 0){
				m.getJulia(c).setFixedComplex(c);
				m.displayJulia();
			}
		}


		public void mouseMoved(MouseEvent e){
			if(m.getJuliaSelection() == 2){
				mouseClicked(e);
			}
		}
	}
	
	private class ZoomListener extends MouseAdapter{
		
		Fractal f;
		
		// Start drag point
		private Point startDrag;

		public ZoomListener(Fractal f){
			this.f = f;
		}
		
		// Sets the start of the drag
		public void mousePressed(MouseEvent e){
			startDrag = e.getPoint();
			f.overlay.startDrag(startDrag);
		}
		
		public void mouseReleased(MouseEvent e){
			Point endDrag = e.getPoint();
			if(startDrag != null && startDrag.distance(endDrag) > 20){
				ComplexNumber c1 = f.getComplex(startDrag);
				ComplexNumber c2 = f.getComplex(endDrag);
				f.setRealRange(Math.min(c1.getReal(), c2.getReal()), Math.max(c1.getReal(), c2.getReal()));
				f.setImaginaryRange(Math.min(c1.getImaginary(), c2.getImaginary()), Math.max(c1.getImaginary(), c2.getImaginary()));
				f.repaint();
				if(control != null){control.updateValues();}
			}
			// Clear the dragging overlay
			f.overlay.clearDrag();
		}
		
		public void mouseDragged(MouseEvent e){
			f.overlay.updateDrag(e.getPoint());
		}
		
		public void mouseExited(MouseEvent e){
			f.overlay.clearDrag();
			startDrag = null;
		}
		
	}

}
