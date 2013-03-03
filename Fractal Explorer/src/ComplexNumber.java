
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	public ComplexNumber(double r, double i){
		real = r;
		imaginary = i;
	}
	
	public ComplexNumber(ComplexNumber c){
		real = c.real;
		imaginary = c.imaginary;
	}
	
	public double getReal(){
		return real;
	}
	
	public double getImaginary(){
		return imaginary;
	}
	
	public ComplexNumber square(){
		//a^2-b^2+(2ab)i
		
		// Create temp variables to store the old real and imaginary
		double tReal = real;
		double tImag = imaginary;
		
		// Set the real and imaginary values
		real = ((tReal*tReal)-(tImag*tImag));
		imaginary = 2*tReal*tImag;
		
		return this;
	}
	
	public double modulusSquared(){
		return (real*real)+(imaginary*imaginary);
	}
	
	public ComplexNumber add(ComplexNumber d){
		real += d.getReal();
		imaginary += d.getImaginary();
		
		return this;
	}
	
}
