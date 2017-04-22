package py.edu.facitec.rfidsystem.tablas;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;

public class TablaPermisoAcceso extends AbstractTableModel{
	
	public String columnas[]={
			"Codigo", "Oficina", "Funcionario", "Nro. Puerta"	};
	
	private Object[][] datos = new Object[0][columnas.length];
	
	public void setLista(List<PermisoAcceso> lista){
		datos = new Object[lista.size()][columnas.length];
		for (int i = 0; i < lista.size(); i++) {
			datos[i][0]= lista.get(i).getId();
			datos[i][1]= lista.get(i).getOficina().getDescripcion();
			datos[i][2]= lista.get(i).getFuncionario().getNombre();
			datos[i][3]= lista.get(i).getPuerta().getNumeroDePuerta();
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
