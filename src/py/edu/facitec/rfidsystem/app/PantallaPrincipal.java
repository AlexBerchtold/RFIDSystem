package py.edu.facitec.rfidsystem.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import py.edu.facitec.rfidsystem.contenedores.BotonPersonalizado;
import py.edu.facitec.rfidsystem.contenedores.PanelFondo;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;

public class PantallaPrincipal extends JFrame {

	private PanelFondo contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaPrincipal frame = new PantallaPrincipal();
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
	public PantallaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 680);
		setLocationRelativeTo(this);
		//setExtendedState(MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMovimientos = new JMenu("Movimientos");
		menuBar.add(mnMovimientos);
		
		JMenuItem mntmAccesos = new JMenuItem("Accesos");
		mnMovimientos.add(mntmAccesos);
		
		JMenuItem mntmMovimientos = new JMenuItem("Movimientos");
		mnMovimientos.add(mntmMovimientos);
		
		JMenu mnTablas = new JMenu("Tablas");
		menuBar.add(mnTablas);
		
		JMenuItem mntmEmpleados = new JMenuItem("Empleados");
		mnTablas.add(mntmEmpleados);
		
		JMenuItem mntmOficinas = new JMenuItem("Oficinas");
		mnTablas.add(mntmOficinas);
		
		JMenuItem mntmPuertas = new JMenuItem("Puertas");
		mnTablas.add(mntmPuertas);
		
		JMenu mnInformes = new JMenu("Informes");
		menuBar.add(mnInformes);
		
		JMenuItem mntmListadoDeEmpleado = new JMenuItem("Listado de Empleado");
		mnInformes.add(mntmListadoDeEmpleado);
		
		JMenuItem mntmListadoDePuerta = new JMenuItem("Listado de Puerta");
		mnInformes.add(mntmListadoDePuerta);
		
		JMenuItem mntmInformeDeAcceso = new JMenuItem("Informe de Acceso");
		mnInformes.add(mntmInformeDeAcceso);
		
		JMenuItem mntmInformeDeMovimiento = new JMenuItem("Informe de Movimiento");
		mnInformes.add(mntmInformeDeMovimiento);
		
		JMenu mnUtilidades = new JMenu("Utilidades");
		menuBar.add(mnUtilidades);
		
		JMenuItem mntmInicializacionDeDatos = new JMenuItem("Inicializacion de Datos");
		mnUtilidades.add(mntmInicializacionDeDatos);
		
		JMenuItem mntmConfiguraciones = new JMenuItem("Configuraciones");
		mnUtilidades.add(mntmConfiguraciones);
		contentPane = new PanelFondo();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBorderPainted(false);//quitar el borde
		toolBar.setOpaque(false);//dejar transparente
		toolBar.setFloatable(false);//para que no sea movible
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		BotonPersonalizado btnprsnlzdEmpleados = new BotonPersonalizado();
		btnprsnlzdEmpleados.setText("Empleados");
		btnprsnlzdEmpleados.setIcon("empleado");
		toolBar.add(btnprsnlzdEmpleados);
		
		BotonPersonalizado btnprsnlzdOficina = new BotonPersonalizado();
		btnprsnlzdOficina.setText("Oficina");
		btnprsnlzdOficina.setIcon("oficina");
		toolBar.add(btnprsnlzdOficina);
		
		BotonPersonalizado btnprsnlzdAcceso = new BotonPersonalizado();
		btnprsnlzdAcceso.setText("Acceso");
		btnprsnlzdAcceso.setIcon("acceso");
		toolBar.add(btnprsnlzdAcceso);
		
		BotonPersonalizado btnprsnlzdMovimiento = new BotonPersonalizado();
		btnprsnlzdMovimiento.setText("Movimiento");
		btnprsnlzdMovimiento.setIcon("movimiento");
		toolBar.add(btnprsnlzdMovimiento);
	}

}
