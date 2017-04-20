package py.edu.facitec.rfidsystem.tablas;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.edu.facitec.rfidsystem.entidad.Funcionario;
import py.edu.facitec.rfidsystem.util.FechaUtil;

public class TablaInformeFuncionario extends AbstractTableModel {

	public String columnas[]={
			"Codigo", "Nombre", "Apellido", "Fecha Nacimiento", "CI.", "Incorporacion", "Tarjeta"	};
	
	private Object[][] datos = new Object[0][columnas.length];
	
	public void setLista(List<Funcionario> lista){
		datos = new Object[lista.size()][columnas.length];
		for (int i = 0; i < lista.size(); i++) {
			datos[i][0] = lista.get(i).getId();
			datos[i][1] = lista.get(i).getNombre();
			datos[i][2] = lista.get(i).getApellido();
			datos[i][3] = FechaUtil.fechaAString(lista.get(i).getFechaNacimiento());
			datos[i][4] = lista.get(i).getNoDocumento();
			datos[i][5] = FechaUtil.fechaAString(lista.get(i).getFechaIncorporacion());
			datos[i][6] = lista.get(i).getTarjeta();
		}
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}
	
	@Override
	public String getColumnName(int i) {
		return columnas[i];
	}

	@Override
	public int getRowCount() {
		return datos.length;
	}

	@Override
	public Object getValueAt(int f, int c) {
		return datos[f][c];
	}

}
