package py.edu.facitec.rfidsystem.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Join;

import py.edu.facitec.rfidsystem.entidad.Movimiento;
import py.edu.facitec.rfidsystem.util.FechaUtil;

public class MovimientoDao extends GenericDao<Movimiento> {

	public MovimientoDao() {
		super(Movimiento.class);
	}
	
	public List<Movimiento> recuperarPorFiltro(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
			
		}
		instanciarCriteria();
		Join<Object, Object> joinPermiso=root.join("permisoAcceso");
		Join<Object, Object> joinFuncionario=joinPermiso.join("funcionario");
		criteriaQuery.where(
				builder.or(
						builder.equal(root.<Integer>get("id"), filtroId),
						builder.like(builder.lower(joinFuncionario.<String>get("nombre")),"%"+filtro.toLowerCase()+"%"))
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	public List<Movimiento> filtrarInforme(String desde, String hasta, int order, int filtro){
		instanciarCriteria();
		
		if (filtro==0) {
			Join<Object, Object> joinPermiso=root.join("permisoAcceso");
			Join<Object, Object> joinOficina=joinPermiso.join("oficina");
			criteriaQuery.where(builder.between(joinOficina.<String>get("descripcion"), desde.toLowerCase(), hasta.toLowerCase()));
		}
		if (filtro==1) {
			Join<Object, Object> joinPermiso=root.join("permisoAcceso");
			Join<Object, Object> joinFuncionario=joinPermiso.join("funcionario");
			criteriaQuery.where(builder.between(joinFuncionario.<String>get("nombre"), desde.toLowerCase(), hasta.toLowerCase()));
		}
		if (filtro==2) {
			Date d = null, h=null;
			d= FechaUtil.stringAHora(desde);
			h= FechaUtil.stringAFecha(hasta);
			criteriaQuery.where(builder.between(root.<java.sql.Date>get("hora"), d, h));
		}
		
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}

}
