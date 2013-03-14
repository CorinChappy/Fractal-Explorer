import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;


@SuppressWarnings("serial")
public class FractalExplorer extends JFrame{

	// Stores the fractal (and control panel, if it exists)
	Fractal F;
	ControlPanel controller;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FractalExplorer().createMandelbrot();
				//new FractalExplorer().createFractal(new BurningShip(), true);
			}
		});
	}


	public FractalExplorer(){
		super("Fractal Explorer");
		this.setPreferredSize(new Dimension(400, 400));
		this.setMinimumSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// Method for creating a generic fractal in the Frame, booleans represent whether
	// to make the panel visible by default and a control panel should be present
	public Fractal createFractal(Fractal f, boolean visible, boolean controls){
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());

		content.add(f, BorderLayout.CENTER);


		// Create and add the user selected point panel
		JPanel uspC = new JPanel(new GridLayout(1,1));
		JTextArea uspT = new JTextArea("Select a point to see its complex number");
		uspT.setEditable(false);
		uspC.add(uspT);
		content.add(uspC,BorderLayout.NORTH);

		// Add the ComplexDisplay
		f.addMouseListener(new ComplexDisplayClicker(f, uspT));

		// Create and attach the control panel
		if(controls){
			// Create and add ZoomListener listeners
			ZoomListener l1 = new ZoomListener(f);
			f.addMouseListener(l1);
			f.addMouseMotionListener(l1);
			f.addMouseWheelListener(l1);

			// Create and add the editing panel
			ControlPanel controller = new ControlPanel(f);
			content.add(controller,BorderLayout.SOUTH);
			this.controller = controller;
			f.setControlPanel(controller);

			this.setPreferredSize(new Dimension(585, 655));
			this.setMinimumSize(new Dimension(535, 575));
		}


		this.setJMenuBar(this.createMenuBar());
		this.setContentPane(content);
		this.pack();
		this.setVisible(visible);
		F = f;
		return f;
	}

	public Fractal createFractal(Fractal f, boolean controls){
		return this.createFractal(f, true, controls);
	}

	// Method to change the currently displayed fractal. Returns the old fractal
	public Fractal changeFractal(Fractal f, boolean controls){
		Fractal oldF = F;
		this.remove(oldF);
		this.remove(controller);
		this.add(f, BorderLayout.CENTER);

		if(controls){
			// Check if the fractal already has an associated control panel
			if(f.getControlPanel() != null){
				controller = f.getControlPanel();
				this.add(controller, BorderLayout.SOUTH);
			}else{
				// Create the control panel and add it to the required places
				// Create and add ZoomListener listeners
				ZoomListener l1 = new ZoomListener(f);
				f.addMouseListener(l1);
				f.addMouseMotionListener(l1);

				// Create and add the editing panel
				ControlPanel controller = new ControlPanel(f);
				this.add(controller,BorderLayout.SOUTH);
				this.controller = controller;
				f.setControlPanel(controller);

				this.setPreferredSize(new Dimension(585, 655));
				this.setMinimumSize(new Dimension(535, 575));
			}
		}else{
			// Set the sizes to non-control panel style
			this.setPreferredSize(new Dimension(400, 400));
			this.setMinimumSize(new Dimension(400, 400));
			this.controller = null;
		}
		this.F = f;
		return oldF;
	}

	public Fractal changeFractal(Fractal f){
		return this.changeFractal(f, (controller != null));
	}




	public Julia createJulia(ComplexNumber c, int iter){
		Julia j = new Julia(c,iter);
		createFractal(j, false, false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		return j;
	}

	Julia createJulia(ComplexNumber c){
		return this.createJulia(c, Fractal.DEFAULT_ITERATIONS);
	}

	Julia createJulia(){
		return this.createJulia(new ComplexNumber(0,0), Fractal.DEFAULT_ITERATIONS);
	}

	public Mandelbrot createMandelbrot(){

		Mandelbrot m = new Mandelbrot();
		createFractal(m, true);
		m.setJulia(new FractalExplorer().createJulia());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		return m;
	}



	// Maker and listener for the Jmenu
	private JMenuBar createMenuBar(){
		// Create the MenuBar
		JMenuBar menuBar = new JMenuBar();

		// Create the save menu
		JMenu saveMenu = new JMenu("Save current fractal", true);
		saveMenu.setSize(50, 10);
		saveMenu.setMnemonic(KeyEvent.VK_S);

		JMenuItem saveBox = new JMenuItem("                                                 ");
		saveBox.setSize(50,10);
		saveBox.setMnemonic(KeyEvent.VK_N);
		saveBox.setEnabled(false);
		saveBox.setLayout(new BorderLayout());
		JPanel saveBoxPanel = new JPanel();
		saveBoxPanel.setLayout(new BorderLayout());
		saveBox.add(saveBoxPanel, BorderLayout.CENTER);
		saveBoxPanel.add(new JLabel("Name: "), BorderLayout.WEST);
		final JTextField tNameBox = new JTextField(100);
		saveBoxPanel.add(tNameBox, BorderLayout.CENTER);
		saveMenu.add(saveBox);

		JMenuItem saveButton = new JMenuItem("Save");
		saveMenu.add(saveButton);


		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SavedView.save(tNameBox.getText(), F);
				} catch (NameInUseException er) {
					JOptionPane.showMessageDialog(null,
							er.getNamedMessage(),
							"Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				tNameBox.setText("");
			}
		});


		// Create the load menu
		final JMenu loadMenu = new JMenu("Load fractal", true);
		loadMenu.setMnemonic(KeyEvent.VK_L);

		loadMenu.add(new JMenuItem("No fractals saved")).setEnabled(false);

		loadMenu.addMenuListener(new MenuListener(){
			public void menuSelected(MenuEvent e){
				LoadListener loadListener = new LoadListener();
				String[] names = SavedView.getSavedNames();
				loadMenu.removeAll();
				if(names.length < 1){
					loadMenu.add(new JMenuItem("No fractals saved")).setEnabled(false);
				}
				for(int i=0;i<names.length;i++){
					loadMenu.add(new JMenuItem(names[i])).addActionListener(loadListener);
				}
			}
			public void menuDeselected(MenuEvent arg0){}
			public void menuCanceled(MenuEvent arg0){}
			
			// Listener for each of the loads
			class LoadListener implements ActionListener{
				public void actionPerformed(ActionEvent e){
					AbstractButton item = ((AbstractButton) e.getSource());
					// If the window has no control panel then create a new frame to display the fractal
					if(controller == null){
						SavedView.loadFramedFractal(item.getText(), false);
					}else{
						// Import it into the current fractal
						SavedView.loadToFractal(item.getText(), F);
					}
				}
			}
			
			
		});
		
		// Export menu button
		JMenu exportMenu = new JMenu("Export saved fractals");
		exportMenu.setMnemonic(KeyEvent.VK_E);
		JMenuItem exportBox = new JMenuItem("                                                 ");
		exportBox.setSize(50,10);
		exportBox.setMnemonic(KeyEvent.VK_N);
		exportBox.setEnabled(false);
		exportBox.setLayout(new BorderLayout());
		JPanel exportBoxPanel = new JPanel();
		exportBoxPanel.setLayout(new BorderLayout());
		exportBox.add(exportBoxPanel, BorderLayout.CENTER);
		exportBoxPanel.add(new JLabel("File name: "), BorderLayout.WEST);
		final JTextField tExportNameBox = new JTextField(100);
		exportBoxPanel.add(tExportNameBox, BorderLayout.CENTER);
		exportMenu.add(exportBox);

		JMenuItem exportButton = new JMenuItem("Export");
		exportMenu.add(exportButton);
		
		
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!tExportNameBox.getText().equals("")){
					SavedView.exportSaves(tExportNameBox.getText());
				}else{
					JOptionPane.showMessageDialog(null,
							"File name must be supplied",
							"Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				
				tExportNameBox.setText("");
			}
		});
		
		// Make the import button
		JButton importButton = new JButton("Import saved fractals");
		importButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser chooser = new JFileChooser();
			    chooser.setFileFilter(new FileNameExtensionFilter("FEF fractal save files", "fef"));
			    int returnVal = chooser.showOpenDialog(F);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       SavedView.importSaves(chooser.getSelectedFile(), true);
			    }
			}
		});
		

		
		menuBar.add(saveMenu);
		menuBar.add(loadMenu);
		menuBar.add(exportMenu);
		menuBar.add(importButton);
		return menuBar;
	}



	// Listener for displaying the complex number
	private class ComplexDisplayClicker extends MouseAdapter{
		private Fractal f;
		JTextComponent t;


		public ComplexDisplayClicker(Fractal f, JTextComponent t){
			this.f = f;
			this.t = t;
		}

		public void mouseClicked(MouseEvent e){
			ComplexNumber c = f.getComplex(e.getPoint().x, e.getPoint().y);
			t.setText("Complex number: "+c.getReal()+" + "+c.getImaginary()+"i");
		}
	}

	private class ZoomListener extends MouseAdapter{

		Fractal f;

		// Start drag point
		private Point startDrag;

		public ZoomListener(Fractal f){
			this.f = f;
		}

		// Sets the start of the drag
		public void mousePressed(MouseEvent e){
			startDrag = e.getPoint();
			f.overlay.startDrag(startDrag);
		}

		public void mouseReleased(MouseEvent e){
			Point endDrag = e.getPoint();
			if(startDrag != null && startDrag.distance(endDrag) > 20){
				ComplexNumber c1 = f.getComplex(startDrag);
				ComplexNumber c2 = f.getComplex(endDrag);
				f.setRealRange(Math.min(c1.getReal(), c2.getReal()), Math.max(c1.getReal(), c2.getReal()));
				f.setImaginaryRange(Math.min(c1.getImaginary(), c2.getImaginary()), Math.max(c1.getImaginary(), c2.getImaginary()));
				f.repaint();
				if(controller != null){controller.updateValues();}
			}
			// Clear the dragging overlay
			f.overlay.clearDrag();
		}

		public void mouseDragged(MouseEvent e){
			f.overlay.updateDrag(e.getPoint());
		}

		public void mouseExited(MouseEvent e){
			f.overlay.clearDrag();
			startDrag = null;
		}

		public void mouseWheelMoved(MouseWheelEvent e){
			double zoomFactor = (e.getWheelRotation() < 0)?1/1.5:1.5;
			/*ComplexNumber zoomPoint = f.getComplex(e.getPoint());


			// Calculate distances and ratios
			Point centre = new Point(f.getWidth()/2,f.getHeight()/2);
			int offsetX = e.getPoint().x - centre.x;
			int offsetY = e.getPoint().y - centre.y;

			// Set the centre as where the mouse is
			//f.setCentre(zoomPoint);
			// Move the display along again
			zoomPoint = f.getComplex(centre.x + offsetX, centre.y + offsetY);
			// Zoom in
			f.setRealRange(f.getMinReal()*zoomFactor, f.getMaxReal()*zoomFactor);
			f.setImaginaryRange(f.getMinImaginary()*zoomFactor, f.getMaxImaginary()*zoomFactor);




			// Set the new centre
			f.setCentre(zoomPoint);*/

			double ratioX = e.getPoint().getX()/f.getWidth();
			double ratioY = e.getPoint().getY()/f.getHeight();

			ComplexNumber zoomPoint = f.getComplex(e.getPoint());

			double newRealMin = zoomPoint.getReal() - ((zoomFactor*(f.getMaxReal()-f.getMinReal()))*ratioX);
			double newRealMax = newRealMin + (f.getMaxReal()-f.getMinReal())*zoomFactor;
			double newImgMin = zoomPoint.getImaginary() - ((zoomFactor*(f.getMaxImaginary()-f.getMinImaginary()))*ratioY);
			double newImgMax = newImgMin + (f.getMaxImaginary()-f.getMinImaginary())*zoomFactor;
			
			// Limit the zoom
			if((newRealMin < f.MINIMUM_REAL() || newRealMax > f.MAXIMUM_REAL()) && (newImgMin < f.MINIMUM_IMAGINARY() || newImgMax > f.MAXIMUM_IMAGINARY())){
				f.setRangesDefault();
			}else{
				f.setRealRange(newRealMin, newRealMax);
				f.setImaginaryRange(newImgMin, newImgMax);
			}
			// Repaint
			f.repaint();
			if(controller != null){controller.updateValues();}
		}
	}

}
