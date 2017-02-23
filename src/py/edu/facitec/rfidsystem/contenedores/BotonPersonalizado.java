package py.edu.facitec.rfidsystem.contenedores;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class BotonPersonalizado extends JButton{

	public BotonPersonalizado() {
		setMaximumSize(new Dimension(100, 100));
		setForeground(new Color(255,255,255,255));
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setBorderPainted(false);
		//setBackground(Color.YELLOW);
		setOpaque(false);
		
	}
	public void setIcon(String icono) {
		setIcon(new ImageIcon(BotonPersonalizado.class.getResource("/py/edu/facitec/rfidsystem/img/"+icono+".png")));
		
	}
	
	
}
