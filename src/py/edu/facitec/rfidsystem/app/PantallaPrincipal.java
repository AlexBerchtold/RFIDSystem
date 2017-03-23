package py.edu.facitec.rfidsystem.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import py.edu.facitec.rfidsystem.abm.BloqueABM;
import py.edu.facitec.rfidsystem.abm.FuncionarioABM;
import py.edu.facitec.rfidsystem.abm.InstitucionABM;
import py.edu.facitec.rfidsystem.abm.MovimientoABM;
import py.edu.facitec.rfidsystem.abm.OficinaABM;
import py.edu.facitec.rfidsystem.abm.PermisoAccesoABM;
import py.edu.facitec.rfidsystem.abm.PuertaABM;
import py.edu.facitec.rfidsystem.contenedores.BotonPersonalizado;
import py.edu.facitec.rfidsystem.contenedores.BotonPersonalizadoABM;
import py.edu.facitec.rfidsystem.contenedores.JMenuItemPersonalizado;
import py.edu.facitec.rfidsystem.contenedores.PanelFondo;
import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;
import py.edu.facitec.rfidsystem.util.Factory;

public class PantallaPrincipal extends JFrame implements KeyEventDispatcher {

	private PanelFondo contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Factory.setUp();
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(PantallaPrincipal.class.getResource("/py/edu/facitec/rfidsystem/img/icono.png")));
        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this); 
		
		setTitle("RFID System  1.6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 680);
		setLocationRelativeTo(this);
		setExtendedState(MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMovimientos = new JMenu("Movimientos");
		menuBar.add(mnMovimientos);
		
		JMenuItemPersonalizado mntmprsnlzdAcceso = new JMenuItemPersonalizado();
		mntmprsnlzdAcceso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioPermiso();
			}
		});
		mntmprsnlzdAcceso.setText("Acceso");
		mntmprsnlzdAcceso.setIcon("accesos");
		mnMovimientos.add(mntmprsnlzdAcceso);
		
		JMenuItemPersonalizado mntmprsnlzdMovimiento = new JMenuItemPersonalizado();
		mntmprsnlzdMovimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioMovimiento();
			}
		});
		mntmprsnlzdMovimiento.setText("Movimiento");
		mntmprsnlzdMovimiento.setIcon("movimientos");
		mnMovimientos.add(mntmprsnlzdMovimiento);
		
		JMenu mnTablas = new JMenu("Tablas");
		menuBar.add(mnTablas);
		
		JMenuItemPersonalizado mntmprsnlzdFuncionario = new JMenuItemPersonalizado();
		mntmprsnlzdFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioFuncionario();
			}
		});
		
		JMenuItemPersonalizado mntmprsnlzdBloque = new JMenuItemPersonalizado();
		mntmprsnlzdBloque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioBloque();
			}
		});
		mntmprsnlzdBloque.setText("Bloque");
		mntmprsnlzdBloque.setIcon("bloque");
		mnTablas.add(mntmprsnlzdBloque);
		mntmprsnlzdFuncionario.setText("Funcionario");
		mntmprsnlzdFuncionario.setIcon("empleados");
		mnTablas.add(mntmprsnlzdFuncionario);
		
		JMenuItemPersonalizado mntmprsnlzdInstitucin = new JMenuItemPersonalizado();
		mntmprsnlzdInstitucin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirFormularioInstitucion();
			}
		});
		mntmprsnlzdInstitucin.setText("Instituci\u00F3n");
		mntmprsnlzdInstitucin.setIcon("institucion");
		mnTablas.add(mntmprsnlzdInstitucin);
		
		JMenuItemPersonalizado mntmprsnlzdOficina = new JMenuItemPersonalizado();
		mntmprsnlzdOficina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioOficina();
			}
		});
		mntmprsnlzdOficina.setText("Oficinas");
		mntmprsnlzdOficina.setIcon("oficinas");
		mnTablas.add(mntmprsnlzdOficina);
		
		JMenuItemPersonalizado mntmprsnlzdPuerta = new JMenuItemPersonalizado();
		mntmprsnlzdPuerta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioPuerta();
			}
		});
		mntmprsnlzdPuerta.setText("Puertas");
		mntmprsnlzdPuerta.setIcon("puertas");
		mnTablas.add(mntmprsnlzdPuerta);
		
		JMenu mnInformes = new JMenu("Informes");
		menuBar.add(mnInformes);
		
		JMenuItemPersonalizado mntmprsnlzdListadoDeEmpleados = new JMenuItemPersonalizado();
		mntmprsnlzdListadoDeEmpleados.setEnabled(false);
		mntmprsnlzdListadoDeEmpleados.setText("Listado de Empleado");
		mntmprsnlzdListadoDeEmpleados.setIcon("listaempleado");
		mnInformes.add(mntmprsnlzdListadoDeEmpleados);
		
		JMenuItemPersonalizado mntmprsnlzdListadoDePuerta = new JMenuItemPersonalizado();
		mntmprsnlzdListadoDePuerta.setEnabled(false);
		mntmprsnlzdListadoDePuerta.setText("Listado de Puerta");
		mntmprsnlzdListadoDePuerta.setIcon("listapuerta");
		mnInformes.add(mntmprsnlzdListadoDePuerta);
		
		JMenuItemPersonalizado mntmprsnlzdInformesDeAcceso = new JMenuItemPersonalizado();
		mntmprsnlzdInformesDeAcceso.setEnabled(false);
		mntmprsnlzdInformesDeAcceso.setText("Informe de Acceso");
		mntmprsnlzdInformesDeAcceso.setIcon("listaacceso");
		mnInformes.add(mntmprsnlzdInformesDeAcceso);
		
		JMenuItemPersonalizado mntmprsnlzdInformeDeMovimiento = new JMenuItemPersonalizado();
		mntmprsnlzdInformeDeMovimiento.setEnabled(false);
		mntmprsnlzdInformeDeMovimiento.setText("Informe de Movimiento");
		mntmprsnlzdInformeDeMovimiento.setIcon("listamovimiento");
		mnInformes.add(mntmprsnlzdInformeDeMovimiento);
		
		JMenu mnUtilidades = new JMenu("Utilidades");
		menuBar.add(mnUtilidades);
		
		JMenuItemPersonalizado mntmprsnlzdInicializacionDeDatos = new JMenuItemPersonalizado();
		mntmprsnlzdInicializacionDeDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int respuesta = JOptionPane.showConfirmDialog(null, 
						"La informacion sera deletada permanentemente, Estas seguro que desea Inicializar los Datos?",
						"Atención",
						JOptionPane.YES_NO_OPTION);
				if (respuesta==JOptionPane.YES_OPTION) {
					inicializarBaseDeDatos();
				}
			}
		});
		mntmprsnlzdInicializacionDeDatos.setText("Inicializaci\u00F3n de Datos");
		mntmprsnlzdInicializacionDeDatos.setIcon("database");
		mnUtilidades.add(mntmprsnlzdInicializacionDeDatos);
		
		JMenuItemPersonalizado mntmprsnlzdConfiguraciones = new JMenuItemPersonalizado();
		mntmprsnlzdConfiguraciones.setEnabled(false);
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
		
		BotonPersonalizado btnprsnlzdFuncionarios = new BotonPersonalizado();
		btnprsnlzdFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirFormularioFuncionario();
			}
		});
		btnprsnlzdFuncionarios.setText("Funcionarios");
		btnprsnlzdFuncionarios.setIcon("empleado");
		toolBar.add(btnprsnlzdFuncionarios);
		
		BotonPersonalizado btnprsnlzdOficina = new BotonPersonalizado();
		btnprsnlzdOficina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioOficina();
			}
		});
		btnprsnlzdOficina.setText("Oficina");
		btnprsnlzdOficina.setIcon("oficina");
		toolBar.add(btnprsnlzdOficina);
		
		BotonPersonalizado btnprsnlzdAcceso = new BotonPersonalizado();
		btnprsnlzdAcceso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioPermiso();
			}
		});
		btnprsnlzdAcceso.setEnabled(false);
		btnprsnlzdAcceso.setText("Acceso");
		btnprsnlzdAcceso.setIcon("acceso");
		toolBar.add(btnprsnlzdAcceso);
		
		BotonPersonalizado btnprsnlzdMovimiento = new BotonPersonalizado();
		btnprsnlzdMovimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioMovimiento();
			}
		});
		btnprsnlzdMovimiento.setEnabled(false);
		btnprsnlzdMovimiento.setText("Movimiento");
		btnprsnlzdMovimiento.setIcon("movimiento");
		toolBar.add(btnprsnlzdMovimiento);
		
	}

	//Metodo para desactivar funciones no deseadas del teclado 
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(e.getID() == e.KEY_PRESSED){
			if(e.getKeyCode()== e.VK_F10 | e.getKeyCode() == e.VK_SPACE){
				e.consume();
			}
		}
		return false;
	}
	
	public void abrirFormularioFuncionario(){
		FuncionarioABM funcionarioABM = new FuncionarioABM();
		funcionarioABM.setVisible(true);
	}
	
	public void abrirFormularioInstitucion() {
		InstitucionABM institucionABM = new InstitucionABM();
		institucionABM.setVisible(true);
	}
	
	public void abrirFormularioPuerta(){
		PuertaABM puertaABM = new PuertaABM();
		puertaABM.setVisible(true);
	}
	
	public void abrirFormularioBloque(){
		BloqueABM bloqueABM = new BloqueABM();
		bloqueABM.setVisible(true);
	}
	
	private void abrirFormularioOficina() {
		OficinaABM oficinaABM = new OficinaABM();
		oficinaABM.setVisible(true);
	}
	
	private void abrirFormularioPermiso() {
		PermisoAccesoABM acceso = new PermisoAccesoABM();
		acceso.setVisible(true);
	}
	
	private void abrirFormularioMovimiento() {
		MovimientoABM movimientoABM = new MovimientoABM();
		movimientoABM.setVisible(true);
	}
	
	private void inicializarBaseDeDatos() {
		PermisoAccesoABM permisoAccesoABM = new PermisoAccesoABM();
		permisoAccesoABM.inicializarPermisoAcceso();
		MovimientoABM movimientoABM = new MovimientoABM();
		movimientoABM.inicializarMovimiento();
		FuncionarioABM funcionarioABM = new FuncionarioABM();
		funcionarioABM.inicializarFuncionario();
		OficinaABM oficinaABM = new OficinaABM();
		oficinaABM.inicializarOficina();
		PuertaABM puertaABM = new PuertaABM();
		puertaABM.inicializarPuerta();
		BloqueABM bloqueABM = new BloqueABM();
		bloqueABM.inicializarBloques();
		InstitucionABM institucionABM = new InstitucionABM();
		institucionABM.inicializarInstitucion();
	}
	
}
