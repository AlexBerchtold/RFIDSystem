package py.edu.facitec.rfidsystem.tablas;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.edu.facitec.rfidsystem.entidad.Puerta;

public class TablaPuerta extends AbstractTableModel {
	
	public String columnas[]={
			"Código", "Descripción", "Nro. de Puerta", "Oficina", "Estado"	};
	
	private Object[][] datos = new Object[0][columnas.length];
	
	public void setLista(List<Puerta> lista){
		datos = new Object[lista.size()][columnas.length];
		for (int i = 0; i < lista.size(); i++) {
			datos[i][0]= lista.get(i).getId();
			datos[i][1]= lista.get(i).getDescripcion();
			datos[i][2]= lista.get(i).getNumeroDePuerta();
			datos[i][3]= lista.get(i).getOficina().getDescripcion();
			if (lista.get(i).isEstado()==true) {
				datos[i][4]= "Activo";
			}else{
				datos[i][4]= "Inactivo";
			}
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
