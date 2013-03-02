import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;


public class Mandelbrot extends JPanel{
	
	// ints to represent the ranges
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	
	public final static int ITTERATIONS = 100;
	
	public Mandelbrot(){
		// Set the min & max
		minX = -2;
		maxX = 2;
		minY = -1.6;
		maxY = 1.6;
		
		this.addMouseListener(new Listener());
		this.setBackground(Color.WHITE);
		this.setSize(400,400);
		
		
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		// Paint each pixel with the x axis then the y axis
		for(int Y=0; Y<=this.getWidth(); Y++){
			for(int X=0; X<=this.getHeight(); X++){
				// Do the work per-pixel here
				ComplexNumber c1 = getComplex(X, Y);
				ComplexNumber c2 = new ComplexNumber(c1);
				// Calculate divergence up to ITTERATIONS
				int n = 0;
				while(n<=ITTERATIONS){
					// Do the formula: Z(i+1) = (Z(i) * Z(i)) + c. Where c = c1 and Z(i) = c2;
					c2 = c2.square().add(c1);
					// Escape if it does diverge
					if(c2.modulusSquared() >= 4){
						break;
					}
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
		double stepX = (maxX-minX)/this.getWidth();
		double stepY = (maxY-minY)/this.getHeight();
		
		// Move the coordinates to the right place in the r-t axis
		double real = x*stepX;
		double imaginary = y*stepY;
		real = minX + real;
		imaginary = maxY - imaginary;
		
		//if(real <= maxX && imaginary >= minY){
			return new ComplexNumber(real, imaginary);
		//}else{
		//	return null;
		//}
	}
	
	
	// Gets the colour the display from the number of iterations
	// TODO: create a better algorithm
	private Color genColor(int it){
		
		if(it <= 10) return Color.YELLOW;
		if(it <= 20) return Color.LIGHT_GRAY;
		if(it <= 30) return Color.PINK;
		if(it <= 40) return Color.MAGENTA;
		if(it <= 50) return Color.GREEN;
		
		if(it <= 60) return Color.BLUE;
		if(it <= 70) return Color.CYAN;
		if(it <= 80) return Color.RED;
		if(it <= 90) return Color.ORANGE;
		
		
		
		if(it >= 100) return Color.BLACK;
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Helper function takes a complex number and converts it into a point on the normalised axis
	private Point getPoint(ComplexNumber c){
		// Represents the number per px on the screen
		double stepX = (maxX-minX)/this.getWidth();
		double stepY = (maxY-minY)/this.getHeight();
		
		
		
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
