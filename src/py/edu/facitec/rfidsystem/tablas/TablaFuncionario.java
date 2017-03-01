package py.edu.facitec.rfidsystem.tablas;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.table.AbstractTableModel;

import py.edu.facitec.rfidsystem.entidad.Funcionario;
import py.edu.facitec.rfidsystem.util.FechaUtil;

public class TablaFuncionario extends AbstractTableModel {
	
	public String columnas[]={
			"Nombre", "Apellido", "Fecha Incorporacion", "Tarjeta"	};
	
	private Object[][] datos = new Object[0][columnas.length];
	
	public void setLista(List<Funcionario> lista){
		datos = new Object[lista.size()][columnas.length];
		for (int i = 0; i < lista.size(); i++) {
			datos[i][0] = lista.get(i).getNombre();
			datos[i][1] = lista.get(i).getApellido();
			datos[i][2] = FechaUtil.fechaAString(lista.get(i).getFechaIncorporacion());
			datos[i][3] = lista.get(i).getNoDocumento();
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
