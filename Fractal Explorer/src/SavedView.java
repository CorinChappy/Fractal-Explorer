import java.util.HashMap;


// Class that stores the info to recover the state of a Fractal (IE save a picture)
public class SavedView {
	
	// Stored information
	private String name;
	private double minR, maxR, minI, maxI;
	private int iterations;
	private Class<? extends Fractal> type = Mandelbrot.class;
	
	// Create a new save
	public SavedView(String name, double minR, double maxR, double minI, double maxI, int iterations) throws NameInUseException{
		// Check for nulls, empty names, and finally if the name is already in use
		if(name == null || name == "" || saves.containsKey(name)){throw new NameInUseException(name);}
		// Make the new Save
		this.name = name;
		this.minR = minR;
		this.maxR = maxR;
		this.minI = minI;
		this.maxI = maxI;
		this.iterations = iterations;
		// Add this save the HashMap
		saves.put(name, this);
	}
	// Create a new save with default iterations
	public SavedView(String name, double minR, double maxR, double minI, double maxI) throws NameInUseException{
		this(name, minR, maxR, minI, maxI, Fractal.DEFAULT_ITERATIONS);
	}
	// Create a new save from an old one (copy)
	public SavedView(SavedView save, String name) throws NameInUseException{
		this(name, save.minR, save.maxR, save.minI, save.maxI, save.iterations);
	}
	
	// Getter and setter for the name
	public String getName(){
		return name;
	}
	public void rename(String name){
		// DO NOTHING RIGHT NOW
	}
	
	// Getters and setters for the ranges
	public double getMinReal(){
		return minR;
	}
	public double getMaxReal(){
		return maxR;
	}
	public double getMinImaginary(){
		return minI;
	}
	public double getMaxImaginary(){
		return maxI;
	}
	
	public void setMinReal(double v){
		minR = v;
	}
	public void setMaxReal(double v){
		maxR = v;
	}
	public void setMinImaginary(double v){
		minI = v;
	}
	public void setMaxImaginary(double v){
		maxI = v;
	}
	
	// Helpers that set the ranges
	public void setRealRange(double low, double high){
		setMinReal(low);
		setMaxReal(high);
	}
	public void setImaginaryRange(double low, double high){
		setMinImaginary(low);
		setMaxImaginary(high);
	}
	
	// Getters and setters for the iterations
	public int getIterations(){
		return iterations;
	}

	public void setIterations(int i){
		iterations = i;
	}
	
	
	
	/*
	 * Now we have all of the static stuff!
	 * These methods are to save, load (and export/import) saved views
	 * They are stored in the program using a map
	 */
	// The magic map or storage
	private static HashMap<String, SavedView> saves = new HashMap<String, SavedView>();
	
	// This saves an already existing SaveVeiw
	public static void save(SavedView save) throws NameInUseException{
		if(saves.containsKey(save.name)){throw new NameInUseException(save.name);}
		saves.put(save.name, save);
	}
	
	// Get (load) a saved view (will return null if save doesn't exits)
	public static SavedView load(String name){
		return saves.get(name);
	}

	// Get all of the save's names in the form of an array of strings
	public static String[] getSavedNames(){
		return saves.keySet().toArray(new String[0]);
	}
	
	
	
	
}


// Thrown if the name is already in use OR if the name was null OR the empty string ("")
@SuppressWarnings("serial")
class NameInUseException extends Exception{
	
	private String usedName;
	private String message;
	
	public NameInUseException(String name){
		usedName = name;
		
		if(name == null){
			message = "The name given was null";
			}else{
		if(name == ""){
			message = "The name given was the empty string (\"\")";
			}else{
				message = "The name given is already in use";
			}
		}
		
	}
	
	// Give them my message damnit!
	public String getMessage(){
		return message;
	}
	
	public String getUsedName(){
		return usedName;
	}
	
	
	
}
