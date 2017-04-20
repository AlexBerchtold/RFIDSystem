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

import py.edu.facitec.rfidsystem.dao.FuncionarioDao;
import py.edu.facitec.rfidsystem.dao.InstitucionDao;
import py.edu.facitec.rfidsystem.entidad.Funcionario;
import py.edu.facitec.rfidsystem.entidad.Institucion;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscadorFuncionario;
import py.edu.facitec.rfidsystem.interfaces.InterfazBuscarInstitucion;
import py.edu.facitec.rfidsystem.tablas.TablaFuncionario;
import py.edu.facitec.rfidsystem.tablas.TablaInstitucion;

public class BuscadorFuncionario extends JDialog  {
	private JTextField tfBuscador;
	private JTable table;
	private TablaFuncionario tablaFuncionario;
	private FuncionarioDao dao;
	private List<Funcionario> funcionarios;
	private InterfazBuscadorFuncionario interfaz;
	
	public void setInterfaz(InterfazBuscadorFuncionario interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorFuncionario() {
		setTitle("Buscador de Funcionario");
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
					consultarEmpleados();
				}
			}
		});
		getContentPane().add(tfBuscador, BorderLayout.NORTH);
		tfBuscador.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tablaFuncionario = new TablaFuncionario();
		table = new JTable(tablaFuncionario);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) seleccionarEmpleado();
			}
		});
		scrollPane.setViewportView(table);
		consultarEmpleados();

	}
	
	private void consultarEmpleados() {
		dao = new FuncionarioDao();
		funcionarios = dao.recuperarPorFiltro(tfBuscador.getText());
		tablaFuncionario.setLista(funcionarios);
		tablaFuncionario.fireTableDataChanged();
	}
	
	
	private void seleccionarEmpleado() {
		if (table.getSelectedRow()<0) return;
		Funcionario funcionario = funcionarios.get(table.getSelectedRow());
		interfaz.setFuncionario(funcionario);
		dispose();
	}

}
