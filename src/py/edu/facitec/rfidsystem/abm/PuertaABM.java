package py.edu.facitec.rfidsystem.abm;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;

import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import py.edu.facitec.rfidsystem.buscadores.BuscadorOficina;
import py.edu.facitec.rfidsystem.contenedores.BotonPersonalizadoABM;
import py.edu.facitec.rfidsystem.dao.FuncionarioDao;
import py.edu.facitec.rfidsystem.dao.OficinaDao;
import py.edu.facitec.rfidsystem.dao.PermisoAccesoDao;
import py.edu.facitec.rfidsystem.dao.PuertaDao;
import py.edu.facitec.rfidsystem.entidad.Funcionario;
import py.edu.facitec.rfidsystem.entidad.Oficina;
import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;
import py.edu.facitec.rfidsystem.entidad.Puerta;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadoPuerta;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscardorOficina;
import py.edu.facitec.rfidsystem.tablas.TablaPuerta;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class PuertaABM extends GenericABM implements InterfazBuscardorOficina {
	private JTextField tfBuscar;
	private JTextField tfCodigo;
	private JTextField tfOficina;
	private JTextField tfDescripcion;
	private JTextField tfNumeroDePuertas;
	private JCheckBox chckbxInactivo;
	private JCheckBox chckbxActivo;
	private PermisoAccesoDao permisoAccesoDao;
	private List<PermisoAcceso> permisoAccesos;
	private Puerta puerta;
	private Oficina oficina;
	private PuertaDao dao;
	private List<Puerta> puertas;
	private TablaPuerta tablaPuerta;
	private BotonPersonalizadoABM btnBuscador;
	private JCheckBox chckbxTodos;
	private JCheckBox chckbxActivos;
	private JCheckBox chckbxInactivos;
	private JLabel lblnroDePuerta;
	
	public PuertaABM() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PuertaABM.class.getResource("/py/edu/facitec/rfidsystem/img/puertas.png")));
		scrollPane.setBounds(400, 168, 384, 318);
		setTitle("Registro de Puerta");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		setBounds(100, 100, 800, 530);
		setLocationRelativeTo(this);
		JLabel label = new JLabel("C\u00F3digo:");
		label.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(label);
		
		tablaPuerta=new TablaPuerta();
		table.setModel(tablaPuerta);
		
		JLabel lblOficina = new JLabel("Oficina:");
		lblOficina.setFont(new Font("Arial", Font.BOLD, 12));
		lblOficina.setBounds(9, 216, 102, 14);
		getContentPane().add(lblOficina);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setFont(new Font("Arial", Font.BOLD, 12));
		lblDescripcin.setBounds(10, 271, 102, 14);
		getContentPane().add(lblDescripcin);
		
		JLabel lblNroDePuerta = new JLabel("Nro. de Puerta:");
		lblNroDePuerta.setFont(new Font("Arial", Font.BOLD, 12));
		lblNroDePuerta.setBounds(10, 324, 102, 14);
		getContentPane().add(lblNroDePuerta);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
		lblEstado.setBounds(10, 376, 113, 14);
		getContentPane().add(lblEstado);
		
		 chckbxActivo = new JCheckBox("Activo");
		 chckbxActivo.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		chckbxInactivo.setSelected(false);
		 	}
		 });
		chckbxActivo.setEnabled(false);
		chckbxActivo.setBounds(129, 373, 97, 23);
		getContentPane().add(chckbxActivo);
		
		chckbxInactivo = new JCheckBox("Inactivo");
		chckbxInactivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxActivo.setSelected(false);
			}
		});
		chckbxInactivo.setEnabled(false);
		chckbxInactivo.setBounds(242, 373, 97, 23);
		getContentPane().add(chckbxInactivo);
		
		JLabel lblRegistroDePuerta = new JLabel("Registro De Puerta");
		lblRegistroDePuerta.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDePuerta.setForeground(Color.BLUE);
		lblRegistroDePuerta.setFont(new Font("Algerian", Font.PLAIN, 24));
		lblRegistroDePuerta.setBounds(47, 6, 667, 53);
		getContentPane().add(lblRegistroDePuerta);
		
		tfBuscar = new JTextField();
		tfBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					buscarPuerta();
				}
			}
		});
		tfBuscar.setColumns(10);
		tfBuscar.setBounds(517, 97, 267, 20);
		getContentPane().add(tfBuscar);
		
		BotonPersonalizadoABM btnBuscar = new BotonPersonalizadoABM();
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscarPuerta();
			}
		});
		btnBuscar.setText("Buscar");
		btnBuscar.setBounds(400, 94, 90, 26);
		getContentPane().add(btnBuscar);
		
		tfCodigo = new JTextField();
		tfCodigo.setEnabled(false);
		tfCodigo.setBounds(112, 162, 97, 20);
		getContentPane().add(tfCodigo);
		tfCodigo.setColumns(10);
		
		tfOficina = new JTextField();
		tfOficina.setEditable(false);
		tfOficina.setBounds(111, 214, 210, 20);
		getContentPane().add(tfOficina);
		tfOficina.setColumns(10);
		
		tfDescripcion = new JTextField();
		tfDescripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfNumeroDePuertas.requestFocus();
				}
			}
		});
		tfDescripcion.setEnabled(false);
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(111, 269, 210, 20);
		getContentPane().add(tfDescripcion);
		
		tfNumeroDePuertas = new JTextField();
		tfNumeroDePuertas.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				verificarNroPuerta();
			}
		});
		tfNumeroDePuertas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c!= e.VK_BACK_SPACE & c!= e.VK_ENTER) {
					e.consume();
					lblnroDePuerta.setText("*Solo Numeros");
					lblnroDePuerta.setVisible(true);
				}else{
					lblnroDePuerta.setVisible(false);
				}
			}
		});
		tfNumeroDePuertas.setEnabled(false);
		tfNumeroDePuertas.setColumns(10);
		tfNumeroDePuertas.setBounds(111, 322, 102, 20);
		getContentPane().add(tfNumeroDePuertas);
		
		btnBuscador = new BotonPersonalizadoABM();
		btnBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					tfDescripcion.requestFocus();
				}
			}
		});
		btnBuscador.setEnabled(false);
		btnBuscador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirBuscador();
			}
		});
		btnBuscador.setText("...");
		btnBuscador.setBounds(333, 214, 46, 20);
		getContentPane().add(btnBuscador);
		
		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setFont(new Font("Arial", Font.BOLD, 12));
		lblCodigo.setBounds(10, 165, 102, 14);
		getContentPane().add(lblCodigo);
		
		chckbxTodos = new JCheckBox("Todos");
		chckbxTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxActivos.setSelected(false);
				chckbxInactivos.setSelected(false);
				chckbxTodos.setSelected(true);
				buscarPuerta();
			}
		});
		chckbxTodos.setBounds(439, 138, 97, 23);
		getContentPane().add(chckbxTodos);
		
		chckbxActivos = new JCheckBox("Activos");
		chckbxActivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxActivos.setSelected(true);
				chckbxInactivos.setSelected(false);
				chckbxTodos.setSelected(false);
				buscarPuerta();
			}
		});
		chckbxActivos.setBounds(565, 138, 97, 23);
		getContentPane().add(chckbxActivos);
		
		chckbxInactivos = new JCheckBox("Inactivos");
		chckbxInactivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxActivos.setSelected(false);
				chckbxInactivos.setSelected(true);
				chckbxTodos.setSelected(false);
				buscarPuerta();
			}
		});
		chckbxInactivos.setBounds(687, 138, 97, 23);
		getContentPane().add(chckbxInactivos);
		
		lblnroDePuerta = new JLabel("*Nro. de Puerta Duplicado");
		lblnroDePuerta.setForeground(Color.RED);
		lblnroDePuerta.setVisible(false);
		lblnroDePuerta.setBounds(112, 344, 150, 14);
		getContentPane().add(lblnroDePuerta);
		consultarPuertas();
	}
	
	//-------------------------Metodos Genericos----------------------
	@Override
	protected void limpiar() {
		lblnroDePuerta.setVisible(false);
		tfCodigo.setText("");
		tfDescripcion.setText("");
		tfOficina.setText("");
		tfNumeroDePuertas.setText("");
		chckbxActivo.setSelected(false);
		chckbxInactivo.setSelected(false);
	}

	@Override
	protected void habilitarCampos(boolean e) {
		tfDescripcion.setEnabled(e);
		tfNumeroDePuertas.setEnabled(e);
		chckbxActivo.setEnabled(e);
		chckbxInactivo.setEnabled(e);
		btnBuscador.setEnabled(e);
	}

	@Override
	protected void guardar() {
		if (campoObligatorio()==true) return;
		if(verificarNroPuerta()==true)return;
		cargarDatos();
		dao = new PuertaDao();
		dao.insertarOModificar(puerta);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
		modificar=false;
		consultarPuertas();
		habilitarCampos(false);
		limpiar();
		accionesPrimarias(true);
		puerta=null;
	}

	@Override
	protected void fechaActual() {}
	
	@Override
	protected void cargarFormulario(int index) {
		limpiar();
		if (index < 0) return;
		puerta = puertas.get(index);
		oficina= puerta.getOficina();
		tfCodigo.setText(""+puerta.getId());
		tfDescripcion.setText(puerta.getDescripcion());
		tfNumeroDePuertas.setText(""+puerta.getNumeroDePuerta());
		tfOficina.setText(puerta.getOficina().getDescripcion());
		if (puerta.isEstado()==true) {
			chckbxActivo.setSelected(true);
		}else{
			chckbxInactivo.setSelected(true);
		}
		habilitarCampos(false);
		accionesPrimarias(false);
	}
	
	public void cargarCodigo(){
		dao = new PuertaDao();
		puertas = dao.recuperarTodo();
		int c =0, i=0;
		i= puertas.size()-1;
		if (i>=0) c = puertas.get(i).getId();
		tfCodigo.setText(""+(c+1));
		tfDescripcion.requestFocus();
	}
	
	private void eliminar() {
		if(verificarRelacion()==false) return;
		if (table.getSelectedRow()<0) return;
		int respuesta = JOptionPane.showConfirmDialog(null, 
				"Esta seguro que desea eliminar la Puerta "+puerta.getDescripcion(),
				"Atenci�n", JOptionPane.YES_NO_OPTION);
		if(respuesta == JOptionPane.YES_OPTION){
			dao = new PuertaDao();
			dao.eliminar(puerta);
			try {
				dao.ejecutar();
			} catch (Exception e) {
				dao.cancelar();
			}
			consultarPuertas();
		}
		limpiar();
		accionesPrimarias(true);
	}
	
	private void cargarDatos() {
		if (modificar==false) puerta = new Puerta();
		puerta.setDescripcion(tfDescripcion.getText());
		puerta.setNumeroDePuerta(Integer.parseInt(tfNumeroDePuertas.getText()));
		puerta.setOficina(oficina);
		if (chckbxActivo.isSelected()==true) puerta.setEstado(true);
		if (chckbxInactivo.isSelected()==true) puerta.setEstado(false);
	}
	
	private void consultarPuertas() {
		dao = new PuertaDao();
		puertas = dao.recuperarTodo();
		tablaPuerta.setLista(puertas);
		tablaPuerta.fireTableDataChanged();
		chckbxTodos.setSelected(true);
	}
	
	private void buscarPuerta() {
		dao = new PuertaDao();
		if (chckbxTodos.isSelected()==true) puertas = dao.recuperarPorFiltro(tfBuscar.getText());
		if (chckbxActivos.isSelected()==true) puertas = dao.recuperarActivo(tfBuscar.getText());
		if (chckbxInactivos.isSelected()==true) puertas = dao.recuperarInactivo(tfBuscar.getText());
		tablaPuerta.setLista(puertas);
		tablaPuerta.fireTableDataChanged();
		System.err.println("Josa");
	}
	
	@Override
	public void setOficina(Oficina oficina) {
		tfOficina.setText(oficina.getDescripcion());
		this.oficina= oficina;
	}
	
	public void abrirBuscador(){
		BuscadorOficina buscadorOficina= new BuscadorOficina();
		buscadorOficina.setInterfaz(this);
		buscadorOficina.setVisible(true);
	}
	

	//-----------------------Validaciones----------------------
	private boolean campoObligatorio() {
		if(tfDescripcion.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Descripcion es un campo obligatorio");
			tfDescripcion.requestFocus();
			return true;
		}
		if(tfOficina.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Oficina es un campo obligatorio");
			tfOficina.requestFocus();
			return true;
		}
		if ((chckbxActivo.isSelected()==false & chckbxInactivo.isSelected()==false)|(chckbxActivo.isSelected()==true & chckbxInactivo.isSelected()==true)) {
			JOptionPane.showMessageDialog(null, "Ingrece un Estado Valido");
			chckbxActivo.requestFocus();
			return true;
		}
		return false;
	}
	
	private boolean verificarRelacion() {
		permisoAccesoDao = new PermisoAccesoDao();
		permisoAccesos = permisoAccesoDao.recuperarTodo();
		for (int i = 0; i < permisoAccesos.size(); i++) {
			if (Integer.parseInt(tfCodigo.getText())==permisoAccesos.get(i).getFuncionario().getId()) {
				JOptionPane.showMessageDialog(null, "Puerta Con Permisos de Acceso", "Atenci�n",JOptionPane.ERROR_MESSAGE);
				chckbxActivo.requestFocus();
				return false;
			}
		}
		return true;
	}

	private boolean verificarNroPuerta() {
		if (tfNumeroDePuertas.getText().isEmpty()) {
			lblnroDePuerta.setVisible(false);
			return false;
		}
		if (modificar==true) {
			for (int i = 0; i < puertas.size(); i++) {
				if (Integer.parseInt(tfNumeroDePuertas.getText())==puertas.get(i).getNumeroDePuerta()
						& Integer.parseInt(tfCodigo.getText())!=puertas.get(i).getId()) {
					lblnroDePuerta.setText("*Nro. De Puerta Duplicado");
					lblnroDePuerta.setVisible(true);
					return true;
				}
			}
		}else{
			for (int i = 0; i < puertas.size(); i++) {
				if (Integer.parseInt(tfNumeroDePuertas.getText())==puertas.get(i).getNumeroDePuerta()) {
					lblnroDePuerta.setText("*Nro. De Puerta Duplicado");
					lblnroDePuerta.setVisible(true);
					return true;
				}
			}
		}
		lblnroDePuerta.setVisible(false);
		return false;
	}
	
	//Metodo para inicializar los datos
	public void inicializarPuerta(){
		String tabla = "Puerta";
		dao = new PuertaDao();
		dao.eliminarTodos(tabla);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
	}
}
