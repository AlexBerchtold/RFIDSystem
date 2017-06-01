package py.edu.facitec.rfidsystem.buscadores;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import py.edu.facitec.rfidsystem.dao.BloqueDao;
import py.edu.facitec.rfidsystem.dao.PermisoAccesoDao;
import py.edu.facitec.rfidsystem.entidad.Bloque;
import py.edu.facitec.rfidsystem.entidad.Institucion;
import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadorBloque;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadorPermisoAcceso;
import py.edu.facitec.rfidsystem.tablas.TablaBloque;
import py.edu.facitec.rfidsystem.tablas.TablaPermisoAcceso;

public class BuscadorPermisoAcceso extends JDialog  {
	private JTextField tfBuscador;
	private JTable table;
	private TablaPermisoAcceso tablaPermisoAcceso;
	private PermisoAccesoDao dao;
	private List<PermisoAcceso> permisoAccesos;
	private InterfazBuscadorPermisoAcceso interfaz;
	private PermisoAcceso permisoAcceso;
	
	public void setInterfaz(InterfazBuscadorPermisoAcceso interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorPermisoAcceso() {
		setTitle("Buscador de Permiso de Acceso");
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
					consultarPermisoAcceso();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar())){
					e.consume();
				}
			}
		});
		getContentPane().add(tfBuscador, BorderLayout.NORTH);
		tfBuscador.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tablaPermisoAcceso = new TablaPermisoAcceso();
		table = new JTable(tablaPermisoAcceso);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) seleccionarPermisoAcceso();
			}
		});
		scrollPane.setViewportView(table);
		consultarTodo();
	}
	
	private void consultarTodo() {
		dao = new PermisoAccesoDao();
		permisoAccesos = dao.recuperarTodo();
		tablaPermisoAcceso.setLista(permisoAccesos);
		tablaPermisoAcceso.fireTableDataChanged();
	}
	
	private void consultarPermisoAcceso() {
		if (tfBuscador.getText().isEmpty()) {
			consultarTodo();
			return;
		}
		dao = new PermisoAccesoDao();
		permisoAccesos = dao.recuperarPorFiltro(tfBuscador.getText());
		tablaPermisoAcceso.setLista(permisoAccesos);
		tablaPermisoAcceso.fireTableDataChanged();
	}
	
	
	private void seleccionarPermisoAcceso() {
		if (table.getSelectedRow()<0) return;
		permisoAcceso = permisoAccesos.get(table.getSelectedRow());
		if(verificarEstado()==false) return;
		interfaz.setPermisoAcceso(permisoAcceso);
		dispose();
	}
	
	private boolean verificarEstado(){
		if (permisoAcceso.getOficina().getEstado()==false){
			JOptionPane.showMessageDialog(null, "Oficina temporalmente Inactiva", "Atención",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(permisoAcceso.getPuerta().isEstado()==false){
			JOptionPane.showMessageDialog(null, "Puerta temporalmente Inactiva", "Atención",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
}
