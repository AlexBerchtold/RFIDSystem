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

public abstract class GenericABM extends JDialog {
	private JTable table;
	private BotonPersonalizadoABM btnNuevo;
	private BotonPersonalizadoABM btnModificar;
	private BotonPersonalizadoABM btnEliminar;
	private BotonPersonalizadoABM btnSalir;
	private BotonPersonalizadoABM btnGuardar;
	private BotonPersonalizadoABM btnCancelar;


	/**
	 * Create the dialog.
	 */
	public GenericABM() {
		setTitle("Formulario");
		setMinimumSize(new Dimension(800, 600));
		setMaximumSize(new Dimension(800, 600));
		setResizable(false);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(400, 131, 384, 402);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(10, 93, 350, 27);
		getContentPane().add(toolBar);
		
		btnNuevo = new BotonPersonalizadoABM();
		btnNuevo.setText("Nuevo");
		toolBar.add(btnNuevo);
		
		btnModificar = new BotonPersonalizadoABM();
		btnModificar.setText("Modificar");
		toolBar.add(btnModificar);
		
		btnEliminar = new BotonPersonalizadoABM();
		btnEliminar.setText("Eliminar");
		toolBar.add(btnEliminar);
		
		btnSalir = new BotonPersonalizadoABM();
		btnSalir.setText("Salir");
		toolBar.add(btnSalir);
		
		btnGuardar = new BotonPersonalizadoABM();
		btnGuardar.setText("Guardar");
		btnGuardar.setBounds(10, 506, 80, 27);
		getContentPane().add(btnGuardar);
		
		btnCancelar = new BotonPersonalizadoABM();
		btnCancelar.setText("Cancelar");
		btnCancelar.setBounds(252, 506, 80, 27);
		getContentPane().add(btnCancelar);
	}
	
	public void habilitarCampos(boolean e){
		btnNuevo.setEnabled(e);
		btnModificar.setEnabled(e);
		btnEliminar.setEnabled(e);
	}
}
