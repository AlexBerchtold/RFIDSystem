package py.edu.facitec.rfidsystem.informe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.HashMap;
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

import net.sf.jasperreports.engine.JRException;
import py.edu.facitec.rfidsystem.dao.MovimientoDao;
import py.edu.facitec.rfidsystem.entidad.Movimiento;
import py.edu.facitec.rfidsystem.tablas.TablaInformeMovimiento;
import py.edu.facitec.rfidsystem.tablas.TablaMovimiento;
import py.edu.facitec.rfidsystem.util.ConexionReportes;
import py.edu.facitec.rfidsystem.util.FechaUtil;

public class InformeDeMovimiento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfDesde;
	private JTable table;
	private JTextField tfHasta;
	private TablaInformeMovimiento tablaMovimiento;
	private MovimientoDao dao;
	private List<Movimiento> movimientos;
	private JComboBox cbxOrder;
	private JLabel lblTotalNumer;
	private JComboBox cbFiltro;
	private JButton btnImprimir;
	private JLabel lblsolonumeros;
	private JFormattedTextField tfDesdeHora;
	private JFormattedTextField tfHastaHora;
	private HashMap par;
	private int c;
	private String fechaActual;
	private JFormattedTextField tfDesdeFecha;
	private JFormattedTextField tfHastaFecha;

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
		
		tablaMovimiento = new TablaInformeMovimiento();
		
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
			}
		});
		btnProcesas.setBounds(501, 11, 108, 30);
		contentPanel.add(btnProcesas);
		
		cbxOrder = new JComboBox();
		cbxOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verificarYConsultar();
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
				if (par==null) {
					par = new HashMap<>();
					par.put("parametro", "Informe General		Ordenado por: "+cbxOrder.getSelectedItem());
				}
				try {
					conexionReportes.GerarRealatorio(movimientos, par, "ReporteDeMovimiento");
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
		lblsolonumeros.setBounds(290, 36, 186, 14);
		contentPanel.add(lblsolonumeros);
		
		tfDesdeHora = new JFormattedTextField(FechaUtil.getFormatoHora());
		tfDesdeHora.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(FechaUtil.stringAHora(tfDesdeHora.getText())==null) tfDesdeHora.setValue(null);
			}
		});
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
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					tfDesdeFecha.requestFocus();
				}
			}
		});
		tfDesdeHora.setVisible(false);
		tfDesdeHora.setColumns(10);
		tfDesdeHora.setBounds(290, 14, 46, 20);
		contentPanel.add(tfDesdeHora);
		
		tfHastaHora = new JFormattedTextField(FechaUtil.getFormatoHora());
		tfHastaHora.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(FechaUtil.stringAHora(tfHastaHora.getText())==null) tfHastaHora.setValue(null);
			}
		});
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
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					tfHastaFecha.requestFocus();
				}
			}
		});
		tfHastaHora.setVisible(false);
		tfHastaHora.setColumns(10);
		tfHastaHora.setBounds(290, 54, 46, 20);
		contentPanel.add(tfHastaHora);
		
		tfDesdeFecha = new JFormattedTextField(FechaUtil.getFormato());
		tfDesdeFecha.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(FechaUtil.stringAFecha(tfDesdeFecha.getText())==null) tfDesdeFecha.setValue(null);
			}
		});
		tfDesdeFecha.addKeyListener(new KeyAdapter() {
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
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c== e.VK_ENTER) {
					tfHastaHora.requestFocus();
				}
			}
		});
		tfDesdeFecha.setVisible(false);
		tfDesdeFecha.setBounds(346, 14, 70, 20);
		contentPanel.add(tfDesdeFecha);
		
		tfHastaFecha = new JFormattedTextField(FechaUtil.getFormato());
		tfHastaFecha.addKeyListener(new KeyAdapter() {
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
			@Override
			public void keyPressed(KeyEvent e) {
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
		tfHastaFecha.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(FechaUtil.stringAFecha(tfHastaFecha.getText())==null) tfHastaFecha.setValue(null);
			}
		});
		tfHastaFecha.setVisible(false);
		tfHastaFecha.setBounds(346, 54, 70, 20);
		contentPanel.add(tfHastaFecha);
		ordenarTodo();
		verificarLista();
		c=movimientos.size();
		tfDesde.requestFocus();
	}// fin del metodo constructor
	
	private void verificarYConsultar() {
		if (cbFiltro.getSelectedIndex()==2) {
			if (FechaUtil.stringAFecha(tfDesdeFecha.getText())==null & FechaUtil.stringAFecha(tfHastaFecha.getText())==null & tfDesdeHora.getValue()==null & tfHastaHora.getValue()==null ){ ordenarTodo(); parametro(); return;}
			if (FechaUtil.stringAFecha(tfDesdeFecha.getText())==null) {lblsolonumeros.setText("Ingrese la fecha inicial"); lblsolonumeros.setVisible(true); return;}
			if (FechaUtil.stringAFecha(tfHastaFecha.getText())==null ) {lblsolonumeros.setText("Ingrese la fecha final"); lblsolonumeros.setVisible(true); return;}
			if(tfDesdeHora.getValue()==null & tfHastaHora.getValue()==null){filtrarPorFecha(); return;}
			if(tfDesdeHora.getValue()==null){tfDesdeHora.requestFocus(); lblsolonumeros.setText("Ingrese la Hora inicial"); return;}
			if(tfHastaHora.getValue()==null){tfHastaHora.requestFocus(); lblsolonumeros.setText("Ingrese Hora final"); return;}
		}else{
			if (tfDesde.getText().isEmpty() & tfHasta.getText().isEmpty()) {ordenarTodo(); parametro(); return;}
			if (tfDesde.getText().isEmpty()) tfDesde.setText("A");
			if (tfHasta.getText().isEmpty()) tfHasta.setText("Z");
		}
		filtrarMovimiento();
		parametro();
	}
	
	private void filtrarMovimiento() {
		dao = new MovimientoDao();
		if (cbFiltro.getSelectedIndex()==2) {
			movimientos = dao.filtrarInforme(tfDesdeHora.getText()+":00 "+tfDesdeFecha.getText(), tfHastaHora.getText()+":00 "+tfHastaFecha.getText(), cbxOrder.getSelectedIndex(), 2);
		}else{
			movimientos = dao.filtrarInforme(tfDesde.getText(), tfHasta.getText()+"zzzzzzz", cbxOrder.getSelectedIndex(), cbFiltro.getSelectedIndex());
		}
		tablaMovimiento.setLista(movimientos);
		tablaMovimiento.fireTableDataChanged();
		verificarLista();
	}
	
	private void filtrarPorFecha() {
		dao = new MovimientoDao();
		movimientos = dao.filtrarInforme("00:00:00 "+tfDesdeFecha.getText(), "23:59:00 "+tfHastaFecha.getText(), cbxOrder.getSelectedIndex(), 2);
		tablaMovimiento.setLista(movimientos);
		tablaMovimiento.fireTableDataChanged();
		verificarLista();
		par = new HashMap<>();
		par.put("parametro", "Desde: "+tfDesdeFecha.getText()+"		Hasta: "+tfHastaFecha.getText()+"		"+ "Orden por: "+cbxOrder.getSelectedItem());
	}
	
	private void ordenarTodo() {
		dao = new MovimientoDao();
		if (cbxOrder.getSelectedIndex()==0) movimientos = dao.filtrarInforme("A", "zzzzzz", 0, 1);
		if (cbxOrder.getSelectedIndex()==1) movimientos = dao.filtrarInforme("A", "zzzzzz", 1, 1);
		if (cbxOrder.getSelectedIndex()==2) movimientos = dao.filtrarInforme("A", "zzzzzz", 2, 1);
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
			tfDesdeFecha.setVisible(true);
			tfHastaFecha.setVisible(true);
			tfDesdeHora.requestFocus();
		}else{
			tfDesde.setVisible(true);
			tfHasta.setVisible(true);
			tfDesdeHora.setVisible(false);
			tfHastaHora.setVisible(false);
			tfDesdeFecha.setVisible(false);
			tfHastaFecha.setVisible(false);
			tfDesde.requestFocus();
		}
		tfDesde.setText("");
		tfHasta.setText("");
		tfDesdeHora.setValue(null);;
		tfHastaHora.setValue(null);
		tfDesdeFecha.setValue("");
		tfHastaFecha.setValue("");
		lblsolonumeros.setVisible(false);
	}
	
	private void verificarLista() {
		if (movimientos.size()==0) {
			btnImprimir.setEnabled(false);
		}else{
			btnImprimir.setEnabled(true);
		}
	}
	
	private void parametro() {
		par = new HashMap<>();
		if (c==movimientos.size()) {
			par.put("parametro", "Informe General		Ordenado por: "+cbxOrder.getSelectedItem());
			return;
		}else{
			if (cbFiltro.getSelectedIndex()==2) {
				par.put("parametro", "Desde: "+tfDesdeHora.getText()+"  "+tfDesdeFecha.getText()+"		Hasta: "+tfHastaHora.getText()+" "+tfHastaFecha.getText()+"		"
						+ "Orden por: "+cbxOrder.getSelectedItem());
			}else
			par.put("parametro", "Desde: "+tfDesde.getText()+"		Hasta: "+tfHasta.getText()+"		"
					+ "Orden por: "+cbxOrder.getSelectedItem());
			}
	}
	
}
