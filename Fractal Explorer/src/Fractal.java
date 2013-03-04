import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class Fractal extends JPanel{
	
	
	// ints to represent the ranges
	protected double minR;
	protected double maxR;
	protected double minI;
	protected double maxI;

	// booleans to represent enabled functions
	protected boolean showAxis = true;

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
	
	// Paintcomponent just calls a couple of abstract methods that need to be overridden
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		paintFractal(g);
		if(showAxis){paintAxis(g);}
	}
	
	// Abstract paintFractal, to be overridden
	protected abstract void paintFractal(Graphics g);
	
	// paints the axis on the screen (Maybe move to another frame?)
	protected void paintAxis(Graphics g){
		g.setColor(Color.RED);
		g.drawLine(getPoint(new ComplexNumber(minR,0)). x,getPoint(new ComplexNumber(minR,0)).y, getPoint(new ComplexNumber(maxR,0)). x,getPoint(new ComplexNumber(maxR,0)).y);
		g.drawLine(getPoint(new ComplexNumber(0,minI)). x,getPoint(new ComplexNumber(0,minI)).y, getPoint(new ComplexNumber(0,maxI)). x,getPoint(new ComplexNumber(0,maxI)).y);
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
	
	
	
	
	
	// Method to display the axis
	public void displayAxis(boolean a){
		showAxis = a;
		this.repaint();
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
	

}
