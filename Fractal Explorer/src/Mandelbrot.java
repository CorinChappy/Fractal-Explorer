import java.awt.Graphics;


@SuppressWarnings("serial")
public class Mandelbrot extends Fractal{

	// Stores the julia set related to this set
	private Julia j;
	
	// Stores how to display the Julia - 0 = none, 1 = static, 2 dynamic
	int juliaDisplay = 1;

	public Mandelbrot(double minR, double maxR, double minI, double maxI, int iter){
		super(minR, maxR, minI, maxI, iter);
	}

	// Other constructors
	public Mandelbrot(double minR, double maxR, double minI, double maxI){
		super(minR, maxR, minI, maxI);
	}

	public Mandelbrot(int iter){
		super(iter);
	}

	// Default constructor
	public Mandelbrot(){
		super();
	}

	protected void paintFractal(Graphics g){
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
	}

	// Override displayAxis to also display the Julia axis
	public void displayAxis(boolean a){
		super.displayAxis(a);
		if(j != null){
			j.displayAxis(a);
		}
	}

	public void setMaxIterations(int i){
		super.setMaxIterations(i);
		if(j != null){
			j.setMaxIterations(i);
			j.repaint();
		}
	}


	// Changes the way a julia is displayed
	public void changeJuliaSelection(int w){
		if(w < 0 || w > 2){throw new IllegalArgumentException();}
		juliaDisplay = w;
	}
	
	public int getJuliaSelection(){
		return juliaDisplay;
	}
	

	// Getter and setter for this Mandelbrot's Julia set
	public Julia getJulia(){
		return getJulia(new ComplexNumber(0,0));
	}
	public Julia getJulia(ComplexNumber c){
		// If there is no julia set then create a new one
		if(j == null){
			j = new FractalExplorer().createJulia(c, iterations);
		}
		return j;
	}
	public void setJulia(Julia j){
		this.j = j;
	}
	public void displayJulia(){
		getJulia().display();
	}

}
