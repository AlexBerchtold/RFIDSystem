package py.edu.facitec.rfidsystem.test;

import py.edu.facitec.rfidsystem.dao.InstitucionDao;
import py.edu.facitec.rfidsystem.entidad.Institucion;

public class PruebaConexion {
	
	public static void main(String[] args) {
		Institucion institucion= new Institucion();
		institucion.setId(1);
		institucion.setDescripcion("FACITEC");
		
		InstitucionDao dao = new InstitucionDao();
		dao.insertarOModificar(institucion);
		try {
			dao.ejecutar();
		} catch (Exception e) {
			dao.cancelar();
		}
	}

}
