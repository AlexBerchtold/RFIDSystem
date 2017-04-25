package py.edu.facitec.rfidsystem.abm;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import py.edu.facitec.rfidsystem.contenedores.BotonPersonalizadoABM;
import py.edu.facitec.rfidsystem.dao.BloqueDao;
import py.edu.facitec.rfidsystem.dao.InstitucionDao;
import py.edu.facitec.rfidsystem.entidad.Bloque;
import py.edu.facitec.rfidsystem.entidad.Institucion;
import py.edu.facitec.rfidsystem.tablas.TablaInstitucion;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class InstitucionABM extends GenericABM {
	private JTextField tfBuscar;
	private JTextField tfCodigo;
	private JTextField tfDescripcion;
	private InstitucionDao dao;
	private List<Institucion> institucions;
	private TablaInstitucion tablaInstitucion;
	private Institucion institucion;
	private BloqueDao bloqueDao;
	private List<Bloque> bloques;
	private JLabel lblcodigoDuplicado;
	
	public InstitucionABM() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(InstitucionABM.class.getResource("/py/edu/facitec/rfidsystem/img/institucion.png")));
		setTitle("Registro de Institución");
		setBounds(100, 100, 697, 449);
		setLocationRelativeTo(this);
		tablaInstitucion= new TablaInstitucion();
		table.setModel(tablaInstitucion);
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminar();
			}
		});
		btnGuardar.setLocation(10, 381);
		btnCancelar.setLocation(239, 381);
		scrollPane.setBounds(350, 132, 328, 276);
		
		BotonPersonalizadoABM btnBuscar = new BotonPersonalizadoABM();
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscarIntitucion();
			}
		});
		btnBuscar.setText("Buscar");
		btnBuscar.setBounds(346, 95, 90, 26);
		getContentPane().add(btnBuscar);
		
		tfBuscar = new JTextField();
		tfBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					buscarIntitucion();
				}
			}
		});
		tfBuscar.setColumns(10);
		tfBuscar.setBounds(463, 98, 215, 20);
		getContentPane().add(tfBuscar);
		
		JLabel lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setFont(new Font("Arial", Font.BOLD, 12));
		lblCodigo.setBounds(10, 178, 46, 14);
		getContentPane().add(lblCodigo);
		
		tfCodigo = new JTextField();
		tfCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c!= e.VK_BACK_SPACE & c!= e.VK_ENTER) {
					e.consume();
					lblcodigoDuplicado.setText("*Solo Numeros");
					lblcodigoDuplicado.setVisible(true);
				}else{
					lblcodigoDuplicado.setVisible(false);
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
		tfCodigo.setBounds(133, 176, 92, 20);
		getContentPane().add(tfCodigo);
		
		tfDescripcion = new JTextField();
		tfDescripcion.setEnabled(false);
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(133, 231, 158, 20);
		getContentPane().add(tfDescripcion);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setFont(new Font("Arial", Font.BOLD, 12));
		lblDescripcin.setBounds(10, 233, 102, 14);
		getContentPane().add(lblDescripcin);
		
		JLabel lblRegistrosDeInstitucin = new JLabel("Registro De Instituci\u00F3n");
		lblRegistrosDeInstitucin.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrosDeInstitucin.setForeground(Color.BLUE);
		lblRegistrosDeInstitucin.setFont(new Font("Algerian", Font.PLAIN, 24));
		lblRegistrosDeInstitucin.setBounds(1, 11, 690, 53);
		getContentPane().add(lblRegistrosDeInstitucin);
		
		lblcodigoDuplicado = new JLabel("*C\u00F3digo Duplicado");
		lblcodigoDuplicado.setVisible(false);
		lblcodigoDuplicado.setForeground(Color.RED);
		lblcodigoDuplicado.setBounds(133, 196, 108, 14);
		getContentPane().add(lblcodigoDuplicado);
		consultarInstitucion();
		
	}
	
	//---------------------Metodos Genericos-----------------
	@Override
	protected void limpiar() {
		tfCodigo.setText("");
		tfDescripcion.setText("");
		lblcodigoDuplicado.setVisible(false);
	}

	@Override
	protected void habilitarCampos(boolean e) {
		if (modificar==true) {
			tfCodigo.setEnabled(!e);
		}else {
			tfCodigo.setEnabled(e);
		}
		tfDescripcion.setEnabled(e);

	}

	@Override
	protected void guardar() {
		if (campoObligatorio()==true) return;
		if (verificarCodigo()==true) return;
		cargarDatos();
		dao=new InstitucionDao();
		dao.insertarOModificar(institucion);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
		modificar=false;
		consultarInstitucion();
		habilitarCampos(false);
		limpiar();
		accionesPrimarias(true);
		institucion=null;
	}

	@Override
	protected void fechaActual() {}
	
	@Override
	protected void cargarFormulario(int index) {
		limpiar();
		if (index < 0) return;
		institucion=institucions.get(index);
		tfCodigo.setText(institucion.getId()+"");
		tfDescripcion.setText(institucion.getDescripcion());
		modificar=false;
		habilitarCampos(false);
		accionesPrimarias(false);
	}
	
	private void eliminar() {
		if(verificarRelacion()==false) return;
		if (table.getSelectedRow()<0) return;
		int respuesta = JOptionPane.showConfirmDialog(null, 
				"Esta seguro que desea eliminar el registro de Intitución "+institucion.getDescripcion(),
				"Atención",
				JOptionPane.YES_NO_OPTION);
		if(respuesta == JOptionPane.YES_OPTION){
			dao = new InstitucionDao();
			dao.eliminar(institucion);
			try {
				dao.ejecutar();
			} catch (Exception e) {
				dao.cancelar();
			}
			consultarInstitucion();;
		}
		limpiar();
		accionesPrimarias(true);

	}

	private void cargarDatos() {
		if (modificar==false) {
			institucion = new Institucion();
		}
		institucion.setId(Integer.parseInt(tfCodigo.getText()));
		institucion.setDescripcion(tfDescripcion.getText());
	}
	
	private void consultarInstitucion() {
		dao = new InstitucionDao();
		institucions=dao.recuperarTodo();
		tablaInstitucion.setLista(institucions);
		tablaInstitucion.fireTableDataChanged();
	}
	
	private void buscarIntitucion() {
		dao = new InstitucionDao();
		institucions = dao.recuperarPorFiltro(tfBuscar.getText());
		tablaInstitucion.setLista(institucions);
		tablaInstitucion.fireTableDataChanged();
	}
	
//------------------------Validaciones-----------------
	private boolean verificarCodigo() {
		if (tfCodigo.getText().isEmpty()) {
			lblcodigoDuplicado.setVisible(false);
			return false;
		}
		for (int i = 0; i < institucions.size(); i++) {
			if (Integer.parseInt(tfCodigo.getText())==institucions.get(i).getId()) {
				lblcodigoDuplicado.setText("*Código Duplicado");
				lblcodigoDuplicado.setVisible(true);
				tfCodigo.requestFocus();
				return true;
			}
		}
		lblcodigoDuplicado.setVisible(false);
		return false;
	}
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
		
		return false;
	}
	

	private boolean verificarRelacion() {
		bloqueDao = new BloqueDao();
		bloques = bloqueDao.recuperarTodo();
		for (int i = 0; i < bloques.size(); i++) {
			if (Integer.parseInt(tfCodigo.getText())==bloques.get(i).getInstitucion().getId()) {
				JOptionPane.showMessageDialog(null, "Intitución con Bloques Registrados", "Atención",JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	//Metodo para inicializar los datos
	public void inicializarInstitucion() {
		String tabla = "Institucion";
		dao = new InstitucionDao();
		dao.eliminarTodos(tabla);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
	}
}
