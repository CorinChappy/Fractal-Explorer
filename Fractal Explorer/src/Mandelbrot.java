import java.awt.Graphics;


@SuppressWarnings("serial")
public class Mandelbrot extends Fractal{

	public Mandelbrot(double minR, double maxR, double minI, double maxI, int iter){
		super(minR, maxR, minI, maxI, iter);
	}
	
	// Other constructors
	public Mandelbrot(double minR, double maxR, double minI, double maxI){
		super(minR, maxR, minI, maxI, DEFAULT_ITERATIONS);
	}
	
	public Mandelbrot(int iter){
		super(iter);
	}

	// Default constructor
	public Mandelbrot(){
		super(DEFAULT_ITERATIONS);
	}


	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		// Paint each pixel with the x axis then the y axis
		for(int Y=0; Y<=this.getHeight(); Y++){
			for(int X=0; X<=this.getWidth(); X++){
				// Do the work per-pixel here
				ComplexNumber c1 = getComplex(X, Y);
				ComplexNumber c2 = new ComplexNumber(c1);
				// Calculate divergence up to given iteration
				int n = 0;
				while(n<=iterations){
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
		if(showAxis){paintAxis(g);}
	}
	
	
}
