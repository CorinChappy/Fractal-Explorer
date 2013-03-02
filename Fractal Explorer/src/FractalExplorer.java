import javax.swing.JFrame;


public class FractalExplorer extends JFrame{


	public static void main(String[] args) {
		
	}

	
	public FractalExplorer(){
		super("Fractal Explorer");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
	}
	
	public void createMandelbrot(){
		Mandelbrot m = new Mandelbrot();
	}
	
	
}
