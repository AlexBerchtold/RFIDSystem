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
import py.edu.facitec.rfidsystem.entidad.Bloque;
import py.edu.facitec.rfidsystem.entidad.Institucion;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadorBloque;
import py.edu.facitec.rfidsystem.tablas.TablaBloque;

public class BuscadorBloque extends JDialog  {
	private JTextField tfBuscador;
	private JTable table;
	private TablaBloque tablaBloque;
	private BloqueDao dao;
	private List<Bloque> bloques;
	private InterfazBuscadorBloque interfaz;
	
	public void setInterfaz(InterfazBuscadorBloque interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorBloque() {
		setTitle("Buscador de Bloque");
		setBounds(100, 100, 550, 391);
		setLocationRelativeTo(this);
		setModal(true);
		setLocationRelativeTo(this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		tfBuscador = new JTextField();
		tfBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					consultarBloque();
				}
			}
		});
		getContentPane().add(tfBuscador, BorderLayout.NORTH);
		tfBuscador.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tablaBloque = new TablaBloque();
		table = new JTable(tablaBloque);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) seleccionarBloque();
			}
		});
		scrollPane.setViewportView(table);
		consultarBloque();
	}
	
	private void consultarBloque() {
		dao = new BloqueDao();
		bloques = dao.recuperarPorFiltro(tfBuscador.getText());
		tablaBloque.setLista(bloques);
		tablaBloque.fireTableDataChanged();
	}
	
	
	private void seleccionarBloque() {
		if (table.getSelectedRow()<0) return;
		Bloque bloque = bloques.get(table.getSelectedRow());
		interfaz.setBloque(bloque);
		dispose();
	}

}
