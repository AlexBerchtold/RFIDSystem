package py.edu.facitec.rfidsystem.abm;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

import py.edu.facitec.rfidsystem.app.PantallaPrincipal;
import py.edu.facitec.rfidsystem.dao.ConfiguracionDao;
import py.edu.facitec.rfidsystem.entidad.Configuracion;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfiguracionABM extends JDialog {
	private JTextField tfNombre;
	private JTextField tfEmail1;
	private JTextField tfEmail2;
	private JTextField tfTelefono;
	private JTextField tfCelular;
	private Configuracion configuracion;
	private ConfiguracionDao dao;
	private PantallaPrincipal pantallaPrincipal;

	/**
	 * Create the dialog.
	 */
	public ConfiguracionABM() {
		setTitle("Configuración");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfiguracionABM.class.getResource("/py/edu/facitec/rfidsystem/img/configuracion.png")));
		setBounds(100, 100, 360, 360);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		
		JLabel lblConfiguracin = new JLabel("Configuraci\u00F3n");
		lblConfiguracin.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguracin.setForeground(Color.BLUE);
		lblConfiguracin.setFont(new Font("Algerian", Font.PLAIN, 24));
		lblConfiguracin.setBounds(10, 11, 334, 53);
		getContentPane().add(lblConfiguracin);
		
		JLabel lblNombre = new JLabel("Empresa/Propietario:");
		lblNombre.setBounds(10, 75, 131, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblEmailPrincipal = new JLabel("Email Principal:");
		lblEmailPrincipal.setBounds(10, 114, 109, 14);
		getContentPane().add(lblEmailPrincipal);
		
		JLabel lblEmailSecundario = new JLabel("Email Secundario:");
		lblEmailSecundario.setBounds(10, 158, 109, 14);
		getContentPane().add(lblEmailSecundario);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(10, 200, 46, 14);
		getContentPane().add(lblTelefono);
		
		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setBounds(10, 247, 46, 14);
		getContentPane().add(lblCelular);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(148, 72, 181, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);
		
		tfEmail1 = new JTextField();
		tfEmail1.setText("");
		tfEmail1.setBounds(148, 111, 181, 20);
		getContentPane().add(tfEmail1);
		tfEmail1.setColumns(10);
		
		tfEmail2 = new JTextField();
		tfEmail2.setBounds(148, 155, 181, 20);
		getContentPane().add(tfEmail2);
		tfEmail2.setColumns(10);
		
		tfTelefono = new JTextField();
		tfTelefono.setBounds(148, 197, 181, 20);
		getContentPane().add(tfTelefono);
		tfTelefono.setColumns(10);
		
		tfCelular = new JTextField();
		tfCelular.setBounds(148, 244, 181, 20);
		getContentPane().add(tfCelular);
		tfCelular.setColumns(10);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int respuesta = JOptionPane.showConfirmDialog(null, 
						"Estas seguro que deseas actualizar la configuración?",
						"Atención",
						JOptionPane.YES_NO_OPTION);
				if (respuesta==JOptionPane.YES_OPTION) {
					guardar();
				}
			}
		});
		btnActualizar.setBounds(30, 297, 97, 23);
		getContentPane().add(btnActualizar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(240, 297, 89, 23);
		getContentPane().add(btnCancelar);

		datosActuales();
		
	}
	
	private void cargarDatos() {
		configuracion = new Configuracion();
		configuracion.setId(1);
		configuracion.setNombre(tfNombre.getText());
		configuracion.setEmail(tfEmail1.getText());
		configuracion.setEmail2(tfEmail2.getText());
		configuracion.setTelefono(tfTelefono.getText());
		configuracion.setCelular(tfCelular.getText());
	}
	
	private void guardar(){
		cargarDatos();
		dao = new ConfiguracionDao();
		dao.insertarOModificar(configuracion);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
		actualizarPantalla();
		dispose();
	}
	
	private void actualizarPantalla(){
		dao = new ConfiguracionDao();
		configuracion = dao.recuperarPorId(1);
		PantallaPrincipal.lblNombre.setText(configuracion.getNombre());
		PantallaPrincipal.lblEmail1.setText(configuracion.getEmail());
		PantallaPrincipal.lblEmail2.setText(configuracion.getEmail2());
		PantallaPrincipal.lblTelefono.setText(configuracion.getTelefono());
		PantallaPrincipal.lblCelular.setText(configuracion.getCelular());
	}
	
	private void datosActuales() {
		dao = new ConfiguracionDao();
		configuracion = dao.recuperarPorId(1);
		tfNombre.setText(configuracion.getNombre());
		tfEmail1.setText(configuracion.getEmail());
		tfEmail2.setText(configuracion.getEmail2());
		tfTelefono.setText(configuracion.getTelefono());
		tfCelular.setText(configuracion.getCelular());
	}
}
