package py.edu.facitec.rfidsystem.abm;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import py.edu.facitec.rfidsystem.buscadores.BuscadorBloque;
import py.edu.facitec.rfidsystem.contenedores.BotonPersonalizadoABM;
import py.edu.facitec.rfidsystem.dao.BloqueDao;
import py.edu.facitec.rfidsystem.dao.OficinaDao;
import py.edu.facitec.rfidsystem.dao.PermisoAccesoDao;
import py.edu.facitec.rfidsystem.dao.PuertaDao;
import py.edu.facitec.rfidsystem.entidad.Bloque;
import py.edu.facitec.rfidsystem.entidad.Oficina;
import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;
import py.edu.facitec.rfidsystem.entidad.Puerta;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadoPuerta;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadorBloque;
import py.edu.facitec.rfidsystem.tablas.TablaOficina;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OficinaABM extends GenericABM implements InterfazBuscadorBloque {
	private JTextField tfBuscar;
	private JTextField tfCodigo;
	private JTextField tfDescripcion;
	private JTextField tfBloque;
	private OficinaDao dao;
	private List<Oficina> oficinas;
	private TablaOficina tablaOficina;
	private JCheckBox cbInactivo;
	private JCheckBox cbActivo;
	private Oficina oficina;
	private BotonPersonalizadoABM btnBuscarBloque;
	private Bloque bloque;
	private PuertaDao puertaDao;
	private List<Puerta> puertas;
	private JCheckBox chckbxTodos;
	private JCheckBox chckbxActivos;
	private JCheckBox chckbxInactivos;
	private PermisoAccesoDao permisoAccesoDao;
	private List<PermisoAcceso> permisoAccesos;
	private JLabel lblcdigoDuplicado;
	
	public OficinaABM() {
		setTitle("Registro de Oficina");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminar();
				}
		});
		setTitle("Registro de Oficina");
		setBounds(100, 100, 697, 449);
		setLocationRelativeTo(this);
		tablaOficina = new TablaOficina();
		table.setModel(tablaOficina);
		btnGuardar.setLocation(10, 381);
		btnCancelar.setLocation(239, 381);
		scrollPane.setBounds(350, 163, 328, 245);
		
		BotonPersonalizadoABM btnBuscar = new BotonPersonalizadoABM();
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscarOficina();
			}
		});
		btnBuscar.setText("Buscar");
		btnBuscar.setBounds(350, 98, 86, 23);
		getContentPane().add(btnBuscar);
		
		tfBuscar = new JTextField();
		tfBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					buscarOficina();
				}
			}
		});
		tfBuscar.setColumns(10);
		tfBuscar.setBounds(446, 98, 232, 20);
		getContentPane().add(tfBuscar);
		
		JLabel lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setFont(new Font("Arial", Font.BOLD, 12));
		lblCodigo.setBounds(10, 168, 46, 14);
		getContentPane().add(lblCodigo);
		
		tfCodigo = new JTextField();
		tfCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c!= e.VK_BACK_SPACE & c!=e.VK_ENTER) {
					e.consume();
					lblcdigoDuplicado.setText("Solo Numeros");
					lblcdigoDuplicado.setVisible(true);
				}else{
					lblcdigoDuplicado.setVisible(false);
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfDescripcion.requestFocus();
				}
			}
		});
		tfCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				verificarCodigo();
			}
		});
		tfCodigo.setEnabled(false);
		tfCodigo.setColumns(10);
		tfCodigo.setBounds(113, 166, 92, 20);
		getContentPane().add(tfCodigo);
		
		tfDescripcion = new JTextField();
		tfDescripcion.setEnabled(false);
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(113, 210, 158, 20);
		getContentPane().add(tfDescripcion);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setFont(new Font("Arial", Font.BOLD, 12));
		lblDescripcin.setBounds(10, 212, 102, 14);
		getContentPane().add(lblDescripcin);
		
		JLabel lblRegistrosDeInstitucin = new JLabel("Registro De Oficina");
		lblRegistrosDeInstitucin.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrosDeInstitucin.setForeground(Color.BLUE);
		lblRegistrosDeInstitucin.setFont(new Font("Algerian", Font.PLAIN, 24));
		lblRegistrosDeInstitucin.setBounds(1, 11, 690, 53);
		getContentPane().add(lblRegistrosDeInstitucin);
		
		JLabel lblIntitucin = new JLabel("Bloque:");
		lblIntitucin.setFont(new Font("Arial", Font.BOLD, 12));
		lblIntitucin.setBounds(10, 259, 102, 14);
		getContentPane().add(lblIntitucin);
		
		tfBloque = new JTextField();
		tfBloque.setEnabled(false);
		tfBloque.setColumns(10);
		tfBloque.setBounds(113, 257, 158, 20);
		getContentPane().add(tfBloque);
		
		btnBuscarBloque = new BotonPersonalizadoABM();
		btnBuscarBloque.setEnabled(false);
		btnBuscarBloque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirBuscador();
			}
		});
		btnBuscarBloque.setText("...");
		btnBuscarBloque.setBounds(281, 257, 46, 20);
		getContentPane().add(btnBuscarBloque);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
		lblEstado.setBounds(10, 311, 102, 14);
		getContentPane().add(lblEstado);
		
		cbActivo = new JCheckBox("Activo");
		cbActivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbInactivo.setSelected(false);
			}
		});
		cbActivo.setEnabled(false);
		cbActivo.setBounds(113, 308, 73, 23);
		getContentPane().add(cbActivo);
		
		cbInactivo = new JCheckBox("Inactivo");
		cbInactivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbActivo.setSelected(false);
			}
		});
		cbInactivo.setEnabled(false);
		cbInactivo.setBounds(188, 308, 90, 23);
		getContentPane().add(cbInactivo);
		
		chckbxTodos = new JCheckBox("Todos");
		chckbxTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxActivos.setSelected(false);
				chckbxInactivos.setSelected(false);
				chckbxTodos.setSelected(true);
				buscarOficina();
			}
		});
		chckbxTodos.setBounds(377, 133, 97, 23);
		getContentPane().add(chckbxTodos);
		
		chckbxActivos = new JCheckBox("Activos");
		chckbxActivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxActivos.setSelected(true);
				chckbxInactivos.setSelected(false);
				chckbxTodos.setSelected(false);
				buscarOficina();
			}
		});
		chckbxActivos.setBounds(476, 133, 97, 23);
		getContentPane().add(chckbxActivos);
		
		chckbxInactivos = new JCheckBox("Inactivos");
		chckbxInactivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxActivos.setSelected(false);
				chckbxInactivos.setSelected(true);
				chckbxTodos.setSelected(false);
				buscarOficina();
			}
		});
		chckbxInactivos.setBounds(581, 133, 97, 23);
		getContentPane().add(chckbxInactivos);
		
		lblcdigoDuplicado = new JLabel("*C\u00F3digo Duplicado");
		lblcdigoDuplicado.setVisible(false);
		lblcdigoDuplicado.setForeground(Color.RED);
		lblcdigoDuplicado.setBounds(113, 186, 128, 14);
		getContentPane().add(lblcdigoDuplicado);
		consultarOficina();
		
	}

//-------------------Metodos Genericos---------------
	@Override
	protected void limpiar() {
		lblcdigoDuplicado.setVisible(false);
		tfCodigo.setText("");
		tfDescripcion.setText("");
		tfBloque.setText("");
		cbActivo.setSelected(false);
		cbInactivo.setSelected(false);
	}

	@Override
	protected void habilitarCampos(boolean e) {
		if (modificar==true) {
			tfCodigo.setEnabled(!e);
		}else {
			tfCodigo.setEnabled(e);
		}
		tfDescripcion.setEnabled(e);
		btnBuscarBloque.setEnabled(e);
		cbActivo.setEnabled(e);
		cbInactivo.setEnabled(e);
	}

	@Override
	protected void guardar() {
		if (campoObligatorio()==true) return;
		if (modificar==false) {
			if(verificarCodigo()==true)return;
		}
		cargarDatos();
		dao = new OficinaDao();
		dao.insertarOModificar(oficina);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
		modificar=false;
		consultarOficina();
		habilitarCampos(false);
		limpiar();
		accionesPrimarias(true);
		oficina=null;
	}

	@Override
	protected void fechaActual() {}
	
	@Override
	protected void cargarFormulario(int index) {
		limpiar();
		if (index < 0) return;
		oficina = oficinas.get(index);
		bloque = oficina.getBloque();
		tfCodigo.setText(oficina.getId()+"");
		tfDescripcion.setText(oficina.getDescripcion());
		tfBloque.setText(oficina.getBloque().getNombre());
		if(oficina.getEstado()==true){
			cbActivo.setSelected(true);
		}else{
			cbInactivo.setSelected(true);
		}
		habilitarCampos(false);
		accionesPrimarias(false);
	}
	
	private void eliminar() {
		if(verificarRelacion()==false) return;
		if (table.getSelectedRow()<0) return;
		int respuesta = JOptionPane.showConfirmDialog(null, 
				"Esta seguro que desea eliminar el registro de Oficina "+oficina.getDescripcion(),
				"Atención",
				JOptionPane.YES_NO_OPTION);
		if(respuesta == JOptionPane.YES_OPTION){
			dao = new OficinaDao();
			dao.eliminar(oficina);
			try {
				dao.ejecutar();
			} catch (Exception e) {
				dao.cancelar();
			}
			consultarOficina();
		}
		limpiar();
		accionesPrimarias(true);
	}
	
	private void cargarDatos() {
		if (modificar==false) {
			oficina = new Oficina();
		}
		oficina.setId(Integer.parseInt(tfCodigo.getText()));
		oficina.setDescripcion(tfDescripcion.getText());
		oficina.setBloque(bloque);
		if (cbActivo.isSelected()==true) {
			oficina.setEstado(true);
		}
		if (cbInactivo.isSelected()==true) {
			oficina.setEstado(false);
		}
	}
	
	private void consultarOficina() {
		dao = new OficinaDao();
		oficinas = dao.recuperarTodo();
		tablaOficina.setLista(oficinas);
		tablaOficina.fireTableDataChanged();
		chckbxTodos.setSelected(true);
	}
	
	private void buscarOficina() {
		dao = new OficinaDao();
		if (chckbxTodos.isSelected()==true) {
			oficinas = dao.recuperarPorFiltro(tfBuscar.getText());
		}
		if (chckbxActivos.isSelected()==true) {
			oficinas = dao.recuperarPorActivo(tfBuscar.getText());
		}
		if (chckbxInactivos.isSelected()==true) {
			oficinas = dao.recuperarInactivo(tfBuscar.getText());
		}
		tablaOficina.setLista(oficinas);
		tablaOficina.fireTableDataChanged();
	}
	
	@Override
	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
		tfBloque.setText(bloque.getNombre());
	}
	
	private void abrirBuscador() {
		BuscadorBloque buscadorBloque = new BuscadorBloque();
		buscadorBloque.setInterfaz(this);
		buscadorBloque.setVisible(true);	
	}
	
//-------------------------------Validaciones----------------
	private boolean campoObligatorio() {
		if(tfCodigo.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Código es un campo obligatorio");
			tfCodigo.requestFocus();
			return true;
		}
		if(tfDescripcion.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Descripción es un campo obligatorio");
			tfDescripcion.requestFocus();
			return true;
		}
		if(tfBloque.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Bloque es un campo obligatorio");
			btnBuscarBloque.requestFocus();
			return true;
		}
		if(((cbActivo.isSelected()==true)&(cbInactivo.isSelected()==true))|((cbActivo.isSelected()==false)&(cbInactivo.isSelected()==false))){
			JOptionPane.showMessageDialog(null, "Seleccione un estado");
			cbActivo.requestFocus();
			return true;
		}
		
		return false;
	}

	private boolean verificarRelacion() {
		puertaDao = new PuertaDao();
		puertas = puertaDao.recuperarTodo();
		for (int i = 0; i < puertas.size(); i++) {
			if (Integer.parseInt(tfCodigo.getText())==puertas.get(i).getOficina().getId()) {
				JOptionPane.showMessageDialog(null, "Oficina con Puertas Registradas", "Atencion",JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		permisoAccesoDao = new PermisoAccesoDao();
		permisoAccesos = permisoAccesoDao.recuperarTodo();
		for(int i =0; i<permisoAccesos.size(); i++){
			if (Integer.parseInt(tfCodigo.getText())==permisoAccesos.get(i).getOficina().getId()) {
				JOptionPane.showMessageDialog(null, "Oficina con Permisos Registrados", "Atencion",JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	private boolean verificarCodigo() {
		if (tfCodigo.getText().isEmpty()) {
			lblcdigoDuplicado.setVisible(false);
			return false;
		}
		for (int i = 0; i < oficinas.size(); i++) {
			if (Integer.parseInt(tfCodigo.getText())==oficinas.get(i).getId()) {
				lblcdigoDuplicado.setText("Código Duplicado");
				lblcdigoDuplicado.setVisible(true);
				tfCodigo.requestFocus();
				return true;
			}
		}
		lblcdigoDuplicado.setVisible(false);
		return false;
	}
	
	//Metodo para inicializar los datos
	public void inicializarOficina(){
		String tabla = "Oficina";
		dao = new OficinaDao();
		dao.eliminarTodos(tabla);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
	}
	
}
