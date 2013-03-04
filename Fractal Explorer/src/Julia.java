import java.awt.Graphics;


@SuppressWarnings("serial")
public class Julia extends Fractal{
	
	private ComplexNumber c;

	public Julia(ComplexNumber c, int iter){
		super(iter);
		this.c = c;
	}
	public Julia(ComplexNumber c){
		super();
		this.c = c;
	}
	
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		// Paint each pixel with the x axis then the y axis
		for(int Y=0; Y<=this.getHeight(); Y++){
			for(int X=0; X<=this.getWidth(); X++){
				// Do the work per-pixel here
				ComplexNumber d = getComplex(X, Y);
				// Calculate divergence up to given iteration
				int n = 0;
				while(n<=iterations){
					// Escape if it does diverge
					if(d.modulusSquared() >= 4.0){
						break;
					}
					// Do the formula: Z(i+1) = (Z(i) * Z(i)) + c. Where c = c and Z(i) = d;
					d.square().add(c);
					n++;
				}


				// Paint the on the pixel according to the iterations
				g.setColor(genColor(n,d));
				g.drawLine(X, Y, X, Y);

			}
		}
		if(showAxis){paintAxis(g);}
	}
	
	
	
	
	
	// Getters and setters for the complex number used to generate the set
	public ComplexNumber getFixedComplex(){
		return c;
	}
	public void setFixedComplex(ComplexNumber c){
		this.c = c;
		this.repaint();
		this.getTopLevelAncestor().setVisible(true);
	}
	
	
	// Override the unwanted Fractal methods with stubs
	public void setMinReal(double v){}
	public void setMaxReal(double v){}
	public void setMinImaginary(double v){}
	public void setMaxImaginary(double v){}
	
	// Helpers that set the ranges
	public void setRealRange(double low, double high){}
	public void setImaginaryRange(double low, double high){}
}
