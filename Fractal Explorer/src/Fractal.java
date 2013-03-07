import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;
import javax.xml.stream.events.EndElement;


@SuppressWarnings("serial")
public abstract class Fractal extends JPanel{
	
	// The overlay to display the axis and the zooming
	public final FractalOverlay overlay;
	
	// ints to represent the ranges
	protected double minR;
	protected double maxR;
	protected double minI;
	protected double maxI;

	// booleans to represent enabled functions

	// Iteration variables
	public final static int DEFAULT_ITERATIONS = 100;
	protected int iterations;

	public Fractal(double minR, double maxR, double minI, double maxI, int iter){
		// Set the min & max
		this.minR = minR;
		this.maxR = maxR;
		this.minI = minI;
		this.maxI = maxI;
		
		// Set the iterations
		this.iterations = iter;

		this.setBackground(Color.WHITE);
		
		// Add the overlay to the fractal
		this.setLayout(new BorderLayout());
		overlay = new FractalOverlay();
		this.add(overlay, BorderLayout.CENTER);
	}
	
	// Other constructors
	public Fractal(double minR, double maxR, double minI, double maxI){
		this(minR, maxR, minI, maxI, DEFAULT_ITERATIONS);
	}
	
	public Fractal(int iter){
		this(-2.0,2.0,-1.6,1.6, iter);
	}

	// Default constructor
	public Fractal(){
		this(-2.0,2.0,-1.6,1.6, DEFAULT_ITERATIONS);
	}
	
	// paintComponent just calls a couple of abstract methods that need to be overridden
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		paintFractal(g);
	}
	
	// Abstract paintFractal, to be overridden
	protected abstract void paintFractal(Graphics g);

	// Helper function, takes an x and y coordinate and finds it's complex number associated with it
	public ComplexNumber getComplex(int x, int y){
		// Represents the how much on the r-i axis to move per pixel
		double stepX = (maxR-minR)/this.getWidth();
		double stepY = (maxI-minI)/this.getHeight();


		// Move the coordinates to the right place in the r-t axis
		double real = x*stepX;
		double imaginary = y*stepY;
		real = minR + real;
		imaginary = maxI - imaginary;


		return new ComplexNumber(real, imaginary);
	}
	
	// Another helper for the above, takes a point instead and simply extracts the x and y
	// and plugs into the above
	public ComplexNumber getComplex(Point p){
		return getComplex(p.x, p.y);
	}
	
	// Helper function, gets the pixel point for a complex number
	public Point getPoint(ComplexNumber c){
		// Represents the how much on the r-i axis to move per pixel
		double stepX = (maxR-minR)/this.getWidth();
		double stepY = (maxI-minI)/this.getHeight();
		
		double x = c.getReal();
		double y = c.getImaginary();
		
		x = x - minR;
		y = maxI - y;
		
		x = x/stepX;
		y = y/stepY;
		
		return new Point((int) x,(int) y);
		
	}
	
	// Gets the colour the display from the number of iterations
	// TODO: create a better algorithm
	protected Color genColor(int it, ComplexNumber c){
		/* SUPER FUNKY COlOURS
		float a = (float) (it + 1 - Math.log(Math.log(Math.sqrt(c.modulusSquared())))/Math.log(2));

		return new Color(Color.HSBtoRGB(0.95f + 10 * a ,0.6f,1.0f));
		 */


		int div = 1677216/iterations;
		//int div = 255/iterations;

		int colnum = div*it;

		return new Color(colnum);
	}
	
	// Method just calls the display axis on the overlay
	public void displayAxis(boolean a){
		overlay.displayAxis(a);
	}
	
	
	// Getters and setters for the maximum iterations
	public int getMaxIterations(){
		return iterations;
	}

	public void setMaxIterations(int i){
		iterations = i;
	}
	
	
	// Getters and setters for the ranges
	public double getMinReal(){
		return minR;
	}
	public double getMaxReal(){
		return maxR;
	}
	public double getMinImaginary(){
		return minI;
	}
	public double getMaxImaginary(){
		return maxI;
	}
	
	public void setMinReal(double v){
		minR = v;
	}
	public void setMaxReal(double v){
		maxR = v;
	}
	public void setMinImaginary(double v){
		minI = v;
	}
	public void setMaxImaginary(double v){
		maxI = v;
	}
	
	// Helpers that set the ranges
	public void setRealRange(double low, double high){
		setMinReal(low);
		setMaxReal(high);
	}
	public void setImaginaryRange(double low, double high){
		setMinImaginary(low);
		setMaxImaginary(high);
	}
	
	
	// The Overlay class, a helper class that displays the axis among others
	final class FractalOverlay extends JPanel{
		
		private boolean showAxis = true;
		private boolean dragging = false;
		
		private Point dragStart;
		private Point dragEnd;
		
		private FractalOverlay(){
			this.setOpaque(false);
		}
		
		// Method to display the axis
		public void displayAxis(boolean a){
			showAxis = a;
			this.repaint();
		}
		
		// Visual dragging display
		public void startDrag(Point p){
			dragStart = new Point(p);
			
		}
		public void updateDrag(Point p){
			if(dragStart != null){
				if(dragStart.distance(p) > 20){
					dragEnd = new Point(p);
					dragging = true;
					this.repaint();
				}
			}
		}
		public void clearDrag(){
			dragging = false;
			dragStart = null;
			dragEnd = null;
			this.repaint();
		}
		
		protected void paintComponent(Graphics g){
			if(showAxis){
				g.setColor(Color.RED);
				g.drawLine(getPoint(new ComplexNumber(minR,0)). x,getPoint(new ComplexNumber(minR,0)).y, getPoint(new ComplexNumber(maxR,0)). x,getPoint(new ComplexNumber(maxR,0)).y);
				g.drawLine(getPoint(new ComplexNumber(0,minI)). x,getPoint(new ComplexNumber(0,minI)).y, getPoint(new ComplexNumber(0,maxI)). x,getPoint(new ComplexNumber(0,maxI)).y);
			}
			if(dragging){
				g.setColor(Color.WHITE);
				int x = Math.min(dragStart.x, dragEnd.x);
				int y = Math.min(dragStart.y, dragEnd.y);
				int width = Math.max(dragStart.x, dragEnd.x) - x;
				int height = Math.max(dragStart.y, dragEnd.y) - y;
				g.drawRect(x, y, width, height);
			}
		}
	}

}
