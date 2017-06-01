package py.edu.facitec.rfidsystem.buscadores;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import py.edu.facitec.rfidsystem.dao.BloqueDao;
import py.edu.facitec.rfidsystem.dao.OficinaDao;
import py.edu.facitec.rfidsystem.entidad.Bloque;
import py.edu.facitec.rfidsystem.entidad.Institucion;
import py.edu.facitec.rfidsystem.entidad.Oficina;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadorBloque;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscardorOficina;
import py.edu.facitec.rfidsystem.tablas.TablaBloque;
import py.edu.facitec.rfidsystem.tablas.TablaOficina;

public class BuscadorOficina extends JDialog  {
	private JTextField tfBuscador;
	private JTable table;
	private TablaOficina tablaOficina;
	private OficinaDao dao;
	private List<Oficina> oficinas;
	private InterfazBuscardorOficina interfaz;
	
	public void setInterfaz(InterfazBuscardorOficina interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorOficina() {
		setTitle("Buscador de Oficina");
		setBounds(100, 100, 550, 391);
		setLocationRelativeTo(this);
		setModal(true);
		setLocationRelativeTo(this);
		setResizable(false);
		
		tfBuscador = new JTextField();
		tfBuscador.setBounds(0, 0, 544, 20);
		tfBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					consultarOficina();
				}
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(tfBuscador);
		tfBuscador.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 20, 544, 342);
		getContentPane().add(scrollPane);
		
		tablaOficina = new TablaOficina();
		table = new JTable(tablaOficina);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) seleccionarOficina();
			}
		});
		scrollPane.setViewportView(table);
		consultarOficina();
	}
	
	private void consultarOficina() {
		dao = new OficinaDao();
		oficinas = dao.recuperarPorFiltro(tfBuscador.getText());
		tablaOficina.setLista(oficinas);
		tablaOficina.fireTableDataChanged();
	}
	
	
	private void seleccionarOficina() {
		if (table.getSelectedRow()<0) return;
		Oficina oficina = oficinas.get(table.getSelectedRow());
		interfaz.setOficina(oficina);
		dispose();
	}

}
