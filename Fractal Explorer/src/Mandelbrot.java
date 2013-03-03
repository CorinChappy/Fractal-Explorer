import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;


public class Mandelbrot extends JPanel{
	
	// ints to represent the ranges
	private double minR;
	private double maxR;
	private double minI;
	private double maxI;
	
	public final static int ITTERATIONS = 10000;
	
	public Mandelbrot(){
		// Set the min & max
		minR = -2;
		maxR = 2;
		minI = -1.6;
		maxI = 1.6;
		
		this.addMouseListener(new Listener());
		this.setBackground(Color.WHITE);
		this.setSize(400,400);
		
		
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		// Paint each pixel with the x axis then the y axis
		for(int Y=0; Y<=this.getHeight(); Y++){
			for(int X=0; X<=this.getWidth(); X++){
				// Do the work per-pixel here
				ComplexNumber c1 = getComplex(X, Y);
				ComplexNumber c2 = new ComplexNumber(c1);
				// Calculate divergence up to ITTERATIONS
				int n = 0;
				while(n<=ITTERATIONS){
					// Escape if it does diverge
					if(c2.modulusSquared() >= 4){
						break;
					}
					// Do the formula: Z(i+1) = (Z(i) * Z(i)) + c. Where c = c1 and Z(i) = c2;
					c2 = c2.square().add(c1);
					n++;
				}
				// Paint the on the pixel according to the iterations
				g.setColor(genColor(n));
				g.drawLine(X, Y, X, Y);
				
			}
		}
	}
	
	// Helper, takes point and finds it's complex number
	private ComplexNumber getComplex(int x, int y){
		// Represents the how much on the r-i axis to move per pixel
		double stepX = (maxR-minR)/this.getWidth();
		double stepY = (maxI-minI)/this.getHeight();
		
		// Move the coordinates to the right place in the r-t axis
		double real = x*stepX;
		double imaginary = y*stepY;
		real = minR + real;
		imaginary = maxI - imaginary;
		
		//if(real <= maxX && imaginary >= minY){
			return new ComplexNumber(real, imaginary);
		//}else{
		//	return null;
		//}
	}
	
	
	// Gets the colour the display from the number of iterations
	// TODO: create a better algorithm
	private Color genColor(int it){
		
		
		/*
		if(it <= 10) return Color.YELLOW;
		//if(it <= 20) return Color.LIGHT_GRAY;
		//if(it <= 30) return Color.PINK;
		if(it <= 40) return Color.MAGENTA;
		//if(it <= 50) return Color.GREEN;
		
		//if(it <= 60) return Color.BLUE;
		//if(it <= 70) return Color.CYAN;
		if(it <= 80) return Color.RED;
		//if(it <= 90) return Color.ORANGE;
		*/
		
		
		if(it >= ITTERATIONS) return Color.BLACK;
		return Color.WHITE;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Helper function takes a complex number and converts it into a point on the normalised axis
	private Point getPoint(ComplexNumber c){
		// Represents the number per px on the screen
		double stepX = (maxR-minR)/this.getWidth();
		double stepY = (maxI-minI)/this.getHeight();
		
		
		
		return null;
	}
	
	
	// Helper function takes a point from the normalised axis
	// and returns where it actually is on the panel 
	private Point getCoords(Point p){
		Point c = new Point(this.getWidth()/2,this.getHeight()/2);
		c.translate(p.x, p.y);
		return c;
	}
	
	// Mouse listener for showing the Julia set (not implemented)
	private class Listener extends MouseAdapter{
		
	}
}
