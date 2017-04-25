package py.edu.facitec.rfidsystem.abm;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import py.edu.facitec.rfidsystem.buscadores.BuscadorPermisoAcceso;
import py.edu.facitec.rfidsystem.contenedores.BotonPersonalizadoABM;
import py.edu.facitec.rfidsystem.dao.MovimientoDao;
import py.edu.facitec.rfidsystem.entidad.Movimiento;
import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadorPermisoAcceso;
import py.edu.facitec.rfidsystem.tablas.TablaMovimiento;
import py.edu.facitec.rfidsystem.util.FechaUtil;
import java.awt.Toolkit;

public class MovimientoABM extends GenericABM implements InterfazBuscadorPermisoAcceso{
	private JTextField tfPermisoAcceso;
	private JTextField tfHora;
	private JTextField tfCodigo;
	private PermisoAcceso permisoAcceso;
	private BotonPersonalizadoABM btnPermisoAcceso;
	private MovimientoDao dao;
	private List<Movimiento> movimientos;
	private TablaMovimiento tablaMovimiento;
	private Movimiento movimiento;
	private JTextField tfBuscador;
	private JLabel lblCodigoDuplicado;
	
	public MovimientoABM() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MovimientoABM.class.getResource("/py/edu/facitec/rfidsystem/img/movimientos.png")));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminar();
			}
		});
		setTitle("Registro de Movimiento");
		btnCancelar.setLocation(239, 382);
		btnGuardar.setLocation(10, 382);
		scrollPane.setBounds(350, 132, 328, 276);
		setBounds(100, 100, 697, 449);
		setLocationRelativeTo(this);
	
		
		tablaMovimiento= new TablaMovimiento();
		table.setModel(tablaMovimiento);
		
		JLabel lblRegistroDe = new JLabel("Registro De Movimiento");
		lblRegistroDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDe.setForeground(Color.BLUE);
		lblRegistroDe.setFont(new Font("Algerian", Font.PLAIN, 24));
		lblRegistroDe.setBounds(1, 11, 690, 53);
		getContentPane().add(lblRegistroDe);
		
		btnPermisoAcceso = new BotonPersonalizadoABM();
		btnPermisoAcceso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorPermisoAcceso();
			}
		});
		btnPermisoAcceso.setText("...");
		btnPermisoAcceso.setEnabled(false);
		btnPermisoAcceso.setBounds(281, 290, 46, 20);
		getContentPane().add(btnPermisoAcceso);
		
		tfPermisoAcceso = new JTextField();
		tfPermisoAcceso.setEnabled(false);
		tfPermisoAcceso.setColumns(10);
		tfPermisoAcceso.setBounds(133, 290, 138, 20);
		getContentPane().add(tfPermisoAcceso);
		
		JLabel lblPermisoDeAcesso = new JLabel("Permiso de Acesso:");
		lblPermisoDeAcesso.setFont(new Font("Arial", Font.BOLD, 12));
		lblPermisoDeAcesso.setBounds(10, 292, 126, 14);
		getContentPane().add(lblPermisoDeAcesso);
		
		tfHora = new JFormattedTextField(FechaUtil.getFormatoHora());
		tfHora.setEnabled(false);
		tfHora.setColumns(10);
		tfHora.setBounds(133, 225, 92, 20);
		getContentPane().add(tfHora);
		
		JLabel lblHora = new JLabel("Hora:");
		lblHora.setFont(new Font("Arial", Font.BOLD, 12));
		lblHora.setBounds(10, 227, 102, 14);
		getContentPane().add(lblHora);
		
		tfCodigo = new JTextField();
		tfCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				verificarCodigo();
			}
		});
		tfCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)& c!= e.VK_BACK_SPACE & c!=e.VK_ENTER) {
					e.consume();
					lblCodigoDuplicado.setText("*Solo numeros");
					lblCodigoDuplicado.setVisible(true);
				}else {
					lblCodigoDuplicado.setVisible(false);
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					tfHora.requestFocus();
				}
			}
		});
		tfCodigo.setEnabled(false);
		tfCodigo.setColumns(10);
		tfCodigo.setBounds(133, 170, 92, 20);
		getContentPane().add(tfCodigo);
		
		JLabel label_2 = new JLabel("C\u00F3digo:");
		label_2.setFont(new Font("Arial", Font.BOLD, 12));
		label_2.setBounds(10, 172, 46, 14);
		getContentPane().add(label_2);
		
		BotonPersonalizadoABM btnBuscar = new BotonPersonalizadoABM();
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarMovimiento();
			}
		});
		btnBuscar.setText("Buscar");
		btnBuscar.setBounds(350, 95, 90, 26);
		getContentPane().add(btnBuscar);
		
		tfBuscador = new JTextField();
		tfBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					buscarMovimiento();
				}
			}
		});
		tfBuscador.setColumns(10);
		tfBuscador.setBounds(467, 98, 211, 20);
		getContentPane().add(tfBuscador);
		
		lblCodigoDuplicado = new JLabel("New label");
		lblCodigoDuplicado.setForeground(Color.RED);
		lblCodigoDuplicado.setVisible(false);
		lblCodigoDuplicado.setBounds(133, 189, 108, 14);
		getContentPane().add(lblCodigoDuplicado);
		consultarMovimiento();
	}
	
	//---------------Metodos Genericos-------------
	@Override
	protected void limpiar() {
		tfCodigo.setText("");
		tfHora.setText("");
		tfPermisoAcceso.setText("");
		lblCodigoDuplicado.setVisible(false);
	}

	@Override
	protected void habilitarCampos(boolean e) {
		if(modificar==true){
			tfCodigo.setEnabled(!e);
		}else {
			tfCodigo.setEnabled(e);
		}
		tfHora.setEnabled(e);
		btnPermisoAcceso.setEnabled(e);
	}

	@Override
	protected void guardar() {
		if (modificar==false) {
			if(verificarCodigo()==true) return;
		}
		if(campoObligatorio()==true) return;
		cargarDatos();
		dao = new MovimientoDao();
		dao.insertarOModificar(movimiento);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
		modificar=false;
		habilitarCampos(false);
		limpiar();
		accionesPrimarias(true);
		movimiento=null;
		consultarMovimiento();
	}

	@Override
	protected void fechaActual() {
		Date hora = new Date();
		DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
		tfHora.setText(""+horaFormat.format(hora));
	}
	
	@Override
	protected void cargarFormulario(int index) {
		limpiar();
		if(index<0) return;
		movimiento=movimientos.get(index);
		permisoAcceso = movimientos.get(index).getPermisoAcceso();
		tfCodigo.setText(movimiento.getId()+"");
		tfHora.setText(FechaUtil.horaAString(movimiento.getHora()));
		tfPermisoAcceso.setText(movimiento.getPermisoAcceso().getId()+"");
		habilitarCampos(false);
		accionesPrimarias(false);
	}
	
	private void eliminar() {
		if (table.getSelectedRow()<0) return;
		int respuesta = JOptionPane.showConfirmDialog(null, 
				"Esta seguro que desea eliminar el registro de Movimiento numero: "+movimiento.getId(),
				"Atención",
				JOptionPane.YES_NO_OPTION);
		if(respuesta == JOptionPane.YES_OPTION){
			dao = new MovimientoDao();
			dao.eliminar(movimiento);
			try {
				dao.ejecutar();
			} catch (Exception e) {
				dao.cancelar();
			}
			consultarMovimiento();
		}
		limpiar();
		accionesPrimarias(true);
		consultarMovimiento();
	}
	
	private void cargarDatos() {
		if(modificar==false){
			movimiento= new Movimiento();
		}
		movimiento.setId(Integer.parseInt(tfCodigo.getText()));
		movimiento.setHora(FechaUtil.stringAHora(tfHora.getText()));
		movimiento.setPermisoAcceso(permisoAcceso);
	}
	
	private void consultarMovimiento() {
		dao = new MovimientoDao();
		movimientos = dao.recuperarTodo();
		tablaMovimiento.setLista(movimientos);
		tablaMovimiento.fireTableDataChanged();
	}
	
	private void buscarMovimiento() {
		if (tfBuscador.getText().isEmpty()) {
			consultarMovimiento();
			return;
		}
		dao = new MovimientoDao();
		movimientos = dao.recuperarPorFiltro(tfBuscador.getText());
		tablaMovimiento.setLista(movimientos);
		tablaMovimiento.fireTableDataChanged();
	}
	
	private void abrirBuscadorPermisoAcceso() {
		BuscadorPermisoAcceso buscadorPermisoAcceso = new BuscadorPermisoAcceso();
		buscadorPermisoAcceso.setInterfaz(this);
		buscadorPermisoAcceso.setVisible(true);
	}
	
	@Override
	public void setPermisoAcceso(PermisoAcceso permisoAcceso) {
		this.permisoAcceso=permisoAcceso;
		tfPermisoAcceso.setText(permisoAcceso.getId()+"");
	}

	
//-----------------------Validaciones-----------------
	private boolean campoObligatorio(){
		if(tfCodigo.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Código es un campo obligatorio");
			tfCodigo.requestFocus();
			return true;
		}
		if(tfHora.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Hora es un campo obligatorio");
			tfCodigo.requestFocus();
			return true;
		}
		if(tfPermisoAcceso.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Permiso de Acceso es un campo obligatorio");
			tfCodigo.requestFocus();
			return true;
		}
		return false;
	}
	
	private boolean verificarCodigo() {
		if (tfCodigo.getText().isEmpty()) {
			lblCodigoDuplicado.setVisible(false);
			return false;
		}
		for (int i = 0; i <movimientos.size(); i++) {
			if (Integer.parseInt(tfCodigo.getText())==movimientos.get(i).getId()) {
				lblCodigoDuplicado.setText("*Código Duplicado");
				lblCodigoDuplicado.setVisible(true);
				tfCodigo.requestFocus();
				return true;
			}
		}
		lblCodigoDuplicado.setVisible(false);
		return false;

	}
	
	public void inicializarMovimiento() {
		String tabla ="Movimiento";
		dao = new MovimientoDao();
		dao.eliminarTodos(tabla);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
	}
}
