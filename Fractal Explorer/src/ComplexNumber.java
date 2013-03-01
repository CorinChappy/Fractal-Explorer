
public class ComplexNumber {
	
	private int real;
	private int imaginary;
	
	public ComplexNumber(int r, int i){
		real = r;
		imaginary = i;
	}
	
	public void square(){
		//a^2-b^2+(2ab)i
		
		// Set the real and imaginary values
		real = ((real*real)-(imaginary*imaginary));
		imaginary = 2*real*imaginary;
	}
	
	public double modulusSquared(){
		
		return 0.0;
	}
	
	public void add(ComplexNumber d){
		
	}
	
}
