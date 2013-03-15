import java.awt.Graphics;


@SuppressWarnings("serial")
public class Julia extends Fractal{
	
	private ComplexNumber c;
	public static final boolean dependent = true;

	public Julia(ComplexNumber c, int iter){
		super(iter);
		this.c = new ComplexNumber(c);
	}
	public Julia(ComplexNumber c){
		super();
		this.c = new ComplexNumber(c);
	}
	
	protected Julia(){
		this(new ComplexNumber(0,0));
	}
	
	
	protected void calculateFractal(Graphics g){

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
				paintPixel(g, n, X, Y);

			}
		}
	}
	
	public void display(){
		this.forcePaint();
		if(!this.getTopLevelAncestor().isVisible()){
			this.getTopLevelAncestor().setVisible(true);
		}
	}
	
	public void hide(){
		if(this.getTopLevelAncestor().isVisible()){
			this.getTopLevelAncestor().setVisible(false);
		}
	}
	
	
	
	// Getters and setters for the complex number used to generate the set
	public ComplexNumber getFixedComplex(){
		return c;
	}
	public void setFixedComplex(ComplexNumber c){
		this.c = c;
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
