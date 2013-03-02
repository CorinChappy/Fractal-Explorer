
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	public ComplexNumber(double r, double i){
		real = r;
		imaginary = i;
	}
	
	public ComplexNumber(ComplexNumber c){
		real = c.getReal();
		imaginary = c.getImaginary();
	}
	
	public double getReal(){
		return real;
	}
	
	public double getImaginary(){
		return imaginary;
	}
	
	public ComplexNumber square(){
		//a^2-b^2+(2ab)i
		
		// Set the real and imaginary values
		real = ((real*real)-(imaginary*imaginary));
		imaginary = 2*real*imaginary;
		
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
