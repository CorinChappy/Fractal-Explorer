import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class FractalExplorer extends JFrame{


	public static void main(String[] args) {
		new FractalExplorer().createMandelbrot();
	}

	
	public FractalExplorer(){
		super("Fractal Explorer");
		this.setPreferredSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.pack();
		this.setVisible(true);
	}
	
	public void createMandelbrot(){
		Mandelbrot m = new Mandelbrot();
		this.add(m, BorderLayout.CENTER);
		
	}
	
	
}
