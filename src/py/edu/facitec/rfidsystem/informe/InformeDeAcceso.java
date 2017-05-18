package py.edu.facitec.rfidsystem.informe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
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
import py.edu.facitec.rfidsystem.dao.PermisoAccesoDao;
import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;
import py.edu.facitec.rfidsystem.tablas.TablaPermisoAcceso;
import py.edu.facitec.rfidsystem.util.ConexionReportes;

public class InformeDeAcceso extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfDesde;
	private JTable table;
	private JTextField tfHasta;
	private TablaPermisoAcceso tablaPermisoAcceso;
	private PermisoAccesoDao dao;
	private List<PermisoAcceso> permisoAccesos;
	private JComboBox cbxOrder;
	private JLabel lblTotalNumer;
	private JComboBox cbFiltro;
	private JButton btnImprimir;
	private JLabel lblsolonumeros;
	private HashMap par;
	private int c;

	/**
	 * Create the dialog.
	 */
	public InformeDeAcceso() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(InformeDeAcceso.class.getResource("/py/edu/facitec/rfidsystem/img/listaacceso.png")));
		setTitle("Informe de Accesos");
		setBounds(100, 100, 700, 470);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		tablaPermisoAcceso = new TablaPermisoAcceso();
		
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
					if (Character.isDigit(c)) {
						e.consume();
						lblsolonumeros.setVisible(true);
						lblsolonumeros.setText("*Solo Letras");
					}else{
						lblsolonumeros.setVisible(false);
					}
				}
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					tfHasta.requestFocus();
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
				if (Character.isDigit(c)) {
					e.consume();
					lblsolonumeros.setVisible(true);
					lblsolonumeros.setText("*Solo Letras");
				}else{
					lblsolonumeros.setVisible(false);
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
		cbxOrder.setModel(new DefaultComboBoxModel(new String[] {"Oficina", "Funcionario"}));
		cbxOrder.setBounds(88, 54, 95, 20);
		contentPanel.add(cbxOrder);
		
		JLabel lblOrdenarPor = new JLabel("Ordenar Por:");
		lblOrdenarPor.setBounds(10, 57, 114, 14);
		contentPanel.add(lblOrdenarPor);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 95, 674, 294);
		contentPanel.add(scrollPane);
		
		table = new JTable(tablaPermisoAcceso);
		scrollPane.setViewportView(table);
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConexionReportes<PermisoAcceso> conexionReportes = new ConexionReportes<PermisoAcceso>();
				if (par==null) {
					par = new HashMap<>();
					par.put("parametro", "Informe General		Ordenado por: "+cbxOrder.getSelectedItem());
				}
				try {
					conexionReportes.GerarRealatorio(permisoAccesos, par, "ReporteDePermisoAcceso");
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
				tfDesde.setText("");
				tfDesde.requestFocus();
				tfHasta.setText("");
				lblsolonumeros.setVisible(false);
			}
		});
		cbFiltro.setModel(new DefaultComboBoxModel(new String[] {"Oficina", "Funcionario"}));
		cbFiltro.setBounds(88, 11, 95, 20);
		contentPanel.add(cbFiltro);
		lblsolonumeros = new JLabel("*SoloNumeros");
		lblsolonumeros.setVisible(false);
		lblsolonumeros.setForeground(Color.RED);
		lblsolonumeros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblsolonumeros.setBounds(290, 36, 114, 14);
		contentPanel.add(lblsolonumeros);
		ordenarTodo();
		verificarLista();
		c=permisoAccesos.size();
		tfDesde.requestFocus();
	}//fin del metodo constructor
	
	private void filtrarAccesos() {
		dao = new PermisoAccesoDao();
		if(cbxOrder.getSelectedIndex()==0) permisoAccesos = dao.filtroPorOficina(tfDesde.getText(), tfHasta.getText()+"zzzzzzzz", 0, cbFiltro.getSelectedIndex());
		if(cbxOrder.getSelectedIndex()==1) permisoAccesos = dao.filtroPorOficina(tfDesde.getText(), tfHasta.getText()+"zzzzzzzz", 1, cbFiltro.getSelectedIndex());
		tablaPermisoAcceso.setLista(permisoAccesos);
		tablaPermisoAcceso.fireTableDataChanged();
		lblTotalNumer.setText(permisoAccesos.size()+"");
	}

	private void verificarYConsultar() {
		if (cbFiltro.getSelectedIndex()==0) {
			if (tfHasta.getText().isEmpty() & tfDesde.getText().isEmpty()) { ordenarTodo(); parametro(); return;}
			if(tfHasta.getText().isEmpty()) tfHasta.setText("Zzzzzzz");
			if(tfDesde.getText().isEmpty()) tfDesde.setText("A");
			filtrarAccesos();
		}
		if (cbFiltro.getSelectedIndex()==1) {
			if (tfHasta.getText().isEmpty() & tfDesde.getText().isEmpty()) { ordenarTodo(); parametro(); return;}
			if(tfDesde.getText().isEmpty()) tfDesde.setText("A");
			filtrarAccesos();
		}
		parametro();
	}
	
	private void ordenarTodo() {
		dao = new PermisoAccesoDao();
		if(cbxOrder.getSelectedIndex()==0) permisoAccesos = dao.filtroPorOficina("A", "zzzzzzz", 0, 0);
		if(cbxOrder.getSelectedIndex()==1) permisoAccesos = dao.filtroPorOficina("A", "zzzzzzzz", 1, 0);
		tablaPermisoAcceso.setLista(permisoAccesos);
		tablaPermisoAcceso.fireTableDataChanged();
		lblTotalNumer.setText(permisoAccesos.size()+"");
	}
	
	
	private void verificarLista(){
		if (permisoAccesos.size()==0) {
			btnImprimir.setEnabled(false);
		}else{
			btnImprimir.setEnabled(true);
		}
	}
	
	private void parametro() {
		par = new HashMap<>();
		if (tfDesde.getText().isEmpty() & tfHasta.getText().isEmpty() | c==permisoAccesos.size()) {
			par.put("parametro", "Informe General		Ordenado por: "+cbxOrder.getSelectedItem());
			return;
		}else{
			par.put("parametro", "Desde: "+tfDesde.getText()+"		Hasta: "+tfHasta.getText()+"		"
					+ "Orden por: "+cbxOrder.getSelectedItem());
		}
	}
}
