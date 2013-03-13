import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class Fractal extends JPanel{
	
	// The overlay to display the axis and the zooming
	public final FractalOverlay overlay;
	
	// The control panel of this Fractal
	ControlPanel controler;
	
	// doubles to represent the ranges
	protected double minR, maxR, minI, maxI;
	
	// Statics to represent defaults for this Fractal, can be changed in subclasses so methods should be used for access
	public static final double MINIMUM_REAL = -2.0, MAXIMUM_REAL = 2.0, MINIMUM_IMAGINARY = -1.6, MAXIMUM_IMAGINARY = 1.6;
	
	// Methods here that allow access outside
	public double MINIMUM_REAL(){
		return MINIMUM_REAL;
	}
	public double MAXIMUM_REAL(){
		return MAXIMUM_REAL;
	}
	public double MINIMUM_IMAGINARY(){
		return MINIMUM_IMAGINARY;
	}
	public double MAXIMUM_IMAGINARY(){
		return MAXIMUM_IMAGINARY;
	}


	// Cache functions
	private FractalCache cache = new FractalCache();
	private boolean recalculate = true;
	private boolean useCache = true;

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
		this(MINIMUM_REAL,MAXIMUM_REAL,MINIMUM_IMAGINARY,MAXIMUM_IMAGINARY, iter);
	}

	// Default constructor
	public Fractal(){
		this(MINIMUM_REAL,MAXIMUM_REAL,MINIMUM_IMAGINARY,MAXIMUM_IMAGINARY, DEFAULT_ITERATIONS);
	}
	
	// paintComponent just calls abstract methods and deals with the cache
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		// Just calculate the fractal if the cache is not being used
		if(!useCache){
			calculateFractal(g);
			return;
		}
		
		// Check if the fractal needs to be recalculated
		if(recalculate || cache.isNull() || cache.width() != this.getWidth() || cache.height() != this.getHeight()){
			cache.remake(this.getWidth(), this.getHeight());
			calculateFractal(g);
			recalculate = false;
		}else{
			// Grab the data from the cache
			for(int X=0; X<this.getWidth(); X++){
				for(int Y=0; Y<this.getHeight(); Y++){
					// Paint the pixels from the cache
					try {
						paintPixel(g, cache.get(X,Y), X, Y);
					}catch(CacheFullException e){
						// Panel has grown, need to recalculate
						recalculate = true;
						this.repaint();
					}
				}
			}
		}
	}
	
	// Force a non-cached repaint
	void forcePaint(){
		recalculate = true;
		this.repaint();
	}
	
	// Abstract paintFractal, to be overridden
	protected abstract void calculateFractal(Graphics g);
	
	// Paints the pixel, called by calculateFractal
	protected void paintPixel(Graphics g, int iterations, int X, int Y){
		g.setColor(genColor(iterations));
		g.drawLine(X, Y, X, Y);
		if(useCache){
			try {
				cache.add(iterations, X, Y);
			}catch(CacheFullException e){
				// Cache isn't big enough, so it's unusable. Recalculate
				recalculate = true;
			}
		}
	}

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
	
	protected Color genColor(int it){return this.genColor(it, new ComplexNumber(0,0));}
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
	
	// Getters and setters for the ControlPanel
	public ControlPanel getControlPanel(){
		return controler;
	}
	
	public void setControlPanel(ControlPanel controler){
		this.controler = controler;
	}
	
	// Method just calls the display axis on the overlay
	public void displayAxis(boolean a){
		overlay.displayAxis(a);
	}
	
	// Method to enable or disable the cache
	public void enableCache(boolean a){
		useCache = a;
	}
	
	
	// Getters and setters for the maximum iterations
	public int getMaxIterations(){
		return iterations;
	}

	public void setMaxIterations(int i){
		iterations = i;
		recalculate = true;
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
		recalculate = true;
	}
	public void setMaxReal(double v){
		maxR = v;
		recalculate = true;
	}
	public void setMinImaginary(double v){
		minI = v;
		recalculate = true;
	}
	public void setMaxImaginary(double v){
		maxI = v;
		recalculate = true;
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
	
	// Reset stuff
	public void setRangesDefault(){
		setImaginaryRange(MINIMUM_IMAGINARY(), MAXIMUM_IMAGINARY());
		setRealRange(MINIMUM_REAL(), MAXIMUM_REAL());
	}
	
	// Setter for the centre (Basically moves the ranges around)
	public void setCentre(ComplexNumber newCentre){
		double realOffset =  (maxR - minR)/2;
		double imaginaryOffset = (maxI - minI)/2;
		
		// Get the complex of the new centre
		//ComplexNumber newCentre = getComplex(p);
		
		// Set the ranges
		setRealRange(newCentre.getReal() - realOffset, newCentre.getReal() + realOffset);
		setImaginaryRange(newCentre.getImaginary() - imaginaryOffset, newCentre.getImaginary() + imaginaryOffset);
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
