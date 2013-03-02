
public class ComplexNumber {
	
	private int real;
	private int imaginary;
	
	public ComplexNumber(int r, int i){
		real = r;
		imaginary = i;
	}
	
	public int getReal(){
		return real;
	}
	
	public int getImaginary(){
		return imaginary;
	}
	
	public void square(){
		//a^2-b^2+(2ab)i
		
		// Set the real and imaginary values
		real = ((real*real)-(imaginary*imaginary));
		imaginary = 2*real*imaginary;
	}
	
	public double modulusSquared(){
		double mod = Math.sqrt((real*real)+(imaginary*imaginary));
		return (mod*mod);
	}
	
	public void add(ComplexNumber d){
		real += d.getReal();
		imaginary += d.getImaginary();
		
	}
	
}
