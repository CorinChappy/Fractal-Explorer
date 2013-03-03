import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Mandelbrot extends JPanel{
	
	// ints to represent the ranges
	private double minR;
	private double maxR;
	private double minI;
	private double maxI;
	
	// booleans to represent enabled functions
	
	public final static int ITTERATIONS = 1000;
	
	public Mandelbrot(double minR, double maxR, double minI, double maxI){
		// Set the min & max
		this.minR = minR;
		this.maxR = maxR;
		this.minI = minI;
		this.maxI = maxI;
		
		this.setBackground(Color.WHITE);
	}
	
	// Default constructor
	public Mandelbrot(){
		this(-2.0,2.0,-1.6,1.6);
	}

	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// Paint each pixel with the x axis then the y axis
		for(int Y=0; Y<=this.getHeight(); Y++){
			for(int X=0; X<=this.getWidth(); X++){
				// Do the work per-pixel here
				ComplexNumber c1 = getComplex(X, Y); //if(this.getWidth() != 400 || this.getWidth() != 400) System.out.println(this.getWidth()+" - "+this.getHeight());
				ComplexNumber c2 = new ComplexNumber(c1);
				// Calculate divergence up to ITTERATIONS
				int n = 0;
				while(n<=ITTERATIONS){
					// Escape if it does diverge
					if(c2.modulusSquared() >= 4.0){
						break;
					}
					// Do the formula: Z(i+1) = (Z(i) * Z(i)) + c. Where c = c1 and Z(i) = c2;
					c2.square().add(c1);
					n++;
				}
				// Paint the on the pixel according to the iterations
				g.setColor(genColor(n,c2));
				g.drawLine(X, Y, X, Y);
				
			}
		}
		// Draw some red lines for axis and shit
		g.setColor(Color.RED);
		g.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
		g.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());
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
	
	
	// Gets the colour the display from the number of iterations
	// TODO: create a better algorithm
	private Color genColor(int it, ComplexNumber c){
		/* SUPER FUNKY COlOURS
		float a = (float) (it + 1 - Math.log(Math.log(Math.sqrt(c.modulusSquared())))/Math.log(2));
		
		return new Color(Color.HSBtoRGB(0.95f + 10 * a ,0.6f,1.0f));
		*/
		
		
		int div = 1677216/ITTERATIONS;
		//int div = 255/ITTERATIONS;
		
		int colnum = div*it;
		
		return new Color(colnum);
	}
	
	
	
}
