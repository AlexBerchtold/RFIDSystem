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
import py.edu.facitec.rfidsystem.contenedores.JMenuItemPersonalizado;
import javax.swing.JLabel;
import java.awt.Color;

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
		setTitle("RFID System  1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 680);
		setLocationRelativeTo(this);
		setExtendedState(MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMovimientos = new JMenu("Movimientos");
		menuBar.add(mnMovimientos);
		
		JMenuItemPersonalizado mntmprsnlzdAcceso = new JMenuItemPersonalizado();
		mntmprsnlzdAcceso.setText("Acceso");
		mntmprsnlzdAcceso.setIcon("accesos");
		mnMovimientos.add(mntmprsnlzdAcceso);
		
		JMenuItemPersonalizado mntmprsnlzdMovimiento = new JMenuItemPersonalizado();
		mntmprsnlzdMovimiento.setText("Movimiento");
		mntmprsnlzdMovimiento.setIcon("movimientos");
		mnMovimientos.add(mntmprsnlzdMovimiento);
		
		JMenu mnTablas = new JMenu("Tablas");
		menuBar.add(mnTablas);
		
		JMenuItemPersonalizado mntmprsnlzdEmpleado = new JMenuItemPersonalizado();
		mntmprsnlzdEmpleado.setText("Empleados");
		mntmprsnlzdEmpleado.setIcon("empleados");
		mnTablas.add(mntmprsnlzdEmpleado);
		
		JMenuItemPersonalizado mntmprsnlzdOficina = new JMenuItemPersonalizado();
		mntmprsnlzdOficina.setText("Oficinas");
		mntmprsnlzdOficina.setIcon("oficinas");
		mnTablas.add(mntmprsnlzdOficina);
		
		JMenuItemPersonalizado mntmprsnlzdPuerta = new JMenuItemPersonalizado();
		mntmprsnlzdPuerta.setText("Puertas");
		mntmprsnlzdPuerta.setIcon("puertas");
		mnTablas.add(mntmprsnlzdPuerta);
		
		JMenu mnInformes = new JMenu("Informes");
		menuBar.add(mnInformes);
		
		JMenuItemPersonalizado mntmprsnlzdListadoDeEmpleados = new JMenuItemPersonalizado();
		mntmprsnlzdListadoDeEmpleados.setText("Listado de Empleado");
		mntmprsnlzdListadoDeEmpleados.setIcon("listaempleado");
		mnInformes.add(mntmprsnlzdListadoDeEmpleados);
		
		JMenuItemPersonalizado mntmprsnlzdListadoDePuerta = new JMenuItemPersonalizado();
		mntmprsnlzdListadoDePuerta.setText("Listado de Puerta");
		mntmprsnlzdListadoDePuerta.setIcon("listapuerta");
		mnInformes.add(mntmprsnlzdListadoDePuerta);
		
		JMenuItemPersonalizado mntmprsnlzdInformesDeAcceso = new JMenuItemPersonalizado();
		mntmprsnlzdInformesDeAcceso.setText("Informe de Acceso");
		mntmprsnlzdInformesDeAcceso.setIcon("listaacceso");
		mnInformes.add(mntmprsnlzdInformesDeAcceso);
		
		JMenuItemPersonalizado mntmprsnlzdInformeDeMovimiento = new JMenuItemPersonalizado();
		mntmprsnlzdInformeDeMovimiento.setText("Informe de Movimiento");
		mntmprsnlzdInformeDeMovimiento.setIcon("listamovimiento");
		mnInformes.add(mntmprsnlzdInformeDeMovimiento);
		
		JMenu mnUtilidades = new JMenu("Utilidades");
		menuBar.add(mnUtilidades);
		
		JMenuItemPersonalizado mntmprsnlzdInicializacionDeDatos = new JMenuItemPersonalizado();
		mntmprsnlzdInicializacionDeDatos.setText("Inicializaci\u00F3n de Datos");
		mntmprsnlzdInicializacionDeDatos.setIcon("database");
		mnUtilidades.add(mntmprsnlzdInicializacionDeDatos);
		
		JMenuItemPersonalizado mntmprsnlzdConfiguraciones = new JMenuItemPersonalizado();
		mntmprsnlzdConfiguraciones.setText("Configuraciones");
		mntmprsnlzdConfiguraciones.setIcon("configuracion");
		mnUtilidades.add(mntmprsnlzdConfiguraciones);
		contentPane = new PanelFondo();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBorderPainted(false);
		toolBar.setEnabled(false);
		toolBar.setForeground(new Color(240, 240, 240));
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
