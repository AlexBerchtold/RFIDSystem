package py.edu.facitec.rfidsystem.contenedores;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class JMenuItemPersonalizado extends JMenuItem{

	//caracteristicas del boton
	public JMenuItemPersonalizado() {
		//setMaximumSize(new Dimension(200, 200));
		setOpaque(false);
	}
	
	//metodo para setear la imagen de icono
	public void setIcon(String icono){
		setIcon(new ImageIcon(JMenuItemPersonalizado.class.getResource("/py/edu/facitec/rfidsystem/img/"+icono+".png")));
	}

	
	
	

}
