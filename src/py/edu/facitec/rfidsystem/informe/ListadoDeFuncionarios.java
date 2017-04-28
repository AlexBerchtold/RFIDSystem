package py.edu.facitec.rfidsystem.informe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.sf.jasperreports.engine.JRException;
import py.edu.facitec.rfidsystem.dao.FuncionarioDao;
import py.edu.facitec.rfidsystem.entidad.Funcionario;
import py.edu.facitec.rfidsystem.tablas.TablaInformeFuncionario;
import py.edu.facitec.rfidsystem.util.ConexionReportes;

public class ListadoDeFuncionarios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfDesde;
	private JTable table;
	private JTextField tfHasta;
	private TablaInformeFuncionario tablaFuncionario;
	private FuncionarioDao dao;
	private List<Funcionario> funcionarios;
	private JComboBox cbxOrder;
	private JLabel lblTotalNumer;
	private JComboBox cbFiltro;
	private JButton btnImprimir;
	private JLabel lblsoloNumeros;

	/**
	 * Create the dialog.
	 */
	public ListadoDeFuncionarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListadoDeFuncionarios.class.getResource("/py/edu/facitec/rfidsystem/img/listaempleado.png")));
		setTitle("Listado de Funcionarios");
		setBounds(100, 100, 700, 470);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		
		tablaFuncionario = new TablaInformeFuncionario();
		
		JLabel lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(234, 14, 46, 14);
		contentPanel.add(lblDesde);
		
		JLabel lblHasta = new JLabel("Hasta ");
		lblHasta.setBounds(234, 54, 46, 14);
		contentPanel.add(lblHasta);
		
		tfDesde = new JTextField();
		tfDesde.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (seleccionarFiltro()==false) {
					if (!Character.isDigit(c) & c!= e.VK_ENTER & c!= e.VK_BACK_SPACE) {
						e.consume();
						lblsoloNumeros.setVisible(true);
						lblsoloNumeros.setText("*Solo Numeros");
					}else{
						lblsoloNumeros.setVisible(false);
					}
				}else{
					if (Character.isDigit(c)) {
						e.consume();lblsoloNumeros.setVisible(true);
						lblsoloNumeros.setText("*Solo Letras");
					}else{
						lblsoloNumeros.setVisible(false);
					}
				}
			}
		});
		tfDesde.setBounds(290, 14, 186, 20);
		contentPanel.add(tfDesde);
		tfDesde.setColumns(10);
		
		tfHasta = new JTextField();
		tfHasta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (seleccionarFiltro()==false) {
					if (!Character.isDigit(c) & c!= e.VK_ENTER & c!= e.VK_BACK_SPACE) {
						e.consume();
						lblsoloNumeros.setVisible(true);
						lblsoloNumeros.setText("*Solo Numeros");
					}else{
						lblsoloNumeros.setVisible(false);
					}
				}else{
					if (Character.isDigit(c)) {
						e.consume();lblsoloNumeros.setVisible(true);
						lblsoloNumeros.setText("*Solo Letras");
					}else{
						lblsoloNumeros.setVisible(false);
					}
				}
			}
		});
		tfHasta.setBounds(290, 54, 186, 20);
		contentPanel.add(tfHasta);
		tfHasta.setColumns(10);
		
		JButton btnProcesas = new JButton("Procesar");
		btnProcesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verificarYConsultar();
				verificarLista();
			}
		});
		btnProcesas.setBounds(501, 11, 108, 30);
		contentPanel.add(btnProcesas);
		
		cbxOrder = new JComboBox();
		cbxOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verificarYConsultar();
				verificarLista();
			}
		});
		cbxOrder.setModel(new DefaultComboBoxModel(new String[] {"Codigo", "Nombre", "Apellido"}));
		cbxOrder.setBounds(88, 54, 95, 20);
		contentPanel.add(cbxOrder);
		
		JLabel lblOrdenarPor = new JLabel("Ordenar Por:");
		lblOrdenarPor.setBounds(10, 57, 114, 14);
		contentPanel.add(lblOrdenarPor);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 95, 674, 294);
		contentPanel.add(scrollPane);
		
		table = new JTable(tablaFuncionario);
		scrollPane.setViewportView(table);
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.setEnabled(false);
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConexionReportes<Funcionario> conexionReportes = new ConexionReportes<Funcionario>();
				try {
					conexionReportes.GerarRealatorio(funcionarios, "ListadoDeEmpleados");
					conexionReportes.viewer.setVisible(true);
				} catch (JRException e) {
					e.printStackTrace();
				}
			}
		});
		btnImprimir.setBounds(58, 400, 89, 30);
		contentPanel.add(btnImprimir);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(525, 400, 89, 30);
		contentPanel.add(btnSalir);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal.setBounds(501, 54, 46, 14);
		contentPanel.add(lblTotal);
		
		lblTotalNumer = new JLabel("");
		lblTotalNumer.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalNumer.setBounds(545, 54, 46, 14);
		contentPanel.add(lblTotalNumer);
		
		JLabel lblBuscarPor = new JLabel("Buscar Por:");
		lblBuscarPor.setBounds(10, 14, 70, 14);
		contentPanel.add(lblBuscarPor);
		
		cbFiltro = new JComboBox();
		cbFiltro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleccionarFiltro();
				tfDesde.setText("");
				tfHasta.setText("");
				lblsoloNumeros.setVisible(false);
			}
		});
		cbFiltro.setModel(new DefaultComboBoxModel(new String[] {"Codigo", "Nombre"}));
		cbFiltro.setBounds(88, 11, 95, 20);
		contentPanel.add(cbFiltro);
		lblsoloNumeros = new JLabel("*Solo Numeros");
		lblsoloNumeros.setVisible(false);
		lblsoloNumeros.setForeground(Color.RED);
		lblsoloNumeros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblsoloNumeros.setBounds(290, 36, 124, 14);
		contentPanel.add(lblsoloNumeros);
		ordenarTodo();
		verificarLista();
	}//fin del metodo constructor
	
	private void filtrarPorNombre() {
		dao = new FuncionarioDao();
		if(cbxOrder.getSelectedIndex()==0) funcionarios = dao.filtroListadoNombre(tfDesde.getText(), tfHasta.getText()+"zzzz", "id");
		if(cbxOrder.getSelectedIndex()==1)funcionarios = dao.filtroListadoNombre(tfDesde.getText(), tfHasta.getText()+"zzzz", "nombre");
		if(cbxOrder.getSelectedIndex()==2) funcionarios = dao.filtroListadoNombre(tfDesde.getText(), tfHasta.getText()+"zzzz", "apellido");
		tablaFuncionario.setLista(funcionarios);
		tablaFuncionario.fireTableDataChanged();
		lblTotalNumer.setText(funcionarios.size()+"");
	}
	
	private void filtrarPorId(){
		dao = new FuncionarioDao();
		if(cbxOrder.getSelectedIndex()==0) funcionarios = dao.filtroListadoId(Integer.parseInt(tfDesde.getText()), Integer.parseInt(tfHasta.getText()), "id");
		if(cbxOrder.getSelectedIndex()==1) funcionarios = dao.filtroListadoId(Integer.parseInt(tfDesde.getText()), Integer.parseInt(tfHasta.getText()), "nombre");	
		if(cbxOrder.getSelectedIndex()==2) funcionarios = dao.filtroListadoId(Integer.parseInt(tfDesde.getText()), Integer.parseInt(tfHasta.getText()), "apellido");
		tablaFuncionario.setLista(funcionarios);
		tablaFuncionario.fireTableDataChanged();
		lblTotalNumer.setText(funcionarios.size()+"");
	}
	
	private void verificarYConsultar() {
		if (seleccionarFiltro()==false) {
			if (tfHasta.getText().isEmpty() & tfDesde.getText().isEmpty()) { ordenarTodo(); return;}
			if(tfHasta.getText().isEmpty()) tfHasta.setText("999999999");
			if(tfDesde.getText().isEmpty()) tfDesde.setText("0");
			filtrarPorId();
		}
		if (seleccionarFiltro()==true) {
			if (tfHasta.getText().isEmpty() & tfDesde.getText().isEmpty()) { ordenarTodo(); return;}
			if(tfDesde.getText().isEmpty()) tfDesde.setText("A");
			filtrarPorNombre();
		}
	}
	
	private void ordenarTodo() {
		dao = new FuncionarioDao();
		if(cbxOrder.getSelectedIndex()==0) funcionarios = dao.filtroListadoId(0, 999999999, "id");
		if(cbxOrder.getSelectedIndex()==1) funcionarios = dao.filtroListadoId(0, 999999999, "nombre");
		if(cbxOrder.getSelectedIndex()==2) funcionarios = dao.filtroListadoId(0, 999999999, "apellido");
		tablaFuncionario.setLista(funcionarios);
		tablaFuncionario.fireTableDataChanged();
		lblTotalNumer.setText(funcionarios.size()+"");
	}
	
	private boolean seleccionarFiltro() {
		if(cbFiltro.getSelectedIndex()==0)return false;
		return true;
	}
	
	private void verificarLista(){
		if (funcionarios.size()==0) {
			btnImprimir.setEnabled(false);
		}else{
			btnImprimir.setEnabled(true);
		}
	}
}
