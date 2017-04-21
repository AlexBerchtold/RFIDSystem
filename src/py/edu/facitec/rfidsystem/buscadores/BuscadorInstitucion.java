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

import py.edu.facitec.rfidsystem.dao.InstitucionDao;
import py.edu.facitec.rfidsystem.entidad.Institucion;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscarInstitucion;
import py.edu.facitec.rfidsystem.tablas.TablaInstitucion;

public class BuscadorInstitucion extends JDialog  {
	private JTextField tfBuscador;
	private JTable table;
	private TablaInstitucion tablaInstitucion;
	private InstitucionDao dao;
	private List<Institucion> institucions;
	private InterfazBuscarInstitucion interfaz;
	
	public void setInterfaz(InterfazBuscarInstitucion interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorInstitucion() {
		setTitle("Buscador de Institución");
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
					consultarInstitucion();
				}
			}
		});
		getContentPane().add(tfBuscador, BorderLayout.NORTH);
		tfBuscador.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tablaInstitucion = new TablaInstitucion();
		table = new JTable(tablaInstitucion);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) seleccionarInstitucion();
			}
		});
		scrollPane.setViewportView(table);
		consultarInstitucion();

	}
	
	private void consultarInstitucion() {
		dao = new InstitucionDao();
		institucions = dao.recuperarPorFiltro(tfBuscador.getText());
		tablaInstitucion.setLista(institucions);
		tablaInstitucion.fireTableDataChanged();
	}
	
	
	private void seleccionarInstitucion() {
		if (table.getSelectedRow()<0) return;
		Institucion institucion = institucions.get(table.getSelectedRow());
		interfaz.setInstitucion(institucion);
		dispose();
	}

}
