
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
