import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JButton;


public class testr extends JPanel {
	private JTextField itDisplay;
	private JTextField minR;
	private JTextField maxR;
	private JTextField minI;
	private JTextField maxI;

	/**
	 * Create the panel.
	 */
	public testr() {
		setLayout(new MigLayout("", "[][grow][][][][][]", "[][][][][][][][]"));
		
		JLabel lblSettings = new JLabel("Settings");
		add(lblSettings, "flowx,cell 3 0 4 1,alignx center");
		
		JLabel lblIterations = new JLabel("Iterations");
		add(lblIterations, "cell 0 1");
		
		JSlider itSlider = new JSlider();
		itSlider.setValue(100);
		itSlider.setMaximum(1000);
		itSlider.setMinimum(10);
		add(itSlider, "cell 1 1");
		
		JLabel lblNewLabel_1 = new JLabel("");
		add(lblNewLabel_1, "cell 2 1");
		
		JToggleButton showAxis = new JToggleButton("Show axis");

		add(showAxis, "cell 4 1 2 1");
		
		itDisplay = new JTextField();
		itDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		itDisplay.setEditable(false);
		add(itDisplay, "cell 1 2,alignx left,aligny center");
		itDisplay.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Ranges");
		add(lblNewLabel, "cell 0 3 2 1,alignx center,aligny center");
		
		JLabel lblReal = new JLabel("Real");
		add(lblReal, "cell 0 4,alignx trailing");
		
		minR = new JTextField();
		minR.setText("-2.0");
		add(minR, "flowx,cell 1 4,alignx left");
		minR.setColumns(10);
		
		JLabel label = new JLabel(" - ");
		add(label, "cell 1 4");
		
		maxR = new JTextField();
		maxR.setText("2.0");
		add(maxR, "cell 1 4,alignx right");
		maxR.setColumns(10);
		
		JLabel lblImaginary = new JLabel("Imaginary");
		add(lblImaginary, "cell 0 5,alignx trailing");
		
		minI = new JTextField();
		minI.setText("-1.6");
		add(minI, "flowx,cell 1 5,alignx left,aligny center");
		minI.setColumns(10);
		
		JLabel label_1 = new JLabel(" - ");
		add(label_1, "cell 1 5");
		
		maxI = new JTextField();
		maxI.setText("1.6");
		add(maxI, "cell 1 5");
		maxI.setColumns(10);
		
		JButton redraw = new JButton("Redraw set");
		add(redraw, "cell 1 7,growx");

	}

}
