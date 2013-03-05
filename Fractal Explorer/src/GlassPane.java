import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class GlassPane extends JComponent{
	
	Fractal F;
	
	
	public GlassPane(Fractal f){
		this.F = f;
		this.setVisible(true);
		this.repaint();
	}
	
	protected void paintComponent(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(200, 200, 70, 70);
	}
	

}
