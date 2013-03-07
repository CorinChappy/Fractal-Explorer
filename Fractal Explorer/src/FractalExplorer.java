import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;


@SuppressWarnings("serial")
public class FractalExplorer extends JFrame{
	
	// Stores the fractal (and control panel, if it exists)
	Fractal F;
	ControlPanel controler;

	public static void main(String[] args) {
		new FractalExplorer().createMandelbrot();
	}


	public FractalExplorer(){
		super("Fractal Explorer");
		this.setPreferredSize(new Dimension(400, 400));
		this.setMinimumSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
	}
	
	// Method for creating a generic fratal in the Frame, booleans represent whether
	// to make the panel visible by default and a control panel should be present
	public Fractal createFractal(Fractal f, boolean visible, boolean controls){
		this.add(f, BorderLayout.CENTER);

		// Create and attach the control panel
		if(controls){
			// Create and add the user selected point panel
			JPanel uspC = new JPanel(new GridLayout(1,1));
			JTextArea uspT = new JTextArea("Select a point to see its complex number");
			uspC.add(uspT);
			this.add(uspC,BorderLayout.NORTH);

			// Add the ComplexDisplay & ZoomListener listeners
			ComplexDisplayClicker l1 = new ComplexDisplayClicker(f, uspT);
			f.addMouseListener(l1);
			ZoomListener l2 = new ZoomListener(f);
			f.addMouseListener(l2);
			f.addMouseMotionListener(l2);

			// Create and add the editing panel
			ControlPanel controler = new ControlPanel(f);
			this.add(controler,BorderLayout.SOUTH);
			this.controler = controler;
			
			this.setPreferredSize(new Dimension(585, 655));
			this.setMinimumSize(new Dimension(535, 575));
		}
		
		
		this.pack();
		this.setVisible(visible);
		F = f;
		return f;
	}
	
	public Fractal createFractal(Fractal f, boolean controls){
		return this.createFractal(f, true, controls);
	}
	
	public Julia createJulia(ComplexNumber c, int iter){
		Julia j = new Julia(c,iter);
		createFractal(j, false, false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		return j;
	}
	
	Julia createJulia(ComplexNumber c){
		return this.createJulia(c, Fractal.DEFAULT_ITERATIONS);
	}
	
	Julia createJulia(){
		return this.createJulia(new ComplexNumber(0,0), Fractal.DEFAULT_ITERATIONS);
	}

	public Mandelbrot createMandelbrot(){

		Mandelbrot m = new Mandelbrot();
		createFractal(m, true);
		m.setJulia(new FractalExplorer().createJulia());
		
		return m;
	}





	
	// Listener for displaying the complex number
	private class ComplexDisplayClicker extends MouseAdapter{
		private Fractal f;
		JTextComponent t;
		

		public ComplexDisplayClicker(Fractal f, JTextComponent t){
			this.f = f;
			this.t = t;
		}
		
		public void mouseClicked(MouseEvent e){
			ComplexNumber c = f.getComplex(e.getPoint().x, e.getPoint().y);
			t.setText("Complex number: "+c.getReal()+" + "+c.getImaginary()+"i");
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
				if(controler != null){controler.updateValues();}
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
