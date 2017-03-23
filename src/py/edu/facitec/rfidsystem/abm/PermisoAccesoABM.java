package py.edu.facitec.rfidsystem.abm;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import py.edu.facitec.rfidsystem.buscadores.BuscadorFuncionario;
import py.edu.facitec.rfidsystem.buscadores.BuscadorOficina;
import py.edu.facitec.rfidsystem.buscadores.BuscadorPuerta;
import py.edu.facitec.rfidsystem.contenedores.BotonPersonalizadoABM;
import py.edu.facitec.rfidsystem.dao.MovimientoDao;
import py.edu.facitec.rfidsystem.dao.PermisoAccesoDao;
import py.edu.facitec.rfidsystem.entidad.Funcionario;
import py.edu.facitec.rfidsystem.entidad.Movimiento;
import py.edu.facitec.rfidsystem.entidad.Oficina;
import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;
import py.edu.facitec.rfidsystem.entidad.Puerta;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadoPuerta;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadorFuncionario;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscardorOficina;
import py.edu.facitec.rfidsystem.tablas.TablaPermisoAcceso;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PermisoAccesoABM extends GenericABM implements InterfazBuscadorFuncionario, InterfazBuscadoPuerta, InterfazBuscardorOficina {
	private JTextField tfCodigo;
	private JTextField tfOficina;
	private JTextField tfFuncionario;
	private JTextField tfPuerta;
	private JTextField tfBuscar;
	private Puerta puerta;
	private Funcionario funcionario;
	private Oficina oficina;
	private TablaPermisoAcceso tablaPermisoAcceso;
	private List<PermisoAcceso> permisoAccesos;
	private PermisoAccesoDao dao;
	private PermisoAcceso permisoAcceso;
	private MovimientoDao movimientoDao;
	private List<Movimiento> movimientos;
	private BotonPersonalizadoABM btnBuscarFuncionario;
	private BotonPersonalizadoABM btnBuscarOficina;
	private BotonPersonalizadoABM btnBuscadorPuerta;
	private byte bandera;
	
	public PermisoAccesoABM() {
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		setBounds(100, 100, 800, 525);
		setLocationRelativeTo(this);
		setTitle("Registro de Acceso");
		btnEliminar.setText("Anular");
		tablaPermisoAcceso = new TablaPermisoAcceso();
		table.setModel(tablaPermisoAcceso);
		
		JLabel lblRegistrosDeAcceso = new JLabel("Registros De Acceso");
		lblRegistrosDeAcceso.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrosDeAcceso.setForeground(Color.BLUE);
		lblRegistrosDeAcceso.setFont(new Font("Algerian", Font.PLAIN, 24));
		lblRegistrosDeAcceso.setBounds(11, 6, 773, 53);
		getContentPane().add(lblRegistrosDeAcceso);
		
		JLabel label = new JLabel("C\u00F3digo:");
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(10, 165, 46, 14);
		getContentPane().add(label);
		
		JLabel lblOficina = new JLabel("Oficina:");
		lblOficina.setFont(new Font("Arial", Font.BOLD, 12));
		lblOficina.setBounds(10, 229, 102, 14);
		getContentPane().add(lblOficina);
		
		JLabel lblPuerta = new JLabel("Funcionario:");
		lblPuerta.setFont(new Font("Arial", Font.BOLD, 12));
		lblPuerta.setBounds(10, 289, 102, 14);
		getContentPane().add(lblPuerta);
		
		JLabel lblPuerta_1 = new JLabel("Puerta:");
		lblPuerta_1.setFont(new Font("Arial", Font.BOLD, 12));
		lblPuerta_1.setBounds(10, 356, 102, 14);
		getContentPane().add(lblPuerta_1);
		
		tfCodigo = new JTextField();
		tfCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!tfCodigo.getText().isEmpty()) {
					verificarCodigo();
				}
			}
		});
		tfCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					if(bandera!=2 & c!= e.VK_BACK_SPACE & c!=e.VK_ENTER){
						JOptionPane.showMessageDialog(null, "Solo se permiten numeros enteros");
						bandera=2;
					}
				}
			}
		});
		tfCodigo.setEnabled(false);
		tfCodigo.setBounds(111, 163, 86, 20);
		getContentPane().add(tfCodigo);
		tfCodigo.setColumns(10);
		
		tfOficina = new JTextField();
		tfOficina.setEnabled(false);
		tfOficina.setColumns(10);
		tfOficina.setBounds(111, 227, 177, 20);
		getContentPane().add(tfOficina);
		
		tfFuncionario = new JTextField();
		tfFuncionario.setEnabled(false);
		tfFuncionario.setColumns(10);
		tfFuncionario.setBounds(111, 287, 177, 20);
		getContentPane().add(tfFuncionario);
		
		tfPuerta = new JTextField();
		tfPuerta.setEnabled(false);
		tfPuerta.setColumns(10);
		tfPuerta.setBounds(111, 354, 177, 20);
		getContentPane().add(tfPuerta);
		
		btnBuscarOficina = new BotonPersonalizadoABM();
		btnBuscarOficina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorOficina();
			}
		});
		btnBuscarOficina.setText("...");
		btnBuscarOficina.setEnabled(false);
		btnBuscarOficina.setBounds(298, 227, 46, 20);
		getContentPane().add(btnBuscarOficina);
		
		btnBuscarFuncionario = new BotonPersonalizadoABM();
		btnBuscarFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirBuscadorFuncionario();
			}
		});
		btnBuscarFuncionario.setText("...");
		btnBuscarFuncionario.setEnabled(false);
		btnBuscarFuncionario.setBounds(298, 287, 46, 20);
		getContentPane().add(btnBuscarFuncionario);
		
		btnBuscadorPuerta = new BotonPersonalizadoABM();
		btnBuscadorPuerta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirBuscadorPuerta();
			}
		});
		btnBuscadorPuerta.setText("...");
		btnBuscadorPuerta.setEnabled(false);
		btnBuscadorPuerta.setBounds(298, 354, 46, 20);
		getContentPane().add(btnBuscadorPuerta);
		
		tfBuscar = new JTextField();
		tfBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					buscarPorFiltro();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					if(bandera!=1 & c!= e.VK_BACK_SPACE & c!=e.VK_ENTER){
						JOptionPane.showMessageDialog(null, "Solo se permiten numeros enteros");
						bandera=1;
					}
				}
			}
		});
		tfBuscar.setColumns(10);
		tfBuscar.setBounds(517, 97, 267, 20);
		getContentPane().add(tfBuscar);
		
		BotonPersonalizadoABM botonPersonalizadoABM = new BotonPersonalizadoABM();
		botonPersonalizadoABM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPorFiltro();
			}
		});
		botonPersonalizadoABM.setText("Buscar");
		botonPersonalizadoABM.setBounds(400, 94, 90, 26);
		getContentPane().add(botonPersonalizadoABM);
		consultarPermisoAccesos();
	}
	
	//---------------Metodos Genericos-------------
	@Override
	protected void limpiar() {
		tfCodigo.setText("");
		tfFuncionario.setText("");
		tfOficina.setText("");
		tfPuerta.setText("");
	}

	@Override
	protected void habilitarCampos(boolean e) {
		if (modificar==true){
			tfCodigo.setEnabled(!e);
		}else{
			tfCodigo.setEnabled(e);
		}
		btnBuscadorPuerta.setEnabled(e);
		btnBuscarOficina.setEnabled(e);
		btnBuscarFuncionario.setEnabled(e);
	}

	@Override
	protected void guardar() {
		if (camposObligatorios()==true) return;
		cargarDatos();
		dao = new PermisoAccesoDao();
		dao.insertarOModificar(permisoAcceso);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
		modificar=false;
		habilitarCampos(false);
		limpiar();
		accionesPrimarias(true);
		permisoAcceso=null;
		consultarPermisoAccesos();
	}
	

	@Override
	protected void fechaActual() {}
	
	@Override
	protected void cargarFormulario(int index) {
		if (index < 0) return;
		permisoAcceso = permisoAccesos.get(index);
		funcionario = permisoAccesos.get(index).getFuncionario();
		oficina = permisoAccesos.get(index).getOficina();
		puerta = permisoAccesos.get(index).getPuerta();
		tfCodigo.setText(permisoAcceso.getId()+"");
		tfOficina.setText(permisoAcceso.getOficina().getDescripcion());
		tfFuncionario.setText(permisoAcceso.getFuncionario().getNombre());
		tfPuerta.setText(permisoAcceso.getPuerta().getDescripcion());
		habilitarCampos(false);
		accionesPrimarias(false);
	}
	
	private void eliminar() {
		if(verificarRelacion()==false) return;
		if (table.getSelectedRow()<0) return;
		int respuesta = JOptionPane.showConfirmDialog(null, 
				"Esta seguro que desea Anular Permiso de Acceso de "+permisoAcceso.getFuncionario().getNombre(),
				"Atención",
				JOptionPane.YES_NO_OPTION);
		if(respuesta == JOptionPane.YES_OPTION){
			dao= new PermisoAccesoDao();
			dao.eliminar(permisoAcceso);
			try {
				dao.ejecutar();
			} catch (Exception e) {
				dao.cancelar();
			}
		}
		limpiar();
		accionesPrimarias(true);
		consultarPermisoAccesos();
	}
	
	private void cargarDatos(){
		if (modificar==false){
			permisoAcceso = new PermisoAcceso();
		}
		permisoAcceso.setId(Integer.parseInt(tfCodigo.getText()));
		permisoAcceso.setFuncionario(funcionario);
		permisoAcceso.setOficina(oficina);
		permisoAcceso.setPuerta(puerta);
	}
	
	private void consultarPermisoAccesos() {
		dao = new PermisoAccesoDao();
		permisoAccesos = dao.recuperarTodo();
		tablaPermisoAcceso.setLista(permisoAccesos);
		tablaPermisoAcceso.fireTableDataChanged();
	}
	
	private void buscarPorFiltro() {
		if (tfBuscar.getText().isEmpty()) {
			consultarPermisoAccesos();
			return;
		}
		dao =new PermisoAccesoDao();
		permisoAccesos = dao.recuperarPorFiltro(tfBuscar.getText());
		tablaPermisoAcceso.setLista(permisoAccesos);
		tablaPermisoAcceso.fireTableDataChanged();	
	}

	@Override
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
		tfOficina.setText(oficina.getDescripcion());
	}

	@Override
	public void setPuerta(Puerta puerta) {
		this.puerta=puerta;
		tfPuerta.setText(puerta.getDescripcion());
	}

	@Override
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario=funcionario;
		tfFuncionario.setText(funcionario.getNombre());
	}
	
	private void abrirBuscadorOficina() {
		BuscadorOficina buscadorOficina= new BuscadorOficina();
		buscadorOficina.setInterfaz(this);
		buscadorOficina.setVisible(true);
	}
	
	private void abrirBuscadorFuncionario() {
		BuscadorFuncionario buscadorFuncionario = new BuscadorFuncionario();
		buscadorFuncionario.setInterfaz(this);
		buscadorFuncionario.setVisible(true);
	}
	
	private void abrirBuscadorPuerta() {
		BuscadorPuerta buscadorPuerta = new BuscadorPuerta();
		buscadorPuerta.setInterfaz(this);
		buscadorPuerta.setVisible(true);
	}
	
	//-----------------------Validaciones-----------------
	private boolean camposObligatorios() {
		if (tfCodigo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,"Codigo es un campo obligatorio");
			tfCodigo.requestFocus();
			return true;
		}
		if (tfFuncionario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,"Funcionario es un campo obligatorio");
			tfFuncionario.requestFocus();
			return true;
		}
		if (tfPuerta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,"Puerta es un campo obligatorio");
			tfPuerta.requestFocus();
			return true;
		}
		if (tfOficina.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,"Oficina es un campo obligatorio");
			tfOficina.requestFocus();
			return true;
		}
		return false;
	}
	
	private boolean verificarRelacion() {
		movimientoDao = new MovimientoDao();
		movimientos = movimientoDao.recuperarTodo();
		for (int i = 0; i < movimientos.size(); i++) {
			if (Integer.parseInt(tfCodigo.getText())==movimientos.get(i).getPermisoAcceso().getId()) {
				JOptionPane.showMessageDialog(null,"Permisso acceso con Movimientos Registrados");
				return false;
			}
		}
		return true;
	}
	
	private void verificarCodigo() {
		if (tfCodigo.getText().isEmpty()) {
			return;
		}
		for (int i = 0; i <permisoAccesos.size(); i++) {
			if (Integer.parseInt(tfCodigo.getText())==permisoAccesos.get(i).getId()) {
				JOptionPane.showMessageDialog(null, "Código duplicado", "Atención",JOptionPane.ERROR_MESSAGE);
				tfCodigo.requestFocus();
				tfCodigo.selectAll();
			}
		}
	}
	
	public void inicializarPermisoAcceso() {
		String tabla = "PermisoAcceso";
		dao = new PermisoAccesoDao();
		dao.eliminarTodos(tabla);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
	}
}
