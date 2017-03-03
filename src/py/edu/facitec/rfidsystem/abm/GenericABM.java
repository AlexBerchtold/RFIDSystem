package py.edu.facitec.rfidsystem.abm;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JButton;
import py.edu.facitec.rfidsystem.contenedores.BotonPersonalizadoABM;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class GenericABM extends JDialog {
	protected JTable table;
	protected BotonPersonalizadoABM btnNuevo;
	protected BotonPersonalizadoABM btnModificar;
	protected BotonPersonalizadoABM btnEliminar;
	protected BotonPersonalizadoABM btnSalir;
	protected BotonPersonalizadoABM btnGuardar;
	protected BotonPersonalizadoABM btnCancelar;


	/**
	 * Create the dialog.
	 */
	public GenericABM() {
		setTitle("Formulario");
		setMinimumSize(new Dimension(600, 400));
		setMaximumSize(new Dimension(800, 600));
		setResizable(false);
		setBounds(100, 100, 800, 530);
		getContentPane().setLayout(null);
		setModal(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(400, 131, 384, 355);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) {
					cargarFormulario(table.getSelectedRow());
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBounds(10, 93, 377, 27);
		getContentPane().add(toolBar);
		
		btnNuevo = new BotonPersonalizadoABM();
		btnNuevo.setText("Nuevo");
		toolBar.add(btnNuevo);
		
		btnModificar = new BotonPersonalizadoABM();
		btnModificar.setEnabled(false);
		btnModificar.setText("Modificar");
		toolBar.add(btnModificar);
		
		btnEliminar = new BotonPersonalizadoABM();
		btnEliminar.setEnabled(false);
		btnEliminar.setText("Eliminar");
		toolBar.add(btnEliminar);
		
		btnSalir = new BotonPersonalizadoABM();
		btnSalir.setText("Salir");
		toolBar.add(btnSalir);
		
		btnGuardar = new BotonPersonalizadoABM();
		btnGuardar.setVisible(false);
		btnGuardar.setText("Guardar");
		btnGuardar.setBounds(10, 459, 80, 27);
		getContentPane().add(btnGuardar);
		
		btnCancelar = new BotonPersonalizadoABM();
		btnCancelar.setVisible(false);
		btnCancelar.setText("Cancelar");
		btnCancelar.setBounds(239, 459, 80, 27);
		getContentPane().add(btnCancelar);
	}
	
	protected abstract void cargarFormulario(int index);
}
