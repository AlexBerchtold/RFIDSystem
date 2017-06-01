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
import java.awt.Toolkit;

public abstract class GenericABM extends JDialog {
	protected JTable table;
	protected BotonPersonalizadoABM btnNuevo;
	protected BotonPersonalizadoABM btnModificar;
	protected BotonPersonalizadoABM btnEliminar;
	protected BotonPersonalizadoABM btnSalir;
	protected BotonPersonalizadoABM btnGuardar;
	protected BotonPersonalizadoABM btnCancelar;
	protected boolean modificar;
	protected JScrollPane scrollPane;


	/**
	 * Create the dialog.
	 */
	public GenericABM() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GenericABM.class.getResource("/py/edu/facitec/rfidsystem/img/icono.png")));
		setTitle("Formulario");
		setResizable(false);
		//setBounds(100, 100, 800, 530);
		getContentPane().setLayout(null);
		setModal(true);
		setLocationRelativeTo(this);
		
		scrollPane = new JScrollPane();
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
		toolBar.setBounds(10, 93, 325, 27);
		getContentPane().add(toolBar);
		
		btnNuevo = new BotonPersonalizadoABM();
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificar=false;
				limpiar();
				accionesSecundarias(true, false);
				fechaActual();
				habilitarCampos(true);
				cargarCodigo();
			}
		});
		btnNuevo.setText("Nuevo");
		toolBar.add(btnNuevo);
		
		btnModificar = new BotonPersonalizadoABM();
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificar=true;
				habilitarCampos(true);
				accionesSecundarias(true, true);
			}
		});
		btnModificar.setEnabled(false);
		btnModificar.setText("Modificar");
		toolBar.add(btnModificar);
		
		btnEliminar = new BotonPersonalizadoABM();
		btnEliminar.setEnabled(false);
		btnEliminar.setText("Eliminar");
		toolBar.add(btnEliminar);
		
		btnSalir = new BotonPersonalizadoABM();
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setText("Salir");
		toolBar.add(btnSalir);
		
		btnGuardar = new BotonPersonalizadoABM();
		btnGuardar.setVisible(false);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
		});
		btnGuardar.setText("Guardar");
		btnGuardar.setBounds(10, 459, 80, 27);
		getContentPane().add(btnGuardar);
		
		btnCancelar = new BotonPersonalizadoABM();
		btnCancelar.setVisible(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificar=false;
				limpiar();
				habilitarCampos(false);
				accionesPrimarias(true);
				
			}
		});
		btnCancelar.setText("Cancelar");
		btnCancelar.setBounds(239, 459, 80, 27);
		getContentPane().add(btnCancelar);
	}
	
	protected void accionesPrimarias(boolean a){
		if(a == true){
			btnNuevo.setEnabled(a);
			btnModificar.setEnabled(!a);
			btnEliminar.setEnabled(!a);
			accionesSecundarias(false, false);
		}else {
			btnNuevo.setEnabled(a);
			btnModificar.setEnabled(!a);
			btnEliminar.setEnabled(!a);
			btnCancelar.setVisible(!a);
			btnGuardar.setVisible(a);
			
		}
	}
	protected void accionesSecundarias(boolean a,boolean e){
		if (a==true) {
			btnNuevo.setEnabled(!a);
			btnModificar.setEnabled(!a);
			btnEliminar.setEnabled(!a);
			if(e==true){
				btnGuardar.setText("Actualizar");
			}
			btnGuardar.setVisible(a);
			btnCancelar.setVisible(a);
		}else {
			btnGuardar.setVisible(a);
			btnCancelar.setVisible(a);
			btnGuardar.setText("Guardar");
		}
	}
	protected abstract void limpiar();
	protected abstract void habilitarCampos(boolean e);
	protected abstract void guardar();
	protected abstract void fechaActual();
	protected abstract void cargarFormulario(int index);
	protected abstract void cargarCodigo();
	
}
