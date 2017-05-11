package py.edu.facitec.rfidsystem.tablas;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.edu.facitec.rfidsystem.entidad.Movimiento;
import py.edu.facitec.rfidsystem.util.FechaUtil;

public class TablaInformeMovimiento extends AbstractTableModel {
	
	public String columnas[]={
			"Código", "Hora", "Fecha", "Funcionario", "Oficina", "Puerta Nro"};
	
	private Object[][] datos = new Object[0][columnas.length];
	
	public void setLista (List<Movimiento> lista){
		datos = new Object[lista.size()][columnas.length];
		for (int i = 0; i < lista.size(); i++) {
			datos[i][0]= lista.get(i).getId();
			datos[i][1]= FechaUtil.horaAString(lista.get(i).getHora());
			datos[i][2]= FechaUtil.fechaAString(lista.get(i).getHora());
			datos[i][3]= lista.get(i).getPermisoAcceso().getFuncionario().getNombre();
			datos[i][4]= lista.get(i).getPermisoAcceso().getOficina().getDescripcion();
			datos[i][5]= lista.get(i).getPermisoAcceso().getPuerta().getNumeroDePuerta();
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
