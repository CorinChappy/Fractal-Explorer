import java.util.HashMap;


// Class that stores the info to recover the state of a Fractal (IE save a picture)
public class SavedView {
	
	// Stored information
	private String name;
	private double minR, maxR, minI, maxI;
	private int iterations;
	private Class<? extends Fractal> type;
	
	// Create a new save
	private SavedView(String name, Class<? extends Fractal> type, double minR, double maxR, double minI, double maxI, int iterations) throws NameInUseException{
		// Check for nulls, empty names, and finally if the name is already in use
		if(name == null || name == "" || saves.containsKey(name)){throw new NameInUseException(name);}
		// Check if the passed class is 'Fractal'
		if(type == Fractal.class){throw new FractalTypeException();}
		// Make the new Save
		this.name = name;
		this.type = type;
		this.minR = minR;
		this.maxR = maxR;
		this.minI = minI;
		this.maxI = maxI;
		this.iterations = iterations;

	}
	// Create a new save with default iterations
	private SavedView(String name, Class<? extends Fractal> type, double minR, double maxR, double minI, double maxI) throws NameInUseException{
		this(name, type, minR, maxR, minI, maxI, Fractal.DEFAULT_ITERATIONS);
	}
	// Create a new save from an old one (copy)
	private SavedView(SavedView save, String name) throws NameInUseException{
		this(name, save.type, save.minR, save.maxR, save.minI, save.maxI, save.iterations);
	}
	
	// Getter and setter for the name
	public String getName(){
		return name;
	}
	public void rename(String name) throws NameInUseException{
		// Check for nulls, empty names, and finally if the name is already in use
		if(name == null || name == "" || saves.containsKey(name)){throw new NameInUseException(name);}
		// Remove the old mapping
		saves.remove(this.name);
		// Make the new mapping
		saves.put(name, this);
		// Change the instance variable to match
		this.name = name;
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
	
	// Getters and setters for the type of Fractal
	public Class<? extends Fractal> getType(){
		return type;
	}
	
	public void setType(Class<? extends Fractal> type){
		// Check if the passed class is 'Fractal'
		if(type == Fractal.class){throw new FractalTypeException();}
		this.type = type;
	}
	
	
	/*
	 * Now we have all of the static stuff!
	 * These methods are to save, load (and export/import) saved views
	 * They are stored in the program using a map
	 * No ability to create new SavedView's, just access the static methods
	 */
	// The magic map or storage
	private static HashMap<String, SavedView> saves = new HashMap<String, SavedView>();
	
	// Saves when values are passed in
	public static void save(String name, Class<? extends Fractal> type, double minR, double maxR, double minI, double maxI, int iterations) throws NameInUseException{
		SavedView s = new SavedView(name, type, minR, maxR, minI, maxI, iterations);
		// Add this save the HashMap
		saves.put(name, s);
	}
	// Same as above, when iterations not defined
	public static void save(String name, Class<? extends Fractal> type, double minR, double maxR, double minI, double maxI) throws NameInUseException{
		SavedView s = new SavedView(name, type, minR, maxR, minI, maxI);
		// Add this save the HashMap
		saves.put(name, s);
	}
	
	// Save from a fractal
	public static void save(String name, Fractal f) throws NameInUseException{
		SavedView s = new SavedView(name, f.getClass(), f.getMinReal(), f.getMaxReal(), f.getMinImaginary(), f.getMaxImaginary(), f.getMaxIterations());
		// Add this save the HashMap
		saves.put(name, s);
	}
	
	// This saves an already existing SaveVeiw (does not copy, just puts the object in the HashMap)
	public static void save(SavedView save) throws NameInUseException{
		if(saves.containsKey(save.name)){throw new NameInUseException(save.name);}
		saves.put(save.name, save);
	}
	
	// Get (load) a saved view, returns the raw object
	public static SavedView loadView(String name){
		return saves.get(name);
	}
	
	// Create a new fractal with the selected SaveVeiw Returns null if the save doesn't exist
	public static Fractal loadFractal(String name){
		SavedView save = saves.get(name);
		if(save == null){return null;}
		
		Fractal f;
		
		// Try to create a new instance of the Fractal
		try{
			f = save.type.newInstance();
		}catch(InstantiationException | IllegalAccessException e){return null;}
		
		// Set all of the things
		f.setRealRange(save.getMinReal(), save.getMaxReal());
		f.setImaginaryRange(save.getMinImaginary(), save.getMaxImaginary());
		f.setMaxIterations(save.getIterations());
		
		return f;
	}
	
	// Loads the data to a specific fractal (Fractal can actually be of any type,
	// not just the one defined when it was saved.
	public static Fractal loadToFractal(String name, Fractal f){
		SavedView save = saves.get(name);
		if(save == null){return null;}
		
		// Set everything
		f.setRealRange(save.getMinReal(), save.getMaxReal());
		f.setImaginaryRange(save.getMinImaginary(), save.getMaxImaginary());
		f.setMaxIterations(save.getIterations());
		
		return f;
	}
	
	// Creates an new Fractal and wraps it in a frame
	public static Fractal loadFramedFractal(String name, boolean controls){
		Fractal f = loadFractal(name);
		if(f == null){return null;}
		
		// Create the new frame and put the Fractal in it
		new FractalExplorer().createFractal(f, controls);
		
		return f;
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

// Thrown if the Fractal type is just Fractal, and not a subclass
class FractalTypeException extends RuntimeException{
	public FractalTypeException(){
		super("Type cannot be 'Fractal'");
	}
}
