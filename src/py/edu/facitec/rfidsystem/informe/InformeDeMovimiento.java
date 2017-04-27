package py.edu.facitec.rfidsystem.informe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.sf.jasperreports.components.list.HorizontalFillList;
import net.sf.jasperreports.engine.JRException;
import py.edu.facitec.rfidsystem.dao.MovimientoDao;
import py.edu.facitec.rfidsystem.dao.PermisoAccesoDao;
import py.edu.facitec.rfidsystem.entidad.Movimiento;
import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;
import py.edu.facitec.rfidsystem.tablas.TablaMovimiento;
import py.edu.facitec.rfidsystem.tablas.TablaPermisoAcceso;
import py.edu.facitec.rfidsystem.util.ConexionReportes;
import py.edu.facitec.rfidsystem.util.FechaUtil;

import java.awt.Toolkit;

public class InformeDeMovimiento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfDesde;
	private JTable table;
	private JTextField tfHasta;
	private TablaMovimiento tablaMovimiento;
	private MovimientoDao dao;
	private List<Movimiento> movimientos;
	private JComboBox cbxOrder;
	private JLabel lblTotalNumer;
	private JComboBox cbFiltro;
	private JButton btnImprimir;
	private JLabel lblsolonumeros;
	private JTextField tfDesdeHora;
	private JTextField tfHastaHora;

	/**
	 * Create the dialog.
	 */
	public InformeDeMovimiento() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(InformeDeMovimiento.class.getResource("/py/edu/facitec/rfidsystem/img/listaacceso.png")));
		setTitle("Informe de Movimientos");
		setBounds(100, 100, 700, 470);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		tablaMovimiento = new TablaMovimiento();
		
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
				validarBusqueda();
			}
		});
		btnProcesas.setBounds(501, 11, 108, 30);
		contentPanel.add(btnProcesas);
		
		cbxOrder = new JComboBox();
		cbxOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validarBusqueda();
			}
		});
		cbxOrder.setModel(new DefaultComboBoxModel(new String[] {"Oficina", "Funcionario", "Hora"}));
		cbxOrder.setBounds(88, 54, 95, 20);
		contentPanel.add(cbxOrder);
		
		JLabel lblOrdenarPor = new JLabel("Ordenar Por:");
		lblOrdenarPor.setBounds(10, 57, 114, 14);
		contentPanel.add(lblOrdenarPor);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 95, 674, 294);
		contentPanel.add(scrollPane);
		
		table = new JTable(tablaMovimiento);
		scrollPane.setViewportView(table);
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConexionReportes<Movimiento> conexionReportes = new ConexionReportes<Movimiento>();
				try {
					conexionReportes.GerarRealatorio(movimientos, "InformeDeMovimiento");
					conexionReportes.viewer.setVisible(true);
					dispose();
				} catch (JRException e) {
					// TODO Auto-generated catch block
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
				tfManager();
			}
		});
		cbFiltro.setModel(new DefaultComboBoxModel(new String[] {"Oficina", "Funcionario", "Hora"}));
		cbFiltro.setBounds(88, 11, 95, 20);
		contentPanel.add(cbFiltro);
		
		lblsolonumeros = new JLabel("*SoloNumeros");
		lblsolonumeros.setVisible(false);
		lblsolonumeros.setForeground(Color.RED);
		lblsolonumeros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblsolonumeros.setBounds(290, 36, 114, 14);
		contentPanel.add(lblsolonumeros);
		
		tfDesdeHora = new JFormattedTextField(FechaUtil.getFormatoHora());
		tfDesdeHora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c!= e.VK_ENTER & c!= e.VK_BACK_SPACE) {
					e.consume();
					lblsolonumeros.setVisible(true);
					lblsolonumeros.setText("*Solo Numeros");
				}else{
					lblsolonumeros.setVisible(false);
				}
			}
		});
		tfDesdeHora.setVisible(false);
		tfDesdeHora.setColumns(10);
		tfDesdeHora.setBounds(290, 14, 186, 20);
		contentPanel.add(tfDesdeHora);
		
		tfHastaHora = new JFormattedTextField(FechaUtil.getFormatoHora());
		tfHastaHora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c!= e.VK_ENTER & c!= e.VK_BACK_SPACE) {
					e.consume();
					lblsolonumeros.setVisible(true);
					lblsolonumeros.setText("*Solo Numeros");
				}else{
					lblsolonumeros.setVisible(false);
				}
			}
		});
		tfHastaHora.setVisible(false);
		tfHastaHora.setColumns(10);
		tfHastaHora.setBounds(290, 54, 186, 20);
		contentPanel.add(tfHastaHora);
		ordenarTodo();
		verificarLista();
	}
	
	private void validarBusqueda() {
		if (cbFiltro.getSelectedIndex()==2) {
			if (tfDesdeHora.getText().isEmpty() & tfHastaHora.getText().isEmpty()){ ordenarTodo(); return;}
			if (tfDesdeHora.getText().isEmpty()) {tfDesdeHora.setText("0000"); buscarMovimiento(); return;}
			if (tfHastaHora.getText().isEmpty()) {tfHastaHora.setText("2300"); buscarMovimiento(); return;}
		}else{
			if (tfDesde.getText().isEmpty() & tfHasta.getText().isEmpty()) {ordenarTodo(); return;}
			if (tfDesde.getText().isEmpty()) {tfDesde.setText("A"); buscarMovimiento(); return;}
		}
		buscarMovimiento();
	}
	
	private void buscarMovimiento() {
		dao = new MovimientoDao();
		if (cbFiltro.getSelectedIndex()==2) {
			movimientos = dao.filtrarInforme(tfDesdeHora.getText(), tfHastaHora.getText(), cbxOrder.getSelectedIndex(), 2);
		}else{
			movimientos = dao.filtrarInforme(tfDesde.getText(), tfHasta.getText()+"zzzzzzz", cbxOrder.getSelectedIndex(), cbFiltro.getSelectedIndex());
		}
		tablaMovimiento.setLista(movimientos);
		tablaMovimiento.fireTableDataChanged();
		verificarLista();
	}
	
	private void ordenarTodo() {
		dao = new MovimientoDao();
		if (cbxOrder.getSelectedIndex()==0) {
			movimientos = dao.filtrarInforme("A", "zzzzzz", 0, 1);
		}
		if (cbxOrder.getSelectedIndex()==1) {
			movimientos = dao.filtrarInforme("A", "zzzzzz", 1, 1);
		}
		if (cbxOrder.getSelectedIndex()==2) {
			movimientos = dao.filtrarInforme("A", "zzzzzz", 2, 1);
		}
		tablaMovimiento.setLista(movimientos);
		tablaMovimiento.fireTableDataChanged();
		verificarLista();
	}
	
	private void tfManager() {
		if (cbFiltro.getSelectedIndex()==2) {
			tfDesde.setVisible(false);
			tfHasta.setVisible(false);
			tfDesdeHora.setVisible(true);
			tfHastaHora.setVisible(true);
		}else{
			tfDesde.setVisible(true);
			tfHasta.setVisible(true);
			tfDesdeHora.setVisible(false);
			tfHastaHora.setVisible(false);
		}
		tfDesde.setText("");
		tfHasta.setText("");
		tfDesdeHora.setText("");
		tfHastaHora.setText("");
		lblsolonumeros.setVisible(false);
	}
	
	private void verificarLista() {
		if (movimientos.size()==0) {
			btnImprimir.setEnabled(false);
		}else{
			btnImprimir.setEnabled(true);
		}

	}
}
