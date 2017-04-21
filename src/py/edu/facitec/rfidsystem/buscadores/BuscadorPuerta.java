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
import py.edu.facitec.rfidsystem.dao.PuertaDao;
import py.edu.facitec.rfidsystem.entidad.Bloque;
import py.edu.facitec.rfidsystem.entidad.Institucion;
import py.edu.facitec.rfidsystem.entidad.Puerta;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadoPuerta;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadorBloque;
import py.edu.facitec.rfidsystem.tablas.TablaBloque;
import py.edu.facitec.rfidsystem.tablas.TablaPuerta;

public class BuscadorPuerta extends JDialog  {
	private JTable table;
	private TablaPuerta tablaPuerta;
	private PuertaDao dao;
	private List<Puerta> puertas;
	private InterfazBuscadoPuerta interfaz;
	private Integer id;
	
	public void setInterfaz(InterfazBuscadoPuerta interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorPuerta() {
		setTitle("Buscador de Puertas");
		setBounds(100, 100, 550, 391);
		setLocationRelativeTo(this);
		setModal(true);
		setLocationRelativeTo(this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tablaPuerta = new TablaPuerta();
		table = new JTable(tablaPuerta);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) seleccionarPuerta();
			}
		});
		scrollPane.setViewportView(table);
		consultarPuertas();
	}
	
	public void consultarPuertas() {
		dao = new PuertaDao();
		puertas = dao.buscadorPuertaPorOficina(id);
		tablaPuerta.setLista(puertas);
		tablaPuerta.fireTableDataChanged();
	}
	
	
	private void seleccionarPuerta() {
		if (table.getSelectedRow()<0) return;
		Puerta puerta = puertas.get(table.getSelectedRow());
		interfaz.setPuerta(puerta);;
		dispose();
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
