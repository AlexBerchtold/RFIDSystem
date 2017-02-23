package py.edu.facitec.rfidsystem.contenedores;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

public class BotonPersonalizadoABM extends JButton{

	//caracteristicas de los botones
	public BotonPersonalizadoABM() {
		setMaximumSize(new Dimension(80, 27));
		setForeground(Color.BLACK);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setBorderPainted(true);
		setBorder(new LineBorder(Color.BLACK));
		//setBackground(Color.YELLOW);
		setOpaque(false);
		
		
	}
	
	//metodo para pasar la imagen del icono al boton
	public void setIcon(String icono) {
		setIcon(new ImageIcon(BotonPersonalizadoABM.class.getResource("/py/edu/facitec/rfidsystem/img/"+icono+".png")));
		
	}
	
	
}
