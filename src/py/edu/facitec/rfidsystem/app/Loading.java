package py.edu.facitec.rfidsystem.app;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import net.sf.jasperreports.engine.JRException;
import py.edu.facitec.rfidsystem.contenedores.LoadigPanel;
import py.edu.facitec.rfidsystem.util.ConexionReportes;
import py.edu.facitec.rfidsystem.util.Factory;

public class Loading extends JFrame {

	private LoadigPanel contentPane;
	private JLabel lblCargando;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loading frame = new Loading();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Loading() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new LoadigPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(this);		
		
		lblCargando = new JLabel("Cargando . . .");
		lblCargando.setFont(new Font("Stencil", Font.PLAIN, 38));
		lblCargando.setForeground(Color.WHITE);
		lblCargando.setBounds(10, 251, 248, 49);
		contentPane.add(lblCargando);
		
		
		lblVersin = new JLabel("Versi\u00F3n 1.9");
		lblVersin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVersin.setForeground(Color.WHITE);
		lblVersin.setBounds(345, 268, 95, 20);
		contentPane.add(lblVersin);
		abrir.start();
	}
	
	Timer abrir = new Timer(500, new ActionListener() {	
		public void actionPerformed(ActionEvent e) {
			abrirMenu();
			abrir.stop();
		}
	});
	private JLabel lblVersin;
	
	public void abrirMenu(){
		Factory.setUp();
		conectarReporte();
		PantallaPrincipal pantallaPrincipal = new PantallaPrincipal();
		pantallaPrincipal.setVisible(true);
		dispose();
	}
	
	private void conectarReporte(){
		ConexionReportes<PantallaPrincipal> conexionReportes = new ConexionReportes<PantallaPrincipal>();
		try {
			conexionReportes.primeraConexion();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
