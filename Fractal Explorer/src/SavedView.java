import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;


// Class that stores the info to recover the state of a Fractal (IE save a picture)
public class SavedView {
	
	// Stored information
	private String name;
	private double minR, maxR, minI, maxI;
	private int iterations;
	private ComplexNumber fixedComplex;
	private Class<? extends Fractal> type;
	
	// Create a new save
	private SavedView(String name, Class<? extends Fractal> type, double minR, double maxR, double minI, double maxI, int iterations, ComplexNumber fixedComplex) throws NameInUseException{
		// Check for nulls, empty names, and finally if the name is already in use
		if(name == null || name.equals("") || saves.containsKey(name)){throw new NameInUseException(name);}
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
		
		this.fixedComplex = fixedComplex;

	}
	
	// Create a new save with default iterations
	private SavedView(String name, Class<? extends Fractal> type, double minR, double maxR, double minI, double maxI, int iterations) throws NameInUseException{
		this(name, type, minR, maxR, minI, maxI, iterations, null);
	}
	// Create a new save with default iterations
	private SavedView(String name, Class<? extends Fractal> type, double minR, double maxR, double minI, double maxI) throws NameInUseException{
		this(name, type, minR, maxR, minI, maxI, Fractal.DEFAULT_ITERATIONS, null);
	}
	// Create a new save from an old one (copy)
	private SavedView(SavedView save, String name) throws NameInUseException{
		this(name, save.type, save.minR, save.maxR, save.minI, save.maxI, save.iterations, save.fixedComplex);
	}
	
	// Getter and setter for the name
	public String getName(){
		return name;
	}
	public void rename(String name) throws NameInUseException{
		// Check for nulls, empty names, and finally if the name is already in use
		if(name == null || name.equals("") || saves.containsKey(name)){throw new NameInUseException(name);}
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
	
	// Getters and setters for the fixedComplex
	public ComplexNumber getFixedComplex(){
		return fixedComplex;
	}
	
	public void setFixedComplex(ComplexNumber c){
		fixedComplex = c;
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
	
	// Take the variables and stringify them
	private String getExportString(){
		String string = "";
		string += type+"|";
		
		string += minR+"|";
		string += maxR+"|";
		string += minI+"|";
		string += maxI+"|";
		
		string += iterations+"|";
		
		if(fixedComplex != null){
			string += "("+fixedComplex.getReal()+","+fixedComplex.getImaginary()+")";
		}else{
			string += "(null)";
		}
		
		return string;
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
	public static void save(String name, Class<? extends Fractal> type, double minR, double maxR, double minI, double maxI, int iterations, ComplexNumber fixedComplex) throws NameInUseException{
		SavedView s = new SavedView(name, type, minR, maxR, minI, maxI, iterations, fixedComplex);
		// Add this save the HashMap
		saves.put(name, s);
	}
	// Saves when values are passed in (without fixedComplex)
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
		ComplexNumber fixed = null;
		if(f.getClass() == Julia.class){
			fixed = ((Julia) f).getFixedComplex();
		}
		SavedView s = new SavedView(name, f.getClass(), f.getMinReal(), f.getMaxReal(), f.getMinImaginary(), f.getMaxImaginary(), f.getMaxIterations(), fixed);
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
	
	// Create a new fractal with the selected SaveVeiw. Returns null if the save doesn't exist
	public static Fractal loadFractal(String name){
		SavedView save = saves.get(name);
		if(save == null){return null;}
		
		Fractal f;
		
		// Try to create a new instance of the Fractal
		try{
			f = save.type.newInstance();
		}catch(InstantiationException | IllegalAccessException e){throw new FractalTypeException();}
		
		// Set all of the things
		f.setRealRange(save.getMinReal(), save.getMaxReal());
		f.setImaginaryRange(save.getMinImaginary(), save.getMaxImaginary());
		f.setMaxIterations(save.getIterations());
		if(f.getClass() == Julia.class){
			((Julia) f).setFixedComplex(save.getFixedComplex());
		}
		
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
		
		f.forcePaint();
		if(f.getControlPanel() != null){
			f.getControlPanel().updateValues();
		}
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
	
	
	// Delete a save
	public static void delete(String name){
		saves.remove(name);
	}
	
	// Clear all of the saves
	public static void empty(){
		saves.clear();
	}
	
	// Export the saves as a file
	public static void exportSaves(String fileName){
		String[] names = getSavedNames();
		if(names.length < 1){JOptionPane.showMessageDialog(null,"No fractals are currently saved","Note",JOptionPane.INFORMATION_MESSAGE); return;}
		try{
			FileWriter fileStream = new FileWriter(fileName+".fef");
			BufferedWriter writer = new BufferedWriter(fileStream);
			for(int i = 0; i < names.length; i++){
				writer.write(names[i]+"|"+loadView(names[i]).getExportString());
				writer.newLine();
			}
			writer.close();
		}catch (IOException e){
			JOptionPane.showMessageDialog(null,"Error writing to the file","Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void importSaves(File f, boolean overwrite){
		try{
			if(f == null || !f.isFile()){throw new FileNotFoundException();}
			
			// Create the buffered reader from a file reader made from the file(!)
			BufferedReader br = new BufferedReader(new FileReader(f));
			// Main loop for each line (each line should contain one View to be saved)
			String currentLine;
			int lineNum = 0;
			ArrayList<SavedView> veiws = new ArrayList<SavedView>();
			while((currentLine = br.readLine()) != null){
				lineNum++;
				// Split into name and data
				String name = currentLine.split("\\|class ")[0];
				String data = currentLine.split("\\|class ")[1];
				
				// Split the data into parts
				String[] parts = data.split("\\|");
				if(parts.length < 6 || parts.length > 7){
					// Line is not correct skip this line
					System.err.println("Error reading line "+lineNum+": number of components different to expected.");
					continue;
				}
				
				// Check if the class name is real AND if it EXTENDS Fractal
				Class<? extends Fractal> type;
				try{
					type = Class.forName(parts[0]).asSubclass(Fractal.class);
					if(type == Fractal.class){throw new ClassCastException();}
				}catch(ClassNotFoundException e){
					// Catch if the class doesn't exist
					System.err.println("Error reading line "+lineNum+": class "+parts[0]+" does not exist");
					continue;
				}catch(ClassCastException e){
					// Catch if class doesn't EXTEND Fractal (cannot be Fractal)
					System.err.println("Error reading line "+lineNum+": class "+parts[0]+" must be a subclass of Fractal");
					continue;
				}
				
				// Check the range and iteration values
				double minR, maxR, minI, maxI;
				int iterations;
				try{
					minR = Double.parseDouble(parts[1]);
					maxR = Double.parseDouble(parts[2]);
					minI = Double.parseDouble(parts[3]);
					maxI = Double.parseDouble(parts[4]);
					
					iterations = Integer.parseInt(parts[5]);
				}catch(NumberFormatException e){
					// Catch if none of the above can be successfully made into numbers
					System.err.println("Error reading line "+lineNum+": range or iterations values not numbers");
					continue;
				}
				
				// If there is a 7th part, try to extract the ComplexNumber from it. Just skip if one of a null is found or if it doesn't match the expected pattern "(double,double)"
				ComplexNumber fixedComplex = null;
				if(parts.length == 7 && !(parts[6].equals("(null)") || parts[6].equals("null"))){
					//if(parts[6].matches("\\(\\d+\\.\\d+,\\d+\\.\\d+\\)")){
						// Split it up into the real and complex parts
						String[] complexParts;
						complexParts = parts[6].substring(parts[6].indexOf('(')+1, parts[6].lastIndexOf(')')).split(",");

						// Try to put it into a complex number
						try{
							fixedComplex = new ComplexNumber(Double.parseDouble(complexParts[0]),Double.parseDouble(complexParts[1]));
						}catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
							System.err.println("Error reading line "+lineNum+": fixed complex cannot be read");
							continue;
						}
					//}else{
					//	System.err.println("Error reading line "+lineNum+": fixed complex does not match format");
					//}

				}
				
				// Everything has been read! Time to make a SavedVeiw :D
				try{
					veiws.add(new SavedView(name, type, minR, maxR, minI, maxI, iterations, fixedComplex));
				}catch(NameInUseException e){
					System.err.println("Warning creating save on line "+lineNum+": name already in use");
					// Add suffixes to the name until it allows it in
					int suffix = 1;
					while(true){
						try{
							veiws.add(new SavedView(name+"_"+suffix, type, minR, maxR, minI, maxI, iterations, fixedComplex));
							break;
						}catch(NameInUseException e1){
							suffix++;
						}
					}
				}
				
				
			}
			
			// Move all of the things across
			if(veiws.size() > 0){
				// Empty the saves if required
				if(overwrite){SavedView.empty();}
				Iterator<SavedView> it = veiws.iterator();
				while(it.hasNext()){
					try{
						SavedView.save(it.next());
					}catch(NameInUseException e){}
				}
			}else{
				JOptionPane.showMessageDialog(null,"File "+f.getName()+" contains no new or correctly defined Fractals","Note",JOptionPane.INFORMATION_MESSAGE);
			}
			
			// Close the buffered reader
			br.close();
		}catch(FileNotFoundException e){
			// Show message on error
			JOptionPane.showMessageDialog(null,"File "+f.getName()+" not found","Error",JOptionPane.ERROR_MESSAGE);
		}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error reading from the file","Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
}


// Thrown if the name is already in use OR if the name was null OR the empty string ("")
@SuppressWarnings("serial")
class NameInUseException extends Exception{
	
	private String usedName;
	private String message;
	private String namedMessage;
	
	public NameInUseException(String name){
		usedName = name;
		
		if(name == null){
			message = "The name given was a null pointer";
			namedMessage = "A name must be given (cannot be a null pointer)";
			}else{
		if(name.equals("")){
			message = "The name given was the empty string (\"\")";
			namedMessage = "A name must be given";
			}else{
				message = "The name given is already in use";
				namedMessage = name+" is aready in use";
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
	
	public String getNamedMessage(){
		return namedMessage;
	}
	
}

// Thrown if the Fractal type is just Fractal, and not a subclass
@SuppressWarnings("serial")
class FractalTypeException extends RuntimeException{
	public FractalTypeException(){
		super("Type cannot be 'Fractal'");
	}
}
